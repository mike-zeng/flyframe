package site.flyframe.ioc.meta;

import lombok.Data;

import java.util.List;

/**
 * @author zeng
 * @Classname BeanDefinition
 * @Description 描述容器中bean的基本信息
 * @Date 2019/9/10 22:26
 */
@Data
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

    /**
     * bean创建时调用
     */
    private String initMethod;

    /**
     * 容器关闭时调用
     */
    private String destroyMethod;
}
