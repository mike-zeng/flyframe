package site.flyframe.ioc.cache;

/**
 * @author zeng
 * @Classname Cache
 * @Description
 * @Date 2019/9/10 22:06
 */
public interface Cache {
    /**
     * 从缓冲中根据BeanName获取Bean的实例
     * @param beanName bean的名称
     * @return bean实例
     */
    Object getBeanForBeanName(String beanName);

    /**
     * 从缓存池中根据Class类型获取Bean的实例
     * @param tClass Class对象
     * @param <T> bean的类型
     * @return bean实例
     */
    <T> T getBeanForBeanClass(Class<T> tClass);


    void putBean(Object o);

    void putBean(String beanName,Object o);
}
