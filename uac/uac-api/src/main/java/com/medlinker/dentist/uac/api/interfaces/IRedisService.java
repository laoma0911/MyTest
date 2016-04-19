package com.medlinker.dentist.uac.api.interfaces;


import com.medlinker.dentist.uac.api.base.model.SaveRedisBean;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRedisService {
	/**
	 * 保存对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, final Object value);

	/**
	 * 保存字符串
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, final String value);

	/**
	 * 保存一个字符串一定时间
	 * 
	 * @param key
	 * @param value
	 * @param saveTime过期时间
	 *            单位为秒
	 * @return
	 */
	public boolean set(final String key, final String value, final long saveTime);

	/**
	 * 保存一个对象一定时间
	 * 
	 * @param key
	 * @param value
	 * @param saveTime过期时间
	 *            单位为秒
	 * @return
	 */
	public boolean set(final String key, final Object value, final long saveTime);

	/**
	 * 批量保存
	 * 
	 * @param list
	 * @return
	 */
	public boolean set(final List<SaveRedisBean> list);

	/**
	 * 从redis中查询对象
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T get(final String key, final Class<T> clazz);

	/**
	 * 从redis中查询出字符串
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key);

	/**
	 * 删除
	 * 
	 * @param key
	 */
	public void delete(String key);

	/**
	 * 批量删除
	 * 
	 * @param keys
	 */
	public void delete(List keys);

    /**
     * 删除队列
     *
     * @param key
     */
    public void deleteQuereKey(String key);

	/**
	 * 入队列到默认的redis连接
	 * 
	 * @param key
	 * @param value
	 */
	public void inQuereDefault(String key, String value);

	/**
	 * 入队列(对象)
	 * 
	 * @param key
	 * @param value
	 */
	public void inQuere(String key, Object value);
	/**
	 * 入队列
	 * 
	 * @param key
	 * @param value
	 */
	public void inQuere(String key, String value);


	/**
	 * 出队列为一个字符串
	 * 
	 * @param key
	 * @return
	 */
	public String outQueue(final String key);

	public List<String> outQueueRange(final String key, int start, int end);

	public void outQueueTrim(final String key, int start, int end) ;

	/**
	 * 出队列为一个对象非阻塞
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T outQueueNoBlack(final String key, Class<T> clazz);

	/**
	 * 获取key 支持正则
	 * 
	 * @param patternKey
	 * @return
	 */
	public Set<String> keys(String patternKey);

	/**
	 * 根据正则获取列表值
	 * 
	 * @param patternKey
	 * @param clazz
	 * @return
	 */
	public <T> List<T> getValueListByPatternKey(final String patternKey,
												final Class<T> clazz);

	/**
	 * 获取队列长度
	 * 
	 * @param key
	 * @return
	 */
	public long getQueueSize(final String key);

	/**
	 * 批量增加到有序集合
	 * 
	 * @param key
	 *            集合的名称
	 * @param valueMaps
	 *            数据集合， map的key为要使用数据, value为排序的字段
	 */
	public void zSetAdd(String key, Map<String, Object> valueMaps);

	/**
	 * 增加到有序集合
	 * 
	 * @param key
	 *            集合的名称
	 * @param value
	 *            数据的值
	 * @param sortFieldValue
	 *            作为排序的字段值
	 */
	public void zSetAdd(String key, String value, Object sortFieldValue);

	/**
	 * 取得有序集合的数据
	 * 
	 * @param key
	 *            集合的名称
	 * @param start
	 *            开始位置
	 * @param end
	 *            截止位置
	 * @return
	 */
	public Set<String> zSetRange(String key, long start, long end);

	/**
	 * 取得有序集合的数据,按照排序字段降序
	 * 
	 * @param key
	 *            集合的名称
	 * @param start
	 *            开始位置
	 * @param end
	 *            截止位置
	 * @return
	 */
	public Set<String> zSetReverseRange(String key, long start, long end);

	/**
	 * 清空有序集合
	 * 
	 * @param key
	 */
	public void zSetClear(String key);

	/**
	 * 读取有序集合的size
	 * 
	 * @param key
	 * @return
	 */
	public long zSetSize(String key);

	/**
	 * 批量删除有序集合中的数据
	 * 
	 * @param key
	 *            集合的名称
	 * @param values
	 *            数据集合
	 */
	public void zSetRemove(String key, List<Object> values);
	
	/**
	 * 根据score批量删除
	 * @param key
	 * @param start
	 * @param end
	 */
	public void zSetRemoveByScore(String key, long start, long end);
	
	/**
	 * 获取并增长key  如果没有设置默认值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Long getAndInc(String key, Long defaultValue);
	
	/**
	 * 从redis中查询出字符串
	 * 
	 * @param key
	 * @return
	 */
	public String getQ(final String key);
	/**
	 * 获取并增长key  如果没有设置默认值     并设置过期时间
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Long getAndInc(final String key, final Long defaultValue, final long saveTime) ;
	/**
	 * 获取并增长key  如果没有设置默认值     并设置过期时间
	 * @param key
	 * @param defaultValue
	 * @param incrBy 增长的值
	 * @return
	 */
	public Long getAndIncBy(final String key, final Long defaultValue, final long incrBy, final long saveTime);
}
