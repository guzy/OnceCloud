/**
 * @author zll
 * @time 上午15:22:48
 * @date 2015年5月11日
 */
package com.beyondsphere.manager.Impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.beyondsphere.manager.CostDetailManager;
import com.oncecloud.entity.CostDetail;
import com.oncecloud.service.CostDetailService;

@Service("CostDetailManager")
public class CostDetailManagerImpl implements CostDetailManager {

	@Resource
	private CostDetailService costDetailService;

	@Override
	public boolean doAddCostDetail(Integer userId,CostDetail costDetail) {
		boolean result = false;
		result = costDetailService.doAdd(userId,costDetail);

		return result;
	}

	@Override
	public boolean doDeleteCostDetail(Integer userId,String costDetailId) {
		boolean result = false;
		result = costDetailService.doRemove(userId,costDetailId);
		return result;
	}

	@Override
	public boolean doUpdateCostDetail(Integer userId,CostDetail costDetail) {
		return costDetailService.doUpdate(userId, costDetail);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getCostDetailList(int page, int limit, String search,String costTypeId) {
		return costDetailService.getPagedList(page, limit, search,costTypeId);
	}

	@Override
	public List<CostDetail> getAllList() {
		return costDetailService.getAllList();
	}

	@Override
	public CostDetail getCostDetail(String costDetailId) {
		CostDetail costDetail =costDetailService.getOneById(costDetailId);
		return costDetail;
	}

	@Override
	public List<CostDetail> getListByTypeDetailId(String typeDetailId) {
		return costDetailService.getListByTypeDetailId(typeDetailId);
	}

	@Override
	public List<CostDetail> getListByTypeId(String typeId) {
		return costDetailService.getListByTypeId(typeId);
	}

}
