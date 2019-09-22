package site.flyframe.http.request;

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

    public void setId(String id) {
        this.id = id;
    }

    public boolean isExpired(){
        long l = System.currentTimeMillis();
        return expiredTime != null && expiredTime > l;
    }
}
