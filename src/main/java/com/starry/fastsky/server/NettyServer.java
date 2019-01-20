package com.starry.fastsky.server;

import com.starry.fastsky.config.AppConfig;
import com.starry.fastsky.factory.BeanFactoryManager;
import com.starry.fastsky.handler.FastSkyRequestHanlder;
import com.starry.fastsky.https.SSLConfig;
import com.starry.fastsky.util.LoggerBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.*;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * ClassName: NettyServer
 * Description: fastsky netty server端
 *
 * @author: starryfei
 * @date: 2019-01-14 11:02
 **/
public class NettyServer {
    private final static Logger logger = LoggerBuilder.getLogger(NettyServer.class);
    private static AppConfig applicationConfig = AppConfig.getInstance();
    private static EventLoopGroup work = new NioEventLoopGroup();
    private static EventLoopGroup boos = new NioEventLoopGroup();
    private static Channel channel;

    public static void init(){
        start();
        stop();
    }
    /**
     * 启动server
     */
    private static void start(){
        final ServerBootstrap server = new ServerBootstrap();
        server.group(work,boos).channel(NioServerSocketChannel.class)
               .childHandler(new ChannelInitializer<SocketChannel>() {
                   @Override
                   protected void initChannel(SocketChannel socketChannel) throws Exception {
                       if(applicationConfig.isOpenSSL()) {
                           SSLEngine sslEngine = SSLConfig.openSSL();
                           // 添加 SslHandler 之 pipeline 中
                           logger.info("start ssl");
                           socketChannel.pipeline().addFirst("ssl", new SslHandler(sslEngine));
                       }

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
            if (applicationConfig.isOpenSSL()) {
                logger.info("server start https://{}:{}{}", "127.0.0.1", applicationConfig.getPort(), applicationConfig.getRootPath());
            } else {
                logger.info("server start http://{}:{}{}", "127.0.0.1", applicationConfig.getPort(), applicationConfig.getRootPath());

            }
        }
        channel = future.channel();

    }


    /**
     * 停止server
     */
    private static void stop() {
        Runtime.getRuntime().addShutdownHook(new ShutServer());
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * shutdown钩子事件
     */
    static class ShutServer extends Thread{
        @Override
        public void run() {
            logger.info("fastsky stoping..");
            BeanFactoryManager.getInstance().releaseBean();
            work.shutdownGracefully();
            boos.shutdownGracefully();

            logger.info("fastsky stoped");
        }
    }
}
