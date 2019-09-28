package site.flyframe.http.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.log4j.Logger;
import site.flyframe.http.context.FlyHttpContext;
import site.flyframe.http.forwarder.HttpForwarder;
import site.flyframe.http.request.FlyHttpRequest;

/**
 * @author zeng
 * @Classname HttpServerHandler
 * @Description TODO
 * @Date 2019/9/19 22:04
 */
public class FlyHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static Logger logger=Logger.getLogger(FlyHttpServerHandler.class);
    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        return super.acceptInboundMessage(msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        // 创建request和response
        FlyHttpRequest request=new FlyHttpRequest(msg);

        System.out.println(request.getSession(true).getId());
        // 调用过滤器

        // 调用拦截器

        // 获取处理该请求的方法
        HttpForwarder forwarder= FlyHttpContext.getFlyHttpContext().getForwarder();
        forwarder.forwarder(ctx,request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.info(cause.getMessage());
    }
}
