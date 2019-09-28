package site.flyframe.http.processor;

import site.flyframe.http.context.FlyHttpContext;
import site.flyframe.http.exception.ProcessorException;
import site.flyframe.http.exception.enums.ProcessorExceptionEnum;
import site.flyframe.http.request.FlyHttpRequest;
import site.flyframe.http.response.FlyHttpResponse;

/**
 * @author zeng
 * @Classname GeneralProcessor
 * @Description TODO
 * @Date 2019/9/25 17:37
 */
public  abstract class GeneralProcessor implements Processor {
    @Override
    public void init() {}

    @Override
    public FlyHttpContext getContext() {
        return FlyHttpContext.getFlyHttpContext();
    }

    @Override
    public  void processor(FlyHttpRequest request, FlyHttpResponse response) throws ProcessorException {
        switch (request.getMethod()){
            case GET:
                doGet(request,response);
                break;
            case POST:
                doPost(request,response);
                break;
            case PUT:
                doPut(request,response);
                break;
            case DELETE:
                doDelete(request,response);
                break;
            default:
                throw new ProcessorException(ProcessorExceptionEnum.CAN_NOT_PROCESSOR_FUNCTION);
        }
    }

    /**
     * 处理get方法
     * @param request http请求
     * @param response http响应
     */
    protected abstract void doGet(FlyHttpRequest request, FlyHttpResponse response);

    /**
     * 处理post方法
     * @param request http请求
     * @param response http响应
     */
    protected abstract void doPost(FlyHttpRequest request, FlyHttpResponse response);

    /**
     * 处理put方法
     * @param request http请求
     * @param response http响应
     */
    protected abstract void doPut(FlyHttpRequest request, FlyHttpResponse response);

    /**
     * 处理delete方法
     * @param request http请求
     * @param response http响应
     */
    protected abstract void doDelete(FlyHttpRequest request, FlyHttpResponse response);

    /**
     * http服务器关闭时调用该方法
     */
    @Override
    public void destroy() {}
}
