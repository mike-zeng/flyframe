package site.flyframe.ioc.meta;

import java.util.List;

/**
 * @author zeng
 * @Classname BeanDefinition
 * @Description 描述容器中bean的基本信息
 * @Date 2019/9/10 22:26
 */
public class BeanDefinition {
    /**
     * bean的名称
     */
    private String beanName;

    /**
     * bean的类型
     */
    private Class<?> beanClass;

    /**
     * 是否懒加载
     */
    private boolean lazy;

    /**
     * 是否是单例
     */
    private boolean singleton=true;

    /**
     * 依赖信息
     */
    private List<Depend> depends;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public List<Depend> getDepends() {
        return depends;
    }

    public void setDepends(List<Depend> depends) {
        this.depends = depends;
    }
}
