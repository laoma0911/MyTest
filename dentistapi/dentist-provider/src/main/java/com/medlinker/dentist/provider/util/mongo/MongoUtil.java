package com.medlinker.dentist.provider.util.mongo;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.medlinker.dentist.provider.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

/**
 * 类 名: MongoDbUtil<br/>
 * 描 述: 描述类完成的主要功能<br/>
 * 作 者: wangbo<br/>
 * 版 本：<br/>
 * 
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
public class MongoUtil {
	/**
	 * slf4j log
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(MongoUtil.class);
	private MongoClient mongoClient = null;
	private DB db = null;
	private static String host;
	private static String port;
	private static String dbName;

	private static Map<String, MongoUtil> instances = new ConcurrentHashMap<String, MongoUtil>();
	
	private static MongoUtil mongoUtil=null;
	
	public MongoUtil() {
		
	}
	
	/**
	 * 实例化
	 * 
	 * @return MongoUtil对象
	 */
	static {
		try {
			Resource resource = new ClassPathResource("/mongo.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			host = props.getProperty("host");
			port = props.getProperty("port");
			dbName = props.getProperty("dbname");
			getInstance();// 初始化默认的MongoDB数据库
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	//这样写是单例加载性能最好的，内部类来做到延迟加载对象
	//在初始化这个内部类的时候，JLS(Java Language Sepcification)会保证这个类的线程安全。
	//这种写法最大的美在于，完全使用了Java虚拟机的机制进行同步保证，没有一个同步的关键字。
	//为了避免getNewInstance出错，暂时不用这个
	/*
	private static class SingletonHolder {
		public final static MongoUtil instance = getNewInstance(host, port, dbName);
	}
	 */
	
	public static MongoUtil getInstance() {
		//最好的写法，但是防止初始化失败，暂时不能用
//		return SingletonHolder.instance;
		//双重检查法，保证线程安全
		if (mongoUtil == null) { 
			// 只有第一次才彻底执行这里的代码
			synchronized (MongoUtil.class) {
				// 再检查一次
				if (mongoUtil == null) {
					mongoUtil = getNewInstance(host, port, dbName);
					LOGGER.info("mongoUtil连接实例已建立:" + host);
				}
			}
		}
		return mongoUtil;
	}
	
	private static MongoUtil getNewInstance(String host, String port, String dbName) {

		MongoUtil mm = new MongoUtil();
		try {
			MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
			builder.connectionsPerHost(100);//默认使用100个连接池
			builder.threadsAllowedToBlockForConnectionMultiplier(5);//默认每个线程创建5个，最多就可以创建100*5个连接
			MongoClientOptions options = builder.build();
			List<ServerAddress> addrs = new ArrayList<ServerAddress>();
			String hosts[]=StringUtils.split(host,",");
			/*
			for(String strHost:hosts) {
				addrs.add(new ServerAddress(strHost, Integer.parseInt(port)));
			}
			*/
			//改为随机顺序
			int len = hosts.length;
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < len; i++) {
				list.add(hosts[i]);
			}
			for (int i = len; i > 0; i--) {
				int index = new Random().nextInt(i);
				String strHost=list.get(index);
				addrs.add(new ServerAddress(strHost, Integer.parseInt(port)));
				System.out.println("mongo连接信息["+(len-i+1)+"]:" + strHost);
				list.remove(index);
			}
			mm.mongoClient = new MongoClient(addrs,options);
			//读写分离代码,优先读取副本
			mm.mongoClient.setReadPreference(ReadPreference.secondaryPreferred());	
			mm.db = mm.mongoClient.getDB(dbName);
			//读写分离代码,优先读取副本
			mm.db.setReadPreference(ReadPreference.secondaryPreferred());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Can't connect " + dbName + " MongoDB!");
		}
		return mm;
	}

	public static void clearInstance(){
		mongoUtil.mongoClient.close();
		mongoUtil = null;
		LOGGER.info("mongoUtil连接实例已清空！");
	}
	
	/**
	 * 根据properties文件的key获取value
	 * 
	 * @param filePath
	 *            properties文件路径
	 * @param key
	 *            属性key
	 * @return 属性value
	 */
	private static String getProperty(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void runCommand(DBObject cmd) throws Exception{
		CommandResult commandResult = this.db.command(cmd);
		if(!commandResult.ok()){
			throw new Exception("mongo执行出错："+commandResult.getErrorMessage());
		}
	}
	
	/**
	 * 
	 * 描 述：建立分片<br/>
	 * 作 者：wangbo<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param collection
	 * @param indexName
	 * @param columnNames
	 * @throws Exception 
	 */
	public void createShard(String collection,String column) throws Exception{
		//分片使用
		BasicDBObject cmd=new BasicDBObject();
		cmd.put("shardcollection", "ods."+collection);
		cmd.put("key", new BasicDBObject(column,1));
		runAdminCommand(cmd);
	}
	
	public void runAdminCommand(DBObject cmd) throws Exception{
		List<ServerAddress> addrs = new ArrayList<ServerAddress>();
		String hosts[]=StringUtils.split(host,",");
		for(String strHost:hosts) {
			addrs.add(new ServerAddress(strHost, Integer.parseInt(port)));
		}
		
		MongoClient client = new MongoClient(addrs);
		DB db = client.getDB("admin");
		CommandResult commandResult = db.command(cmd);
		if(!commandResult.ok()){
			throw new Exception("mongo执行出错："+commandResult.getErrorMessage());
		}
		client.close();
	}
	
	/**获取Mongodb-表的状态*/
	public MongoServerStatsBean getServerStats(){
		MongoServerStatsBean statBean=new MongoServerStatsBean();
		CommandResult commandResult =this.db.command("serverStatus");
		statBean.setRawResult(commandResult);
		statBean.setRawResultStr(commandResult.toString());
		if(commandResult.ok()){
			//Map map=commandResult.toMap();
			statBean.setVersion(commandResult.getString("version"));
			statBean.setServerUsed(commandResult.getString("serverUsed"));
			statBean.setHost(commandResult.getString("host"));
			statBean.setPid(commandResult.getInt("pid"));
			statBean.setUptime(commandResult.getLong("uptime"));
			statBean.setUptimeMillis(commandResult.getLong("uptimeMillis"));
			statBean.setUptimeEstimate(commandResult.getLong("uptimeEstimate"));
			statBean.setProcess(commandResult.getString("process"));
			statBean.setLocalTime(DateUtil.formatDate(commandResult.getDate("localTime")));
			BasicDBObject memBasicDBObject=(BasicDBObject)commandResult.get("mem");
			statBean.setMemResident(memBasicDBObject.getLong("resident"));
			statBean.setMemVirtual(memBasicDBObject.getLong("virtual"));
		}
		return statBean;
	}
	
	
	/**获取Mongodb-表的状态*/
	public MongoDataBaseStatsBean getDBStats(){
		MongoDataBaseStatsBean statBean=new MongoDataBaseStatsBean();
		CommandResult commandResult = this.db.getStats();
//		System.out.println(commandResult.toString());
		statBean.setDb(dbName);
		if(commandResult.ok()){
			
			
			if(commandResult.containsField("raw")){
				BasicDBObject rawDBObject = (BasicDBObject)commandResult.get("raw");
				Map<String,Object> rawMap=rawDBObject.toMap();
				List<MongoDataBaseStatsBean> shardList=new ArrayList<MongoDataBaseStatsBean>();
				for(String shardKey : rawMap.keySet()){
				    MongoDataBaseStatsBean shardStatsBean=new MongoDataBaseStatsBean();
				    shardStatsBean.setDb(shardKey);
				    BasicDBObject shardObject = (BasicDBObject)rawMap.get(shardKey);
					if(shardObject.containsField("collections")){
						shardStatsBean.setCollections(shardObject.getInt("collections"));
					}
					shardStatsBean.setObjects(shardObject.getInt("objects"));
					if(shardObject.containsField("avgObjSize")){
						shardStatsBean.setAvgObjSize(shardObject.getDouble("avgObjSize"));
					}
					if(shardObject.containsField("dataSize")){
						shardStatsBean.setDataSize(shardObject.getDouble("dataSize"));
					}
					shardStatsBean.setStorageSize(shardObject.getDouble("storageSize"));
					shardStatsBean.setIndexes(shardObject.getInt("indexes"));
					shardStatsBean.setIndexSize(shardObject.getDouble("indexSize"));
					shardStatsBean.setFileSize(shardObject.getDouble("fileSize"));
					shardList.add(shardStatsBean);
				}
				statBean.setShardList(shardList);
			}
			if(commandResult.containsField("collections")){
				statBean.setCollections(commandResult.getInt("collections"));
			}
			statBean.setObjects(commandResult.getInt("objects"));
			if(commandResult.containsField("avgObjSize")){
				statBean.setAvgObjSize(commandResult.getDouble("avgObjSize"));
			}
			if(commandResult.containsField("dataSize")){
				statBean.setDataSize(commandResult.getDouble("dataSize"));
			}
			statBean.setStorageSize(commandResult.getDouble("storageSize"));
			statBean.setIndexes(commandResult.getInt("indexes"));
			statBean.setIndexSize(commandResult.getDouble("indexSize"));
			statBean.setFileSize(commandResult.getDouble("fileSize"));
		}
		return statBean;
	}
	
	
	/**获取Mongodb-表的状态*/
	public MongoCollectionStatsBean getCollectionStats(String collection){
		MongoCollectionStatsBean statBean=new MongoCollectionStatsBean();
		CommandResult commandResult = getCollection(collection).getStats();
//		System.out.println(commandResult.toString());
		statBean.setNs(collection);
		if(commandResult.ok()){
//			Map map=commandResult.toMap();
//			statBean.setNs((String)map.get("ns"));
			statBean.setCount(commandResult.getInt("count"));
			statBean.setSize(commandResult.getDouble("size"));
			if(commandResult.containsField("avgObjSize")){
				statBean.setAvgObjSize(commandResult.getDouble("avgObjSize"));
			}
			statBean.setStorageSize(commandResult.getDouble("storageSize"));
			statBean.setNumExtents(commandResult.getInt("numExtents"));
			statBean.setNindexes(commandResult.getInt("nindexes"));
			if(commandResult.containsField("lastExtentSize")){
				statBean.setLastExtentSize(commandResult.getInt("lastExtentSize"));
			}
			statBean.setTotalIndexSize(commandResult.getInt("totalIndexSize"));
			DBObject indexSizes=(DBObject)commandResult.get("indexSizes");
			List<DBObject> indexList=getCollection(collection).getIndexInfo();
			List<MongoIndexInfoBean> idxInfoList=new ArrayList();
			for(DBObject idxDBObject:indexList){
				MongoIndexInfoBean idxInfo=new MongoIndexInfoBean();
				idxInfo.setNs(collection);//namespace
				String indexName=(String)idxDBObject.get("name");
				idxInfo.setName(indexName);
				if(indexSizes.containsField(indexName)){
					idxInfo.setSize(NumberUtils.toLong(indexSizes.get(indexName).toString()));
				}
				if(idxDBObject.containsField("key")){
					DBObject key=(DBObject)idxDBObject.get("key");
					idxInfo.setKey(key.toString());
				}else{
					idxInfo.setKey("");
				}
				if(idxDBObject.containsField("unique")){
					idxInfo.setUnique((Boolean)idxDBObject.get("unique"));
				}else{
					idxInfo.setUnique(false);
				}
				if(idxDBObject.containsField("dropDups")){
					idxInfo.setDropDups((Boolean)idxDBObject.get("dropDups"));
				}else{
					idxInfo.setDropDups(false);
				}
				if(idxDBObject.containsField("background")){
					idxInfo.setBackground((Boolean)idxDBObject.get("background"));
				}else{
					idxInfo.setBackground(false);
				}
				idxInfoList.add(idxInfo);
//				System.out.println(idxDBObject.toString());
			}
			statBean.setIndexInfoList(idxInfoList);
			
			
			//分片信息
			if(commandResult.containsField("sharded")){
				statBean.setSharded(commandResult.getBoolean("sharded"));
				if(statBean.isSharded()&&commandResult.containsField("shards")){
					BasicDBObject shardsDBObject = (BasicDBObject)commandResult.get("shards");
					Map<String,Object> shardsMap=shardsDBObject.toMap();
					List<MongoCollectionStatsBean> shardList=new ArrayList<MongoCollectionStatsBean>();
					for(String shardKey : shardsMap.keySet()){
						MongoCollectionStatsBean shardConllectionStat=new MongoCollectionStatsBean();
						shardConllectionStat.setNs(shardKey);
						BasicDBObject shardObject = (BasicDBObject)shardsMap.get(shardKey);
						shardConllectionStat.setCount(shardObject.getInt("count"));
						shardConllectionStat.setSize(shardObject.getDouble("size"));
						if(shardObject.containsField("avgObjSize")){
							shardConllectionStat.setAvgObjSize(shardObject.getDouble("avgObjSize"));
						}
						shardConllectionStat.setStorageSize(shardObject.getDouble("storageSize"));
						shardConllectionStat.setNumExtents(shardObject.getInt("numExtents"));
						shardConllectionStat.setNindexes(shardObject.getInt("nindexes"));
						if(shardObject.containsField("lastExtentSize")){
							shardConllectionStat.setLastExtentSize(shardObject.getInt("lastExtentSize"));
						}
						shardConllectionStat.setTotalIndexSize(shardObject.getInt("totalIndexSize"));
						
						shardList.add(shardConllectionStat);
					}
					statBean.setShardList(shardList);
				}
			}else{
				statBean.setSharded(false);
			}
		}
		return statBean;
	}
	/**
	 * 
	 * 描 述：建立索引<br/>
	 * 作 者：wangbo<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param collection
	 * @param indexName
	 * @param columnNames
	 */
	public void ensureIndex(String collection,DBObject keys,String indexName,boolean unique){
		getCollection(collection).createIndex(keys, indexName, unique);
	}
	
	/**
	 * 
	 * 描 述：删除索引<br/>
	 * 作 者：wangbo<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param collection
	 * @param indexName
	 * @param columnNames
	 */
	public void dropIndex(String collection,String indexName){
		getCollection(collection).dropIndex(indexName);
	}

	/**
	 * 获取集合（表）
	 * 
	 * @param collection
	 */
	public DBCollection getCollection(String collection) {
		return this.db.getCollection(collection);
	}
	
	/**
	 * 获取全部集合（表）
	 * 
	 * @param collection
	 */
	public Set<String> getAllCollections() {
		Set<String> set= this.db.getCollectionNames();
		return set;
	}

	/**
	 * ----------------------------------分割线------------------------------------
	 */

	/**
	 * 插入
	 * 
	 * @param collection
	 * @param o
	 *            插入
	 */
	public void insert(String collection, DBObject o) {
		getCollection(collection).insert(o);
	}

	/**
	 * 批量插入
	 * 
	 * @param collection
	 * @param list
	 *            插入的列表
	 */
	public void insertBatch(String collection, List<DBObject> list) {

		if (list == null || list.isEmpty()) {
			return;
		}

		getCollection(collection).insert(list);

	}

	/**
	 * 删除
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 */
	public void delete(String collection, DBObject q) {

		getCollection(collection).remove(q);
	}

	/**
	 * 批量删除
	 * 
	 * @param collection
	 * @param list
	 *            删除条件列表
	 */
	public void deleteBatch(String collection, List<DBObject> list) {

		if (list == null || list.isEmpty()) {
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			getCollection(collection).remove(list.get(i));
		}
	}

	/**
	 * 计算集合总条数
	 * 
	 * @param collection
	 */
	public long getCount(String collection) {

		return getCollection(collection).find().count();
	}

	/**
	 * 计算满足条件条数
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 */

	public long getCount(String collection, DBObject q) {

		return getCollection(collection).getCount(q);
	}

	/**
	 * 更新某几个字段，无需自己写$set
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param setFields
	 *            更新对象
	 */
	public void updateFields(String collection, DBObject q, DBObject setFields) {
		getCollection(collection).updateMulti(q, new BasicDBObject("$set", setFields));
	}
	/**
	 * 原始update,更新全部字段，除非自己写$set
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param setFields
	 *            更新对象
	 */
	public void updateMulti(String collection, DBObject q, DBObject dbObject) {
		getCollection(collection).updateMulti(q, dbObject);
	}
	
	/**
	 * 原始update,更新部分字段
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param setFields
	 *            更新对象
	 */
	public void updateOne(String collection, DBObject q, DBObject setFields) {
		getCollection(collection).update(q, new BasicDBObject("$set", setFields),false,false);
	}
	
	/**
	 * 仅更新一条（如果没有则插入），更新部分字段
	 * @param collection
	 * @param query 查询条件
	 * @param setFields 更新对象
	 */
	public void updins(String collection, DBObject query, DBObject setFields) {
		WriteResult writeResult=getCollection(collection).update(query, new BasicDBObject("$set", setFields), true, false);
	}  
	
	/**
	 * 删除部分字段
	 * @param collection
	 * @param q 查询条件
	 * @param unsetFields 删除的字段
	 */
	public void unset(String collection, DBObject q, DBObject unsetFields) {
		BasicDBObject fields=new BasicDBObject();
		for(String key:unsetFields.keySet()) {
			fields.append(key, 1);
		}
		if(fields.size()>0) {
			WriteResult writeResult=getCollection(collection).update(q, new BasicDBObject("$unset", fields), true, false);
		}
	}  
	
	

	/**
	 * 查找集合所有对象
	 * 
	 * @param collection
	 */
	public List<DBObject> findAll(String collection) {

		return getCollection(collection).find().toArray();
	}

	/**
	 * 按顺序查找集合所有对象
	 * 
	 * @param collection
	 *            数据集
	 * @param orderBy
	 *            排序
	 */
	public List<DBObject> findAll(String collection, DBObject orderBy) {

		return getCollection(collection).find().sort(orderBy).toArray();
	}

	/**
	 * 查找（返回一个对象）
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 */
	public DBObject findOne(String collection, DBObject q) {

		return getCollection(collection).findOne(q);
	}
	/**
	 * 
	 * 修改
	 * @param collection
	 * @param query
	 * @param update
	 * @return
	 */
	public DBObject findAndModify(String collection, DBObject query,DBObject setFields) {
		return getCollection(collection).findAndModify(query, new BasicDBObject("$set", setFields));
	}
	
	
	/**
	 * 查找（返回一个对象）
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param fileds
	 *            返回字段
	 */
	public DBObject findOne(String collection, DBObject q, DBObject fileds) {

		return getCollection(collection).findOne(q, fileds);
	}

	/**
	 * 查找返回特定字段（返回一个List<DBObject>）
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param fileds
	 *            返回字段
	 */
	public List<DBObject> findLess(String collection, DBObject q, DBObject fileds) {

		DBCursor c = getCollection(collection).find(q, fileds);
		if (c != null)
			return c.toArray();
		else
			return null;
	}

	/**
	 * 查找返回特定字段（返回一个List<DBObject>）
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param fileds
	 *            返回字段
	 * @param orderBy
	 *            排序
	 */
	public List<DBObject> findLess(String collection, DBObject q, DBObject fileds, DBObject orderBy) {

		DBCursor c = getCollection(collection).find(q, fileds).sort(orderBy);
		if (c != null)
			return c.toArray();
		else
			return null;
	}

	/**
	 * 分页查找集合对象，返回特定字段
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param fileds
	 *            返回字段
	 * @pageNo 第n页
	 * @perPageCount 每页记录数
	 */
	public List<DBObject> findLess(String collection, DBObject q, DBObject fileds, int pageNo, int perPageCount) {

		return getCollection(collection).find(q, fileds).skip((pageNo - 1) * perPageCount).limit(perPageCount).toArray();
	}

	/**
	 * 按顺序分页查找集合对象，返回特定字段
	 * 
	 * @param collection
	 *            集合
	 * @param q
	 *            查询条件
	 * @param fileds
	 *            返回字段
	 * @param orderBy
	 *            排序
	 * @param pageNo
	 *            第n页
	 * @param perPageCount
	 *            每页记录数
	 */
	public List<DBObject> findLess(String collection, DBObject q, DBObject fileds, DBObject orderBy, int pageNo, int perPageCount) {

		return getCollection(collection).find(q, fileds).sort(orderBy).skip((pageNo - 1) * perPageCount).limit(perPageCount).toArray();
	}

	/**
	 * 查找（返回一个List<DBObject>）
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 */
	public List<DBObject> find(String collection, DBObject q) {

		DBCursor c = getCollection(collection).find(q);
		if (c != null)
			return c.toArray();
		else
			return null;
	}

	/**
	 * 按顺序查找（返回一个List<DBObject>）
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param orderBy
	 *            排序
	 */
	public List<DBObject> find(String collection, DBObject q, DBObject orderBy) {

		DBCursor c = getCollection(collection).find(q).sort(orderBy);
		if (c != null)
			return c.toArray();
		else
			return null;
	}

	/**
	 * 分页查找集合对象
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @pageNo 第n页
	 * @perPageCount 每页记录数
	 */
	public List<DBObject> find(String collection, DBObject q, int pageNo, int perPageCount) {

		return getCollection(collection).find(q).skip((pageNo - 1) * perPageCount).limit(perPageCount).toArray();
	}

	/**
	 * 按顺序分页查找集合对象
	 * 
	 * @param collection
	 *            集合
	 * @param q
	 *            查询条件
	 * @param orderBy
	 *            排序
	 * @param pageNo
	 *            第n页
	 * @param perPageCount
	 *            每页记录数
	 */
	public List<DBObject> find(String collection, DBObject q, DBObject orderBy, int pageNo, int perPageCount) {

		return getCollection(collection).find(q).sort(orderBy).skip((pageNo - 1) * perPageCount).limit(perPageCount).toArray();
	}

	/**
	 * distinct操作
	 * 
	 * @param collection
	 *            集合
	 * @param field
	 *            distinct字段名称
	 */
	public Object[] distinct(String collection, String field) {

		return getCollection(collection).distinct(field).toArray();
	}

	/**
	 * distinct操作
	 * 
	 * @param collection
	 *            集合
	 * @param field
	 *            distinct字段名称
	 * @param q
	 *            查询条件
	 */
	public Object[] distinct(String collection, String field, DBObject q) {

		return getCollection(collection).distinct(field, q).toArray();
	}

	/**
	 * group分组查询操作，返回结果少于10,000keys时可以使用
	 * 
	 * @param collection
	 *            集合
	 * @param key
	 *            分组查询字段
	 * @param q
	 *            查询条件
	 * @param reduce
	 *            reduce Javascript函数，如：function(obj,
	 *            out){out.count++;out.csum=obj.c;}
	 * @param finalize
	 *            reduce
	 *            function返回结果处理Javascript函数，如：function(out){out.avg=out.csum
	 *            /out.count;}
	 */
	public BasicDBList group(String collection, DBObject key, DBObject q, DBObject initial, String reduce, String finalize) {

		return ((BasicDBList) getCollection(collection).group(key, q, initial, reduce, finalize));
	}

	/**
	 * group分组查询操作，返回结果大于10,000keys时可以使用
	 * 
	 * @param collection
	 *            集合
	 * @param map
	 *            映射javascript函数字符串，如：function(){ for(var key in this) {
	 *            emit(key,{count:1}) } }
	 * @param reduce
	 *            reduce Javascript函数字符串，如：function(key,emits){ total=0; for(var
	 *            i in emits){ total+=emits[i].count; } return {count:total}; }
	 * @param q
	 *            分组查询条件
	 * @param orderBy
	 *            分组查询排序
	 */
	/*
	public Iterable<DBObject> mapReduce(String collection, String map, String reduce, DBObject q, DBObject orderBy) {

		DBCollection coll = db.getCollection(collection);
		MapReduceCommand cmd = new MapReduceCommand(coll, map, reduce, null, MapReduceCommand.OutputType.INLINE, q);
		// return coll.mapReduce(cmd).results();
		MapReduceOutput out = getCollection(collection).mapReduce(map, reduce, null, q);
		return out.getOutputCollection().find().sort(orderBy).toArray();
	}
	 */
	/**
	 * group分组分页查询操作，返回结果大于10,000keys时可以使用
	 * 
	 * @param collection
	 *            集合
	 * @param map
	 *            映射javascript函数字符串，如：function(){ for(var key in this) {
	 *            emit(key,{count:1}) } }
	 * @param reduce
	 *            reduce Javascript函数字符串，如：function(key,emits){ total=0; for(var
	 *            i in emits){ total+=emits[i].count; } return {count:total}; }
	 * @param q
	 *            分组查询条件
	 * @param orderBy
	 *            分组查询排序
	 * @param pageNo
	 *            第n页
	 * @param perPageCount
	 *            每页记录数
	 */
	// public List<DBObject> mapReduce(String collection, String map, String
	// reduce,
	// DBObject q, DBObject orderBy, int pageNo, int perPageCount) {
	//
	// MapReduceOutput out = getCollection(collection).mapReduce(map, reduce,
	// null, q);
	// return out.getOutputCollection().find().sort(orderBy).skip((pageNo - 1) *
	// perPageCount)
	// .limit(perPageCount).toArray();
	// }

	/**
	 * group分组查询操作，返回结果大于10,000keys时可以使用
	 * 
	 * @param collection
	 *            集合
	 * @param map
	 *            映射javascript函数字符串，如：function(){ for(var key in this) {
	 *            emit(key,{count:1}) } }
	 * @param reduce
	 *            reduce Javascript函数字符串，如：function(key,emits){ total=0; for(var
	 *            i in emits){ total+=emits[i].count; } return {count:total}; }
	 * @param outputCollectionName
	 *            输出结果表名称
	 * @param q
	 *            分组查询条件
	 * @param orderBy
	 *            分组查询排序
	 */
	public List<DBObject> mapReduce(String collection, String map, String reduce, String outputCollectionName, DBObject q, DBObject orderBy) {

		if (!db.collectionExists(outputCollectionName)) {
			getCollection(collection).mapReduce(map, reduce, outputCollectionName, q);
		}

		return getCollection(outputCollectionName).find(null, new BasicDBObject("_id", false)).sort(orderBy).toArray();
	}

	/**
	 * group分组分页查询操作，返回结果大于10,000keys时可以使用
	 * 
	 * @param collection
	 *            集合
	 * @param map
	 *            映射javascript函数字符串，如：function(){ for(var key in this) {
	 *            emit(key,{count:1}) } }
	 * @param reduce
	 *            reduce Javascript函数字符串，如：function(key,emits){ total=0; for(var
	 *            i in emits){ total+=emits[i].count; } return {count:total}; }
	 * @param outputCollectionName
	 *            输出结果表名称
	 * @param q
	 *            分组查询条件
	 * @param orderBy
	 *            分组查询排序
	 * @param pageNo
	 *            第n页
	 * @param perPageCount
	 *            每页记录数
	 */
	public List<DBObject> mapReduce(String collection, String map, String reduce, String outputCollectionName, DBObject q, DBObject orderBy,
			int pageNo, int perPageCount) {

		if (!db.collectionExists(outputCollectionName)) {
			getCollection(collection).mapReduce(map, reduce, outputCollectionName, q);
		}

		return getCollection(outputCollectionName).find(null, new BasicDBObject("_id", false)).sort(orderBy).skip((pageNo - 1) * perPageCount)
				.limit(perPageCount).toArray();
	}

	public static void main(String[] args) {
		try {
			MongoUtil mongoUtil=MongoUtil.getInstance();
			List list=mongoUtil.find("FRM_CODE", new BasicDBObject());
			System.out.println(list);
			MongoUtil.getInstance();
			MongoUtil.getInstance();
			MongoUtil.getInstance();
			MongoUtil.getInstance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

//	public String getHost() {
//		return host;
//	}
//
//	public void setHost(String host) {
//		this.host = host;
//	}
//
//	public String getPort() {
//		return port;
//	}
//
//	public void setPort(String port) {
//		this.port = port;
//	}

//	public String getDbName() {
//		return dbName;
//	}
//
//	public void setDbName(String dbName) {
//		this.dbName = dbName;
//	}



}
