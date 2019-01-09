package com.starry.fastsky;

import com.starry.fastsky.common.FastskyCommon;
import com.starry.fastsky.config.LoadClass;
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
        System.out.println(FastskyCommon.FASTSKY_LOGO);
        LoadClass.autoLoadClass(FastSkyServer.class);

        NettyServer server = new NettyServer();
        server.start();


    }
}
