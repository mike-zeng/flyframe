package site.flyframe.aop.proxy;

import lombok.Data;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 最小执行单元
 */
@Data
class InvokeUnit{
    private Object target;
    private Method method;
    private MethodProxy methodProxy;
    private Object[] args;
    private Object retValue;
    void invoke() throws Throwable {
        if (methodProxy!=null){
            retValue=methodProxy.invokeSuper(target,args);
        }
        if (method==null){
            return;
        }
        method.invoke(target, args);
    }
}