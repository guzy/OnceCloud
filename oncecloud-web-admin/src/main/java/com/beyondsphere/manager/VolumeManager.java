/**
 * @author zll
 * @time 下午14:36:48
 * @date 2015年5月19日
 */
package com.beyondsphere.manager;


import java.util.List;

import com.oncecloud.entity.Volume;



public interface VolumeManager {

	public abstract List<Volume> getAllList();
	
	public abstract List<Volume> getListbyUserid(int userid);

	public abstract Volume getVolume(String uid);
	
}