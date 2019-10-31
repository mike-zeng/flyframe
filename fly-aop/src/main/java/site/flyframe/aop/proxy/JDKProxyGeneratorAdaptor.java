package site.flyframe.aop.proxy;

import site.flyframe.aop.annotation.AdviseEnum;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 适配器
 */
public class JDKProxyGeneratorAdaptor implements ProxyGenerator {
    public JDKProxyGeneratorAdaptor(){

    }
    public Object generate(Object target, Method method, AdviseEnum type) {
        Object o = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                return null;
            }
        });
        return o;
    }

    public Class generateClass(Object target, Method method, AdviseEnum type) {
        return null;
    }
}
