package com.starry.fastsky.config;

/**
 * ClassName: ApplicationConfig
 * Description: TODO
 * Author: starryfei
 * Date: 2019-01-09 22:38
 **/
public class ApplicationConfig {
    private static ApplicationConfig applicationConfig ;
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private ApplicationConfig() {

    }

    public static ApplicationConfig getInstance() {
        if (applicationConfig == null) {
            return applicationConfig = new ApplicationConfig();
        }
        return applicationConfig;
    }

}
