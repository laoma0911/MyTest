package com.medlinker.dentist.uac.app.action;

import com.medlinker.dentist.uac.api.base.model.MsgBean;
import com.medlinker.dentist.uac.api.interfaces.UserInfoService;
import com.medlinker.dentist.uac.api.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户注册
 * Created by mazb on 2016/4/7.
 */
@Controller
@RequestMapping("/user")
public class RegisterAction {


    @Autowired
    private UserInfoService userInfoService;


    /**
     * 用户注册
     *
     * @param req
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/reg")
    public
    @ResponseBody
    Object reg(HttpServletRequest req,
               @RequestParam(value = "username") String username,
               @RequestParam("password") String password,
               @RequestParam("groupId") Long groupId) {

        MsgBean msg = new MsgBean();

        String trimUsername = username == null ? null : username.trim();
        String trimPassword = password.trim();

        UserInfo userInfo = new UserInfo();
        userInfo.setLastLogin(0l);
        userInfo.setRegTime(System.currentTimeMillis() / 1000);
        userInfo.setUserName(trimUsername);
        userInfo.setPassword(trimPassword);
        userInfo.setLoginNum(0);
        userInfo.setGroupId(groupId);
        userInfo = userInfoService.saveUserInfo(userInfo);

        if (userInfo.getUid() == null) {
            msg.setFailure("注册失败，请稍后再试。");
            return msg.returnMsg();
        }

        msg.put("username", userInfo.getUserName());
        msg.put("uid", userInfo.getUid());

        return msg.returnMsg();
    }

}
