/**
 * @author zll
 * @time 下午14:36:48
 * @date 2015年5月11日
 */
package com.oncecloud.manager;


import java.util.List;

import org.json.JSONArray;

import com.oncecloud.entity.CostTypeDetail;

public interface CostTypeDetailManager {

	public abstract boolean doAddCostTypeDetail(Integer userId,CostTypeDetail costTypeDetail);

	public abstract boolean doDeleteCostTypeDetail(Integer userId,String costTypeId);

	public abstract boolean doUpdateCostTypeDetail(Integer userId,CostTypeDetail costTypeDetail);

	public abstract JSONArray getCostTypeDetailList(int page, int limit, String search);

	public abstract List<CostTypeDetail> getAllList();

	public abstract CostTypeDetail getCostTypeDetail(String costTypeDetailId);
	
	public abstract List<CostTypeDetail> getListByTypeId(String typeId);
	
}