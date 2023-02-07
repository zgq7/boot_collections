package com.boot.netty.server.socketio.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/16 15:45
 **/
public class GroupModel {

	/**
	 * 消息来源
	 **/
	private String fromUuid;

	/**
	 * 群组ID
	 **/
	private String groupId;

	/**
	 * 消息ID，以防止消息丢失
	 **/
	private String msgId;

	/**
	 * 消息内容
	 **/
	private String msg;

	/**
	 * 消息类型  text 文本 img 图片  emoji 表情
	 **/
	private String type;

	/**
	 * 被转换为JSON字符串后会自动矫正格式
	 **/
	@JSONField(format = "yyyy-MM-dd HH:mm")
	private Date time;

	/**
	 * 用户名称
	 **/
	private String name;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
