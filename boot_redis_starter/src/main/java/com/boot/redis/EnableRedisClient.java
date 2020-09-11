package com.boot.redis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author liaonanzhou
 * @date 2020/8/28 10:17
 * @description 启用redis starter 下的redis 工具包
 * <p> 如何使依赖中的spring configuration 生效
 * 1：@Import 主要作用是启用具体的某些spring configuration
 * 2：可以使用spring自动装配机制（spring autoconfigure starter），在spring.factories 文件表明你要启动的spring configuration
 * 参考 https://www.baeldung.com/spring-boot-custom-starter
 **/
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {RedisConfig.class, RedisComConfig.class})
public @interface EnableRedisClient {

//FIXME
// @Target 注解主要表明当前注解的修饰作用域，是类、方法、亦或是属性...
// @Document 用于描述该类型的annotation应该被作为被标注的程序成员的公共API
// @Retention 表明该注解何时有效
// @Import 是spring 的注解，代表启用 xxxConfiguration

}
