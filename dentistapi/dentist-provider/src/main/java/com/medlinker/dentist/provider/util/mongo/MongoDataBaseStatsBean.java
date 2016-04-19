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
public class MongoDataBaseStatsBean {

	private String db;
	private int collections;//Contains a count of the number of collections in that database.
	private int objects;
	private double avgObjSize;// 平均每个文档占用的空间大小
	private double dataSize;// 在此数据库中包括的填充因子中保存的数据的总大小（以字节为单位）。规模参数会影响这个值。例试图将不会减少文件时收缩，但将减少当你删除文件。
	private double storageSize;// 在这个数据库中的文件存储空间总量（以字节为单位）分配到集合。规模参数会影响这个值。的storageSize并没有减少您删除或缩小文档。
	private int indexes;// 索引个数，每个集合至少有一个 _id 索引。
	private double indexSize;// 包含所有集合在数据库中的索引总数计数。
	private double fileSize;// 有数据库的数据文件的总大小（以字节为单位）。此值包括预分配的空间和填充因子。的的fileSize文件的值只反映为数据库的数据文件，而不是命名空间中的文件的大小。
	private int nsZizeMB;// 此数据库命名空间的文件的总大小（即为此。纳秒）。你不能改变的命名空间的大小，文件创建数据库后，运行选项nssize所有新的命名空间的文件，但你可以改变这个默认大小。
	
	private List<MongoDataBaseStatsBean> shardList;//分片模式
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	public int getCollections() {
		return collections;
	}
	public void setCollections(int collections) {
		this.collections = collections;
	}
	public int getObjects() {
		return objects;
	}
	public void setObjects(int objects) {
		this.objects = objects;
	}
	public double getAvgObjSize() {
		return avgObjSize;
	}
	public void setAvgObjSize(double avgObjSize) {
		this.avgObjSize = avgObjSize;
	}
	public double getDataSize() {
		return dataSize;
	}
	public void setDataSize(double dataSize) {
		this.dataSize = dataSize;
	}
	public double getStorageSize() {
		return storageSize;
	}
	public void setStorageSize(double storageSize) {
		this.storageSize = storageSize;
	}
	public int getIndexes() {
		return indexes;
	}
	public void setIndexes(int indexes) {
		this.indexes = indexes;
	}
	public double getIndexSize() {
		return indexSize;
	}
	public void setIndexSize(double indexSize) {
		this.indexSize = indexSize;
	}
	public double getFileSize() {
		return fileSize;
	}
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	public int getNsZizeMB() {
		return nsZizeMB;
	}
	public void setNsZizeMB(int nsZizeMB) {
		this.nsZizeMB = nsZizeMB;
	}
	public List<MongoDataBaseStatsBean> getShardList() {
		return shardList;
	}
	public void setShardList(List<MongoDataBaseStatsBean> shardList) {
		this.shardList = shardList;
	}

}
