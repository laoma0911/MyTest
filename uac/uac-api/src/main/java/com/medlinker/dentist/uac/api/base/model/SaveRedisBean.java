package com.medlinker.dentist.uac.api.base.model;

/**
 * Created by mazb on 2016/4/6.
 * redis保存键值对的bean
 * @author mazb
 *
 */
public class SaveRedisBean {
    private String key;
    private Object value;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
}
