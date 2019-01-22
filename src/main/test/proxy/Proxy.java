package proxy;

import com.starry.fastsky.annotation.After;
import com.starry.fastsky.annotation.Before;
import com.starry.fastsky.annotation.Pointcut;
import com.starry.fastsky.common.FastskyCommon;
import com.starry.fastsky.proxy.CGLibProxy;
import com.starry.fastsky.test.Demo;
import com.starry.fastsky.test.annontation_test.Log;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: Proxy.Proxy
 * Description: TODO
 *
 * @author: starryfei
 * @date: 2019-01-18 16:13
 **/
public class Proxy {
    @Test
    public void proxytest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> log = Log.class;
        Object cla = log.newInstance();
        Map<String,Method> map = new HashMap<>(3);
        for (Method method : log.getDeclaredMethods()) {
            Pointcut pointcut = method.getAnnotation(Pointcut.class);
            Before before = method.getAnnotation(Before.class);
            After after = method.getAnnotation(After.class);
            if (pointcut != null) {
                map.put(FastskyCommon.POINTCUT,method);
            }
            if (before != null) {
                map.put(FastskyCommon.START,method);
            }
            if (after != null) {
                map.put(FastskyCommon.END, method);
            }
        }
        Method start = map.get(FastskyCommon.START);
        Method end = map.get(FastskyCommon.END);
        CGLibProxy cgLibProxy = new CGLibProxy(start,end,cla);
        Object  test = cgLibProxy.getProxy(Demo.class);
//        test.test();
        Method method = Demo.class.getMethod("getUsers",null);
        method.invoke(test, null);
//        obj.

        }

    @Test
    public void proxy11(){
        CGLibProxy cgLibProxy = new CGLibProxy();
        ProxyTest  test = (ProxyTest) cgLibProxy.getProxy(ProxyTest.class);
        test.test();
    }

}

