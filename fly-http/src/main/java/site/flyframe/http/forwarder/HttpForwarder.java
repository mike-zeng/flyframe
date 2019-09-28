package site.flyframe.http.forwarder;

import io.netty.channel.ChannelHandlerContext;
import site.flyframe.http.request.FlyHttpRequest;

/**
 * @author zeng
 * @Classname HttpForwarder
 * @Description Http转发器，用于将http请求和http响应转发到具体的处理方法
 * @Date 2019/9/25 16:47
 */
public interface HttpForwarder {

    /**
     * 将请求转发给处理对象
     * @param ctx 通道
     * @param request http请求
     */
    void forwarder(ChannelHandlerContext ctx, FlyHttpRequest request);
}
