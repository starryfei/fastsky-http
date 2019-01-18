package com.starry.fastsky.test;

import com.starry.fastsky.annotation.FastController;
import com.starry.fastsky.annotation.FastRoute;
import com.starry.fastsky.enums.FastSkyServerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: Demo
 * Description: 测试类
 * Author: starryfei
 * Date: 2019-01-11 14:46
 **/
@FastController(value = "/Demo")
public class Demo  {
    @FastRoute(path = "/hello")
    public String hello(int a, String b){

        return a+" "+b;
    }
    @FastRoute(path = "/getUser",type = FastSkyServerResponse.JSON)
    public  User getUser(User user){
        return user;
    }

    @FastRoute(path = "/getAllUser",type = FastSkyServerResponse.JSON)
    public  List<User> getUsers(){
        User user = new User();
        user.setName("aa");
        user.setPwd("123");
        User user1 = new User();
        user1.setName("bb");
        user1.setPwd("456");
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        return users;
    }
}
