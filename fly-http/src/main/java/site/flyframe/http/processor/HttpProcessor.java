package site.flyframe.http.processor;

import site.flyframe.http.request.FlyHttpRequest;
import site.flyframe.http.response.FlyHttpResponse;

/**
 * @author zeng
 * @Classname HttpProcessor
 * @Description TODO
 * @Date 2019/9/25 19:42
 */
public abstract class HttpProcessor extends GeneralProcessor{
    /**
     * 处理get请求
     * @param request http请求
     * @param response http响应
     */
    @Override
    protected abstract void doGet(FlyHttpRequest request, FlyHttpResponse response);

    /**
     * 处理post请求
     * @param request http请求
     * @param response http响应
     */
    @Override
    protected abstract void doPost(FlyHttpRequest request, FlyHttpResponse response);

    @Override
    protected void doPut(FlyHttpRequest request, FlyHttpResponse response){}

    @Override
    protected void doDelete(FlyHttpRequest request, FlyHttpResponse response) {}
}
