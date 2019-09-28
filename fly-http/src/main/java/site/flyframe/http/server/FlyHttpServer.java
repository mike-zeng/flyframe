package site.flyframe.http.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.apache.log4j.Logger;
import site.flyframe.http.exception.HttpServerException;

/**
 * @author zeng
 * @Classname HttpServer
 * @Description http服务器实例，单例模式
 * @Date 2019/9/19 21:30
 */
public class FlyHttpServer {
    private static Logger logger=Logger.getLogger(FlyHttpServer.class);
    private static final int MAX_PORT=65535;
    private static final int MIN_PORT=0;
    private int port;
    private FlyHttpServer(){}
    private FlyHttpServer(int port){
        this.port=port;
    }
    private static FlyHttpServer flyHttpServer;

    public static FlyHttpServer getInstance(int port)throws Exception{
        // 检查端口是否符合要求
        if (port<MIN_PORT||port>MAX_PORT){
            throw new HttpServerException();
        }
        synchronized (FlyHttpServer.class){
            if (flyHttpServer ==null){
                flyHttpServer =new FlyHttpServer(port);
            }
        }
        return flyHttpServer;
    }

    /**
     * Http启动方法
     */
    public void start() throws Exception {
        ServerBootstrap bootstrap=new ServerBootstrap();
        EventLoopGroup boss=new NioEventLoopGroup();
        EventLoopGroup work=new NioEventLoopGroup();
        try {
            bootstrap.group(boss,work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec());// http 编解码
                            pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024));
                            pipeline.addLast(new FlyHttpServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(this.port).sync();
            future.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
            logger.info("server shut down successfully");
        }
    }
}
