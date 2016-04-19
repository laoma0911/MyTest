package com.medlinker.dentist.uac.api.model;
/**
 * 登陆失败日志bean
 * @author mazb
 *
 */
public class UserLoginFailLog {
	private Long uid;
//	private String ip;//暂时不考虑Ip(备用属性)
	private Long first;
	private Long last;
	private Integer count;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getFirst() {
		return first;
	}
	public void setFirst(Long first) {
		this.first = first;
	}
	public Long getLast() {
		return last;
	}
	public void setLast(Long last) {
		this.last = last;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
