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
        boolean isPointCut = false;
        String classPath = "";
        Map<String,Method> map = new HashMap<>(2);
        for (Method method: methods) {
            Pointcut pointcut = method.getAnnotation(Pointcut.class);
            Before before = method.getAnnotation(Before.class);
            After after = method.getAnnotation(After.class);
            if (pointcut != null) {
                isPointCut = true;
                classPath = pointcut.value();
            }
            if (before != null) {
                map.put(FastskyCommon.START,method);
            }
            if (after != null) {
                map.put(FastskyCommon.END, method);
            }
        }
        if (isPointCut) {
            Method start = map.get(FastskyCommon.START);
            Method end = map.get(FastskyCommon.END);
            // 转化动态类，可以考虑一个切面关联多个类
            Object object = manager.getBean(classPath);
            Object instance = cla.newInstance();
            CGLibProxy cgLibProxy = new CGLibProxy(start,end,instance);
            Object obj = cgLibProxy.getProxy(object.getClass());
            // 移除原有的bean
            manager.remove(classPath);
            manager.register(classPath, obj);
        }
    }
}
