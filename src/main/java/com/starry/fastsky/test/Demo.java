package com.starry.fastsky.test;

import com.starry.fastsky.annotation.FastController;
import com.starry.fastsky.annotation.FastRoute;
import com.starry.fastsky.enums.FastSkyServerResponse;

/**
 * ClassName: Demo
 * Description: 测试类
 * Author: starryfei
 * Date: 2019-01-11 14:46
 **/
@FastController(value = "/Demo")
public class Demo  {
    @FastRoute(path = "/hello")
    public static String hello(int a, String b){

        return a+" "+b;
    }
    @FastRoute(path = "/getUser",type = FastSkyServerResponse.JSON)
    public static User HH(User user){
        return user;
    }
}
