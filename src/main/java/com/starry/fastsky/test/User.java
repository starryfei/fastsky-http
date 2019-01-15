package com.starry.fastsky.test;

/**
 * ClassName: User
 * Description: TODO
 *
 * @author: starryfei
 * @date: 2019-01-15 14:27
 **/
public class User {
    private String name;
    private String pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
