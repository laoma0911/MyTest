package com.medlinker.dentist.uac.app.action;

import com.medlinker.dentist.uac.api.interfaces.IRedisService;
import com.medlinker.dentist.uac.api.interfaces.UserInfoService;
import com.medlinker.dentist.uac.api.model.UserInfo;
import com.medlinker.dentist.uac.provider.util.MD5Util;
import com.medlinker.dentist.uac.api.base.model.MsgBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mazb on 2016/4/8.
 */
@Controller
@RequestMapping("/user")
public class LoginAction {


    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private IRedisService redisService;

    @RequestMapping(value = "/login")
    public
    @ResponseBody
    Object login(HttpServletRequest req,
                 @RequestParam(value = "username") String username,
                 @RequestParam("uid") Long uid,
                 @RequestParam("password") String password) {
        MsgBean msg = new MsgBean();

        if (StringUtils.isBlank(username)) {
            msg.setFailure("用户名不能为空");
            return msg.returnMsg();
        }
        if (StringUtils.isBlank(password)) {
            msg.setFailure("密码不能为空");
            return msg.returnMsg();
        }
        UserInfo userinfo = null;
        if (null != uid) {
            userinfo = userInfoService.get(uid);
        }
        if (!userInfoService.checkisAllowLogin(uid)) {
            msg.setFailure("密码错误次数过多，请五分钟后重试！");
            return msg.returnMsg();
        }
        if (!userinfo.getPassword().equals(MD5Util.MD5(password))) {
            userInfoService.setUserLoginFailLog(uid);
            msg.setFailure("密码错误！");
            return msg.returnMsg();
        }
        userInfoService.login(userinfo);

        msg.put("uid", userinfo.getUid());
        msg.put("username", userinfo.getUserName());
        msg.put("access_token", userInfoService.makeAccessToken(userinfo));
        return msg.returnMsg();
    }

}
