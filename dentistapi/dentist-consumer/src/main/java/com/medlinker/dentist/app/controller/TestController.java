package com.medlinker.dentist.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public String index() {
        return "success";
    }

    @RequestMapping(value = "/test")
    public
    @ResponseBody
    Object reg(@RequestParam(value = "username") String username) {
        System.out.println("=========QWQWQW=========");
        return "success";
    }

}
