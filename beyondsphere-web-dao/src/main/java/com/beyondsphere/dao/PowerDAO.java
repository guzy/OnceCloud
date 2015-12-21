/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.dao;

import com.beyondsphere.entity.Power;

public interface PowerDAO {
	
	/**
	 * 添加电源管理信息
	 * @param power
	 * @return boolean
	 */
    public abstract boolean addPower(Power power);
    
    /**
	 * 修改电源管理信息
	 * @param power
	 * @return boolean
	 */
    public abstract boolean editPower(Power power);
    
    /**
	 * 删除电源管理信息
	 * @param power
	 * @return boolean
	 */
    public abstract boolean removePower(Power power);
    
    /**
	 * 根据host的id获取对应主机的电源管理信息
	 * @param power
	 * @return boolean
	 */
    public abstract Power getPowerByHostID(String hostUuid);
}
