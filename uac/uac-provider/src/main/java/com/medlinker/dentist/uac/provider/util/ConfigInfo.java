package com.medlinker.dentist.uac.provider.util;

import com.medlinker.dentist.uac.api.exception.UACException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 可刷新配置信息读取工具类
 * {@link org.springframework.context.support.ReloadableResourceBundleMessageSource}
 * @author qianxg
 *
 */
public class ConfigInfo implements ApplicationContextAware {
	private static ApplicationContext ctx;

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		ConfigInfo.ctx = ctx;
	}
	
	/**
	 * 获取字符串配置项
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		Assert.hasLength(key, "查询的属性名称不可为空!");
		return ctx.getMessage(key, null, null);
	}
	
	/**
	 * 获取数字配置项
	 * @param key
	 * @return
	 */
	public static Integer getInteger(String key) {
		String val = getString(key);
		Assert.hasLength(val, "配置项[" + key + "]的值不可为空!");
		
		int v;
		try {
			v = Integer.valueOf(val);
		} catch (NumberFormatException nfe) {
			throw UACException.newInstance("此配置项[" + key + "]的格式异常");
		}
		
		return v;
	}
	
	/**
	 * 获取字符串数组配置项
	 * @param key
	 * @return
	 */
	public static List<String> getStringList(String key) {
		String val = getString(key);

		List<String> list = new ArrayList<String>();
		if (val != null && val.length() > 0 && val.indexOf(",") != -1) {
			String[] array = val.split(",");
			for (String ele : array) {
				list.add(ele.trim());
			}
		}
		
		return list;
	}
	
}
