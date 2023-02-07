package com.boot.client;

import com.boot.client.socket.SingleTonSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @author Leethea
* @apiNote
* @date 2020/3/17 16:34
**/
@SpringBootApplication
public class NettyClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyClientApplication.class, args);
		SingleTonSocketClient.getInstance().getClient().connect();
	}

}
