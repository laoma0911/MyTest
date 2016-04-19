package com.medlinker.dentist.provider.dao.impl;

import com.medlinker.dentist.api.model.Test;
import com.medlinker.dentist.provider.dao.TestDao;
import com.medlinker.dentist.uac.api.base.dao.impl.BaseDaoImpl;

/**
 * Created by mazb on 2016/4/18.
 */
public class TestDaoImpl extends BaseDaoImpl<Test,Long> implements TestDao {
    public TestDaoImpl() {
        setNameSpace("com.medlinker.dentist.test");
    }

}
