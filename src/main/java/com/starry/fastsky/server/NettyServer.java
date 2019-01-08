package com.starry.fastsky.server;

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
import org.slf4j.Logger;

public class NettyServer {
    private final static Logger logger = LoggerBuilder.getLogger(NettyServer.class);
    private EventLoopGroup work = new NioEventLoopGroup();
    private EventLoopGroup boos = new NioEventLoopGroup();
    private Channel channel;
    private final static int PORT = 9511;

    public void start(){
        final ServerBootstrap server = new ServerBootstrap();
        server.group(work,boos).channel(NioServerSocketChannel.class)
               .childHandler(new ChannelInitializer<SocketChannel>() {
                   @Override
                   protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new HttpResponseEncoder());
                        socketChannel.pipeline().addLast(new HttpRequestDecoder());
                        socketChannel.pipeline().addLast(new FastSkyRequestHanlder());

                   }
               }).option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true);
        ChannelFuture future = null;
        try {
            future = server.bind(PORT).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (future.isSuccess()) {
            logger.info("connect server success");
        }
        channel = future.channel();

    }

    public void stop() {
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            work.shutdownGracefully();
            boos.shutdownGracefully();
        }
    }
}
