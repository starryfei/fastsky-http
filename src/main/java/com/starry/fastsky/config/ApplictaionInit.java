package com.starry.fastsky.config;

import com.starry.fastsky.annotation.FastController;
import com.starry.fastsky.common.FastskyCommon;
import com.starry.fastsky.factory.BeanFactory;
import com.starry.fastsky.factory.DefaultBeanFactory;
import com.starry.fastsky.util.LoggerBuilder;
import org.slf4j.Logger;

import java.util.*;


/**
 * ClassName: ApplictaionInit
 * Description: 根据root class加载所有的class
 *
 * @author: starryfei
 * Date: 2019-01-09 22:13
 **/
public class ApplictaionInit {
    private static Logger logger = LoggerBuilder.getLogger(ApplictaionInit.class);
    private static Map<String,Class<?>> controllMap = null;
    private static Class<?> beanFactoryClass = null;

    public static void init(Class<?> rootClass, String rootPath) {
        System.out.println(FastskyCommon.FASTSKY_LOGO);
        if (rootPath == null || "".equals(rootPath)) {
            ApplicationConfig.getInstance().setRootPath(FastskyCommon.FASTSKY_PATH);
        } else {
            ApplicationConfig.getInstance().setRootPath(rootPath);
        }
        Set<Class<?>> classes = ScanClass.autoLoadClass(rootClass);
        routeBean(classes);

        try {
            registerBeanToFactory();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }

    /**
     * 实例化bean,并注册到beanfactory
     */
    private static void registerBeanToFactory() throws IllegalAccessException, InstantiationException {
        for (Map.Entry<String,Class<?>> entry: controllMap.entrySet()) {
            Object obj = entry.getValue().newInstance();
            BeanFactory beanFactory = (BeanFactory) beanFactoryClass.newInstance();
            beanFactory.register(obj);

        }
    }

    public static void routeBean(Set<Class<?>> classes) {
        List<Class<?>> beans = new ArrayList<>();
        controllMap = new HashMap<>(16);
        for(Class<?> cla: classes) {
            // 获取所有的FastController注解的类
            if(cla.getAnnotation(FastController.class) != null) {

                controllMap.put(cla.getName(),cla);
            }

            if(cla.getInterfaces().length == 0) {
                continue;
            }
            if(cla.getInterfaces()[0] != BeanFactory.class) {
                continue;
            }
            beans.add(cla);

        }
        if (beans.size() > 2){
            new RuntimeException("BeanFactory impl to many");
        }
        if (beans.size() == 2) {
            Iterator<Class<?>> iterable = beans.iterator();
            while (iterable.hasNext()) {
                if (iterable.next() == DefaultBeanFactory.class) {
                    iterable.remove();
                }
            }
        }
        beanFactoryClass = beans.get(0);

    }

}
