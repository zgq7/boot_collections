package com.boot.client.controller;

import com.alibaba.fastjson.JSON;
import com.boot.client.common.ConstantKeys;
import com.boot.client.common.MsgModel;
import com.boot.client.socket.SingleTonSocketClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/16 16:35
 **/
@RestController("/msg")
public class MsgController {

	@PostMapping
	public void msg(@RequestBody MsgModel msgModel) {
		SingleTonSocketClient.getInstance()
				.getClient()
				.emit(ConstantKeys.CLIENT_EVENT_MESSAGE_SEND, JSON.toJSONString(msgModel));
	}

}
