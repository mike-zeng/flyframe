package site.flyframe.ioc.core;

/**
 *
 */
public interface BeanPostProcessor {
    Object beforeInitMethodInvoke(Object bean,String beanName)throws Exception;
    Object afterInitMethodInvoke(Object bean,String beanName)throws Exception;
}
