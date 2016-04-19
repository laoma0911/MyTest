package com.medlinker.dentist.provider.util.mongo;

import java.util.List;

/**
 * 类 名: MongoCollectionStats<br/>
 * 描 述: 描述类完成的主要功能<br/>
 * 作 者: wangbo<br/>
 * 版 本：<br/>
 * 
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
public class MongoCollectionStatsBean {

	private String serverUsed;
	private String ns;// 集合的命名空间，可以理解为集合名称
	private long count;// 集合中的文档总数
	private double size;// 集合中数据占用空间大小，不包括索引 ，单位为字节。
	private double avgObjSize;// 平均每个文档占用的空间大小
	private double storageSize;// 给整个集合分配的存储空间，当删除集合中的文档时，这个值不会降代。
	private int numExtents;// 连续分配的数据块
	private int nindexes;// 索引个数，每个集合至少有一个 _id 索引。
	private int lastExtentSize;// 最近分配的块的大小
	private int paddingFactor;// 这个参数不太清楚，以后补充。
	private int systemFlags;// 这个参数不太清楚，以后补充。
	private int userFlags;// 这个参数不太清楚，以后补充。
	private int totalIndexSize;// 所有索引大小总和
	private List<MongoIndexInfoBean> indexInfoList;// 列出集合的所有索引字段，以及索引大小。
	private boolean sharded;//是否分片
	private List<MongoCollectionStatsBean> shardList;// 分片集合信息
	
	

	public String getServerUsed() {
		return serverUsed;
	}

	public void setServerUsed(String serverUsed) {
		this.serverUsed = serverUsed;
	}

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getAvgObjSize() {
		return avgObjSize;
	}

	public void setAvgObjSize(double avgObjSize) {
		this.avgObjSize = avgObjSize;
	}

	public double getStorageSize() {
		return storageSize;
	}

	public void setStorageSize(double storageSize) {
		this.storageSize = storageSize;
	}

	public int getNumExtents() {
		return numExtents;
	}

	public void setNumExtents(int numExtents) {
		this.numExtents = numExtents;
	}

	public int getNindexes() {
		return nindexes;
	}

	public void setNindexes(int nindexes) {
		this.nindexes = nindexes;
	}

	public int getLastExtentSize() {
		return lastExtentSize;
	}

	public void setLastExtentSize(int lastExtentSize) {
		this.lastExtentSize = lastExtentSize;
	}

	public int getPaddingFactor() {
		return paddingFactor;
	}

	public void setPaddingFactor(int paddingFactor) {
		this.paddingFactor = paddingFactor;
	}

	public int getSystemFlags() {
		return systemFlags;
	}

	public void setSystemFlags(int systemFlags) {
		this.systemFlags = systemFlags;
	}

	public int getUserFlags() {
		return userFlags;
	}

	public void setUserFlags(int userFlags) {
		this.userFlags = userFlags;
	}

	public int getTotalIndexSize() {
		return totalIndexSize;
	}

	public void setTotalIndexSize(int totalIndexSize) {
		this.totalIndexSize = totalIndexSize;
	}

	public List<MongoIndexInfoBean> getIndexInfoList() {
		return indexInfoList;
	}

	public void setIndexInfoList(List<MongoIndexInfoBean> indexInfoList) {
		this.indexInfoList = indexInfoList;
	}

	@Override
	public String toString() {
		return "MongoCollectionStatsBean [serverUsed=" + serverUsed + ", ns=" + ns + ", count=" + count + ", size=" + size + ", avgObjSize="
				+ avgObjSize + ", storageSize=" + storageSize + ", numExtents=" + numExtents + ", nindexes=" + nindexes + ", lastExtentSize="
				+ lastExtentSize + ", paddingFactor=" + paddingFactor + ", systemFlags=" + systemFlags + ", userFlags=" + userFlags
				+ ", totalIndexSize=" + totalIndexSize + ", indexInfoList=" + indexInfoList + "]";
	}

	public List<MongoCollectionStatsBean> getShardList() {
		return shardList;
	}

	public void setShardList(List<MongoCollectionStatsBean> shardList) {
		this.shardList = shardList;
	}


	public void setSharded(boolean sharded) {
		this.sharded = sharded;
	}

	public boolean isSharded() {
		return sharded;
	}

}
