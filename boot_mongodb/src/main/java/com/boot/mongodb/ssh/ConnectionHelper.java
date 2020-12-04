package com.boot.mongodb.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/13 11:01
 **/
@Configuration
@ConditionalOnProperty(prefix = "spring.ssh", name = "enable", havingValue = "true")
@EnableConfigurationProperties(JschProperties.class)
public class ConnectionHelper implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionHelper.class);
    private final JschProperties properties;
    private Session sshSession;

	public ConnectionHelper(JschProperties jschProperties) {
		this.properties = jschProperties;
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        JSch jsch = new JSch();
        // ssh 代理服务器连接信息
        sshSession = jsch.getSession(properties.getProxyUserName(), properties.getProxyHost(), properties.getProxyPort());
        sshSession.setPassword(properties.getProxyPassword());
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", properties.getStrictHostKeyChecking());
        sshSession.setConfig(config);
        sshSession.connect();
        // 代理地址
        sshSession.setPortForwardingL(properties.getLocalPort(), properties.getDestinationHost(),
                properties.getDestinationPort());
        if (sshSession.isConnected()) {
            logger.info("ssh mongodb 连接成功！");
        }
    }

    @Override
    public void destroy() throws Exception {
        sshSession.disconnect();
    }

}
