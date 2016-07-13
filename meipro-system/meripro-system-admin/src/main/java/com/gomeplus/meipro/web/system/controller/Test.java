package com.gomeplus.meipro.web.system.controller;

import com.gomeplus.meipro.web.system.config.JedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mazhenbang on 16/7/12.
 */
@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    private JedisProperties jedisProperties;



    @RequestMapping(value = "/1", method = RequestMethod.GET)
    public String view()   {

        List<String> stringList = jedisProperties.getVersion();

        return stringList.toString();

    }

}
