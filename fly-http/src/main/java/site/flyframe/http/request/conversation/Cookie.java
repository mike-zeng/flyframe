package site.flyframe.http.request.conversation;

/**
 * @author zeng
 * @Classname Cookies
 * @Description TODO
 * @Date 2019/9/21 22:30
 */
public class Cookie implements io.netty.handler.codec.http.cookie.Cookie {
    private io.netty.handler.codec.http.cookie.Cookie cookie;
    public Cookie(io.netty.handler.codec.http.cookie.Cookie cookie){
        this.cookie=cookie;
    }

    public String name() {
        return cookie.name();
    }

    public String value() {
        return cookie.value();
    }

    public void setValue(String value) {
        cookie.setValue(value);
    }

    public boolean wrap() {
        return cookie.wrap();
    }

    public void setWrap(boolean wrap) {
        cookie.setWrap(wrap);
    }

    public String domain() {
        return cookie.domain();
    }

    public void setDomain(String domain) {
        cookie.setDomain(domain);
    }

    public String path() {
        return cookie.path();
    }

    public void setPath(String path) {
        cookie.setPath(path);
    }

    public long maxAge() {
        return cookie.maxAge();
    }

    public void setMaxAge(long maxAge) {
        cookie.setMaxAge(maxAge);
    }

    public boolean isSecure() {
        return cookie.isSecure();
    }

    public void setSecure(boolean secure) {
        cookie.setSecure(secure);
    }

    public boolean isHttpOnly() {
        return cookie.isHttpOnly();
    }

    public void setHttpOnly(boolean httpOnly) {
        cookie.setHttpOnly(httpOnly);
    }

    public int compareTo(io.netty.handler.codec.http.cookie.Cookie o) {
        return cookie.compareTo(o);
    }
}
