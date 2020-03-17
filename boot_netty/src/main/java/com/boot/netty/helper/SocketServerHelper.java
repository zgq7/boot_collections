package com.boot.netty.helper;

import com.alibaba.fastjson.JSON;
import com.boot.netty.common.ConstantKeys;
import com.boot.netty.common.MsgModel;
import com.boot.netty.config.SocketServerConfig;
import com.boot.netty.model.SingleClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/13 18:32
 **/
public class SocketServerHelper {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerHelper.class);

	@Resource(name = SocketServerConfig.SERVER_NAME)
	private SocketIOServer server;
	@Resource
	private SingleClient singleClient;

	/**
	 * @author Leethea
	 * @apiNote 初始化socket服务器的监听事件
	 * @date 2020/3/16 15:42
	 **/
	public void initEvent() {
		server.addConnectListener(client -> {
			logger.info("远程客户端 ip = {} 已连接！", client.getRemoteAddress());
		});
		server.addDisconnectListener(client -> {
			logger.info("远程客户端 ip = {} 已断开！", client.getRemoteAddress());
		});
		server.addPingListener(client -> logger.info("clientid = {} 正在ping", client.getSessionId()));
		//注冊事件
		server.addEventListener(ConstantKeys.CLIENT_EVENT_REGISTRY, String.class, (client, data, ackSender) -> {
			String uuid = JSON.parseObject(data).getString(ConstantKeys.UUID);
			logger.info("客户端 uuid = {} 请求注册", uuid);
			if (uuid != null) {
				if (!singleClient.contain(uuid)) {
					singleClient.add(uuid, client);
				}
				//对客户端的呼叫事件进行回应
				client.sendEvent(ConstantKeys.CLIENT_EVENT_REGISTRY_RESULT, Boolean.toString(true));
			} else {
				client.sendEvent(ConstantKeys.CLIENT_EVENT_REGISTRY_RESULT, Boolean.toString(false));
			}
		});
		//消息事件
		server.addEventListener(ConstantKeys.CLIENT_EVENT_MESSAGE_SEND, String.class, (client, data, ackSender) -> {
			String uuid = JSON.parseObject(data, MsgModel.class).getUuid();
			String msgId = JSON.parseObject(data, MsgModel.class).getMsgId();
			logger.info("客户端 uuid = {} 有新消息", uuid);
			if (singleClient.contain(uuid)) {
				singleClient.getClient(uuid).sendEvent(ConstantKeys.CLIENT_EVENT_MESSAGE_RECIEVE, data);
				client.sendEvent(ConstantKeys.CLIENT_EVENT_MESSAGE_SEND_RESULT, true, msgId);
			}
		});
	}

}
