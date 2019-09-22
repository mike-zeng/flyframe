package site.flyframe.ioc.exception;

import site.flyframe.ioc.exception.enums.ScannerExceptionEnum;

/**
 * @author zeng
 * @Classname ScannerException
 * @Description TODO
 * @Date 2019/9/11 20:43
 */
public class ScannerException extends Exception {
    public ScannerException(ScannerExceptionEnum exceptionEnum){
        super(exceptionEnum.toString());
    }
}
