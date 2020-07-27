package com.boot.netty.common;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/16 12:42
 **/
public class ConstantKeys {

	/**
	 * 客户端唯一ID
	 **/
	public static final String UUID = "uuid";

	/**
	 * 客户端注册事件
	 **/
	public static final String CLIENT_EVENT_REGISTRY = "registry";

	/**
	 * 客户端注册事件
	 **/
	public static final String CLIENT_EVENT_REGISTRY_GROUP = "registryGroup";

	/**
	 * 客戶端回应事件
	 **/
	public static final String CLIENT_EVENT_REGISTRY_RESULT = "registryResult";

	/**
	 * 客户端消息事件 单发
	 **/
	public static final String CLIENT_EVENT_MESSAGE_SEND = "messageSend";

	/**
	 * 客户端消息事件 群发
	 **/
	public static final String CLIENT_EVENT_MESSAGE_SEND_GROUP = "messageSendGroup";

	/**
	 * 客户端消息事件
	 **/
	public static final String CLIENT_EVENT_MESSAGE_SEND_RESULT = "messageSendResult";

	/**
	 * 客户端消息事件响应结果
	 **/
	public static final String CLIENT_EVENT_MESSAGE_RECIEVE = "messageRecieve";

	/**
	 * 通知谁上线
	 **/
	public static final String CLIENT_EVENT_WHO_ONLINE = "whoOnline";

	/**
	 * 通知谁下线
	 **/
	public static final String CLIENT_EVENT_WHO_OUT_ONLINE = "whoOutOnline";

	/**
	 * 是否需要再登录
	 **/
	public static final String CLIENT_EVENT_NEED_LOGIN = "needLogin";

}
