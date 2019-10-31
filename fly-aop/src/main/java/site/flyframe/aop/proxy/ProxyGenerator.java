package site.flyframe.aop.proxy;

import site.flyframe.aop.annotation.AdviseEnum;

import java.lang.reflect.Method;

/**
 * 用来生成一个代理对象
 */
public interface ProxyGenerator {
    /**
     * 根据参数，返回代理对象
     * @param target 目标对象
     * @param method 方法
     * @param type 通知的类型
     * @return 代理对象
     */
    Object generate(Object target, Method method, AdviseEnum type);

    /**
     * 根据参数，返回代理对象的类型
     * @param target 目标对象
     * @param method 方法
     * @param type 通知的类型
     * @return 代理对象
     */
    Class generateClass(Object target, Method method, AdviseEnum type);
}
