package com.starry.fastsky.factory;

/**
 * ClassName: BeanFactory
 * Description: bean的接口
 * @author: starryfei
 * @date: 2019-01-11 11:46
 **/
public interface BeanFactory {
    /**
     * 通过name获取bean
      * @param name
     * @return
     */
    Object getBean(String name);

    /**
     * 注册bean
     * @param obj
     * @return
     */
    void register(Object obj);
    /**
     * 销毁bean
     *
     * @return
     */
    void destroy();

}
