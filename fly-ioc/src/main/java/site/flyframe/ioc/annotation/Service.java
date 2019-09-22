package site.flyframe.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zeng
 * @Classname Service
 * @Description TODO
 * @Date 2019/9/10 22:03
 */
@Bean
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {
    String beanName() default "";
    boolean singleton() default true;
    boolean lazy() default true;
}
