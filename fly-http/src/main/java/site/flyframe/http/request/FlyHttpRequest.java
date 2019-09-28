package site.flyframe.http.request;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import site.flyframe.http.context.FlyHttpContext;
import site.flyframe.http.request.conversation.Cookie;
import site.flyframe.http.request.conversation.Session;
import site.flyframe.http.server.parser.HttpParser;
import site.flyframe.http.server.parser.HttpRequestParser;
import site.flyframe.http.utils.HttpUtil;

import java.util.*;

/**
 * @author zeng
 * @Classname HttpRequest
 * @Description Netty实现的HttpRequest并不方便用户的使用，因此重新定义了一个HttpRequest
 *             使用者如果需要操作Request对象可以直接使用这个类
 * @Date 2019/9/21 22:26
 */
public class FlyHttpRequest {

    /**
     * netty提供的HttpRequest
     */
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

    /**
     * 是否保持KeepLive
     */
    boolean keepAlive=true;

    public FlyHttpRequest(){}

    public FlyHttpRequest(FullHttpRequest request){
        this.request=request;
        init();
    }

    /**
     * 初始化HttpRequest的信息
     */
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
        this.keepAlive=request.protocolVersion().isKeepAliveDefault();
        this.context= FlyHttpContext.getFlyHttpContext();
        this.attributeMap= new HashMap<>(16);
        this.cookies= new ArrayList<>();
        this.url=request.uri().split("\\?")[0];

        switch (request.method().name()){
            case "POST":method=FlyHttpMethod.POST;break;
            case "GET":method=FlyHttpMethod.GET;break;
            case "DELETE":method=FlyHttpMethod.DELETE;break;
            case "PUT":method=FlyHttpMethod.PUT;break;
            default:method=FlyHttpMethod.DEFAULT;
        }
        methodName=request.method().name();
    }

    /**
     * 初始化Cookie信息
     */
    private void cookiesInit(){
        if (request.headers()!=null) {
            ServerCookieDecoder cookieDecoder= ServerCookieDecoder.STRICT;
            String cookiesStr=request.headers().get("Cookie");

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

    /**
     * 获取Session，如果不存在，不创建一个新的Session
     * @return
     */
    public Session getSession() {
        return getSession(false);
    }

    /**
     * 获取Session，根据参数决定是否创建Session
     * @param isCreate 是否创建
     * @return Session
     */
    public Session getSession(boolean isCreate){
        if (session!=null||!isCreate){
            return session;
        }
        session=createSession();
        context.putSession(session);
        return session;
    }

    /**
     * 创建一个Session,但是该Session不会被存放在全局上下问环境
     * @return Session
     */
    private Session createSession(){
        // 随机生成一个SessionId
        String sessionId;
        do{
            sessionId=HttpUtil.produceSessionId();
        }while (context.getSession(sessionId)!=null);
        // 创建session并保存
        session=new Session();
        session.setId(sessionId);
        return session;
    }

    public String getParameter(String name){
        Object o = parameterMap.get(name);
        if (o instanceof String){
            return ((String) o);
        }else if (o instanceof List){
            return ((List) o).get(0).toString();
        }
        return null;
    }

    public FlyHttpMethod getMethod() {
        return method;
    }

    public void setMethod(FlyHttpMethod method) {
        this.method = method;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }
}
