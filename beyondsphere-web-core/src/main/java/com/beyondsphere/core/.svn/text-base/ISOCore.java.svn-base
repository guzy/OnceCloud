package com.beyondsphere.core;

import java.util.List;

import org.json.JSONArray;

import com.beyondsphere.entity.OCPool;
import com.beyondsphere.model.ISOModel;


public interface ISOCore {

	/**
	 * 从物理服务器端获取iso model列表
	 * @author lining
	 * @param pool
	 * @return
	 */
	public abstract List<ISOModel> getISOModelOfPool(List<ISOModel> list, OCPool pool);
	
	/**
	 * 获取iso
	 * @author lining
	 * @param poolUuid
	 * @return
	 */
	
	public abstract JSONArray getISOArray(String poolUuid);
}
