/**
 * Copyright (2014, ) Institute of Software, Chinese Academy of Sciences
 */
package com.oncecloud.core.ha.ref;

import org.Xen.API.Connection;

/**
 * @author henry
 * @date   2014年8月22日
 *  
 *  用于记录资源资源的关键信息
 */
public class PoolRef {

	private String m_poolUUID = null;

	private String m_HAPath = null;
	
	private Connection m_xenConn= null;
	
	private String m_master;
	
	public String getPoolUUID() {
		return m_poolUUID;
	}

	public void setPoolUUID(String poolUUID) {
		this.m_poolUUID = poolUUID;
	}

	public String getHAPath() {
		return m_HAPath;
	}

	public void setHAPath(String HAPath) {
		m_HAPath = HAPath;
	}

	public String getMaster() {
		return m_master;
	}

	public void setMaster(String master) {
		this.m_master = master;
	}

	public Connection getXenConn() {
		return m_xenConn;
	}

	public void setXenConn(Connection xenConn) {
		this.m_xenConn = xenConn;
	}

}
