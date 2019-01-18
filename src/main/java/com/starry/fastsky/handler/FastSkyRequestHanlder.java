package com.starry.fastsky.handler;

import com.starry.fastsky.config.AppConfig;
import com.starry.fastsky.route.RouteMethod;
import com.starry.fastsky.util.LoggerBuilder;
import com.starry.fastsky.util.URLUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;

import java.net.URLDecoder;

/**
 * ClassName: FastSkyRequestHanlder
 * Description:
 *
 * @author: starryfei
 * @date: 2019-01-11 18:04
 **/
@ChannelHandler.Sharable
public class FastSkyRequestHanlder extends SimpleChannelInboundHandler<DefaultHttpRequest> {
    private final static Logger logger = LoggerBuilder.getLogger(FastSkyRequestHanlder.class);
    private final static String FAVICON_ICO = "/favicon.ico";
    private RouteMethod routeMethod = new RouteMethod();


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DefaultHttpRequest defaultHttpRequest) throws Exception {
//        logger.info(defaultHttpRequest.method().name());
//        logger.info(defaultHttpRequest.uri());
//
//        for (Map.Entry<String, String> entry : defaultHttpRequest.headers().entries()) {
//            logger.info(entry.getKey(),entry.getValue());
//        }

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(URLDecoder.decode(defaultHttpRequest.uri(), "utf-8"));
        // 过滤图标请求
        if (FAVICON_ICO.equals(queryStringDecoder.path())) {
            return;
        }
        checkUri(queryStringDecoder.path());
        DefaultHttpResponse response = routeMethod.invoke(queryStringDecoder);
//        for (Map.Entry<String, List<String>> entry: queryStringDecoder.parameters().entrySet()) {
//            logger.info(entry.getValue().get(0));
//            logger.info(entry.getKey());
//
//        }
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

        String rootPath = AppConfig.getInstance().getRootPath();
        String path = URLUtil.getRootPath(uri);
        if (!rootPath.equals(path)) {
            throw new RuntimeException("can not connect: "+uri);
        }
    }
}
