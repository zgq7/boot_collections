package com.boot.netty.model;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/16 14:48
 **/
public class SingleClient {

	private static final Logger logger = LoggerFactory.getLogger(SingleClient.class);

	/**
	 * key is uuid
	 * value is client
	 **/
	private ConcurrentHashMap<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

	/**
	 * @author Leethea
	 * @apiNote 新增 uuid 和 client 的映射对象
	 * 存在则进行socket更新
	 * @date 2020/3/16 14:58
	 **/
	public void add(String uuid, SocketIOClient client) {
		clientMap.put(uuid, client);
		logger.info("当前数量：{}", clientMap.size());
	}

	/**
	 * @author Leethea
	 * @apiNote 新增 uuid 和 client 的映射对象
	 * 存在则进行socket更新
	 * @date 2020/3/16 14:58
	 **/
	public void update(String uuid, SocketIOClient client) {
		try {
			//如果当前uuid 已存在，移除掉old socket
			if (clientMap.containsKey(uuid)) {
				clientMap.put(uuid, client);
			}
			//如果当前socket 已存在，移除掉old entry
			if (clientMap.containsValue(client)) {
				String uuids = getUuid(client);
				if (uuids != null) {
					clientMap.remove(uuids);
				}
				clientMap.put(uuid, client);
			}
			logger.info("当前数量：{}", clientMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Leethea
	 * @apiNote uuid 是否已存在
	 * @date 2020/3/16 14:59
	 **/
	public boolean contain(String uuid) {
		return clientMap.containsKey(uuid);
	}

	/**
	 * @author Leethea
	 * @apiNote socket 是否已存在
	 * @date 2020/3/16 14:59
	 **/
	public boolean contain(SocketIOClient client) {
		return clientMap.containsValue(client);
	}

	public boolean contain(String key, SocketIOClient value) {
		if (key == null || value == null) {
			return false;
		}
		for (Map.Entry entry : clientMap.entrySet()) {
			if (entry.getKey().equals(key) && entry.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @author Leethea
	 * @apiNote
	 * @date 2020/3/16 16:01
	 **/
	public SocketIOClient getClient(String uuid) {
		return clientMap.get(uuid);
	}

	/**
	 * @author Leethea
	 * @apiNote
	 * @date 2020/3/16 16:01
	 **/
	public String getUuid(SocketIOClient client) {
		AtomicReference<String> uuid = new AtomicReference<>(null);
		clientMap.forEach((k, v) -> {
			if (v == client) {
				uuid.set(k);
			}
		});
		return uuid.get();
	}

	/**
	 * @author Leethea
	 * @apiNote 清除所有缓存
	 * @date 2020/3/16 16:01
	 **/
	public void clear() {
		clientMap.clear();
	}

	/**
	 * @author Leethea
	 * @apiNote
	 * @date 2020/3/19 11:10
	 **/
	public List<JSONObject> getAll() {
		List<JSONObject> result = new ArrayList<>();
		clientMap.forEach((k, v) -> {
			JSONObject object = new JSONObject();
			object.put("uuid", k);
			object.put("sessionID", v.getSessionId().toString());
			result.add(object);
		});
		return result;
	}

	/**
	 * @author Leethea
	 * @apiNote
	 * @date 2020/3/19 11:55
	 **/
	public void remove(SocketIOClient client) {
		String uuid = getUuid(client);
		if (uuid == null) {
			return;
		}
		clientMap.remove(uuid, client);
		logger.info("客户端 uuid = {} 已断开连接！",uuid);
	}

}
