package com.boot.mongodb.config;

import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/2/19 17:37
 **/
@Configuration
@EnableAspectJAutoProxy
public class MongodbConfig {

	@Bean
	public MongoClientOptions mongoClientOptions() {
		return MongoClientOptions.builder().maxConnectionIdleTime(6000).build();
	}

	/**
	 * @apiNote 使用MongodbTemplate 时会生成 _class 字段
	 * @date 2020/3/13 16:15
	 **/
	@Bean
	public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context,
													   BeanFactory beanFactory) {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
		MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
		try {
			mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
		} catch (NoSuchBeanDefinitionException ignore) {
		}

		// Don't save _class to mongo
		mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

		return mappingConverter;
	}

	@Bean
	public MongoCommandAspect mongoCommandAspect(){
		return new MongoCommandAspect();
	}

}
