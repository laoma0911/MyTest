package com.medlinker.dentist.uac.provider.redis.impl;

import com.alibaba.fastjson.JSON;
import com.medlinker.dentist.uac.api.base.model.SaveRedisBean;
import com.medlinker.dentist.uac.provider.util.RedisUtil;
import com.medlinker.dentist.uac.api.interfaces.IRedisService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 对redis操作的service
 * 
 * @author mazb
 * 
 */
public class RedisServiceImpl implements IRedisService {
	private StringRedisTemplate redisTemplate;
	
	private StringRedisTemplate redisQueueTemplate;
	
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	public void setRedisQueueTemplate(StringRedisTemplate redisQueueTemplate) {
		this.redisQueueTemplate = redisQueueTemplate;
	}

	private RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

	/**
	 * 保存对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, final Object value) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(getRedisSerializer().serialize(key),
						RedisUtil.getBytesFromObject(value));
				return true;
			}
		});
		return result;
	}

	/**
	 * 保存字符串
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, final String value) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		if (value == null) {
			throw new RuntimeException("value不可以是null");
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(getRedisSerializer().serialize(key),
						getRedisSerializer().serialize(value));
				return true;
			}
		});
		return result;
	}

	/**
	 * 保存一个字符串一定时间
	 * 
	 * @param key
	 * @param value
	 * @param saveTime过期时间
	 *            单位为秒
	 * @return
	 */
	public boolean set(final String key, final String value, final long saveTime) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		if (value == null) {
			throw new RuntimeException("value不可以是null");
		}
		
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.setEx(getRedisSerializer().serialize(key), saveTime,
						getRedisSerializer().serialize(value));
				return true;
			}
		});
		return result;
	}
	
	public long getQueueSize(final String key) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		
		return redisQueueTemplate.opsForList().size(key);
	}
	
	/**
	 * 保存一个对象一定时间
	 * 
	 * @param key
	 * @param value
	 * @param saveTime过期时间
	 *            单位为秒
	 * @return
	 */
	public boolean set(final String key, final Object value, final long saveTime) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.setEx(getRedisSerializer().serialize(key), saveTime,
						RedisUtil.getBytesFromObject(value));
				return true;
			}
		});
		return result;
	}

	/**
	 * 批量保存
	 * 
	 * @param list
	 * @return
	 */
	public boolean set(final List<SaveRedisBean> list) {
		List<Object> result = redisTemplate
				.execute(new SessionCallback<List<Object>>() {
					public List<Object> execute(RedisOperations operations)
							throws DataAccessException {
						operations.multi();
						for (SaveRedisBean bean : list) {
							if (bean.getKey() == null) {
								throw new RuntimeException("key不可以是null");
							}
							BoundValueOperations<String, Object> oper = operations
									.boundValueOps(bean.getKey());
							oper.set(RedisUtil.getJsonFromObject(bean
									.getValue()));

						}
						return operations.exec();
					}
				});
		return true;
	}

	/**
	 * 从redis中查询对象
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T get(final String key, final Class<T> clazz) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		if (clazz == null) {
			throw new RuntimeException("clazz不可以是null");
		}
		if (clazz.getName().equals("java.lang.String")) {
			return (T) get(key);
		}
		return redisTemplate.execute(new RedisCallback<T>() {
			@Override
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] serialKey = getRedisSerializer().serialize(key);
				if (connection.exists(serialKey)) {
					byte[] value = connection.get(serialKey);
					return RedisUtil.getObjectFromBytes(value, clazz);
				}
				return null;
			}
		});
	}

	/**
	 * 从redis中查询出字符串
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] serialKey = getRedisSerializer().serialize(key);
				if (connection.exists(serialKey)) {
					byte[] value = connection.get(serialKey);
					return getRedisSerializer().deserialize(value);
				}
				return null;
			}
		});
	}

	/**
	 * 删除
	 * 
	 * @param key
	 */
	public void delete(String key) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		redisTemplate.delete(key);
	}

	/**
	 * 批量删除
	 * 
	 * @param keys
	 */
	public void delete(List keys) {
		if (keys == null || keys.isEmpty() == true) {
			return;
		}
		redisTemplate.delete(keys);
	}

    @Override
    public void deleteQuereKey(String key) {
        if (key == null) {
            throw new RuntimeException("key不可以是null");
        }
        redisQueueTemplate.delete(key);
    }

    /**
	 * 入队列
	 * 
	 * @param key
	 * @param value
	 */
	public void inQuere(String key, Object value) {
		redisQueueTemplate.opsForList().rightPush(key,
				RedisUtil.getJsonFromObject(value));
	}

	/**
	 * 入队列
	 * 
	 * @param key
	 * @param value
	 */
	public void inQuere(String key, String value) {
		redisQueueTemplate.opsForList().rightPush(key, value);
	}

	/**
	 * 出队列为一个字符串
	 * 
	 * @param key
	 * @return
	 */
	public String outQueue(final String key) {
		return redisQueueTemplate.opsForList().leftPop(key);
	}

	public List<String> outQueueRange(final String key, int start, int end) {
		return redisQueueTemplate.opsForList().range(key, start, end);
	}

	public void outQueueTrim(final String key, int start, int end) {
		redisQueueTemplate.opsForList().trim(key, start, end);
	}
	/**
	 * 获取key 支持正则
	 * 
	 * @param patternKey
	 * @return
	 */
	public Set<String> keys(String patternKey) {
		return redisTemplate.keys(patternKey);
	}

	/**
	 * 根据正则获取列表值
	 * 
	 * @param patternKey
	 * @param clazz
	 * @return
	 */
	public <T> List<T> getValueListByPatternKey(final String patternKey,
			final Class<T> clazz) {
		List<T> rows = new ArrayList<T>();
		Set<String> failSet = this.keys(patternKey);
		for (String key : failSet) {
			rows.add(get(key, clazz));
		}
		return rows;
	}

	/**
	 * 批量增加到有序集合
	 * @param key  集合的名称
	 * @param valueMaps 数据集合， map的key为要使用数据, value为排序的字段
	 */
	public void zSetAdd (String key, Map<String, Object> valueMaps){
		Set<ZSetOperations.TypedTuple<String>> list = new HashSet<ZSetOperations.TypedTuple<String>>();
		for(String s : valueMaps.keySet()){
			list.add(new DefaultTypedTuple(s, Double.parseDouble(valueMaps.get(s).toString())));
		}
		redisTemplate.opsForZSet().add(key, list);
	}


	/**
	 * 批量删除有序集合中的数据
	 * @param key  集合的名称
	 * @param values 数据集合
	 */
	public void zSetRemove (String key, List<Object> values){
		redisTemplate.opsForZSet().remove(key, values.toArray());
	}
	/**
	 *增加到有序集合
	 * @param key 集合的名称
	 * @param value 数据的值
	 * @param sortFieldValue  作为排序的字段值
	 */
	public void zSetAdd (String key, String value, Object sortFieldValue){
		redisTemplate.opsForZSet().add(key, value, Double.parseDouble(sortFieldValue.toString()));
	}

	/**
	 * 取得有序集合的数据,按照排序字段升序
	 * @param key  集合的名称
	 * @param start  开始位置
	 * @param end  截止位置
	 * @return
	 */
	public Set<String> zSetRange (String key, long start, long end){
		return redisTemplate.opsForZSet().range(key, start, end);
	}
	/**
	 * 取得有序集合的数据,按照排序字段降序
	 * @param key  集合的名称
	 * @param start  开始位置
	 * @param end  截止位置
	 * @return
	 */
	public Set<String> zSetReverseRange (String key, long start, long end){
		return redisTemplate.opsForZSet().reverseRange(key, start, end);
	}
	public void zSetClear(String key){
		long size = redisTemplate.opsForZSet().size(key).longValue();
		redisTemplate.opsForZSet().removeRange(key, 0l, size);
	}

	public long zSetSize(String key){
		return redisTemplate.opsForZSet().size(key);
	}
	@Override
	public <T> T outQueueNoBlack(String key, Class<T> clazz) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		if (clazz == null) {
			throw new RuntimeException("clazz不可以是null");
		}
		String json=redisQueueTemplate.opsForList().leftPop(key,1,TimeUnit.SECONDS);
		if(json==null){
			return null;
		}
		if (clazz.getName().equals("java.lang.String")) {
			return (T)json;
		}
		return JSON.parseObject(json, clazz);
	}

	
	@Override
	public void zSetRemoveByScore(String key, long start, long end) {
		redisTemplate.opsForZSet().removeRangeByScore(key, start, end);
	}
	@Override
	public Long getAndInc(final String key, final Long defaultValue) {
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] serialKey = getRedisSerializer().serialize(key);
//				if(!connection.exists(serialKey)){
//					connection.setNX(serialKey, getRedisSerializer().serialize(defaultValue+""));
//				}
//				connection.multi();
					connection.setNX(serialKey, getRedisSerializer().serialize(defaultValue+""));
					connection.expire(serialKey, 60*30l);
					return connection.incr(serialKey);
