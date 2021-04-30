package com.boot.netty.socketio.config;

import com.boot.netty.socketio.helper.SocketServerHelper;
import com.boot.netty.socketio.helper.TalkingHelper;
import com.boot.netty.socketio.model.ClientAssignment;
import com.boot.netty.socketio.model.GroupClient;
import com.boot.netty.socketio.model.SingleClient;
import com.boot.netty.socketio.properties.SocketIoProperties;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * created on 2019/11/19
 *
 * @author Leethea_廖南洲
 * 主要依赖：https://mvnrepository.com/artifact/com.corundumstudio.socketio/netty-socketio
 * @apiNote 基于 netty 的 socketIO 实现
 **/
@Configuration
@EnableConfigurationProperties(SocketIoProperties.class)
public class SocketServerConfig {

	public static final String SERVER_NAME = "server";

	private final SocketIoProperties properties;

	public SocketServerConfig(SocketIoProperties socketIoProperties) {
		this.properties = socketIoProperties;
	}

	@Bean(name = SERVER_NAME)
	public SocketIOServer server() {
		com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
		//configuration.setExceptionListener();
		configuration.setPingInterval(1000 * 600);
		configuration.setPingTimeout(15000);
		// 文档建议:0 = current_processors_amount * 2
		configuration.setBossThreads(4);
		configuration.setWorkerThreads(8);
		configuration.setMaxFramePayloadLength(2 * 1024 * 1024);
		configuration.setMaxHttpContentLength(2 * 1024 * 1024);
		//direct buffer 直接由堆内存分配
		configuration.setPreferDirectBuffer(false);
		//设置 socket 配置
		SocketConfig socketConfig = new SocketConfig();
		socketConfig.setTcpKeepAlive(true);
		socketConfig.setTcpNoDelay(true);
		socketConfig.setSoLinger(0);

		configuration.setSocketConfig(socketConfig);

		configuration.setHostname(properties.getHost());
		configuration.setPort(properties.getPort());
		configuration.setOrigin(properties.getOriginHost());
		return new SocketIOServer(configuration);
	}

	@Bean
	public SingleClient singleClient() {
		return new SingleClient();
	}

	@Bean
	public SocketServerHelper socketServerHelper() {
		return new SocketServerHelper();
	}

	@Bean
	public ClientAssignment clientAssignment() {
		return new ClientAssignment();
	}

	@Bean
	public GroupClient groupClient() {
		return new GroupClient();
	}

	@Bean
	public TalkingHelper talkingHelper() {
		return new TalkingHelper();
	}

	/**
	 * @author Leethea
	 * @apiNote 不知道幹嘛的
	 * @date 2020/3/19 15:40
	 **/
	@Bean
	public SpringAnnotationScanner springAnnotationScanner() {
		return new SpringAnnotationScanner(server());
	}


}
