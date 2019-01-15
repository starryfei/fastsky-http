package beanfactory;

import com.starry.fastsky.annotation.FastController;
import com.starry.fastsky.factory.BeanFactory;
import com.starry.fastsky.factory.DefaultBeanFactory;
import com.starry.fastsky.test.Demo;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ClassName: BeanFactoryTest
 * Description: TODO
 * Author: starryfei
 * Date: 2019-01-11 14:44
 **/

public class BeanFactoryTest {
    @Test
    public void fastskyController() throws ClassNotFoundException {
        Class cla = Class.forName("com.starry.fastsky.test.Demo");
        assert cla.getName().equals("com.starry.fastsky.test.Demo");
        assert cla.getAnnotation(FastController.class) != null;
    }

    @Test
    public void runhello() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String name ="com.starry.fastsky.test.Demo";
        System.out.println(name);
        String[] arrays = (name.replaceAll("\\.","/")).split("/");
        for (String str: arrays
             ) {
            System.out.println(str);
        }
//        System.out.println(arrays[arrays.length-1]);
        BeanFactory beanFactory = new DefaultBeanFactory();
        Demo demo = new Demo();
        Method method = demo.getClass().getMethod("hello");
        String name1 =  method.getDeclaringClass().getName();
        Object obj = method.invoke(demo.getClass(),null);
        System.out.println(obj+" "+name1);
    }

}
