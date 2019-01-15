package com.starry.fastsky.test;

import com.starry.fastsky.annotation.FastController;
import com.starry.fastsky.annotation.FastRoute;
import com.starry.fastsky.enums.FastSkyServerResponse;

/**
 * ClassName: Demo
 * Description: TODO
 * Author: starryfei
 * Date: 2019-01-11 14:46
 **/
@FastController(value = "/Demo")
public class Demo  {
    @FastRoute()
    public static String hello(int a, String b){

        return a+" "+b;
    }
    @FastRoute(type = FastSkyServerResponse.JSON)
    public static User HH(User user){
        return user;
    }
}
