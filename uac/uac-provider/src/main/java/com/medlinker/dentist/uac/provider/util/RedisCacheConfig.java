package com.medlinker.dentist.uac.provider.util;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * redis配置
 * Created by mazb on 2016/4/5.
 */
public class RedisCacheConfig {
	private static JedisConnectionFactory connectionFactory;

	public static String GlobalPrefix = "_j_";
	public static JedisConnectionFactory getConnectionFactory(){
		return connectionFactory;
	}
	public static String cachePrefix = "";
	public static void setConnectionFactory(JedisConnectionFactory connectionFactory) {
		RedisCacheConfig.connectionFactory = connectionFactory;
	}

	public static String getCachePrefix() {
		return cachePrefix;
	}

	public static void setCachePrefix(String cachePrefix) {
		RedisCacheConfig.cachePrefix = cachePrefix;
	}
}
