package com.starry.fastsky.config;

import com.starry.fastsky.FastSkyServer;
import com.starry.fastsky.annotation.FastController;
import com.starry.fastsky.common.FastskyCommon;
import com.starry.fastsky.factory.BeanFactory;
import com.starry.fastsky.factory.BeanFactoryManager;
import com.starry.fastsky.factory.DefaultBeanFactory;
import com.starry.fastsky.util.LoggerBuilder;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * ClassName: ApplictaionInit
 * Description: 根据root class加载所有的class
 *
 * @author: starryfei
 * @date: 2019-01-09 22:13
 **/
public class ApplictaionInit {
    private static Logger logger = LoggerBuilder.getLogger(ApplictaionInit.class);
    private final static int BEANS_LENGTH = 2;
    private static Map<String,Class<?>> controllMap = null;
    private static Class<?> beanFactoryClass = null;
    private static Class<?> rootClass;
    private static List<Class<?>> beans = null;

    public static void init(Class<?> cla, String rootPath) {
        rootClass = cla;
        initRootPath(rootPath);
        routeBean();

        try {
            registerBeanToFactory();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    private static void initRootPath(String rootPath) {
        System.out.println(FastskyCommon.FASTSKY_LOGO);
        if (rootPath == null || "".equals(rootPath)) {
            loadProperties();
        } else {
            ApplicationConfig.getInstance().setRootPath(rootPath);
        }
        ApplicationConfig.getInstance().setPackageName(rootClass.getPackage().getName());
    }

    /**
     * 读resource文件夹下的配置文件
     */
    private static void loadProperties(){
        String propertiesPath = FastskyCommon.APPLICATION_PROPERTIES;
        try {
            InputStream stream = FastSkyServer.class.getClassLoader().getResourceAsStream(propertiesPath);
            Properties properties = new Properties();
            properties.load(stream);
            ApplicationConfig.getInstance().setRootPath(properties.getProperty(FastskyCommon.FASTSKY_PATH));
            ApplicationConfig.getInstance().setPort(Integer.parseInt(properties.getProperty(FastskyCommon.FASTSKY_PORT)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * 实例化bean,并注册到beanfactory
     */

    private static void registerBeanToFactory() throws IllegalAccessException, InstantiationException {
        Set<Class<?>> classes = ScanPackage.autoLoadClass(rootClass);
        List<Class<?>> beans = new ArrayList<>();
        for(Class<?> cla: classes) {
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
    public static List<Class<?>>  routeBean() {
        Set<Class<?>> classes = ScanPackage.autoLoadClass(rootClass);
        if (beans == null) {
            beans = new ArrayList<>();
            controllMap = new HashMap<>(16);
            for(Class<?> cla: classes) {
                // 获取所有的FastController注解的类
                if(cla.getAnnotation(FastController.class) != null) {
                    controllMap.put(cla.getName(),cla);
                    beans.add(cla);
                }
            }
        }
        return beans;
    }

}
