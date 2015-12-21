package com.beyondsphere.service;

import com.beyondsphere.entity.Power;

public interface PowerService {
	
	/**
	 * 编辑电源管理
	 * @author lining
	 * @param power
	 * @return
	 */
	public abstract boolean savePower(int userId, String powerUuid, String hostUuid,
			String motherboardIP, int powerPort, String powerUsername,
			String powerPassword, Integer powerValid);
	
	/**
	 * 删除电源管理
	 * @author lining
	 * @param hostUuid
	 * @return
	 */
	public abstract boolean deletePower(String hostUuid);
    
	/**
	 * 获取电源管理
	 * @author lining
	 * @param hostUuid
	 * @return
	 */
    public abstract Power getPower(String hostUuid);
    
    /**
     * 更新电源管理
     * @author lining
     * @param power
     */
    public abstract boolean updatePower(Power power);
}
