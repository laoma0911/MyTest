package com.medlinker.dentist.uac.api.base.service.impl;

import com.medlinker.dentist.uac.api.base.dao.BaseDao;
import com.medlinker.dentist.uac.api.base.service.BaseService;
import com.medlinker.dentist.uac.api.base.filter.QueryFilter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mazb on 2016/4/6.
 */
public class BaseServiceImpl<T, PK extends Serializable> implements
        BaseService<T, PK> {

    protected BaseDao<T, PK> dao = null;

    public BaseServiceImpl() {

    }

    public BaseServiceImpl(BaseDao<T, PK> dao) {
        this.dao = dao;
    }

    public void setDao(BaseDao<T, PK> dao) {
        this.dao = dao;
    }

    public T get(PK id) {
        return this.dao.get(id);
    }

    public int insert(T bean) {
        return this.dao.insert(bean);
    }

    public int update(T bean) {
        return this.dao.update(bean);
    }

    public List<T> find(QueryFilter filter) {
        return this.dao.find(filter);
    }

    public Integer count(QueryFilter filter) {
        return this.dao.count(filter);
    }

    public void remove(PK id) {
        this.dao.remove(id);
    }

}