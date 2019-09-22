package site.flyframe.http.filter;

import site.flyframe.http.request.FlyHttpRequest;
import site.flyframe.http.server.FlyHttpResponse;

/**
 * @author zeng
 * @Classname Filter
 * @Description TODO
 * @Date 2019/9/22 12:08
 */
public interface Filter {
    /**
     * 调用Filter链
     * @param request 客户端http请求
     * @param response 客户端http响应
     * @param filterChain 过滤器链
     * @throws Exception 异常
     */
    void doFilter(FlyHttpRequest request, FlyHttpResponse response,FilterChain filterChain)throws Exception;
}
