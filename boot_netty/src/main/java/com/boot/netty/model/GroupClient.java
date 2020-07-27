package com.boot.netty.model;

import com.alibaba.fastjson.JSONObject;
import com.boot.netty.config.SocketServerConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
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
public class GroupClient implements InitializingBean {

	public static final String GROUPID = "123456";
	private static final Logger logger = LoggerFactory.getLogger(GroupClient.class);

	/**
	 * key is 房间号
	 * value is uuid=client
	 **/
	private ConcurrentHashMap<String, Map<String, SocketIOClient>> clientMap = new ConcurrentHashMap<>();

	/**
	 * @author Leethea
	 * @apiNote 新增 uuid 和 client 的映射对象
	 * 存在则进行socket更新
	 * @date 2020/3/16 14:58
	 **/
	public void add(String uuid, SocketIOClient client) {
		clientMap.get(GROUPID).put(uuid, client);
		logger.info("当前数量：{}", clientMap.size());
	}

	/**
	 * @author Leethea
	 * @apiNote
	 * @date 2020/3/16 14:59
	 **/
	public boolean contain(String uuid) {
		//测试房间号
		return clientMap.get(GROUPID).containsKey(uuid);
	}

	/**
	 * @author Leethea
	 * @apiNote
	 * @date 2020/3/16 14:59
	 **/
	public boolean contain(SocketIOClient client) {
		//测试房间号
		return clientMap.get(GROUPID).containsValue(client);
	}

	/**
	 * @author Leethea
	 * @apiNote
	 * @date 2020/3/16 14:59
	 **/
	public boolean contain(String uuid, SocketIOClient client) {
		AtomicReference<Boolean> flag = new AtomicReference<>(false);
		//测试房间号
		clientMap.get(GROUPID).forEach((k, v) -> {
			if (k.equals(uuid) && v == client) {
				flag.set(true);
			}
		});
		return flag.get();
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
				clientMap.get(GROUPID).put(uuid, client);
			}
			//如果当前socket 已存在，移除掉old entry
			if (contain(client)) {
				String uuids = getUuid(client);
				if (uuids != null) {
					clientMap.get(GROUPID).remove(uuids);
				}
				clientMap.get(GROUPID).put(uuid, client);
			}
			logger.info("当前数量：{}", clientMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Leethea
	 * @apiNote
	 * @date 2020/3/16 16:01
	 **/
	public String getUuid(SocketIOClient client) {
		AtomicReference<String> uuid = new AtomicReference<>(null);
		clientMap.get(GROUPID).forEach((k, v) -> {
			if (v == client) {
				uuid.set(k);
			}
		});
		return uuid.get();
	}

	/**
	 * @author Leethea
	 * @apiNote
	 * @date 2020/3/16 16:01
	 **/
	public List<SocketIOClient> getAllClient() {
		List<SocketIOClient> clients = new ArrayList<>(3);
		clientMap.get(GROUPID).forEach((k, v) -> {
			clients.add(v);
		});
		return clients;
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
		clientMap.get(GROUPID).remove(uuid);
		logger.info("客户端 uuid = {} 已断开连接", uuid);
	}


	/**
	 * @author Leethea
	 * @apiNote
	 * @date 2020/3/19 11:10
	 **/
	public List<JSONObject> getAll() {
		List<JSONObject> result = new ArrayList<>();
		clientMap.get(GROUPID).forEach((k, v) -> {
			JSONObject object = new JSONObject();
			object.put("uuid", k);
			object.put("sessionID", v.getSessionId().toString());
			result.add(object);
		});
		return result;
	}

	/**
	 * @author Leethea
	 * @apiNote 清除所有缓存
	 * @date 2020/3/16 16:01
	 **/
	public void clear() {
		clientMap.get(GROUPID).clear();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		clientMap.put(GROUPID, new ConcurrentHashMap<>(100));
	}

}
