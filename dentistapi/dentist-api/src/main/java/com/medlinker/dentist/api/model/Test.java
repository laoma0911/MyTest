package com.medlinker.dentist.api.model;

import java.io.Serializable;

public class Test implements Serializable {
    private static final long serialVersionUID = -6188544384149284540L;
    private Long id;
    private Long uid;
    private Long status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
