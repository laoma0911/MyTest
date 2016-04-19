package com.medlinker.dentist.uac.api.model;


import com.medlinker.dentist.uac.api.base.model.BaseModel;

/**
 * @author mazb
 * @date: 06 03, 2015 16:06
 */
public class UserInfo extends BaseModel {

    private static final long serialVersionUID = -5047579083849777134L;

    private Long uid;
    private String userName;
    private String password;
    private Long regTime;//注册时间
    private int loginNum;//登录次数
    private Long lastLogin;//上次登录时间
    private Long groupId;//组织id

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRegTime() {
        return regTime;
    }

    public void setRegTime(Long regTime) {
        this.regTime = regTime;
    }

    public int getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(int loginNum) {
        this.loginNum = loginNum;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
