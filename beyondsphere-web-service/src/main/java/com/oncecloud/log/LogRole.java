package com.oncecloud.log;

import com.oncecloud.constants.LogConstant;

public class LogRole {
	//主机
	public final static String INSTANCE = LogConstant.logObject.Instance.toString();
	
	//硬盘
	public final static String VOLUME = LogConstant.logObject.Volume.toString();
	
	//备份
	public final static String SNAPSHOT = LogConstant.logObject.Snapshot.toString();
	
	//模板
	public final static String IMAGE = LogConstant.logObject.Images.toString();
	
	//备份连
	public final static String SNAPSHOTSERIES = LogConstant.logObject.Snapshotseries.toString();
	
	//用户
	public final static String USER = LogConstant.logObject.User.toString();

	//资源池
	public final static String POOL = LogConstant.logObject.Pool.toString();
	
	//高可用
	public final static String HA = LogConstant.logObject.Ha.toString();
	
	//存储
	public final static String STORAGE = LogConstant.logObject.Storage.toString();
	
	//主机
	public final static String HOST = LogConstant.logObject.Host.toString();
	
	//网络
	public final static String NETWORK = LogConstant.logObject.Network.toString();
	
	//地址
	public final static String ADDRESS = LogConstant.logObject.Address.toString();
	
	//地址池
	public final static String ADDRESSPOOL = LogConstant.logObject.AddressPool.toString();
	
	//数据中心
	public final static String DATACENTER = LogConstant.logObject.DataCenter.toString();
	
	//主节点
	public final static String MASTERNODE = LogConstant.logObject.MasterNode.toString();
	
	//角色
	public final static String ROLE = LogConstant.logObject.Role.toString();
	
	//ISO
	public final static String ISO = LogConstant.logObject.ISO.toString();
}
