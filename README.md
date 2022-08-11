# boot_collections

- 练手项目，博客梳理
- SpringBoot 集成当前流行框架
- Welcome to fork and start

## 1：SpringBoot 整合 JPA

  已完成学习，不做博客介绍。
    
    如何手写一个jdbc 连接池？
    >>>>看项目boot_jdbc。

## 2：RabbitMQ 的使用

博客地址：https://www.cnblogs.com/zgq7/p/13692616.html

## 3：RabbitMQ 死信队列

博客地址：https://www.cnblogs.com/zgq7/p/13785760.html

## 4：SpringCloud stream 整合RabbitMQ

博客地址：https://www.cnblogs.com/zgq7/p/13903644.html

## 5：SpringBoot 整合 Redis (Lettuce)
  
  1：自定义SpringBoot Starter
  
    A：使用spring.facotries 文件 (SPI机制)
    
    B：使用Spring的 @Import 注解加载第三方包的 Bean配置
    
## 6：websocket/socketIO 的 学习
  
    1：基于Netty的 socket.io 的使用
  
    2：原生websocket 的使用

## 7：SpringBoot 整合 MongoDB

博客地址：https://www.cnblogs.com/zgq7/p/12336016.html

## 8：SpringBoot 整合 ElasticSearch

博客地址：https://www.cnblogs.com/zgq7/p/13885990.html


## 9：SpringBoot 整合 Redisson

博客地址：https://www.cnblogs.com/zgq7/p/14746128.html
    
    1：分布式可重入锁思想
    
    2：监听redis key 过期
    
## 10：SpringBoot 整合 Sharding-jdbc

不做博客介绍。
    
    1：io.shardingjdbc 依赖（我的练手项目使用依赖）
    2：org.apache.shardingsphere 依赖
    3：shardingsphere-jdbc-core-spring-boot-starter 依赖
    
    以上三个版本都可以使用，第三种由SpringBoot支持整合进项目比较方便。
    
    请勿使用该依赖：
    <dependency>
        <groupId>org.apache.shardingsphere</groupId>
        <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
        <version>4.1.1</version>
    </dependency>
    
    改版本停更，且存在bug，不建议使用。
    bug详情：因为基于jdk7开发的，因此在使用mapper时不能使用localdatetime 类型的参数。
