package site.flyframe.aop.proxy;

import site.flyframe.aop.annotation.AdviseEnum;

import java.lang.reflect.Method;

public class CglibProxyGeneratorAdaptor implements ProxyGenerator {
    public Object generate(Object target, Method method, AdviseEnum type) {
        return null;
    }

    public Class generateClass(Object target, Method method, AdviseEnum type) {
        return null;
    }
}
