package com.medlinker.dentist.provider.service.impl;

import com.medlinker.dentist.api.interfaces.TestService;
import com.medlinker.dentist.api.model.Test;
import com.medlinker.dentist.provider.dao.TestDao;
import com.medlinker.dentist.uac.api.base.service.impl.BaseServiceImpl;

/**
 * Created by mazb on 2016/4/18.
 */
public class TestServiceImpl extends BaseServiceImpl<Test, Long> implements TestService {

    private TestDao dao;


    public TestServiceImpl(TestDao dao) {
        super(dao);
        this.dao = dao;
    }

}
