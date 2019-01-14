package com.starry.fastsky.util;

/**
 *
 */
public class URLUtil {



    public static String getRootPath(String path) {
        return "/" + path.split("/")[1];
    }

    /**
     * Get Action Path
     * /cicada-example/demoAction
     * @param path
     * @return demoAction
     */
    public static String getRoutePath(String path) {
        return path.split("/")[2];
    }

    /**
     * Get Action Path
     * /cicada-example/routeAction/getUser
     * @param path
     * @return getUser
     */
//    public static String getRoutePath(String path) {
//        AppConfig instance = AppConfig.getInstance();
//        return path.replace(instance.getRootPackageName(),"") ;
//    }


}
