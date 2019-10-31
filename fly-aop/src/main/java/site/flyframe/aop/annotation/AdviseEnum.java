package site.flyframe.aop.annotation;

/**
 * 定义五种通知类型，
 * 1. 前置通知
 * 2. 后置通知
 * 3. 最终通知
 * 4. 异常通知
 * 5. 环绕通知
 */
public enum AdviseEnum {
    BEFORE,AFTER,AFTER_RETURN,EXCEPTION,AROUND;
}
