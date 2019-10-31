package site.flyframe.aop.annotation;

/**
 * 声明一个方法为通知
 */
public @interface Advise {
    /**
     * 枚举类表示的通知类型
     * @return 通知类型
     */
    AdviseEnum type();

    /**
     * 使用字符串表示的匹配表达式
     * @return 表达式
     */
    String match();
}
