package com.medlinker.dentist.uac.api.interfaces;


import com.medlinker.dentist.uac.api.base.service.BaseService;
import com.medlinker.dentist.uac.api.model.UserInfo;

/**
 * @author mazb
 * @date: 06 03, 2015 16:08
 */
public interface UserInfoService extends BaseService<UserInfo,Long> {

    /**
     * 保存用户信息
     * @param userInfo
     * @return
     */
    UserInfo saveUserInfo(UserInfo userInfo);

    /**
     * 验证用户昵称
     * @param username
     * @return
     */
    public void validateNick(String username);

    /**
     * 生成用户的访问Token
     *
     * @param userInfo
     * @return
     */
    public String makeAccessToken(UserInfo userInfo);


    /**
     * 检查token是否有效
     * @param token
     * @return
     */
    public boolean checkIsOnline(String token);


    /**
     * 用户登录（主逻辑：缓存用户信息）
     * @param userinfo
     */
    public void login(UserInfo userinfo);

    /**
     * 根据昵称检索用户
     * @param username
     * @return
     */
    UserInfo findByUsername(String username);

    /**
     * 设置用户登陆失败日志
     * 登录失败(密码错误)记录时间，防止多次请求
     * 密码连续错误五次，300s内禁止登录
     * @param uid
     */
    public void setUserLoginFailLog(Long uid);

    /**
     * 检查用户是否允许登陆， 读取redis中用户登陆失败的日志， 检查登陆次数
     * 密码连续错误五次，1800s内禁止登录
     * @param uid
     * @return
     */
    public boolean checkisAllowLogin(Long uid);


}
