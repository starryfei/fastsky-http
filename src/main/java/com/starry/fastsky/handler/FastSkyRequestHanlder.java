package com.starry.fastsky.handler;

import com.starry.fastsky.util.LoggerBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
public class FastSkyRequestHanlder extends SimpleChannelInboundHandler<DefaultHttpRequest> {
    private final static Logger logger = LoggerBuilder.getLogger(FastSkyRequestHanlder.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DefaultHttpRequest defaultHttpRequest) throws Exception {
        logger.info(defaultHttpRequest.method().name());
        logger.info(defaultHttpRequest.uri());

        for (Map.Entry<String, String> entry : defaultHttpRequest.headers().entries()) {
            logger.info(entry.getKey(),entry.getValue());
        }

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(URLDecoder.decode(defaultHttpRequest.uri(), "utf-8"));
        for (Map.Entry<String, List<String>> entry: queryStringDecoder.parameters().entrySet()) {
            logger.info(entry.getValue().get(0),entry.getKey());
        }
        String context = "hello fastsky.....";
        ByteBuf buf = Unpooled.wrappedBuffer(context.getBytes(StandardCharsets.UTF_8));
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        channelHandlerContext.writeAndFlush(response);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
