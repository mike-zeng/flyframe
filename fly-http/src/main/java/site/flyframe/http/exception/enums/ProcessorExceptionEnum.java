package site.flyframe.http.exception.enums;

/**
 * @author zeng
 * @Classname ProcessorExceptionEnum
 * @Description TODO
 * @Date 2019/9/25 19:39
 */
public enum  ProcessorExceptionEnum {
    /**
     * 无法处理的方法
     */
    CAN_NOT_PROCESSOR_FUNCTION("无法处理该方法，服务器只支持get,post.put,delete方法");
    String msg;
    ProcessorExceptionEnum(String msg){
        this.msg=msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
