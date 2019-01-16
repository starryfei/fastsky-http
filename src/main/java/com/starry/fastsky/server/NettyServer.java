package com.starry.fastsky.server;

import com.starry.fastsky.FastSkyServer;
import com.starry.fastsky.config.ApplicationConfig;
import com.starry.fastsky.factory.BeanFactoryManager;
import com.starry.fastsky.handler.FastSkyRequestHanlder;
import com.starry.fastsky.util.LoggerBuilder;
import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;

/**
 * ClassName: NettyServer
 * Description: fastsky netty server端
 *
 * @author: starryfei
 * @date: 2019-01-14 11:02
 **/
public class NettyServer {
    private final static Logger logger = LoggerBuilder.getLogger(NettyServer.class);
    private ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
    private EventLoopGroup work = new NioEventLoopGroup();
    private EventLoopGroup boos = new NioEventLoopGroup();
    private Channel channel;

    /**
     * 启动server
     */
    public void start(){
        final ServerBootstrap server = new ServerBootstrap();
        server.group(work,boos).channel(NioServerSocketChannel.class)
               .childHandler(new ChannelInitializer<SocketChannel>() {
                   @Override
                   protected void initChannel(SocketChannel socketChannel) throws Exception {
                       /**
                        * http-response解码器
                        * http服务器端对response编码
                        */

                       socketChannel.pipeline().addLast(new HttpResponseEncoder());
                       /**
                        * http-request解码器
                        * http服务器端对request解码
                        */

                       socketChannel.pipeline().addLast(new HttpRequestDecoder());
                       socketChannel.pipeline().addLast(new ChunkedWriteHandler());

                       socketChannel.pipeline().addLast(new FastSkyRequestHanlder());

                   }
               }).option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true);
        int port = applicationConfig.getPort();

        ChannelFuture future = null;
        try {
            future = server.bind(port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (future.isSuccess()) {
            logger.info("connect server success");
            logger.info("server start http://{}:{}{}","127.0.0.1",applicationConfig.getPort(),applicationConfig.getRootPath());

        }
        channel = future.channel();

    }

    /**
     * 停止server
     */
    public void stop() {
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new ShutServer());


    }

    /**
     * shutdown钩子事件
     */
    class ShutServer extends Thread{
        @Override
        public void run() {
            logger.info("fastsky stoping..");
            BeanFactoryManager.getInstance().releaseBean();
            work.shutdownGracefully();
            boos.shutdownGracefully();

            logger.info("Server stoped");
        }
    }
}
