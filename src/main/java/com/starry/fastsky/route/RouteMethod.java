package com.starry.fastsky.route;

import com.alibaba.fastjson.JSON;
import com.starry.fastsky.annotation.FastController;
import com.starry.fastsky.annotation.FastRoute;
import com.starry.fastsky.common.FastskyCommon;
import com.starry.fastsky.config.AppConfig;
import com.starry.fastsky.config.Appinitialize;
import com.starry.fastsky.enums.FastSkyServerResponse;
import com.starry.fastsky.factory.BeanFactoryManager;
import com.starry.fastsky.util.ConvertComplexTypeUtil;
import com.starry.fastsky.util.LoggerBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: RouteMethod
 * Description: 根据Uri加载method
 *
 * @author: starryfei
 * @date: 2019-01-14 11:02
 **/
public class RouteMethod {
    private final static Logger logger = LoggerBuilder.getLogger(RouteMethod.class);
    private static Map<String, Method> methodMap = null;

    /**
     * 加载bean方法名与方法的映射
     *
     */
    private void loadMethod() {
        if (methodMap == null) {
            methodMap = new HashMap<>(16);
            List<Class<?>> beans = Appinitialize.routeBeans();
            for (Class<?> cla : beans) {
                FastController controller = cla.getAnnotation(FastController.class);
                for (Method method : cla.getMethods()) {
                    FastRoute fastRoute = method.getAnnotation(FastRoute.class);
                    if(fastRoute != null) {
                        String methodName = AppConfig.getInstance().getRootPath() + controller.value()
                                +fastRoute.path();
                        methodMap.put(methodName, method);
                    }
                }
            }
        }
    }

    /**
     * 执行带@FastRoute注解的方法，并将结果转化为对应的类型json,text..
     * @param queryStringDecoder
     * @return
     */
    public DefaultHttpResponse invoke(QueryStringDecoder queryStringDecoder) {
        String path = queryStringDecoder.path();
        logger.info(path);
        // 首页判断
        if(AppConfig.getInstance().getRootPath().equals(path)) {
            return buildResponse(null,FastskyCommon.CONTEXT,null);
        }
        loadMethod();

        Method method = methodMap.get(path);
        if (method == null) {
            logger.error("can not run method" + queryStringDecoder.path());
            String context = "404: can not find method";
            return buildResponse(null, context, null);
        }
        FastRoute fastRoute = method.getAnnotation(FastRoute.class);
        BeanFactoryManager manager = BeanFactoryManager.getInstance();
        Object obj = manager.getBean(method.getDeclaringClass().getName());
        logger.info(obj.getClass().getName());
        Object returnObj = null;
        try {
            Object[] args = ConvertComplexTypeUtil.convertDataType(method.getParameterTypes(), queryStringDecoder);
            returnObj = method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return buildResponse(returnObj,null,fastRoute.type());
    }
    /**
     * 构建响应信息
     * @param result
     * @return
     */
    private static DefaultFullHttpResponse buildResponse(Object result,String context, FastSkyServerResponse serverResponse) {
        ByteBuf buf;
        String contentType = FastskyCommon.HTML;
        if (result != null) {

            if (FastskyCommon.JSON.equals(serverResponse.getReturnType())) {
                buf = Unpooled.wrappedBuffer(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
                contentType = FastskyCommon.JSON;
            } else if (FastskyCommon.HTML.equals(serverResponse.getReturnType())) {
                buf = Unpooled.wrappedBuffer(result.toString().getBytes(StandardCharsets.UTF_8));
                contentType = FastskyCommon.HTML;
            } else {
                buf = Unpooled.wrappedBuffer(result.toString().getBytes(StandardCharsets.UTF_8));
            }
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            return response;
        } else {

                buf = Unpooled.wrappedBuffer(context.getBytes(StandardCharsets.UTF_8));
                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
                return response;
            }
    }
}
