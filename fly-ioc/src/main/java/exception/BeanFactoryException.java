package exception;

import exception.enums.BeanFactoryExceptionEnum;

/**
 * @author zeng
 * @Classname BeanFactoryException
 * @Description TODO
 * @Date 2019/9/11 20:44
 */
public class BeanFactoryException extends Exception {
    public BeanFactoryException(BeanFactoryExceptionEnum exceptionEnum){
        super(exceptionEnum.toString());
    }

}
