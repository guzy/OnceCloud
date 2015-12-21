/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.dao;

import java.util.Set;

import com.oncecloud.entity.HostSR;

public interface HostSRDAO {
	
	/**
	 * 建立host和storage之间的联系
	 * @param hostUuid srUuid
	 * @return 
	 */
	public abstract HostSR createHostSR(String hostUuid, String srUuid);
	
	/**
	 * 根据hostUuid获取存储硬盘storage的列表
	 * @param hostUuid
	 * @return set
	 */
	public abstract Set<String> getSRList(String hostUuid);

	/**
	 * 根据srUuid获取主机列表
	 * @param srUuid
	 * @return
	 */
	public abstract Set<String> getHostList(String srUuid);

}
