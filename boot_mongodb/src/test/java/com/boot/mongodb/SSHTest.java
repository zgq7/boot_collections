package com.boot.mongodb;

import com.boot.mongodb.model.YapiUser;
import com.boot.mongodb.repository.YapiUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/13 14:54
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BootMongodbApplication.class)
public class SSHTest {

	private static final Logger logger = LoggerFactory.getLogger(SSHTest.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private YapiUserRepository yapiUserRepository;

	@Test
	public void ssh() {
		mongoTemplate.findAll(YapiUser.class).forEach(item -> logger.info("{}", item));
	}

	@Test
	public void ssh3() {
		YapiUser yapiUser = new YapiUser();
		yapiUser.setId(81);
		yapiUser.setStudy(true);
		yapiUser.setType("site");
		yapiUser.setUsername("joson");
		// 密码 123456
		yapiUser.setPassword("af596205e4db194493d517cb031d2e1fba6178f1");
		yapiUser.setPasssalt("nojnyry6oz");
		yapiUser.setEmail("dapeng.chen@vdaifu.com");
		yapiUser.setRole(YapiUser.MEMBER);
		yapiUser.setAddTime(System.currentTimeMillis());
		yapiUser.setUpTime(System.currentTimeMillis());
		yapiUser.setV(0);
		logger.info("{}", yapiUserRepository.insert(yapiUser));
	}

}
