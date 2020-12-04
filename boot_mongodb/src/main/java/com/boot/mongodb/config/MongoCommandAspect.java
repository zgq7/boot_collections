package com.boot.mongodb.config;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author liaonanzhou
 * @date 2020-12-01 17:51
 * @description
 */
@Aspect
public class MongoCommandAspect {

    private final Logger logger = LoggerFactory.getLogger(MongoTemplate.class);

    @Pointcut(value = "execution(public * org.springframework.data.mongodb.core.MongoTemplate.*(..))")
    //@Pointcut(value = "execution(public * org.slf4j.Logger.debug(..)))")
    public void point() {
    }

    @Before(value = "point()")
    public void before() {
        logger.info("===>> mongo start ===>>");
    }

    @AfterReturning(value = "point()")
    public void afterReturning() {
        logger.info("<<=== mongo end << ===");
    }

}
