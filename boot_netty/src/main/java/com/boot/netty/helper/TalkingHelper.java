package com.boot.netty.helper;

import com.boot.netty.model.GroupClient;
import com.corundumstudio.socketio.SocketIOClient;

import javax.annotation.Resource;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/25 13:44
 **/
public class TalkingHelper {

	@Resource
	private GroupClient groupClient;

	/**
	 * @author Leethea
	 * @apiNote 通告其他人
	 * @date 2020/3/25 11:54
	 **/
	public void tellOthers(SocketIOClient client, String msgType, String msg) {
		groupClient.getAllClient()
				.stream()
				//过滤掉自己
				.filter(socket -> !socket.getSessionId().toString().equals(client.getSessionId().toString()))
				.forEach(
						socket -> socket.sendEvent(msgType, msg)
				);
	}


}
