package com.starry.fastsky.handler;

import com.starry.fastsky.config.ApplicationConfig;
import com.starry.fastsky.route.RouteMethod;
import com.starry.fastsky.util.LoggerBuilder;
import com.starry.fastsky.util.URLUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;
import org.slf4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 *
 */
@ChannelHandler.Sharable
public class FastSkyRequestHanlder extends SimpleChannelInboundHandler<DefaultHttpRequest> {
    private final static Logger logger = LoggerBuilder.getLogger(FastSkyRequestHanlder.class);
    private RouteMethod routeMethod = new RouteMethod();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DefaultHttpRequest defaultHttpRequest) throws Exception {
        logger.info(defaultHttpRequest.method().name());
        logger.info(defaultHttpRequest.uri());

        for (Map.Entry<String, String> entry : defaultHttpRequest.headers().entries()) {
            logger.info(entry.getKey(),entry.getValue());
        }

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(URLDecoder.decode(defaultHttpRequest.uri(), "utf-8"));
        logger.info(queryStringDecoder.path());
        // 过滤图标请求
        if ("/favicon.ico".equals(queryStringDecoder.path())) {
            return;
        }
        checkUri(queryStringDecoder.path());
        Object obj = routeMethod.invoke(queryStringDecoder.path());
        for (Map.Entry<String, List<String>> entry: queryStringDecoder.parameters().entrySet()) {
            logger.info(entry.getValue().get(0));
            logger.info(entry.getKey());

        }
        String context = "hello fastsky....."+obj;
        ByteBuf buf = Unpooled.wrappedBuffer(context.getBytes(StandardCharsets.UTF_8));
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ChannelFuture future = channelHandlerContext.writeAndFlush(response);
        future.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    private static void checkUri(String uri) {

        String rootPath = ApplicationConfig.getInstance().getRootPath();
        String path = URLUtil.getRootPath(uri);
        if (!rootPath.equals(path)) {
            throw new RuntimeException("can not connect: "+uri);
        }
    }
}
