package com.medlinker.dentist.uac.app.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 不用拦截的url数组 后台考虑数据库+redis缓存
 * 
 * @author mazb
 * 
 */
public class FilterMethods {
	private static Log log = LogFactory.getLog(FilterMethods.class);
	// 无需用户登陆使用的 Method 暂时有reg注册接口、login登录接口
	private static final Map<String, Set> NoLoginFilterMethodsMap = new HashMap<String, Set>() {
		{
			put("user", new HashSet<String>() {
				{
					add("reg");
					add("login");
				}
			});
		}
	};

	/**
	 * 是否是不用登陆拦截
	 * 
	 * @param contextPath
	 * @return
	 */
	public static boolean isNoLoginFilter(String contextPath) {
		//log.info("判断相对路径【" + contextPath + "】是否需要登陆拦截");
		contextPath = contextPath.toLowerCase();
		/* 1.取出首/ */
		if (contextPath.startsWith("/")) {
			contextPath = contextPath.substring(1, contextPath.length());
		}
		/* 2.分解成数组 */
		String[] pathArray = contextPath.split("/");
		if (pathArray.length > 0) {
			/* 2.1验证第一级 */
			Set<String> secPathSet = NoLoginFilterMethodsMap.get(pathArray[0]);
			if (secPathSet != null) {
				/* 2.1.1验证第二级 */
				if (secPathSet.contains("*")) {
					return true;
				}
				if (pathArray.length > 1) {
					return secPathSet.contains(pathArray[1]);
				}
			}
		}
		return false;
	}


}
