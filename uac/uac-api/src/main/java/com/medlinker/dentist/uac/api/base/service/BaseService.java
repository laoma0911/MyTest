package com.medlinker.dentist.uac.api.base.service;

import com.medlinker.dentist.uac.api.base.filter.QueryFilter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mazb on 2016/4/6.
 */
public abstract interface BaseService<T, PK extends Serializable> {

    public abstract int insert(T paramT);

    public abstract int update(T paramT);

    public abstract T get(PK paramPK);

    public abstract List<T> find(QueryFilter filter);

    public abstract Integer count(QueryFilter filter);

    public abstract void remove(PK paramPK);
}

