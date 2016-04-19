package com.medlinker.dentist.uac.provider.util;

import com.alibaba.fastjson.JSON;

/**
 * 对redis读写参数的转化工具
 * @author mazb
 */
public class RedisUtil {

    /**
     * 转化对象为byte格式
     * @param obj
     * @return
     */
    public static byte[] getBytesFromObject(Object obj){
        return JSON.toJSONString(obj).getBytes();
    }
    /**
     * 转化byte格式为对象
     * @return
     */
    public static <T> T getObjectFromBytes(byte[] bytes,Class<T> clazz){
        return JSON.parseObject(bytes,clazz);
    }
    /**
     * 对象转成json
     * @param obj
     * @return
     */
    public static String getJsonFromObject(Object obj){
        return JSON.toJSONString(obj);
    }
}
