package com.boot.netty.socketio.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/13 18:11
 **/
@ConfigurationProperties(prefix = "socket.io.server")
public class SocketIoProperties {

	private String host;

	private Integer port;

	private String originHost;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getOriginHost() {
		return originHost;
	}

	public void setOriginHost(String originHost) {
		this.originHost = originHost;
	}
}
