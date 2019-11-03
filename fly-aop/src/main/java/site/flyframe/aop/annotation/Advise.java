package site.flyframe.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明一个方法为通知
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Advise {
    /**
     * 枚举类表示的通知类型
     * @return 通知类型
     */
    AdviseEnum type();

    /**
     * @return  包名表达式，可以指定包名方式
     * 1. 指定明确的包名 如 site.flyframe 只会匹配site.flyframe包下的类，不包括子包
     * 2. + 包含子包 如 site.flyframe 匹配site.flyframe包及其子包下的所有类
     * 3. regp:正则表达式 利用正则表达式匹配包
     * 4. * 不限定包 默认值
     */
    String packageName() default "*";

    /**
     * @return  类名匹配表达式，可以指定类名匹配方式
     * 1. 指定明确的类名 如Offer会匹配所有类简答名称未Offer的类，不包括子类
     * 2. + 包含子类
     * 3. regp:正则表达式 利用正则表达式匹配类名
     * 4. * 不限定类 默认值
     */
    String className() default "*";

    /**
     * @return  方法访问权限匹配表达式，可以指定方法返回值匹配方式
     * 1. public
     * 2. private
     * 3. protect
     * 4. package
     * 5. default
     * 6. * 不限定访问类型
     */
    String accessType()default "*";

    /**
     * @return  方法返回值匹配表达式，可以指定方法返回值匹配方式
     * 1. 指定明确的返回值类型
     * 2. + 包含子类
     * 3. regp:正则表达式 利用正则表达式匹配方法返回值类型
     * 4. * 不限定返回类型 默认值
     */
    String retType() default "*";

    /**
     * @return  方法名匹配表达式，可以指定方法名匹配方式
     * 1. 指定明确的方法名
     * 2. + 以该字符串开头的方法名
     * 3. regp:正则表达式 利用正则表达式匹配方法返回值类型
     * 4. * 不限定方法名 默认值
     */
    String methodName()default "*";

    /**
     * @return  方法名匹配表达式，可以指定方法名匹配方式
     * 1. 指定明确的方法名
     * 2. + 以该字符串开头的方法名
     * 3. regp:正则表达式 利用正则表达式匹配方法返回值类型
     * 4. * 不限定方法名 默认值
     */
    String args()default "*";
}
