package exception.enums;

/**
 * @author zeng
 * @Classname BeanFactoryExceptionEnum
 * @Description TODO
 * @Date 2019/9/15 17:03
 */
public enum BeanFactoryExceptionEnum {
    /**
     * BeanDefinition解析失败
     */
    BEAN_DEFINITION_PARSER_FAILED("BeanDefinition解析失败");
    private String msg;

    BeanFactoryExceptionEnum(String msg){
        this.msg=msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
