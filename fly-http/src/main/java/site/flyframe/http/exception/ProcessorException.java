package site.flyframe.http.exception;

import site.flyframe.http.exception.enums.ProcessorExceptionEnum;

/**
 * @author zeng
 * @Classname ProcessorException
 * @Description TODO
 * @Date 2019/9/25 19:35
 */
public class ProcessorException extends Exception {
    public ProcessorException(ProcessorExceptionEnum exceptionEnum){
        super(exceptionEnum.toString());
    }
}
