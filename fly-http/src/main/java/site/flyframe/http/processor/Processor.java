package site.flyframe.http.processor;

import site.flyframe.http.context.FlyHttpContext;
import site.flyframe.http.request.FlyHttpRequest;
import site.flyframe.http.response.FlyHttpResponse;

/**
 * @author zeng
 * @Classname Processor
 * @Description TODO
 * @Date 2019/9/25 17:33
 */
public interface Processor {
    /**
     * 初始化方法
     */
    void init();

    /**
     * 获取上下文环境
     * @return 上下文环境
     */
    FlyHttpContext getContext();

    /**
     * 处理方法
     * @param request http请求
     * @param response http响应
     * @throws Exception 处理异常
     */
    void processor(FlyHttpRequest request, FlyHttpResponse response)throws Exception;

    /**
     * 销毁方法
     */
    void destroy();
}
