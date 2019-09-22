package site.flyframe.http.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import site.flyframe.http.request.FlyHttpRequest;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * @author zeng
 * @Classname HttpServerHandler
 * @Description TODO
 * @Date 2019/9/19 22:04
 */
public class FlyHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    public static void sendResponse(ChannelHandlerContext ctx, HttpRequest request, boolean keepAlive,
                                    FullHttpResponse response) {
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        if (keepAlive) {
            if (!request.protocolVersion().isKeepAliveDefault()) {
                response.headers().set(CONNECTION, KEEP_ALIVE);
            }
        } else {
            response.headers().set(CONNECTION, CLOSE);
        }
        ChannelFuture f = ctx.writeAndFlush(response);
        if (!keepAlive) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        // 获取FlyHttpRequest
        FlyHttpRequest flyHttpRequest=new FlyHttpRequest(msg);
        // 调用过滤器

        // 调用拦截器

        // 获取处理该请求的方法

        // 获取Http响应

        // 发送http响应

        FullHttpResponse response = new DefaultFullHttpResponse(msg.protocolVersion(), OK,
                Unpooled.wrappedBuffer("Hello, Welcome Wto Netty Server !!! ".getBytes()));
        response.headers()
                .set(CONTENT_TYPE, "text/plain;charset=utf-8");
        sendResponse(ctx,msg,true,response);
    }
}
