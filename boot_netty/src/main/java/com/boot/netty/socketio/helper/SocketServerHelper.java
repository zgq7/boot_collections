package com.boot.netty.socketio.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.netty.socketio.common.ConstantKeys;
import com.boot.netty.socketio.config.SocketServerConfig;
import com.boot.netty.socketio.model.ClientAssignment;
import com.boot.netty.socketio.model.GroupClient;
import com.boot.netty.socketio.model.SingleClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/13 18:32
 **/
public class SocketServerHelper implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(SocketServerHelper.class);

	@Resource(name = SocketServerConfig.SERVER_NAME)
	private SocketIOServer server;
	@Resource
	private SingleClient singleClient;
	@Resource
	private GroupClient groupClient;
	@Resource
	private ClientAssignment clientAssignment;
	@Resource
	private TalkingHelper talkingHelper;

	private DataListener<String> registryListner;
	private DataListener<String> singChatListener;
	private DataListener<String> groupRegistListener;
	private DataListener<String> groupChatListener;

	/**
	 * @author Leethea
	 * @apiNote 初始化socket服务器的监听事件
	 * @date 2020/3/16 15:42
	 **/
	public void initEvent() {
		server.addConnectListener(client -> {
			JSONObject response = new JSONObject();
			//是否需要登录
			response.put("needLogin", true);
			try {
				String uuid = CacheHelper.getInstance().getUserName(client.getSessionId().toString());
				if (StringUtils.isNoneBlank(uuid)) {
					logger.info("用户 uuid = {} 不需要再次登录", uuid);
					groupClient.add(uuid, client);
					response.put("needLogin", false);
					response.put("uuid", uuid);
					JSONObject data = new JSONObject();
					data.put("uuid", uuid);
					data.put("name", uuid);
					talkingHelper.tellOthers(client, ConstantKeys.CLIENT_EVENT_WHO_ONLINE, JSON.toJSONString(data));
				}
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			client.sendEvent(ConstantKeys.CLIENT_EVENT_NEED_LOGIN, JSON.toJSONString(response));
		});

		server.addDisconnectListener(client -> {
			talkingHelper.tellOthers(client, ConstantKeys.CLIENT_EVENT_WHO_OUT_ONLINE, groupClient.getUuid(client));
			groupClient.remove(client);
		});

		server.addPingListener(client -> {
			//logger.info("正在ping 客戶端 clientid = {}", client.getSessionId().toString());
		});

		//注冊事件、上线、支持单聊
		server.addEventListener(ConstantKeys.CLIENT_EVENT_REGISTRY, String.class, registryListner);
		//消息事件 单发
		server.addEventListener(ConstantKeys.CLIENT_EVENT_MESSAGE_SEND, String.class, singChatListener);
		//注冊事件、上线、支持群聊
		server.addEventListener(ConstantKeys.CLIENT_EVENT_REGISTRY_GROUP, String.class, groupRegistListener);
		//消息事件 群发
		server.addEventListener(ConstantKeys.CLIENT_EVENT_MESSAGE_SEND_GROUP, String.class, groupChatListener);
	}

	@Override
	public void afterPropertiesSet() {
		registryListner = clientAssignment::registryCall;
		singChatListener = clientAssignment::singChatCall;
		groupChatListener = clientAssignment::groupChatCall;
		groupRegistListener = clientAssignment::registryGroupCall;
	}

}
