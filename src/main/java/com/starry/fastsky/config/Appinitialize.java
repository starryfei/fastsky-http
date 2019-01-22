package com.starry.fastsky.config;

import com.starry.fastsky.annotation.*;
import com.starry.fastsky.common.FastskyCommon;
import com.starry.fastsky.factory.BeanFactory;
import com.starry.fastsky.factory.BeanFactoryManager;
import com.starry.fastsky.factory.DefaultBeanFactory;
import com.starry.fastsky.proxy.CGLibProxy;
import com.starry.fastsky.util.LoggerBuilder;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.*;


/**
 * ClassName: Appinitialize
 * Description: 根据root class加载所有的class
 *
 * @author: starryfei
 * @date: 2019-01-09 22:13
 **/
public class Appinitialize {
    private static Logger logger = LoggerBuilder.getLogger(Appinitialize.class);
    private final static int BEANS_LENGTH = 2;
    private static Map<String,Class<?>> controllMap = null;
    private static Class<?> beanFactoryClass = null;
    private static List<Class<?>> beans = null;
    private static Set<Class<?>> allClasss = null;


    /**
     * 初始化工程信息
     * @param cla
     * @param rootPath
     */
    public static void init(Class<?> cla, String rootPath) {
        LoadProperties.initRootPath(rootPath, cla);
        allClasss = ScanPackage.autoLoadClass(cla);
        routeBeans();
        try {
            registerBeanToFactory();
            AspectConfig.loadAspect(allClasss);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        logger.info("----------beans load finish-------------");
    }

    /**
     * 实例化bean,并注册到beanfactory
     */

    private static void registerBeanToFactory() throws IllegalAccessException, InstantiationException {
        List<Class<?>> beans = new ArrayList<>();
        for(Class<?> cla: allClasss) {
            // 获取所有的实现BeanFactory的类
            if(cla.getInterfaces().length == 0) {
                continue;
            }
            if(cla.getInterfaces()[0] != BeanFactory.class) {
                continue;
            }
            beans.add(cla);
        }
        if (beans.size() > BEANS_LENGTH){
            throw new RuntimeException("BeanFactory impl to many");
        }
        if (beans.size() == BEANS_LENGTH) {
            Iterator<Class<?>> iterable = beans.iterator();
            while (iterable.hasNext()) {
                if (iterable.next() == DefaultBeanFactory.class) {
                    iterable.remove();
                }
            }
        }
        beanFactoryClass = beans.get(0);
        BeanFactoryManager manager = BeanFactoryManager.getInstance();
        manager.init((BeanFactory) beanFactoryClass.newInstance());
        for (Map.Entry<String,Class<?>> entry: controllMap.entrySet()) {
            Object obj = entry.getValue().newInstance();
            manager.register(obj);
        }

    }

    /**
     * 加载所有的FastController注解的类
     * @return
     */
    public static List<Class<?>> routeBeans() {
        if (beans == null) {
            beans = new ArrayList<>(10);
            controllMap = new HashMap<>(16);
            for(Class<?> cla: allClasss) {
                // 获取所有的FastController注解的类
                FastController fastController = cla.getAnnotation(FastController.class);
                if(fastController != null) {
                    logger.info("find bean [{}]", cla.getName());
                    controllMap.put(cla.getName(),cla);
                    beans.add(cla);
                }
            }
        }
        return beans;
    }

}
