package site.flyframe.aop.proxy;

import java.lang.reflect.Method;
import java.util.List;

public interface ProxyClassGenerateStrategy {
    Object generateProxyClass(Object target, List<Method> methods);
}
