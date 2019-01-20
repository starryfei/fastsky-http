# [fastsky](https://starryfei.github.io/fastsky/ "fastsky")

### 简介
基于Netty http实现的简单的web框架

### 项目概述
- [x] 支持消息路由
- [x] bean的集中管理
- [x] 支持注解
- [x] 配置文件的读取
- [x] 切面
- [x] 支持多种响应方式(json,html,text)
- [ ] 模板引擎支持
- [x] [支持netty https/http](https://www.zuoyanyouwu.com/2017/01/netty-ssl-using-and-analyze/)

------------

### 设计思路
- 加载配置文件
- 扫描用户配置包下面所有的类
- 拿到扫描到的类，通过反射机制，实例化。并且放到ioc容器中(Map的键值对  beanName-bean)
- 解析用户http请求，根据路由地位到对应到bean的方法上

### demo
#### 启动类
```java
public class FastSkyDemo {
    
    public static void main(String[] args) {
        FastSkyServer.start(FastSkyDemo.class);

    }
}
```
#### 配置文件
```properties
# path
fastsky.server.path = demo
# 端口
fastsky.server.port = 9121
#  开启https
fastsky.server.ssl = true
```

##### Controller编码
```java
@FastController(value = "/Demo")
public class Demo  {
    @FastRoute(path = "/hello")
    public String hello(int a, String b){
        return a+" "+b;
    }
    @FastRoute(path = "/getUser",type = FastSkyServerResponse.JSON)
    public User HH(User user){
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

public class User {
    private String name;
    private String pwd;

    # 省略类setget 
}
```
访问 http://127.0.0.1:9121/demo/Demo/getUser?name=fastsky&pwd=1243
响应信息： {"name":"fastsky","pwd":"1243"}
[![](响应)](https://github.com/starryfei/fastsky/img/http.png)
#### 切面实现思路
- 扫描带有@FastAspect注解的类，根据该注解配置需要使用的类，可以同时配置多个，以分号分割，从BeanFactory中获取该类，
- 根据获取到类，使用CGLib生成动态类，同时将标有@Before和@After添加到该动态类中
- 将这个动态类管理到BeanFactory中，同时将原有的Bean从BeanFactory中移除

```java
BeanFactoryManager manager = BeanFactoryManager.getInstance();
Object object = manager.getBean(classPath);
Object instance = cla.newInstance();
CGLibProxy cgLibProxy = new CGLibProxy(start,end,instance);
Object obj = cgLibProxy.getProxy(object.getClass());
// 移除原有的bean
manager.remove(classPath);
manager.register(classPath, obj);
```
##### 切面的使用
```java
@FastAspect("com.starry.fastsky.test.Demo")
public class Log {
    private static final Logger LOGGER = LoggerBuilder.getLogger(Log.class);
    private Long start;
    private Long end;

    @Before()
    public void before() {
        start = System.currentTimeMillis();
        LOGGER.info("---before---");
    }
    @After()
    public void after() {
        end = System.currentTimeMillis();
        LOGGER.info("cast [{}] times", end - start);
        LOGGER.info("---after---");
    }
}

```
