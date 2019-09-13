package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zeng
 * @Classname Autowired
 * @Description TODO
 * @Date 2019/9/12 10:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Autowired {
    AutowiredEnum type() default AutowiredEnum.BY_TYPE;
    String beanName()default "";
}
