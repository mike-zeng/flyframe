package site.flyframe.http.forwarder;

import io.netty.channel.ChannelHandlerContext;
import site.flyframe.http.request.FlyHttpRequest;
import site.flyframe.http.response.FlyHttpResponse;
import site.flyframe.http.response.FlyHttpResponseSender;

/**
 * @author zeng
 * @Classname BaseHttpForwarder
 * @Description TODO
 * @Date 2019/9/25 16:54
 */
public abstract class BaseHttpForwarder implements HttpForwarder{

    @Override
    public void forwarder(ChannelHandlerContext ctx, FlyHttpRequest request) {
        FlyHttpResponse response=new FlyHttpResponse();
        try {
            doForwarder(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish(ctx,request,response);
    }

    /**
     * 由子类实现具体处理的过程
     * @param request http请求
     * @param response http响应
     * @throws Exception 处理失败
     */
    protected abstract void doForwarder(FlyHttpRequest request, FlyHttpResponse response) throws Exception;

    private void finish(ChannelHandlerContext ctx,FlyHttpRequest request, FlyHttpResponse response){
        FlyHttpResponseSender.sendResponse(ctx,request,true,response);
    }
}
