package exception;

import exception.enums.InjectionExceptionEnum;

/**
 * @author zeng
 * @Classname InjectionException
 * @Description 发生在注入时的异常
 * @Date 2019/9/11 20:45
 */
public class InjectionException extends Exception {
    public InjectionException(InjectionExceptionEnum exceptionEnum){
        super(exceptionEnum.toString());
    }

}
