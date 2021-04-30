package com.boot.netty.socketio.model;

import com.alibaba.fastjson.JSON;
import com.boot.netty.socketio.common.ConstantKeys;
import com.boot.netty.socketio.helper.CacheHelper;
import com.boot.netty.socketio.helper.TalkingHelper;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/19 14:10
 **/
public class ClientAssignment {

	private static final Logger logger = LoggerFactory.getLogger(ClientAssignment.class);

	@Resource
	private SingleClient singleClient;
	@Resource
	private GroupClient groupClient;
	@Resource
	private TalkingHelper talkingHelper;


	/**
	 * @author Leethea
	 * @apiNote 注册回应
	 * @date 2020/3/19 14:13
	 **/
	public void registryCall(SocketIOClient client, String data, AckRequest ackSender) {
		String uuid = JSON.parseObject(data).getString(ConstantKeys.UUID);
		logger.info("客户端 uuid = {} 请求注册,id = {} 地址 = {}", uuid, client.getSessionId().toString(), client.getRemoteAddress());
		//当uuid不存在,client也不存在是进行新增
		if (uuid == null) {
			client.sendEvent("logout", "请选择用户！");
		}
		if (!singleClient.contain(client) && !singleClient.contain(uuid)) {
			singleClient.add(uuid, client);
			//对客户端的呼叫事件进行回应
			client.sendEvent(ConstantKeys.CLIENT_EVENT_REGISTRY_RESULT, Boolean.toString(true));
		}
		//uuid 存在,socket不存在，不能抢占他人账号
		else if (!singleClient.contain(client) && singleClient.contain(uuid)) {
			client.sendEvent("logout", "该账号已登录，请使用其他账号！");
			client.sendEvent(ConstantKeys.CLIENT_EVENT_REGISTRY_RESULT, Boolean.toString(false));
		}
		//若client 已存在，说明已登录过,此时账号需要进行更新
		else if (singleClient.contain(client) && !singleClient.contain(uuid)) {
			client.sendEvent("logout", "成功切换账号！");
			singleClient.update(uuid, client);
		}
		// uuid存在,client也存在
		else if (singleClient.contain(uuid) && singleClient.contain(client)) {
			//值对应一个账户时
			if (singleClient.contain(uuid, client)) {
				client.sendEvent("logout", "请勿重复登录！");
			} else {
				client.sendEvent("logout", "用户" + uuid + " 请勿登录他人账号！");
			}
		} else {
			client.sendEvent(ConstantKeys.CLIENT_EVENT_REGISTRY_RESULT, Boolean.toString(false));
		}
	}


	/**
	 * @author Leethea
	 * @apiNote 单聊回应
	 * @date 2020/3/19 14:20
	 **/
	public void singChatCall(SocketIOClient client, String data, AckRequest ackSender) {
		SingleModel msgModel = JSON.parseObject(data, SingleModel.class);
		String uuid = msgModel.getUuid();
		String msgId = msgModel.getMsgId();
		String fromUuid = singleClient.getUuid(client);
		if (fromUuid == null) {
			client.sendEvent("logout", "尚未登录！");
			return;
		}
		msgModel.setFromUuid(fromUuid);
		logger.info("客户端 uuid = {} 有新消息,发起人：{}", uuid, fromUuid);
		Map<String, Object> call = new HashMap<>(3);
		call.put("msgId", msgId);
		boolean canSend = uuid != null && singleClient.contain(uuid) && singleClient.contain(fromUuid);
		if (canSend) {
			logger.info("正在发送...");
			singleClient.getClient(uuid).sendEvent(ConstantKeys.CLIENT_EVENT_MESSAGE_RECIEVE,
					JSON.toJSONString(msgModel));
			call.put("flag", true);
			client.sendEvent(ConstantKeys.CLIENT_EVENT_MESSAGE_SEND_RESULT, call);
		} else {
			call.put("flag", false);
			client.sendEvent(ConstantKeys.CLIENT_EVENT_MESSAGE_SEND_RESULT, call);
		}
	}

