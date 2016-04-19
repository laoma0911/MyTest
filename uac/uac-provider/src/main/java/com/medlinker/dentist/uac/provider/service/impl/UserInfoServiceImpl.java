package com.medlinker.dentist.uac.provider.service.impl;


import com.alibaba.fastjson.JSON;
import com.medlinker.dentist.uac.api.util.Consts;
import com.medlinker.dentist.uac.api.base.filter.DynamicSQLFilterImpl;
import com.medlinker.dentist.uac.api.base.filter.QueryFilter;
import com.medlinker.dentist.uac.api.base.service.impl.BaseServiceImpl;
import com.medlinker.dentist.uac.api.exception.UACException;
import com.medlinker.dentist.uac.api.interfaces.IRedisService;
import com.medlinker.dentist.uac.api.interfaces.UserInfoService;
import com.medlinker.dentist.uac.api.model.UserInfo;
import com.medlinker.dentist.uac.api.model.UserLoginFailLog;
import com.medlinker.dentist.uac.api.util.DateTimeUtil;
import com.medlinker.dentist.uac.provider.dao.UserDao;
import com.medlinker.dentist.uac.provider.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author mazb
 * @date: 06 03, 2015 16:09
 */
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Long> implements UserInfoService {

    private static Log log = LogFactory.getLog(UserInfoServiceImpl.class);

    private UserDao dao;

    public UserInfoServiceImpl(UserDao dao) {
        super(dao);
        this.dao = dao;
    }

    @Autowired
    private IRedisService redisService;

    @Override
    public UserInfo saveUserInfo(UserInfo userInfo) {

        validateNick(userInfo.getUserName());
        userInfo.setPassword(MD5Util.MD5(userInfo.getPassword()));
        this.dao.insert(userInfo);
        return userInfo;
    }

    @Override
    public void validateNick(String username) {
        if (!StringUtils.isEmpty(username)) {
            if (StringUtils.isBlank(username)) {
                throw UACException.newInstance("用户名不能为空、空格、换行、Tab");
            }
            //TODO 1.用户名校验规则

            /* 用户名重复检测*/
            QueryFilter filter = new DynamicSQLFilterImpl();
            filter.addFilter("Q_userName_S_EQ", username);
            if (dao.count(filter) > 0) {
                throw UACException.newInstance("该名字已经被使用，请换一个试试。");
            }
        } else if (StringUtils.isEmpty(username)) {
            throw UACException.newInstance("用户名不能为空");
        }
    }


    @Override
    public String makeAccessToken(UserInfo userInfo) {
        if (userInfo.getUid() != null) {
            return userInfo.getUid()
                    + "-"
                    + MD5Util.MD5(userInfo.getUid() + userInfo.getPassword()
                    + Consts.tokenEncryptionKey
                    + System.currentTimeMillis() / 1000);
        }
        return null;
    }

    @Override
    public boolean checkIsOnline(String token) {
        String redisToken = redisService.get(String.format("token_key_%s", getUidByToken(token)));
        if (StringUtils.isNotBlank(redisToken) && token.equals(redisToken)) {
            return true;
        }
        return false;
    }


    /**
     * 获取token
     *
     * @param token
     * @return
     */
    public Long getUidByToken(String token) {
        if (!StringUtils.isEmpty(token)) {
            String TOKEN_SEPARATOR = "-";
            try {
                return Long.parseLong(token.split(TOKEN_SEPARATOR)[0]);
            } catch (Exception ex) {
                throw new UACException("10011");
            }
        }
        return null;
    }

    @Override
    public void login(UserInfo userinfo) {
        log.debug("debug级别日志==================================login");
        try {
            /* 1.缓存用户信息*/
            String cacheKey = "userinfo_" + userinfo.getUid();
            redisService.set(cacheKey, userinfo, 60 * 60 * 24 * 3);
        } catch (Exception e) {
            log.error(String.format("用户%s信息到redis出错：", userinfo.getUid() + e.getMessage()));
        }
        try {
            /* 2.缓存token有效期7天，7天后需要重新登录*/
            redisService.set(String.format("token_key_%s", userinfo.getUid()), makeAccessToken(userinfo), 3600 * 24 * 7);
        } catch (Exception e) {
            log.error(String.format("用户%s保存token到redis出错：", userinfo.getUid() + e.getMessage()));
        }
    }

    @Override
    public UserInfo findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public void setUserLoginFailLog(Long uid) {
        long dateline = System.currentTimeMillis() / 1000;
        // 缓存300 秒 五分钟后重试
        int cacheTime = 300;
        String cacheId = loginFailCacheKey(uid);
        String redisStr = redisService.get(cacheId);
        UserLoginFailLog loginFailLog = null;
        if (StringUtils.isNotBlank(redisStr)) {
            loginFailLog = JSON.parseObject(redisStr, UserLoginFailLog.class);
            if (null == loginFailLog) {
                loginFailLog = new UserLoginFailLog();
                loginFailLog.setUid(uid);
                loginFailLog.setFirst(dateline);
                loginFailLog.setLast(dateline);
                loginFailLog.setCount(1);
            } else {
                loginFailLog.setCount(loginFailLog.getCount() + 1);
                loginFailLog.setLast(dateline);
            }
        } else {
            loginFailLog = new UserLoginFailLog();
            loginFailLog.setUid(uid);
            loginFailLog.setFirst(dateline);
            loginFailLog.setLast(dateline);
            loginFailLog.setCount(1);
        }
        // redisService.set(cacheId, log, cacheTime); // 缓存300 秒
        redisService.set(cacheId, loginFailLog, cacheTime);
    }

    @Override
    public boolean checkisAllowLogin(Long uid) {
        String cacheId = loginFailCacheKey(uid);
        UserLoginFailLog loginFailLog = JSON.parseObject(redisService.get(cacheId), UserLoginFailLog.class);
        if (null != loginFailLog) {
            try {
                int failCount = loginFailLog.getCount();
                if (failCount >= 5) {
                    return false;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 根据uid和地址，生成登陆错误日志的KEY
     *
     * @param uid
     * @return
     */
    private String loginFailCacheKey(Long uid) {
        return "setUserLoginFailLog::" + uid;
    }
}
