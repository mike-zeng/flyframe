package site.flyframe.aop.annotation;

import site.flyframe.ioc.annotation.Bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切点表达式
 */
@Bean
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PointCut {
    /**
     * 使用字符串表示的匹配表达式
     * @return 表达式
     */
    String match();
}
