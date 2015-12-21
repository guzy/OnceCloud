/**
 * Copyright (2014, ) Institute of Software, Chinese Academy of Sciences
 */
package com.oncecloud.core.ha.ref;

import java.util.ArrayList;
import java.util.List;


/**
 * @author henry
 * @date   2014年8月25日
 *
 * 用于记录服务器标识，IP以及可达状态，以及已迁移虚拟机信息，只是记录迁移过来的虚拟机信息，它是该物理机上所有虚拟机的子集
 */
public class HostRef {

	/**
	 * 主机UUID
	 */
	private String m_hostUUID;

	/**
	 * 所属资源池 UUID
	 */
	private String m_poolUUID;

	/**
	 * 物理服务器给虚拟机桥接使用的IP, 比如ovs0
	 */
	private String m_vbIP;

	/**
	 * 物理服务器给虚拟机桥接使用的IP连续不可达次数, 比如ovs0
	 */
	private int vbUnavaiableTimes = 0;
	
	/**
	 * 迁移过来的虚拟机全集
	 */
	private List<VMRef> m_failoverVMs = new ArrayList<VMRef>();
	

	public String getHostUUID() {
		return m_hostUUID;
	}

	public void setHostUUID(String hostUUID) {
		this.m_hostUUID = hostUUID;
	}


	public String getPoolUUID() {
		return m_poolUUID;
	}

	public void setPoolUUID(String poolUUID) {
		this.m_poolUUID = poolUUID;
	}

	/**
	 * 物理服务器给虚拟机桥接使用的IP, 比如ovs0
	 */
	public String getVMHostIP() {
		return m_vbIP;
	}

	/**
	 * 物理服务器给虚拟机桥接使用的IP, 比如ovs0
	 */
	public void setVMHostIP(String vbIP) {
		this.m_vbIP = vbIP;
	}

	/**
	 * 获取物理服务器给虚拟机桥接使用的IP连续不可达次数
	 * 
	 */
	public int getVmUnavaiableTimes() {
		return vbUnavaiableTimes;
	}

	/**
	 * 增加物理服务器给虚拟机桥接使用的IP连续不可达次数
	 */
	public void incrementVmUnavaiableTimes() {
		vbUnavaiableTimes++;
	}
	
	
	public void resetVmUnavaiableTimes() {
		vbUnavaiableTimes = 0;
	}
	
	

	public List<VMRef> getM_failoverVMs() {
		return m_failoverVMs;
	}

	public void setM_failoverVMs(List<VMRef> m_failoverVMs) {
		this.m_failoverVMs = m_failoverVMs;
	}

	/**
	 * 不存在返回Null的场景，只会返回数据数为0的List
	 * 
	 * @return
	 */
	public List<VMRef> getFailoverVMs() {
		return m_failoverVMs;
	}
	
	public void registerFailoverVM(VMRef vm) {
		m_failoverVMs.add(vm);
	}
	
	public void markVMFailoverSucessful(VMRef vm) {
		m_failoverVMs.remove(vm);
	}
	
	public void markAllVMsFailoverSucessful() {
		m_failoverVMs.clear();;
	}

}
