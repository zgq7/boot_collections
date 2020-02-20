package com.boot.mongodb.config;

import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/2/19 17:37
 **/
@Configuration
public class MongodbConfig {

	@Bean
	public MongoClientOptions mongoClientOptions(){
		return MongoClientOptions.builder().maxConnectionIdleTime(6000).build();
	}


}
