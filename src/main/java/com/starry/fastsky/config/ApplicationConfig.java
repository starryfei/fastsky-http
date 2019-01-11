package com.starry.fastsky.config;

/**
 * ClassName: ApplicationConfig
 * Description: 单例模式，存储root包信息和路径信息
 * Author: starryfei
 * Date: 2019-01-09 22:38
 **/
public class ApplicationConfig {
    private static ApplicationConfig applicationConfig ;
    private String packageName;
    private String rootPath;

    public static ApplicationConfig getInstance() {
        if (applicationConfig == null) {
            return applicationConfig = new ApplicationConfig();
        }
        return applicationConfig;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private ApplicationConfig() {

    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
