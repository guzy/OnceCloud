package com.beyondsphere.core;

import com.beyondsphere.entity.OCHost;


public interface ImageCore {
	
	/**
	 * 检查image在物理主机中是否存在
	 * @author lining
	 * @param host
	 * @return
	 */
	public abstract boolean imageExist(OCHost host, String imageUuid);
	
	/**
	 * 分享迁移image
	 * @author lining
	 * @param poolUuid, imageUuid, hostIp(迁移ip)
	 */
	public void migrateImage(String poolUuid, String imageUuid, String hostIp);
}
