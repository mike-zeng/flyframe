package site.flyframe.aop.proxy;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Method;
import java.util.List;

public class CglibProxyClassGenerateStrategy implements ProxyClassGenerateStrategy {

    @Override
    public Object generateProxyClass(Object target, final List<Method> methods) {
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(target.getClass());
        // MethodInterceptor
        CglibProxyMethodInterceptor cglibProxyMethodInterceptor = new CglibProxyMethodInterceptor();
        enhancer.setCallback(cglibProxyMethodInterceptor);
        return enhancer.create();
    }

}
