package com.starry.fastsky.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: DefaultBeanFactory
 * Description: BeanFactory的默认实现类
 * @author: starryfei
 * @date: 2019-01-11 14:14
 **/
public class DefaultBeanFactory implements BeanFactory {

    private static Map<String, Object> beans = new HashMap<>();
    public DefaultBeanFactory(){

    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);

    }

    @Override
    public void register(String name, Object obj) {
        beans.put(name, obj);
    }
//    public void register( Object obj) {
//        register(obj.getClass().getName(), obj);
//    }


    @Override
    public void destroy() {
        beans.clear();
    }

    @Override
    public void remove(String name) {
        beans.remove(name);
    }
}
