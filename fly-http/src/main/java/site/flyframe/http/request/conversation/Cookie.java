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

    @Override
    public String name() {
        return cookie.name();
    }

    @Override
    public String value() {
        return cookie.value();
    }

    @Override
    public void setValue(String value) {
        cookie.setValue(value);
    }

    @Override
    public boolean wrap() {
        return cookie.wrap();
    }

    @Override
    public void setWrap(boolean wrap) {
        cookie.setWrap(wrap);
    }

    @Override
    public String domain() {
        return cookie.domain();
    }

    @Override
    public void setDomain(String domain) {
        cookie.setDomain(domain);
    }

    @Override
    public String path() {
        return cookie.path();
    }

    @Override
    public void setPath(String path) {
        cookie.setPath(path);
    }

    @Override
    public long maxAge() {
        return cookie.maxAge();
    }

    @Override
    public void setMaxAge(long maxAge) {
        cookie.setMaxAge(maxAge);
    }

    @Override
    public boolean isSecure() {
        return cookie.isSecure();
    }

    @Override
    public void setSecure(boolean secure) {
        cookie.setSecure(secure);
    }

    @Override
    public boolean isHttpOnly() {
        return cookie.isHttpOnly();
    }

    @Override
    public void setHttpOnly(boolean httpOnly) {
        cookie.setHttpOnly(httpOnly);
    }

    @Override
    public int compareTo(io.netty.handler.codec.http.cookie.Cookie o) {
        return cookie.compareTo(o);
    }
}
