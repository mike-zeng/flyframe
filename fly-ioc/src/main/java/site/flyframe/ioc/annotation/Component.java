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
    String beanName() default "";
    boolean singleton() default true;
    boolean lazy() default true;
}
