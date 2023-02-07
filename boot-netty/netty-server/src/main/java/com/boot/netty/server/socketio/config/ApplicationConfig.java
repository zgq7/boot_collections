package com.boot.netty.server.socketio.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/30 10:28
 **/
@Configuration
public class ApplicationConfig {

	/**
	 * @author Leethea
	 * @apiNote 配置前后端分离跨域
	 * @date 2020/3/30 10:33
	 **/
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				//添加映射路径
				registry.addMapping("/**")
						//放行哪些原始域
						.allowedOrigins("*")
						//是否发送Cookie信息
						.allowCredentials(true)
						//放行哪些原始域(请求方式)
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						//放行哪些原始域(头部信息)
						.allowedHeaders("*")
						//暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
						.exposedHeaders("Header1", "Header2");
			}
		};
	}

	/**
	 * @author Leethea
	 * @apiNote 文件上传配置
	 * @date 2020/3/30 10:29
	 **/
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件上传限制
		factory.setMaxFileSize(DataSize.ofKilobytes(1024L * 5));
		//单个请求文件上传限制
		factory.setMaxRequestSize(DataSize.ofBytes(1024L * 10 * 5));
		return factory.createMultipartConfig();
	}

}
