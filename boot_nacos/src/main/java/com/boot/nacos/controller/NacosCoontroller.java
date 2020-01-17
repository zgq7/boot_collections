package com.boot.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description nacos测试
 * @Author Leethea_廖南洲
 * @Date 2019/12/31 10:22
 * @Vesion 1.0
 **/
@RestController
public class NacosCoontroller {

    @NacosValue(value = "${alive:SB}", autoRefreshed = true)
    private String avlie;

    @GetMapping(value = "/nacos")
    public String get() {
        return "hello nacos " + avlie;
    }
}
