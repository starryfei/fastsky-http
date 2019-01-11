package com.starry.fastsky.factory;

/**
 * ClassName: BeanFactory
 * Description: TODO
 * Author: starryfei
 * Date: 2019-01-11 11:46
 **/
public interface BeanFactory {
    /**
     * 通过name回去bean
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
