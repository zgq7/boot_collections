package com.boot.netty.server.socketio.controller;

import com.alibaba.fastjson.JSON;
import com.boot.netty.server.socketio.config.SocketServerConfig;
import com.boot.netty.server.socketio.helper.CacheHelper;
import com.boot.netty.server.socketio.common.Constants;
import com.boot.netty.server.socketio.model.GroupClient;
import com.boot.netty.server.socketio.model.GroupModel;
import com.boot.netty.server.socketio.model.SingleClient;
import com.corundumstudio.socketio.ClientOperations;
import com.corundumstudio.socketio.SocketIOServer;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/19 10:50
 **/
@RestController
@RequestMapping
public class SocketController {

	private static final Logger logger = LoggerFactory.getLogger(SocketController.class);

	@Resource
	private SingleClient singleClient;
	@Resource
	private GroupClient groupClient;
	@Resource(name = SocketServerConfig.SERVER_NAME)
	private SocketIOServer server;

	/**
	 * @author Leethea
	 * @apiNote 清除全部连接
	 * @date 2020/3/30 10:25
	 **/
	@GetMapping("/flush")
	public Object refresh() {
		server.getAllClients().forEach(ClientOperations::disconnect);
		singleClient.clear();
		groupClient.clear();
		return "success";
	}

	/**
	 * @author Leethea
	 * @apiNote 全部连接
	 * @date 2020/3/30 10:25
	 **/
	@GetMapping("/all")
	public Object all() {
		return JSON.toJSONString(groupClient.getAll());
	}

	/**
	 * @author Leethea
	 * @apiNote 图片上传
	 * @date 2020/3/25 18:42
	 **/
	@PostMapping("/fileUp")
	public Object fileUp(@RequestParam("file") MultipartFile file) {
		File dest = new File(Constants.IMG_ROOT);
		if (!dest.exists()) {
			boolean mkdir = dest.mkdirs();
		}

		String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
		try {
			FileOutputStream stream = new FileOutputStream(Constants.IMG_ROOT + fileName);
			stream.write(file.getBytes());
			stream.close();
			String serverPath = Constants.SERVER_IMG_ADDRESS + fileName;
			return ImmutableMap.of("msg", "successed",
					"code", 200,
					"path", serverPath);
		} catch (IOException e) {
			logger.error("上传文件报错 : {}", e.getMessage());
			return ImmutableMap.of("msg", "failed", "code", 3000);
		}
	}

	/**
	 * @author Leethea
	 * @apiNote
	 * @date 2020/3/30 10:21
	 */
	@GetMapping(value = "/historyMsg")
	public List<Object> historyMsg() {
/*		return CacheHelper.getInstance().asMap().values()
				.stream()
				.map(m -> JSON.parseObject(m, GroupModel.class))
				.sorted(Comparator.comparing(GroupModel::getTime))
				.map(model -> {
					JSONObject result = JSON.parseObject(JSON.toJSONString(model), JSONObject.class);
					result.put("time",JSON.toJSONStringWithDateFormat(model.getTime(),
							"yyyy-MM-dd HH:mm", SerializerFeature.WriteDateUseDateFormat).replace("\"",""));
					return result;
				})
				.collect(Collectors.toList());*/
		return CacheHelper.getInstance().asMap().values()
				.stream()
				.map(m -> JSON.parseObject(m, GroupModel.class))
				.sorted(Comparator.comparing(GroupModel::getTime))
				.map(model -> JSON.parseObject(JSON.toJSONString(model), Object.class))
				.collect(Collectors.toList());
	}

}
