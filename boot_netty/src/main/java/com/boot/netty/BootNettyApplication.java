package com.boot.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimeZone;

/**
 * @author Leethea
 * @apiNote
 * @date 2020/3/17 14:26
 **/
@SpringBootApplication
public class BootNettyApplication {

	private static final Logger logger = LoggerFactory.getLogger(BootNettyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BootNettyApplication.class, args);
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String hostname = addr.getHostName();
			logger.info("IP地址：{},机器名称：{}", addr.getHostAddress(), hostname);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