//				List<Object> execReult=connection.exec();
//				Long result=(Long) execReult.get(1);
//				return result;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue+1;
		}
	}
	@Override
	public void inQuereDefault(String key, String value) {
		redisTemplate.opsForList().rightPush(key, value);
		
	}
	
	/**
	 * 从redis中查询出字符串
	 * 
	 * @param key
	 * @return
	 */
	public String getQ(final String key) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		return redisQueueTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] serialKey = getRedisSerializer().serialize(key);
				if (connection.exists(serialKey)) {
					byte[] value = connection.get(serialKey);
					return getRedisSerializer().deserialize(value);
				}
				return null;
			}
		});
	}
	/**
	 * 获取并增长key  如果没有设置默认值     并设置过期时间
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	@Override
	public Long getAndInc(final String key, final Long defaultValue,final long saveTime) {
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] serialKey = getRedisSerializer().serialize(key);
					connection.setNX(serialKey, getRedisSerializer().serialize(defaultValue+""));
					connection.expire(serialKey,saveTime);
					return connection.incr(serialKey);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue+1;
		}
	}

	/**
	 * 获取并增长key  如果没有设置默认值     并设置过期时间
	 * @param key
	 * @param defaultValue
	 * @param incrBy 增长的值
	 * @return
	 */
	@Override
	public Long getAndIncBy(final String key, final Long defaultValue,final long incrBy, final long saveTime) {
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] serialKey = getRedisSerializer().serialize(key);
					connection.setNX(serialKey, getRedisSerializer().serialize(defaultValue+""));
					connection.expire(serialKey,saveTime);
					return connection.incrBy(serialKey, incrBy);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue+incrBy;
		}
	}
}
