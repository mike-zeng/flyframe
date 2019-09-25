package site.flyframe.http.response;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import site.flyframe.http.request.FlyHttpRequest;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;

/**
 * @author zeng
 * @Classname FlyHttpResponseSender
 * @Description 发送http响应到客户端
 * @Date 2019/9/25 16:13
 */
public class FlyHttpResponseSender {
    private static final String CONTENT_LENGTH="content-length";

    public static void sendResponse(ChannelHandlerContext ctx, FlyHttpRequest request, boolean keepAlive,
                                    FlyHttpResponse response) {
        FullHttpResponse fullHttpResponse=response.getResponse();
        fullHttpResponse.headers().set(CONTENT_LENGTH, fullHttpResponse.content().readableBytes());
        if (keepAlive) {
            if (!request.isKeepAlive()) {
                fullHttpResponse.headers().set(CONNECTION, KEEP_ALIVE);
            }
        } else {
            fullHttpResponse.headers().set(CONNECTION, CLOSE);
        }
        ChannelFuture f = ctx.writeAndFlush(fullHttpResponse);
        if (!keepAlive) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
