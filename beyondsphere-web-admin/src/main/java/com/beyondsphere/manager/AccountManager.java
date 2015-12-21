package com.beyondsphere.manager;

import java.util.Map;

public interface AccountManager {
	/**
	 * @author lining
	 * @return
	 * 获取平台的总体信息，包括数据中心数量，资源池数量，服务器数量，映像数量，虚拟机数量，硬盘数量
	 * 
	 */
	public Map<String, Object> getInfrastructureInfos();
}
