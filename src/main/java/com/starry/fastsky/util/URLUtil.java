package com.starry.fastsky.util;

/**
 * Class: URLUtil
 * Describle: logger类
 * @author starryfei
 * @date: 2019-01-15 11:10
 */
public class URLUtil {

    /**
     *  获取项目的跟路径
     * @param path
     * @return
     */

    public static String getRootPath(String path) {
        return "/" + path.split("/")[1];
    }



}
