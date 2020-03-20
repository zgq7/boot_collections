package com.boot.netty.controller;

import com.alibaba.fastjson.JSON;
import com.boot.netty.config.SocketServerConfig;
import com.boot.netty.model.GroupClient;
import com.boot.netty.model.SingleClient;
import com.corundumstudio.socketio.ClientOperations;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/19 10:50
 **/
@RestController
@RequestMapping
public class SocketController {

	@Resource
	private SingleClient singleClient;
	@Resource
	private GroupClient groupClient;
	@Resource(name = SocketServerConfig.SERVER_NAME)
	private SocketIOServer server;

	@GetMapping("/flush")
	public Object refresh() {
		server.getAllClients().forEach(ClientOperations::disconnect);
		//singleClient.clear();
		groupClient.clear();
		return "success";
	}

	@GetMapping("/all")
	public Object all() {
		//return JSON.toJSONString(singleClient.getAll());
		return JSON.toJSONString(groupClient.getAll());
	}

}
