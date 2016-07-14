package com.gomeplus.meipro.web.system.controller;

import com.gomeplus.meipro.common.response.ResponseJson;
import com.gomeplus.meipro.web.system.config.AppVersionProperties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by mazhenbang on 16/7/12.
 */
@RestController
@RequestMapping("/app")
public class Test {

    @Autowired
    private AppVersionProperties appVersionProperties;



    @RequestMapping(value = "/checkVersion", method = RequestMethod.GET)
    public ResponseJson checkVersion()   {

        ResponseJson responseJson = new ResponseJson();

        List<String> stringList = appVersionProperties.getVersion();



        String s = StringUtils.join(stringList,",");


        responseJson.setData(s);

        return responseJson;

    }

}
