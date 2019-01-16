package com.starry.fastsky.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class: LoggerBuilder
 * Describle: logger类
 * @author starryfei
 * @date: 2019-01-15 11:10
 */
public class LoggerBuilder {


    /**
     * get static Logger
     * @param clazz
     * @return
     */
    public static Logger getLogger(Class clazz){
        return LoggerFactory.getLogger(clazz);
    }
}
