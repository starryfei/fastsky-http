package com.starry.fastsky.test;

import com.starry.fastsky.annotation.FastController;
import com.starry.fastsky.annotation.FastRoute;

/**
 * ClassName: Demo
 * Description: TODO
 * Author: starryfei
 * Date: 2019-01-11 14:46
 **/
@FastController(value = "/Demo")
public class Demo  {
    @FastRoute()
    public static String hello(){

        return "hello ,world";
    }
}
