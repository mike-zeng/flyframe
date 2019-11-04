package site.flyframe.aop.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import site.flyframe.aop.annotation.Advise;
import site.flyframe.aop.annotation.AdviseEnum;
import site.flyframe.aop.core.AopBeanFactoryProcessor;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class CglibProxyMethodInterceptor implements MethodInterceptor {


    CglibProxyMethodInterceptor(){

    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        ProxyMethod proxyMethod = getProxyMethod(o,objects,methodProxy);
        return proxyMethod.process();
    }

    /**
     *
     * @param target 代理对象
     * @param args 参数
     * @param methodProxy 被代理的方法
     * @return 方法的包装对象
     */
    private ProxyMethod getProxyMethod(Object target,Object[] args,MethodProxy methodProxy){
        // 最底层方法
        InvokeUnit invokeUnit = new InvokeUnit();
        invokeUnit.setMethodProxy(methodProxy);
        invokeUnit.setTarget(target);
        invokeUnit.setArgs(args);
        ProxyMethod proxyMethod=new ProxyMethod();
        proxyMethod.setTarget(invokeUnit);

        List<Method> methodList=AopBeanFactoryProcessor.getAspectMethodList();
        for (Method adviseMethod : methodList) {
            // 检测是否需要代理该方法
            if (isAfter(adviseMethod)){
                proxyMethod.setAfter(wrapInvokeUnit(adviseMethod));
            }else if (isBefore(adviseMethod)){
                proxyMethod.setBefore(wrapInvokeUnit(adviseMethod));
            }else if (isAfterReturn(adviseMethod)){
                proxyMethod.setAfterReturn(wrapInvokeUnit(adviseMethod));
            }else if (isAfterThrow(adviseMethod)){
                proxyMethod.setAfterThrow(wrapInvokeUnit(adviseMethod));
            }else if (isAround(adviseMethod)){
                // 判断环绕方法是否符合要求
                Class<?>[] parameterTypes = adviseMethod.getParameterTypes();
                if (parameterTypes.length==1&&parameterTypes[0]==ProxyMethod.class){
                    InvokeUnit around = new InvokeUnit();
                    around.setTarget(AopBeanFactoryProcessor.getAspectMethodObjectHashMap().get(adviseMethod));
                    around.setMethod(adviseMethod);
                    Object[] arg={proxyMethod};
                    around.setArgs(arg);
                    ProxyMethod temp = new ProxyMethod();
                    temp.setTarget(around);
                    proxyMethod=temp;
                }else {
                    log.warn("around method may be error,need right args");
                }
            }
        }
        return proxyMethod;
    }

    private InvokeUnit wrapInvokeUnit(Method method){
        InvokeUnit invokeUnit = new InvokeUnit();
        invokeUnit.setMethod(method);
        invokeUnit.setTarget(AopBeanFactoryProcessor.getAspectMethodObjectHashMap().get(method));
        return invokeUnit;
    }

    private AdviseEnum getAdviseEnum(Method method){
        return method.getAnnotation(Advise.class).type();
    }
    private boolean isBefore(Method method){
        return getAdviseEnum(method)==AdviseEnum.BEFORE;
    }

    private boolean isAfter(Method method){
        return getAdviseEnum(method)==AdviseEnum.AFTER;
    }

    private boolean isAfterReturn(Method method){
        return getAdviseEnum(method)==AdviseEnum.AFTER_RETURN;
    }

    private boolean isAround(Method method){
        return getAdviseEnum(method)==AdviseEnum.AROUND;
    }

    private boolean isAfterThrow(Method method){
        return getAdviseEnum(method)==AdviseEnum.AFTER_THROW;
    }
}
