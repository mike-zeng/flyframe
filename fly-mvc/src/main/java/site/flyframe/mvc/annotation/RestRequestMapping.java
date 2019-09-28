package site.flyframe.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zeng
 * @Classname RestRequestMapping
 * @Description 路由映射
 * @Date 2019/9/19 22:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface RestRequestMapping {
    String method() default "ALL";
    String path();
}
