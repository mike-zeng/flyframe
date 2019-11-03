package site.flyframe.aop.proxy;

import lombok.Data;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 最小执行单元
 */
@Data
public class InvokeUnit{
    private Object target;
    private Method method;
    private MethodProxy methodProxy;
    private Object[] args;
    Object invoke() throws Throwable {
        if (methodProxy!=null){
            return methodProxy.invokeSuper(target,args);
        }
        return method.invoke(target,args);
    }
}