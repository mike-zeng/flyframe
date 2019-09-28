package site.flyframe.http.response;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import site.flyframe.http.request.conversation.Cookie;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.SET_COOKIE;

/**
 * @author zeng
 * @Classname HttpResponse
 * @Description TODO
 * @Date 2019/9/21 22:26
 */
public class FlyHttpResponse {


    public static final String HTTP_VERSION_1_0="HTTP/1.0";
    public static final String HTTP_VERSION_1_1="HTTP/1.1";

    private HttpHeaders headers = new DefaultHttpHeaders();

    private ResponseContent content;


    private HttpVersion version=HttpVersion.HTTP_1_1;

    private HttpResponseStatus status=HttpResponseStatus.OK;

    public void setContent(ResponseContent content){
        this.content=content;
    }

    /**
     * 设置响应版本
     * @param version 版本号
     */
    void setHttpVersion(String version){
        this.version=HttpVersion.valueOf(version);
    }

    public void setStatus(int status){
        this.status=HttpResponseStatus.valueOf(status);
    }

    public void setHeader(String name,Object value){
        headers.set(name,value);
    }

    public void addCookies(List<Cookie> cookies){
        for (Cookie cookie : cookies) {
            addCookie(cookie);
        }
    }

    public void addCookie(Cookie cookie){
        headers.set(SET_COOKIE,cookie.name()+"="+cookie.value());
    }


    /**
     * 之前的方法仅仅缓存了参数信息，并未构造一个真正可被发送的响应
     * 该方法将实现响应的构造
     * @return 请求
     */
    FullHttpResponse getResponse(){
        FullHttpResponse response;
        if (content==null){
            response = new DefaultFullHttpResponse(version,status);
        }else {
            response=new DefaultFullHttpResponse(version,status, Unpooled.wrappedBuffer(content.getBytes()));
        }
        response.headers().set(headers);
        return response;
    }

}
