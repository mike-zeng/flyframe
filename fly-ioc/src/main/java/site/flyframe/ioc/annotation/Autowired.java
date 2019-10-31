package site.flyframe.ioc.annotation;

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
    /**
     * 自动注入类型，默认按照类型注入
     * @return 注入类型
     */
    AutowiredEnum type() default AutowiredEnum.BY_TYPE;

    /**
     * 如果注入类型为BY_NAME,则该属性生效，表示参考的bean名称
     * @return bean名称
     */
    String beanName()default "";
}
