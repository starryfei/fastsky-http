package com.starry.fastsky;

import com.starry.fastsky.config.ApplictaionInit;
import com.starry.fastsky.server.NettyServer;

/**
 * ClassName: FastSkyServer
 * Description: fastsky 启动类
 *
 * @author: starryfei
 * Date: 2019-01-09 22:13
 **/
public class FastSkyServer {

    public static void main(String[] args) {

        ApplictaionInit.init(FastSkyServer.class, null);

        NettyServer server = new NettyServer();
        server.start();
        server.stop();

    }
}
