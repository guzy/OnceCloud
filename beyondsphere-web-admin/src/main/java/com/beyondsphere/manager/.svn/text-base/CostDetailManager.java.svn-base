/**
 * @author zll
 * @time 下午14:36:48
 * @date 2015年5月11日
 */
package com.beyondsphere.manager;


import java.util.List;
import java.util.Map;

import com.beyondsphere.entity.CostDetail;

public interface CostDetailManager {

	public abstract boolean doAddCostDetail(Integer userId,CostDetail costDetail);

	public abstract boolean doDeleteCostDetail(Integer userId,String costId);

	public abstract boolean doUpdateCostDetail(Integer userId,CostDetail costDetail);

	@SuppressWarnings("rawtypes")
	public abstract Map getCostDetailList(int page, int limit, String search,String costTypeId);

	public abstract List<CostDetail> getAllList();

	public abstract CostDetail getCostDetail(String costDetailId);
	
	public abstract List<CostDetail> getListByTypeDetailId(String typeDetailId);
	
	public abstract List<CostDetail> getListByTypeId(String typeId);
	
}