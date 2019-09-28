package site.flyframe.http.request.conversation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zeng
 * @Classname Session
 * @Description TODO
 * @Date 2019/9/21 22:30
 */
public class Session {
    public static final String COOKIE_NAME="JSESSIONID";
    private Long expiredTime=null;

    private String id;

    public String getId() {
        return id;
    }

    private Map<Object,Object> sessionMap=new ConcurrentHashMap<Object, Object>();

    public void setId(String id) {
        this.id = id;
    }

    public boolean isExpired(){
        long l = System.currentTimeMillis();
        return expiredTime != null && expiredTime > l;
    }

    public Object getAttribute(Object name){
        return sessionMap.get(name);
    }

    public void setAttribute(Object name,Object value){
        sessionMap.put(name,value);
    }

    public boolean containAttributeName(Object name){
        return sessionMap.containsKey(name);
    }

    public boolean containAttributeValue(Object value){
        return sessionMap.containsValue(value);
    }
}
