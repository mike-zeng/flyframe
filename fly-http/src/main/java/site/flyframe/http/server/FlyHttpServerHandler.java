package site.flyframe.http.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import site.flyframe.http.request.FlyHttpRequest;
import site.flyframe.http.response.FlyHttpResponse;
import site.flyframe.http.response.FlyHttpResponseSender;

/**
 * @author zeng
 * @Classname HttpServerHandler
 * @Description TODO
 * @Date 2019/9/19 22:04
 */
public class FlyHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        // 创建request和response
        FlyHttpRequest request=new FlyHttpRequest(msg);
        FlyHttpResponse response=new FlyHttpResponse();

        // 调用过滤器

        // 调用拦截器

        // 获取处理该请求的方法

        // 发送响应给客户端
        FlyHttpResponseSender.sendResponse(ctx,request,true,response);
    }
}
