package com.boot.redission.controller;

import com.boot.redission.expire.RedissonKeyExpireListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liaonanzhou
 * @date 2021/12/16 17:31
 * @description
 **/
@RequestMapping(value = "/expire")
@RestController
public class ExpireController {

    private final RedissonKeyExpireListener redissonKeyExpireListener;

    public ExpireController(RedissonKeyExpireListener redissonKeyExpireListener) {
        this.redissonKeyExpireListener = redissonKeyExpireListener;
    }

    @PostMapping
    public String expire(@RequestParam String key, @RequestParam String value) {
        redissonKeyExpireListener.addKeyListener(key, value);
        return "ok";
    }


}
