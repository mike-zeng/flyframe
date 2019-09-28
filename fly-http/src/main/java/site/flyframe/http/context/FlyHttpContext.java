package site.flyframe.http.context;

import site.flyframe.http.forwarder.DefaultHttpForwarder;
import site.flyframe.http.forwarder.HttpForwarder;
import site.flyframe.http.request.conversation.Session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zeng
 * @Classname HttpContext
 * @Description TODO
 * @Date 2019/9/22 12:10
 */
public class FlyHttpContext {
    private ConcurrentHashMap<String, Session> sessionHashMap=new ConcurrentHashMap<String, Session>();

    private static  FlyHttpContext flyHttpContext=new FlyHttpContext();

    private static FlyHttpConfig config=new FlyHttpConfig();

    static {
        flyHttpContext=new FlyHttpContext();
    }


    /**
     * 请求转发器
     */
    private HttpForwarder forwarder;

    private FlyHttpContext(){}

    public static FlyHttpContext getFlyHttpContext(){
        return flyHttpContext;
    }

    public HttpForwarder getForwarder() {
        if (forwarder==null){
            forwarder=new DefaultHttpForwarder();
        }
        return forwarder;
    }

    /**
     * 通过sessionId获取Session
     * @param sessionId sessionId
     * @return Session
     */
    public Session getSession(String sessionId){
        Session session = this.sessionHashMap.get(sessionId);
        // 判断session是否已经过期
        if (session!=null&&session.isExpired()){
            this.sessionHashMap.remove(sessionId);
            return null;
        }
        return session;
    }
    public void putSession(Session session){
        this.sessionHashMap.put(session.getId(),session);
    }

    public FlyHttpConfig getConfig() {
        return FlyHttpContext.config;
    }
}
