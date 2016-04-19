package com.medlinker.dentist.provider.util.mongo;

import com.mongodb.CommandResult;


/**
 * 类 名: MongoCollectionStats<br/>
 * 描 述: 描述类完成的主要功能<br/>
 * 作 者: wangbo<br/>
 * 版 本：<br/>
 * 
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
public class MongoServerStatsBean {

	private String host;
	private String version;
	private String serverUsed;
	private String localTime;
	private String process;
	private int pid;
	private long uptime;
	private long uptimeMillis;
	private long uptimeEstimate;
	private long memResident;
	private long memVirtual;
	private String rawResultStr;
	private CommandResult rawResult;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getServerUsed() {
		return serverUsed;
	}
	public void setServerUsed(String serverUsed) {
		this.serverUsed = serverUsed;
	}
	public String getLocalTime() {
		return localTime;
	}
	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public long getUptime() {
		return uptime;
	}
	public void setUptime(long uptime) {
		this.uptime = uptime;
	}
	public long getUptimeMillis() {
		return uptimeMillis;
	}
	public void setUptimeMillis(long uptimeMillis) {
		this.uptimeMillis = uptimeMillis;
	}
	public long getUptimeEstimate() {
		return uptimeEstimate;
	}
	public void setUptimeEstimate(long uptimeEstimate) {
		this.uptimeEstimate = uptimeEstimate;
	}
	public long getMemResident() {
		return memResident;
	}
	public void setMemResident(long memResident) {
		this.memResident = memResident;
	}
	public long getMemVirtual() {
		return memVirtual;
	}
	public void setMemVirtual(long memVirtual) {
		this.memVirtual = memVirtual;
	}
	public CommandResult getRawResult() {
		return rawResult;
	}
	public void setRawResult(CommandResult rawResult) {
		this.rawResult = rawResult;
	}
	public String getRawResultStr() {
		return rawResultStr;
	}
	public void setRawResultStr(String rawResultStr) {
		this.rawResultStr = rawResultStr;
	}

}
