package com.starry.fastsky;

import com.starry.fastsky.common.FastskyCommon;
import com.starry.fastsky.server.NettyServer;

public class FastSkyServer {
    public static void main(String[] args) {
        System.out.println(FastskyCommon.FASTSKY_LOGO);
        NettyServer server = new NettyServer();
        server.start();
    }
}
