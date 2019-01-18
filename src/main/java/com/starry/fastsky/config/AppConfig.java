package com.starry.fastsky.config;

/**
 * ClassName: AppConfig
 * Description: 单例模式，存储root包信息和路径信息
 * @author: starryfei
 * @date: 2019-01-09 22:38
 **/
public class AppConfig {
    private static AppConfig appConfig ;
    private String packageName;
    private String rootPath;
    private boolean isOpenSSL;
    private static final String LINE = "/";
    private int port = 9511;

    public boolean isOpenSSL() {
        return isOpenSSL;
    }

    public void setOpenSSL(boolean openSSL) {
        isOpenSSL = openSSL;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {

        this.port = port;
    }

    public static AppConfig getInstance() {
        if (appConfig == null) {
            return appConfig = new AppConfig();
        }
        return appConfig;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private AppConfig() {

    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = LINE+rootPath;
    }
}
