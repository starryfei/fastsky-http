package com.starry.fastsky.factory;

/**
 * ClassName: BeanFactoryManager
 * Description: Bean的管理类
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

    /**
     * 实例化beanFactory对象
     * @param bean
     */
    public void init(BeanFactory bean) {
        beanFactory = bean;
    }

    /**
     * 注册bean对象
     * @param obj
     */
    public void register(Object obj) {
        beanFactory.register(obj);
    }

    /**
     * get route bean
     * @param name
     * @return
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
