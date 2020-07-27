package com.boot.auth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/5/26 13:41
 **/
@RestController
@RequestMapping(value = "/data")
public class DataController {

	@GetMapping(value = "/users")
	public Map<String, Object> users() {
		HashMap<String, Object> data = new HashMap<>();
		data.put("user1", "1");
		data.put("user2", "2");
		data.put("user3", "3");
		return data;
	}

}
