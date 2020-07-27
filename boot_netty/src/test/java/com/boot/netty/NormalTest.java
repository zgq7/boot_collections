package com.boot.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.boot.netty.model.GroupModel;
import org.junit.Test;

import java.util.Date;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/27 11:54
 **/
public class NormalTest {


	@Test
	public void test01() {
		GroupModel model = new GroupModel();
		model.setTime(new Date());
		System.out.println(JSON.toJSONString(model));
		System.out.println(JSON.toJSONStringWithDateFormat(model,
				"yyyy-MM-dd HH:mm", SerializerFeature.WriteDateUseDateFormat));
	}

}
