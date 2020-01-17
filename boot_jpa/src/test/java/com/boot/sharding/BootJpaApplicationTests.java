package com.boot.sharding;

import com.boot.jpa.BootJpaApplication;
import com.boot.jpa.mapper.AopiMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BootJpaApplication.class)
class BootJpaApplicationTests {

	@Autowired
	AopiMapper aopiMapper;

	/**
	 * 功能：xx
	 *
	 * @return JPA 測試
	 * @author Leethea
	 * @date 2020/1/17 15:48
	 **/
	@Test
	void contextLoads() {
		System.out.println(aopiMapper.findAll().toString());
	}

}
