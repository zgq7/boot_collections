package com.boot.netty.server.socketio.model;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/16 15:45
 **/
public class SingleModel {

	private String fromUuid;
	/**
	 * 客户端ID
	 **/
	private String uuid;

	/**
	 * 消息ID，以防止消息丢失
	 **/
	private String msgId;

	/**
	 * 消息内容
	 **/
	private String msg;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFromUuid() {
		return fromUuid;
	}

	public void setFromUuid(String fromUuid) {
		this.fromUuid = fromUuid;
	}
}
