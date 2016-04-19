package com.medlinker.dentist.uac.provider;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.medlinker.dentist.uac.api.base.filter.DynamicSQLFilterImpl;
import com.medlinker.dentist.uac.api.base.filter.QueryFilter;
import com.medlinker.dentist.uac.api.interfaces.IRedisService;
import com.medlinker.dentist.uac.api.interfaces.UserInfoService;
import com.medlinker.dentist.uac.api.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MazbStartService {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"/app-context.xml"});
        context.start();

//        UserInfoService service = context.getBean(UserInfoService.class);
//        IRedisService redisService = context.getBean(IRedisService.class);
//        QueryFilter filter = new DynamicSQLFilterImpl();
//        List<UserInfo> list = service.find(filter);
//        outString(list);

        /*redis队列测试*/
//        Map<String, Object> map = Maps.newHashMap();
//        map.put("id", 25);
//        map.put("name", "zhangsan");
//        map.put("age", 15);
//        map.put("number", 2012432534);
//        map.put("classId", 45);
//        redisService.inQuere("mama", map);
//        map.clear();
//        map.put("id", 46);
//        map.put("name", "lisi");
//        map.put("age", 17);
//        map.put("number", 2012434226);
//        map.put("classId", 43);
//        redisService.inQuere("mama", map);
//        map.clear();
//        map.put("id", 92);
//        map.put("name", "wangwu");
//        map.put("age", 17);
//        map.put("number", 2012832531);
//        map.put("classId", 14);
//        redisService.inQuere("mama", map);
//        List<String> rows = redisService.outQueueRange("mama", 0, 100);
//        System.out.println(rows);
//        redisService.outQueueTrim("mama", rows.size(), -1);

//        UserInfo user = new UserInfo();
//        user.setGroupId(1l);
//        user.setUserName("mazhenbang");
//        user.setPassword("123456");
//        user.setLoginNum(0);
//        user.setRegTime(System.currentTimeMillis() / 1000);
//        user.setLastLogin(System.currentTimeMillis() / 1000);
//
//        service.insert(user);

//
//
//        UserLoginFailLog loginFailLog = new UserLoginFailLog();
//        loginFailLog.setUid(40l);
//        loginFailLog.setFirst(System.currentTimeMillis()/1000);
//        loginFailLog.setLast(System.currentTimeMillis()/1000);
//        loginFailLog.setCount(1);
//        // redisService.set(cacheId, log, cacheTime); // 缓存1800 秒
//        redisService.set("failloginLog", loginFailLog, 1800);

        /*线程池测试*/
        new MazbStartService().run();

        System.out.println("线程池处理成功");

        try {
            System.out.println("开始阻塞");
            System.in.read(); // 按任意键退出
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void run() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 5; i >= 0; i--) {
                    try {
                        System.out.println("我要睡了！");
                        Thread.sleep(i);
                        System.out.println("我睡了" + i + "秒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void outString(Object obj) {
        System.out.println(JSONObject.toJSON(obj));
    }
}
