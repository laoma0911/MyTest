package com.medlinker.dentist.uac.provider.dao;


import com.medlinker.dentist.uac.api.base.dao.BaseDao;
import com.medlinker.dentist.uac.api.model.UserInfo;


/**
 * @author mazb
 * @date: 06 03, 2015 16:10
 */
public interface UserDao extends BaseDao<UserInfo,Long> {

    /**
     * 根据用户名查询用户
     *
     * @param nick
     * @return
     */
    public UserInfo findByUsername(String nick);
}
