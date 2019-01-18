package com.starry.fastsky;

import com.starry.fastsky.config.Appinitialize;
import com.starry.fastsky.server.NettyServer;

/**
 * ClassName: FastSkyServer
 * Description: fastsky 启动类
 *
 * @author: starryfei
 * Date: 2019-01-09 22:13
 **/
public class FastSkyServer {

    /**
     * Start the service path
     * @param clazz
     * @param path
     */
    public static void start(Class<?> clazz,String path) {
        Appinitialize.init(clazz, null);
        NettyServer server = new NettyServer();
        server.start();
    }


    /**
     * Start the service
     * @param clazz
     */
    public static void start(Class<?> clazz) {
        start(clazz,null);
    }

    public static void main(String[] args) {

        FastSkyServer.start(FastSkyServer.class);

    }
}
