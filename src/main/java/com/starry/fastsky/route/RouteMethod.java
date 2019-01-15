package com.starry.fastsky.route;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.starry.fastsky.annotation.FastRoute;
import com.starry.fastsky.config.ApplicationConfig;
import com.starry.fastsky.config.ApplictaionInit;
import com.starry.fastsky.factory.BeanFactory;
import com.starry.fastsky.factory.BeanFactoryManager;
import com.starry.fastsky.factory.DefaultBeanFactory;
import com.starry.fastsky.util.ConvertComplexTypeUtil;
import com.starry.fastsky.util.LoggerBuilder;
import com.starry.fastsky.util.URLUtil;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private void loadMethod(String className) {
        if (methodMap == null) {
            methodMap = new HashMap<>(16);
            List<Class<?>> beans = ApplictaionInit.routeBean();
            for (Class<?> cla : beans) {
                for (Method method : cla.getMethods()) {
                    if(method.getAnnotation(FastRoute.class) != null) {
                        String methodName = ApplicationConfig.getInstance().getRootPath() +
                                "/"+className+"/" + method.getName();
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
    public Object invoke(QueryStringDecoder queryStringDecoder) {
        String routeName = URLUtil.getRoutePath(queryStringDecoder.path());
        loadMethod(routeName);

        Method method = methodMap.get(queryStringDecoder.path());
        BeanFactoryManager manager = BeanFactoryManager.getInstance();
        Object obj = manager.getBean(method.getDeclaringClass().getName());
        if (method == null) {
            logger.error("can not run method"+queryStringDecoder.path());
        }
        Object returnObj = null;
        try {

            FastRoute fastRoute = method.getAnnotation(FastRoute.class);
            Object[] args = ConvertComplexTypeUtil.convertDataType(method.getParameterTypes(),queryStringDecoder);
            returnObj = method.invoke(obj.getClass(),args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return returnObj;
    }


}
