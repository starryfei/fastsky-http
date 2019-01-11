package BeanFactory;

import com.starry.fastsky.annotation.FastController;
import org.junit.Test;

import java.lang.annotation.Annotation;

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
//        for(Annotation annotation :cla.getAnnotations()) {
//            System.out.println(annotation.getClass().getName());
//        }
        assert cla.getAnnotation(FastController.class) != null;
    }

}
