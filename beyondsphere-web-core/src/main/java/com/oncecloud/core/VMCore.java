package com.oncecloud.core;

import org.Xen.API.VM;


public interface VMCore {
	
	/**
	 * 获取电源状态
	 * @author lining
	 * @param poolUuid vmUuid
	 * @return
	 */
	public String getPowerStatus(String poolUuid, String vmUuid);
	
	/**
	 * 获取vm
	 * @author lining
	 * @param vmUuid
	 * @return
	 */
	public VM getVm(String poolUuid, String vmUuid);
	
	/**
	 * 删除vm
	 * @author lining
	 * @param poolUuid, vmUuid
	 * @return 
	 */
	public boolean destory(String poolUuid, String vmUuid);
	
	/**
	 * 添加网卡
	 * @author lining
	 * @param vmUuid, type, physical, vnetId
	 * @return
	 */
	public String addMAC(String poolUuid, String vmUuid, String physical,
			String vnetid);
}
