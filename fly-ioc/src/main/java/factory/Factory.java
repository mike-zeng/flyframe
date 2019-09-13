package factory;

/**
 * @author zeng
 * @Classname Factory
 * @Description TODO
 * @Date 2019/9/10 22:24
 */
public interface Factory {
    /**
     * 从容器中获取bean实例
     * @param beanName beanName
     * @return bean实例
     */
    Object getBean(String beanName);

    /**
     * 从容器中获取bean实例
     * @param tClass bean类型
     * @param <T> bean的实际类型
     * @return bean实例
     */
    <T> T getBean(Class<T> tClass);

    /**
     * 销毁容器
     */
    void destroy();

    /**
     * 容器是否被销毁
     * @return 容器是否被销毁
     */
    boolean isDestroy();

}
