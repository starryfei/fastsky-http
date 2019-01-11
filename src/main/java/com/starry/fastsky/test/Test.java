package com.starry.fastsky.test;

import com.starry.fastsky.factory.BeanFactory;

/**
 * ClassName: Test
 * Description: TODO
 * Author: starryfei
 * Date: 2019-01-11 14:50
 **/
public class Test implements BeanFactory {
    @Override
    public Object getBean(String name) {
        return null;
    }

    @Override
    public void register(Object obj) {

    }

    @Override
    public void destroy() {

    }
}
