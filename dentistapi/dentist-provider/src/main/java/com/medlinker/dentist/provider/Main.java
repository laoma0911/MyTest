package com.medlinker.dentist.provider;

import com.alibaba.fastjson.JSONObject;
import com.medlinker.dentist.api.interfaces.TestService;
import com.medlinker.dentist.api.model.Test;
import com.medlinker.dentist.uac.api.base.filter.DynamicSQLFilterImpl;
import com.medlinker.dentist.uac.api.base.filter.QueryFilter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by mazb on 2016/4/18.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"app-context.xml"});
        context.start();
        try {
            System.out.println("加载完毕开始阻塞");

            TestService service = context.getBean(TestService.class);
            Test test = new Test();
            test.setUid(40l);
            test.setStatus(0l);
            service.insert(test);

            outString(service.get(1l));

            QueryFilter filter = new DynamicSQLFilterImpl();
            outString(service.find(filter));


        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.in.read(); // 按任意键退出
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void outString(Object obj) {
        System.out.println(JSONObject.toJSON(obj));
    }
}
