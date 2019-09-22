package site.flyframe.http.request;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zeng
 * @Classname HttpContext
 * @Description TODO
 * @Date 2019/9/22 12:10
 */
public class FlyHttpContext {
    private ConcurrentHashMap<String,Session> sessionHashMap=new ConcurrentHashMap<String, Session>();

    private static FlyHttpContext flyHttpContext =new FlyHttpContext();

    private FlyHttpContext(){}

    public static FlyHttpContext getFlyHttpContext(){
        return flyHttpContext;
    }

    /**
     * 通过sessionId获取Session
     * @param sessionId sessionId
     * @return Session
     */
    Session getSession(String sessionId){
        Session session = this.sessionHashMap.get(sessionId);
        // 判断session是否已经过期
        if (session!=null&&session.isExpired()){
            this.sessionHashMap.remove(sessionId);
            return null;
        }
        return session;
    }
    void putSession(Session session){
        this.sessionHashMap.put(session.getId(),session);
    }
}
