package com.boot.netty.model;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/16 14:48
 **/
public class SingleClient {

	private ConcurrentHashMap<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

	public ConcurrentHashMap<String, SocketIOClient> getClientMap() {
		return clientMap;
	}

	public void setClientMap(ConcurrentHashMap<String, SocketIOClient> clientMap) {
		this.clientMap = clientMap;
	}

	/**
	 * @author Leethea
	 * @apiNote 新增 uuid 和 client 的映射对象
	 * @date 2020/3/16 14:58
	 **/
	public void add(String uuid, SocketIOClient client) {
		clientMap.put(uuid, client);
	}

	/**
	 * @author Leethea
	 * @apiNote 删除 uuid 和 client 的映射对象
	 * @date 2020/3/16 14:59
	 **/
	public void remove(String uuid) {
		clientMap.remove(uuid);
	}

	/**
	 * @author Leethea
	 * @apiNote uuid 是否已存在
	 * @date 2020/3/16 14:59
	 **/
	public boolean contain(String uuid) {
		return clientMap.keySet().contains(uuid);
	}

	/**
	 * @author Leethea
	 * @apiNote
	 * @date 2020/3/16 16:01
	 **/
	public SocketIOClient getClient(String uuid) {
		return clientMap.get(uuid);
	}

}
