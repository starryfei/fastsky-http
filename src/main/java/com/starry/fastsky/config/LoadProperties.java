package com.starry.fastsky.config;

import com.starry.fastsky.FastSkyServer;
import com.starry.fastsky.common.FastskyCommon;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ClassName: LoadProperties
 * Description: 初始化加载properties文件 *
 * @author: starryfei
 * @date: 2019-01-18 17:13
 **/
public class LoadProperties {
    /**
     * 配置根结点的信息
     * @param rootPath
     */
    public static void initRootPath(String rootPath, Class<?> rootClass) {
        System.out.println(FastskyCommon.FASTSKY_LOGO);
        if (rootPath == null || "".equals(rootPath)) {
            load();
        } else {
            AppConfig.getInstance().setRootPath(rootPath);
        }
        AppConfig.getInstance().setPackageName(rootClass.getPackage().getName());
    }
    /**
     * 读resource文件夹下的配置文件
     */
    private static void load(){
        String propertiesPath = FastskyCommon.APPLICATION_PROPERTIES;
        try {
            InputStream stream = FastSkyServer.class.getClassLoader().getResourceAsStream(propertiesPath);
            Properties properties = new Properties();
            properties.load(stream);
            AppConfig app = AppConfig.getInstance();
            app.setRootPath(properties.getProperty(FastskyCommon.FASTSKY_PATH));
            app.setPort(Integer.parseInt(properties.getProperty(FastskyCommon.FASTSKY_PORT)));
            app.setOpenSSL(Boolean.parseBoolean(properties.getProperty(FastskyCommon.FASTSKY_SSL)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
