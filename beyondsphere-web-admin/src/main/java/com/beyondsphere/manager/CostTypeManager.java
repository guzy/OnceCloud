/**
 * @author zll
 * @time 下午14:36:48
 * @date 2015年5月11日
 */
package com.beyondsphere.manager;


import java.util.List;

import org.json.JSONArray;

import com.oncecloud.entity.CostType;

public interface CostTypeManager {

	public abstract boolean doAddCostType(Integer userId,CostType costType);

	public abstract boolean doDeleteCostType(Integer userId,String costTypeId);

	public abstract boolean doUpdateCostType(Integer userId,CostType costType);

	public abstract JSONArray getCostTypeList(int page, int limit, String search);

	public abstract List<CostType> getAllList();

	public abstract CostType getCostType(String costTypeId);
	
}