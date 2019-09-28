package site.flyframe.http.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zeng
 * @Classname ProcessorPath
 * @Description http映射，支持多个路径匹配一个类
 * @Date 2019/9/25 20:01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProcessorPath {
    String path();
}