	/**
	 * @author Leethea
	 * @apiNote 群组注册回应
	 * @date 2020/3/19 14:13
	 **/
	public void registryGroupCall(SocketIOClient client, String data, AckRequest ackSender) {
		String uuid = JSON.parseObject(data).getString(ConstantKeys.UUID);
		String name = JSON.parseObject(data).getString("name");
		logger.info("客户端 uuid = {} 请求注册,id = {} 地址 = {}", uuid, client.getSessionId().toString(), client.getRemoteAddress());
		boolean flag = false;
		//当uuid不存在,client也不存在是进行新增
		if (StringUtils.isBlank(uuid)) {
			client.sendEvent("logout", "请选择用户！");
		}
		if (!groupClient.contain(client) && !groupClient.contain(uuid)) {
			groupClient.add(uuid, client);
			//对客户端的呼叫事件进行回应
			client.sendEvent(ConstantKeys.CLIENT_EVENT_REGISTRY_RESULT, Boolean.toString(true));
			CacheHelper.getInstance().putUserName(client.getSessionId().toString(), uuid);
			flag = true;
		}
		//uuid 存在,socket不存在，不能抢占他人账号
		else if (!groupClient.contain(client) && groupClient.contain(uuid)) {
			client.sendEvent("logout", "该账号已登录，请使用其他账号！");
			client.sendEvent(ConstantKeys.CLIENT_EVENT_REGISTRY_RESULT, Boolean.toString(false));
		}
		//若client 已存在，说明已登录过,此时账号需要进行更新
		else if (groupClient.contain(client) && !groupClient.contain(uuid)) {
			client.sendEvent("logout", "成功切换账号！");
			groupClient.update(uuid, client);
			flag = true;
		}
		// uuid存在,client也存在
		else if (groupClient.contain(uuid) && groupClient.contain(client)) {
			//值对应一个账户时
			if (groupClient.contain(uuid, client)) {
				client.sendEvent("logout", "请勿重复登录！");
			} else {
				client.sendEvent("logout", "用户" + uuid + " 请勿登录他人账号！");
			}
		} else {
			client.sendEvent(ConstantKeys.CLIENT_EVENT_REGISTRY_RESULT, Boolean.toString(false));
		}
		if (flag) {
			talkingHelper.tellOthers(client, ConstantKeys.CLIENT_EVENT_WHO_ONLINE, data);
		}
	}

	/**
	 * @author Leethea
	 * @apiNote 群组消息回应
	 * @date 2020/3/19 14:20
	 **/
	public void groupChatCall(SocketIOClient client, String data, AckRequest ackSender) {
		GroupModel groupModel = JSON.parseObject(data, GroupModel.class);
		String groupId = groupModel.getGroupId();
		String msgId = groupModel.getMsgId();
		String fromUuid = groupClient.getUuid(client);
		String msg = groupModel.getMsg();
		//防 xss 攻击 <script>alert('一日三餐没烦恼')</script>
		groupModel.setMsg(StringEscapeUtils.escapeHtml4(msg));
		if (fromUuid == null) {
			client.sendEvent("logout", "尚未登录！");
			return;
		}
		if (groupId == null) {
			groupId = GroupClient.GROUPID;
		}
		groupModel.setFromUuid(fromUuid);
		logger.info("客户端 groupId = {} 有新消息,发起人：{}", groupId, fromUuid);
		Map<String, Object> call = new HashMap<>(3);
		call.put("msgId", msgId);
		boolean canSend = groupId.equals(GroupClient.GROUPID);
		if (canSend) {
			logger.info("正在发送...");
/*			talkingHelper.tellOthers(client, ConstantKeys.CLIENT_EVENT_MESSAGE_RECIEVE,
					JSON.toJSONStringWithDateFormat(groupModel, "yyyy-MM-dd HH:mm", SerializerFeature.WriteDateUseDateFormat));*/
			talkingHelper.tellOthers(client, ConstantKeys.CLIENT_EVENT_MESSAGE_RECIEVE, JSON.toJSONString(groupModel));
			CacheHelper.getInstance().put(msgId, JSON.toJSONString(groupModel));
			call.put("flag", true);
			client.sendEvent(ConstantKeys.CLIENT_EVENT_MESSAGE_SEND_RESULT, call);
		} else {
			call.put("flag", false);
			client.sendEvent(ConstantKeys.CLIENT_EVENT_MESSAGE_SEND_RESULT, call);
		}
	}


}
