package com.starry.fastsky.https;

import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * ClassName: SSLConfig
 * Description: 配置SSL
 *
 * @author: starryfei
 * @date: 2019-01-20 20:37
 **/
public class SSLConfig {
    /**
     * Netty 自带的自签名证书工具
     * @return
     * @throws CertificateException
     * @throws SSLException
     */
    public static SSLEngine openSSL() throws CertificateException, SSLException {
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        SSLEngine sslEngine = sslCtx.newEngine(UnpooledByteBufAllocator.DEFAULT);
        // 配置为 server 模式
        sslEngine.setUseClientMode(false);
        // 选择需要启用的 SSL 协议，如 SSLv2 SSLv3 TLSv1 TLSv1.1 TLSv1.2 等
        sslEngine.setEnabledProtocols(sslEngine.getSupportedProtocols());
        // 选择需要启用的 CipherSuite 组合，如 ECDHE-ECDSA-CHACHA20-POLY1305 等
        sslEngine.setEnabledCipherSuites(sslEngine.getSupportedCipherSuites());
        return sslEngine;
    }
}
