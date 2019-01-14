package com.starry.fastsky.factory;

import java.util.Map;

/**
 * ClassName: BeanFactoryManager
 * Description: TODO
 *
 * @author: starryfei
 * @date: 2019-01-14 15:38
 **/
public class BeanFactoryManager {
    private BeanFactory beanFactory;
    private static BeanFactoryManager beanFactoryManager = null;

    public static BeanFactoryManager getInstance(){
        if (beanFactoryManager == null){
            beanFactoryManager = new BeanFactoryManager();
        }
        return beanFactoryManager;
    }
    public void init(BeanFactory bean) {
        beanFactory = bean;
    }
    public void register(Object obj) {
        beanFactory.register(obj);
    }

    /**
     * get route bean
     * @param name
     * @return
     * @throws Exception
     */
    public Object getBean(String name) {
        return beanFactory.getBean(name) ;
    }


    /**
     * release all beans
     */
    public void releaseBean(){
        beanFactory.destroy();
    }
}
