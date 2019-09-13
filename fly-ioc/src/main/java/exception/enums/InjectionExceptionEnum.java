package exception.enums;

/**
 * @author zeng
 * @Classname InjectionExceptionEnum
 * @Description TODO
 * @Date 2019/9/12 14:44
 */
public enum InjectionExceptionEnum {
    /**
     * set注入失败
     */
    SET_INJECTION_FAILED("set注入失败"),

    VALUE_INJECTION_FAILED("value注入失败"),

    AUTOWIRED_INJECTION_FAILED("autowired注入失败");
    private String msg;

    InjectionExceptionEnum(String msg){
        this.msg=msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
