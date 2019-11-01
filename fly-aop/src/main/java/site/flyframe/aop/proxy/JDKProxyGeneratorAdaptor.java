package site.flyframe.aop.proxy;

import site.flyframe.aop.annotation.AdviseEnum;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * 适配器
 */
public class JDKProxyGeneratorAdaptor implements ProxyGenerator {

    public Object generate(Class target, List<Method> methods, Set<Method> adviseMethod, AdviseEnum type) {
        return null;
    }

    public Class generateClass(Class target, Set<Method> methods, Method adviseMethod, AdviseEnum type) {
        return null;
    }
}
