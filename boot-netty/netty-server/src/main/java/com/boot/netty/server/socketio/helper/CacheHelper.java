package com.boot.netty.server.socketio.helper;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Leethea_廖南洲
 * @version 1.0 存放消息
 * @date 2020/3/27 16:50
 **/
public class CacheHelper {

	/**
	 * key 为 msgid
	 * value 为 groupmodel 的json string
	 **/
	private LoadingCache<String, String> historyCache;

	/**
	 * key 为 sessionId
	 * value 为 uuid/username
	 **/
	private LoadingCache<String, String> userInfoCache;

	public CacheHelper() {
		this.historyCache = CacheBuilder.newBuilder()
				//存放量为10000，超过之后采用LRU算法
				.maximumSize(10000)
				//超过三天没就会过期，将采取LRU算法进行清理
				.expireAfterAccess(3, TimeUnit.DAYS)
				//最大访问线程数8
				.concurrencyLevel(8)
				//当key对应的缓存值不存在时,通过CacheBuilder返回一个空值
				.build(new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						return "";
					}
				});
		this.userInfoCache = CacheBuilder.newBuilder()
				//存放量为10000，超过之后采用LRU算法
				.maximumSize(10000)
				//超过三天没就会过期，将采取LRU算法进行清理
				.expireAfterAccess(3, TimeUnit.DAYS)
				//最大访问线程数8
				.concurrencyLevel(8)
				//当key对应的缓存值不存在时,通过CacheBuilder返回一个空值
				.build(new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						return "";
					}
				});
	}

	public static CacheHelper getInstance() {
		return InnerClass.CACHE_HELPER;
	}

	/**
	 * 增加
	 **/
	public void put(String k, String v) {
		historyCache.put(k, v);
	}

	/**
	 * 获取
	 **/
	public String get(String k) throws ExecutionException {
		return historyCache.get(k);
	}

	/**
	 * 获取全部
	 **/
	public ConcurrentMap<String, String> asMap() {
		return historyCache.asMap();
	}


	//===============================【用户信息】

	/**
	 * 增加
	 **/
	public void putUserName(String k, String v) {
		userInfoCache.put(k, v);
	}

	/**
	 * 获取
	 **/
	public String getUserName(String k) throws ExecutionException {
		return userInfoCache.get(k);
	}


	private static class InnerClass {
		public static final CacheHelper CACHE_HELPER = new CacheHelper();
	}

}
