package com.starry.fastsky.proxy;

import com.starry.fastsky.util.LoggerBuilder;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;

import java.lang.reflect.Method;

/**
 * ClassName: CGLibProxy
 * Description: 使用cglib代理实现切面
 *
 * @author: starryfei
 * @date: 2019-01-18 14:34
 **/
public class CGLibProxy implements MethodInterceptor {
    private static Logger LOGGER = LoggerBuilder.getLogger(CGLibProxy.class);
    private Method startMethod = null;
    private Method endMethod = null;
    private Object object ;
    private Enhancer enhancer = new Enhancer();

    /**
     *
     * @param startMethod: @before方法
     * @param endMethod @After方法
     * @param object 该注解所在的类对象
     */
    public CGLibProxy(Method startMethod, Method endMethod,Object object) {
        this.startMethod = startMethod;
        this.endMethod = endMethod;
        this.object = object;

    }
    public CGLibProxy() {

    }

    /**
     * 创建动态代理对象
     * @param clz
     * @return
     */
    public Object getProxy(Class clz) {
        // 设置需要创建的子类
        enhancer.setSuperclass(clz);
        enhancer.setCallback(this);
        //反射机制获得实例构造，并创建代理类对象，主题类的构造函数不是默认空参数的，那么在使用Enhancer类create的时候，
        //选择create(java.lang.Class[] argumentTypes, java.lang.Object[] arguments) 方法。
        // 通过字节码技术动态创建子类实例
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        LOGGER.info(o.getClass().getName());
        if (startMethod != null) {
            startMethod.invoke(object, null);
        }
        // 通过代理类调用父类中的方法
        Object result = methodProxy.invokeSuper(o , objects);

        if(endMethod != null) {
            endMethod.invoke(object, null);
        }

        return result;
    }
}
