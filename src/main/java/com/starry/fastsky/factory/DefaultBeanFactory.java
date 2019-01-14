package com.starry.fastsky.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: DefaultBeanFactory
 * Description: BeanFactory的默认实现类
 * Author: starryfei
 * Date: 2019-01-11 14:14
 **/
public class DefaultBeanFactory implements BeanFactory {

    private static DefaultBeanFactory defaultBeanFactory;
    private static Map<String, Object> beans = new HashMap<>();
    public DefaultBeanFactory(){

    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);

    }

    @Override
    public void register(Object obj) {
        String[] arrays = obj.getClass().getName().split("\\.");
        String name = arrays[arrays.length-1];
        beans.put(name, obj);
    }

    @Override
    public void destroy() {
        beans.clear();
    }
}
