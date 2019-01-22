package com.starry.fastsky.test.annontation_test;

import com.starry.fastsky.annotation.After;
import com.starry.fastsky.annotation.Before;
import com.starry.fastsky.annotation.FastAspect;
import com.starry.fastsky.annotation.Pointcut;
import com.starry.fastsky.aspect.Iterceptor;
import com.starry.fastsky.util.LoggerBuilder;
import org.slf4j.Logger;

/**
 * ClassName: Log
 * Description: TODO
 *
 * @author: starryfei
 * @date: 2019-01-18 14:15
 **/
@FastAspect(value = {"com.starry.fastsky.test.Demo","com.starry.fastsky.test.Demo2"})
public class Log implements Iterceptor {
    private static final Logger LOGGER = LoggerBuilder.getLogger(Log.class);
    private Long start;
    private Long end;

//    @Pointcut
//    public void start() {
//        LOGGER.info("开始注解");
//    }

    @Before()
    @Override
    public void before() {
        start = System.currentTimeMillis();
        LOGGER.info("---before---");
    }
    @After()
    @Override
    public void after() {
        end = System.currentTimeMillis();
        LOGGER.info("cast [{}] times", end - start);
        LOGGER.info("---after---");
    }

}
