package site.flyframe.http.server;

import org.junit.Test;
import site.flyframe.http.context.FlyHttpContext;

/**
 * @author zeng
 * @Classname HttpServerTest
 * @Description TODO
 * @Date 2019/9/19 22:09
 */
public class FlyHttpServerTest {
    @Test
    public void startServer() throws Exception {
        FlyHttpServer flyHttpServer = FlyHttpServer.getInstance(8081);
        FlyHttpContext httpContext=FlyHttpContext.getFlyHttpContext();
        httpContext.getConfig().addProcessorPackagePath("site.flyframe.http.processortest");
        flyHttpServer.start();
    }
}