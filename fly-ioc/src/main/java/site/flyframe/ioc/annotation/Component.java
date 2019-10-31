package site.flyframe.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zeng
 * @Classname Component
 * @Description 可以被IOC扫描的组件
 * @Date 2019/9/10 22:03
 */
@Bean
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    /**
     * @return bean的名称
     */
    String beanName() default "";

    /**
     * @return 是否单例
     */
    boolean singleton() default true;

    /**
     * @return 是否懒加载
     */
    boolean lazy() default true;

    /**
     * @return 初始化方法
     */
    String initMethod() default "";

    /**
     * @return 销毁方法
     */
    String destroy() default "";
}
