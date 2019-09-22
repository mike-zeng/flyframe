package site.flyframe.http.request;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import site.flyframe.http.server.parser.HttpParser;
import site.flyframe.http.server.parser.HttpRequestParser;
import site.flyframe.http.utils.HttpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zeng
 * @Classname HttpRequest
 * @Description Netty实现的HttpRequest并不方便用户的使用，因此重新定义了一个HttpRequest
 *             使用者如果需要操作Request对象可以直接使用这个类
 * @Date 2019/9/21 22:26
 */
public class FlyHttpRequest {

    private FullHttpRequest request=null;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 请求方法
     */
    private FlyHttpMethod method;

    /**
     * 请求方法名
     */
    private String methodName;

    /**
     * 请求头信息
     */
    private Map<String,String> heads;

    /**
     * Cookie信息
     */
    List<Cookie> cookies;

    /**
     * Session信息
     */
    Session session;

    /**
     * Http上下文
     */
    FlyHttpContext context;

    /**
     * 请求参数
     */
    HashMap<String,Object> parameterMap=new HashMap<String, Object>();

    /**
     * 属性
     */
    HashMap<String,Object> attributeMap=new HashMap<String, Object>();

    public FlyHttpRequest(){}

    public FlyHttpRequest(FullHttpRequest request){
        this.request=request;
        init();
    }

    private void init(){
        // 1. 初始化基础信息
        basicInfoInit();
        // 2. 初始化Cookie信息
        cookiesInit();
        // 3. 初始化请求头信息
        headerInit();
        // 4. 处理参数
        parameterInit();
    }

    /**
     * 基础的信息初始化
     */
    private void basicInfoInit(){
        this.context= FlyHttpContext.getFlyHttpContext();
        this.attributeMap=new HashMap<String, Object>(16);
        url=request.uri();
        methodName=request.method().name();
    }

    /**
     * 初始化Cookie信息
     */
    private void cookiesInit(){
        if (request.headers()!=null) {
            ServerCookieDecoder cookieDecoder= ServerCookieDecoder.STRICT;
            String cookiesStr=request.headers().get("Cookies");
            if (cookiesStr!=null) {
                Set<io.netty.handler.codec.http.cookie.Cookie> cookies = cookieDecoder.decode(cookiesStr);
                for (io.netty.handler.codec.http.cookie.Cookie cookie : cookies) {
                    Cookie flyCookie=new Cookie(cookie);
                    // 处理session
                    if (Session.COOKIE_NAME.equals(flyCookie.name())){
                        this.session= context.getSession(flyCookie.value());
                    }
                    this.cookies.add(flyCookie);
                }
            }
        }
    }

    /**
     * 请求头信息初始化
     */
    private void headerInit(){
        this.heads=new HashMap<String, String>(16);
        if (request.headers()!=null) {
            for (String name : request.headers().names()) {
                this.heads.put(name,request.headers().get(name));
            }
        }
    }

    /**
     * 参数信息初始化
     */
    private void parameterInit(){
        HttpParser parser=new HttpRequestParser(request);
        Object ret = parser.parser();
        if (ret != null){
            Map parameters = (Map) ret;
            parameterMap.putAll(parameters);
        }
    }

    /**
     * 返回请求的url
     * @return url字符串
     */
    public String getUrl() {
        return url;
    }

    /**
     * 返回请求方法名称
     * @return 方法名称
     */
    public String getMethodName() {
        return methodName;
    }

    public Map<String, String> getHeads() {
        return heads;
    }

    public Session getSession() {
        return getSession(false);
    }

    public Session getSession(boolean isCreate){
        if (!isCreate){
            return session;
        }
        // 随机生成一个SessionId
        String sessionId;
        while (context.getSession(sessionId= HttpUtil.produceSessionId())!=null){ }

        // 创建session并保存
        session=new Session();
        session.setId(sessionId);
        context.putSession(session);
        return session;
    }
}
