package site.flyframe.aop.annotation;

/**
 * 切点表达式
 */
public @interface PointCut {
    /**
     * 使用字符串表示的匹配表达式
     * @return 表达式
     */
    String match();
}
