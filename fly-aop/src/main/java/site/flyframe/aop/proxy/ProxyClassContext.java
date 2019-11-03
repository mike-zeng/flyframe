package site.flyframe.aop.proxy;

import site.flyframe.aop.util.AspectObjectMatcher;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 生成代理类
 */
public class ProxyClassContext {
    private ProxyClassGenerateStrategy strategy;

    private AspectObjectMatcher matcher=new AspectObjectMatcher();

    public void setStrategy(ProxyClassGenerateStrategy strategy) {
        this.strategy = strategy;
    }

    public Object getProxy(Object bean){
        Method[] methods = bean.getClass().getDeclaredMethods();
        return strategy.generateProxyClass(bean, Arrays.asList(methods));
    }

}
