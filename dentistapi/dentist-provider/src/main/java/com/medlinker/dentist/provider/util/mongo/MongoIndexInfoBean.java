package com.medlinker.dentist.provider.util.mongo;

/**
 * 类 名: MongoCollectionStats<br/>
 * 描 述: 描述类完成的主要功能<br/>
 * 作 者: wangbo<br/>
 * 版 本：<br/>
 * 
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
public class MongoIndexInfoBean {
	private String name;// 索引名
	private boolean unique;// 是否唯一
	private String key;// 索引的列名
	private String order;// 索引的排序
	private boolean dropDups;// 是否消除重复数据
	private boolean background;// 是否后台创建
	private String ns;//命名空间 
	private long size; //索引大小

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public boolean isDropDups() {
		return dropDups;
	}

	public void setDropDups(boolean dropDups) {
		this.dropDups = dropDups;
	}

	public boolean isBackground() {
		return background;
	}

	public void setBackground(boolean background) {
		this.background = background;
	}

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
