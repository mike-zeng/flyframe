package site.flyframe.ioc.core;

/**
 * bean的后置处理器，在bean的init方法调用前后会执行该方法
 */
public interface BeanPostProcessor {
    /**
     * init方法调用前执行
     * @param bean bean对象
     * @param beanName bean的名称
     * @return bean对象
     * @throws Exception 异常
     */
    Object beforeInitMethodInvoke(Object bean,String beanName)throws Exception;
    /**
     * init方法调用前执行
     * @param bean bean对象
     * @param beanName bean的名称
     * @return bean对象
     * @throws Exception 异常
     */
    Object afterInitMethodInvoke(Object bean,String beanName)throws Exception;
}
