package com.starry.fastsky.config;

import com.starry.fastsky.annotation.After;
import com.starry.fastsky.annotation.Before;
import com.starry.fastsky.annotation.FastAspect;
import com.starry.fastsky.annotation.Pointcut;
import com.starry.fastsky.common.FastskyCommon;
import com.starry.fastsky.factory.BeanFactoryManager;
import com.starry.fastsky.proxy.CGLibProxy;
import com.starry.fastsky.util.LoggerBuilder;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ClassName: AspectConfig
 * Description: 配置切面相关的类
 *
 * @author: starryfei
 * @date: 2019-01-18 17:50
 **/
public class AspectConfig {
    private static Logger logger = LoggerBuilder.getLogger(AspectConfig.class);

    private static BeanFactoryManager manager = BeanFactoryManager.getInstance();
    /**
     * 加载所有的FastAspect注解的类
     * @return
     */
    public static void loadAspect(Set<Class<?>> allClasss) throws InstantiationException, IllegalAccessException {
        for (Class<?> cla : allClasss) {
            if (cla.getAnnotation(FastAspect.class) != null) {
                getPointCutMethod(cla, cla.getDeclaredMethods());
            }
        }
    }

    /**
     * 获取切面的关联的类，将其转化为cglib动态类管理
     * @param cla
     * @param methods
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static void getPointCutMethod(Class<?> cla, Method[] methods) throws IllegalAccessException, InstantiationException {
//        boolean isPointCut = false;
        String classPath = cla.getAnnotation(FastAspect.class).value();
        for (String path: classPath.split(";")) {
            Map<String,Method> map = new HashMap<>(2);
            for (Method method: methods) {
                Before before = method.getAnnotation(Before.class);
                After after = method.getAnnotation(After.class);
                if (before != null) {
                    map.put(FastskyCommon.START,method);
                }
                if (after != null) {
                    map.put(FastskyCommon.END, method);
                }
            }
            Method start = map.get(FastskyCommon.START);
            Method end = map.get(FastskyCommon.END);

            Object object = manager.getBean(path);
            if (object != null) {
                Object instance = cla.newInstance();
                // 转化动态类
                CGLibProxy cgLibProxy = new CGLibProxy(start, end, instance);
                Object obj = cgLibProxy.getProxy(object.getClass());
                // 移除原有的bean
                manager.remove(path);
                manager.register(path, obj);
            }
        }

    }
}
