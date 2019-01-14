package com.starry.fastsky.route;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.starry.fastsky.annotation.FastRoute;
import com.starry.fastsky.config.ApplicationConfig;
import com.starry.fastsky.config.ApplictaionInit;
import com.starry.fastsky.factory.BeanFactory;
import com.starry.fastsky.factory.BeanFactoryManager;
import com.starry.fastsky.factory.DefaultBeanFactory;
import com.starry.fastsky.util.URLUtil;

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

    private static Map<String, Method> methodMap = null;

    private void loadMethod(String className) {
        if (methodMap == null) {
            methodMap = new HashMap<>(16);
            List<Class<?>> beans = ApplictaionInit.routeBean();
            for (Class<?> cla : beans) {
                for (Method method : cla.getMethods()) {
                    if(method.getAnnotation(FastRoute.class) != null) {
//                        System.out.println(cla.getName());
                        String methodName = ApplicationConfig.getInstance().getRootPath() +
                                "/"+className+"/" + method.getName();
                        System.out.println(methodName);
                        methodMap.put(methodName, method);
                    }
                }
            }
        }
    }

    public Object invoke(String uri) {
        String routeName = URLUtil.getRoutePath(uri);
        loadMethod(routeName);
        BeanFactoryManager manager = BeanFactoryManager.getInstance();
        Object obj = manager.getBean(routeName);
        Method method = methodMap.get(uri);
        if (method == null) {
            throw new RuntimeException("can not run method"+uri);
        }
        System.out.println(method.getName()+" "+obj.getClass().getName());
        Object returnObj = null;
        try {
            returnObj = method.invoke(obj.getClass(),null);
            System.out.println(returnObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return returnObj;
    }

}
