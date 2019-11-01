package site.flyframe.aop.proxy;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import site.flyframe.aop.annotation.AdviseEnum;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class CglibProxyGeneratorAdaptor implements ProxyGenerator {


    public Object generate(Class target, List<Method> methods, Set<Method> adviseMethod, AdviseEnum type) {
        return null;
    }

    public Class generateClass(Class target, final Set<Method> targetMethods, Method adviseMethod, AdviseEnum type) {
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(target);
        // 过滤方法
        enhancer.setCallbackFilter(new CallbackFilter() {
            public int accept(Method method) {
                if (!targetMethods.contains(method)){
                    return 1;
                }
                return 0;
            }
        });
        MethodInterceptor methodInterceptor=createMethodInterceptor(target,targetMethods,adviseMethod,type);
        enhancer.setCallback(methodInterceptor);
        return enhancer.createClass();
    }

    private MethodInterceptor createMethodInterceptor(Class target, Set<Method> targetMethods, final Method adviseMethod, AdviseEnum type) {
        MethodInterceptor methodInterceptor=null;
        switch (type){
            case BEFORE:
                methodInterceptor=new MethodInterceptor() {
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        Object invoke = method.invoke(o, objects);
                        return null;
                    }
                };
                break;
        }
        return methodInterceptor;
    }



}
