package com.boot.mongodb.ssh;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/13 14:10
 **/
@ConfigurationProperties(prefix = "spring.ssh")
public class JschProperties {

	private String proxyHost;

	private String proxyUserName;

	private String proxyPassword;

	private Integer proxyPort;

	private String strictHostKeyChecking = "no";

	private String destinationHost;

	private Integer destinationPort;

	private Integer localPort;

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyUserName() {
		return proxyUserName;
	}

	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getStrictHostKeyChecking() {
		return strictHostKeyChecking;
	}

	public void setStrictHostKeyChecking(String strictHostKeyChecking) {
		this.strictHostKeyChecking = strictHostKeyChecking;
	}

	public String getDestinationHost() {
		return destinationHost;
	}

	public void setDestinationHost(String destinationHost) {
		this.destinationHost = destinationHost;
	}

	public Integer getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(Integer destinationPort) {
		this.destinationPort = destinationPort;
	}

	public Integer getLocalPort() {
		return localPort;
	}

	public void setLocalPort(Integer localPort) {
		this.localPort = localPort;
	}
}
