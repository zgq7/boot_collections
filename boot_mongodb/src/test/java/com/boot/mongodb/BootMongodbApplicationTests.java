package com.boot.mongodb;

import com.boot.mongodb.model.UserModel;
import com.boot.mongodb.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BootMongodbApplication.class)
public class BootMongodbApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(BootMongodbApplicationTests.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void contextLoads() {
		UserModel userModel = new UserModel(1, "lzq", 23);
		userRepository.insert(userModel);
		System.out.println(userRepository.findAll());
	}

	//==============================基于Repository的增删改查
	@Test
	public void add() {
		UserModel userModel = new UserModel(1, "lzq", 23, "Repository");
		System.out.println(userRepository.insert(userModel));
	}

	@Test
	public void remove() {
		UserModel userModel = new UserModel(1, "lzq", 23, "Repository");
		userRepository.delete(userModel);
	}

	@Test
	public void update() {
		UserModel userModel = new UserModel(2, "lzq", 24, "Repository");
		//有则修改，无则新增
		System.out.println(userRepository.save(userModel));
	}

	@Test
	public void find() {
		System.out.println(userRepository.findAll());
		UserModel userModel = new UserModel(2, "lzq", 24, "Repository");
		Example example = Example.of(userModel);
		System.out.println(userRepository.findOne(example));
		System.out.println(userRepository.findAll(example));
	}


	//===============================基于MongoTemplate的增删改查
	@Test
	public void tadd() {
		UserModel userModel = new UserModel(3, "lzq", 24, "MongoTemplate");
		logger.info("{}", mongoTemplate.insert(userModel));
	}

	@Test
	public void tdelete() {
		//UserModel userModel = new UserModel(3, "lzq", 24, "MongoTemplate");
		//输出删除的行数
		//System.out.println(mongoTemplate.remove(userModel).getDeletedCount());
		Query query = new Query(Criteria.where("id").is(3));
		logger.info("{}", mongoTemplate.remove(query, UserModel.class).getDeletedCount());
	}

	@Test
	public void tupdate() {
		Query query = new Query(Criteria.where("id").is(3));
		Update update = new Update().set("name", "2333333");
		logger.info("{}", mongoTemplate.updateFirst(query, update, UserModel.class));
	}

	@Test
	public void tfind() {
		Query query = new Query(Criteria.where("id").is(3));
		logger.info("{}", mongoTemplate.find(query, UserModel.class));
	}


}
