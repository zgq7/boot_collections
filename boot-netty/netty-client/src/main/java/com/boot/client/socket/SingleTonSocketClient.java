package com.boot.client.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.client.common.ConstantKeys;
import com.boot.client.common.MsgModel;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/16 11:38
 **/
public class SingleTonSocketClient {

	private final static Logger logger = LoggerFactory.getLogger(SingleTonSocketClient.class);
	/**
	 * socket server 地址
	 **/
	//private static final String SERVER_IP = "http://127.0.0.1:9066";
	private static final String SERVER_IP = "http://49.235.64.160:9191";
	private static SingleTonSocketClient singleTonSocketClient;
	/**
	 * 当前客户端的唯一标识 dongxinjieshishadiao
	 * absxdsadsa123adas
	 **/
	private final String UUID = "absxdsadsa123adas";
	/**
	 * 客户端实例
	 **/
	private Socket client;

	private SingleTonSocketClient() {
		JSONObject request = new JSONObject();
		request.put("uuid", UUID);
		IO.Options opts = new IO.Options();
		opts.reconnection = true;
		//毫秒
		opts.reconnectionDelay = 1000;
		opts.forceNew = true;
		try {
			client = IO.socket(SERVER_IP, opts);
			client
					.on(Socket.EVENT_CONNECT, args -> {
						logger.info("客户端 uuid = {} 正在注册..", UUID);
						//呼叫服务端注册事件，并发送数据包
						client.emit(ConstantKeys.CLIENT_EVENT_REGISTRY, request.toJSONString());
					})
					.on(Socket.EVENT_DISCONNECT, args -> logger.info("连接断开..."))
					.on(Socket.EVENT_RECONNECT, args -> logger.info("重连成功"))
					//每 5秒会ping 一次
					.on(Socket.EVENT_PONG, args -> {
						logger.info("ping success ->{}", JSON.toJSONString(args));
					})
					//注册结果监听事件，
					.on(ConstantKeys.CLIENT_EVENT_REGISTRY_RESULT, args -> {
						//默认返回:false
						boolean flag = Boolean.parseBoolean(Objects.toString(args[0]));
						if (flag) {
							logger.info("客户端:{}注册成功。", UUID);
							return;
						}
						logger.info("客户端:{}，注册失败，启动重新注册机制", UUID);
						//1分钟重试一次
						try {
							TimeUnit.MINUTES.sleep(1);
						} catch (InterruptedException e) {
							logger.error("regist 线程被意外中断");
						}
						client.emit(ConstantKeys.CLIENT_EVENT_REGISTRY, request.toJSONString());
					})
					//消息发送结果
					.on(ConstantKeys.CLIENT_EVENT_MESSAGE_SEND_RESULT, args -> {
						JSONObject call = JSON.parseObject(Objects.toString(args[0]));
						String flag = call.getString("flag");
						String msgId = call.getString("msgId");
						if (Boolean.parseBoolean(flag)) {
							logger.info("消息 msgID = {} 发送成功", msgId);
						} else {
							logger.info("消息 msgID = {} 发送失败", msgId);
						}
					})
					//消息接收事件
					.on(ConstantKeys.CLIENT_EVENT_MESSAGE_RECIEVE, args -> {
						MsgModel msgModel = JSON.parseObject(Objects.toString(args[0]), MsgModel.class);
						logger.info("你有新的消息，内容<<<{}>>>", msgModel.getMsg());
					});
		} catch (URISyntaxException e) {
			logger.error("注册到socket 服务器失败！");
		}
	}

	/**
	 * 获取实例
	 **/
	public static SingleTonSocketClient getInstance() {
		if (singleTonSocketClient == null) {
			singleTonSocketClient = new SingleTonSocketClient();
		}
		return singleTonSocketClient;
	}

	/**
	 * 获取客户端实例
	 **/
	public Socket getClient() {
		return this.client;
	}


}
