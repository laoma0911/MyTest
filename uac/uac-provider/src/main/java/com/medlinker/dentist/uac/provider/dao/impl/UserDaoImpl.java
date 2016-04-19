package com.medlinker.dentist.uac.provider.dao.impl;


import com.medlinker.dentist.uac.api.base.dao.impl.BaseDaoImpl;
import com.medlinker.dentist.uac.api.model.UserInfo;
import com.medlinker.dentist.uac.provider.dao.UserDao;


/**
 * @author mazb
 * @date: 06 03, 2015 16:10
 */
public class UserDaoImpl extends BaseDaoImpl<UserInfo,Long> implements UserDao {

    public UserDaoImpl() {
        setNameSpace("com.medlinker.uac.user");
    }

    @Override
    public UserInfo findByUsername(String nick) {
        return (UserInfo)getSqlSession().selectOne(getNameSpace() + ".getByUsername", nick);
    }
}
