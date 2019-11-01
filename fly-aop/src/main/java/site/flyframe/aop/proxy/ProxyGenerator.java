package site.flyframe.aop.proxy;

import site.flyframe.aop.annotation.AdviseEnum;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * 用来生成一个代理对象
 */
public interface ProxyGenerator {

    /**
     * 生成代理对象
     * @param target 目标类
     * @param methods 目标方法
     * @param adviseMethod 通知方法
     * @param type 通知类型
     * @return 代理对象
     */
    Object generate(Class target, List<Method> methods, Set<Method> adviseMethod, AdviseEnum type);


    /**
     * 生成代理对象
     * @param target 目标类
     * @param methods 目标方法
     * @param adviseMethod 通知方法
     * @param type 通知类型
     * @return 代理类
     */
    Class generateClass(Class target, Set<Method> methods, Method adviseMethod, AdviseEnum type);
}
