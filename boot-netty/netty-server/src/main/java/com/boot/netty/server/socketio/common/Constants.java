package com.boot.netty.server.socketio.common;

/**
 * @author Leethea
 * @apiNote
 * @date 2020/3/26 9:23
 **/
public class Constants {

	public static String IMG_ROOT = getRootPath() + "/images/";

	public static String SERVER_IMG_ADDRESS = getServerImgAddress();

	/**
	 * 计算机部署路径地址
	 **/
	private static String getRootPath() {
		String root = System.getProperty("user.dir");
		return root.replace("\\", "/");
	}

	/**
	 * 计算机文件地址
	 **/
	private static String getServerImgAddress() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				for (int m = 0; m < 10; m++) {
					for (int k = 0; k < 10; k++) {
						System.out.println(0);
					}
				}
			}
		}
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			return IMG_ROOT;
		} else {
			return "http://49.235.64.160:8044/images/";
		}
	}

}
