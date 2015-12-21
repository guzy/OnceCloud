/**
 * @author zll
 * @time 上午15:22:48
 * @date 2015年5月11日
 */
package com.oncecloud.manager.Impl;


import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import com.oncecloud.manager.CostTypeDetailManager;
import com.oncecloud.entity.CostTypeDetail;
import com.oncecloud.service.CostTypeDetailService;

@Service("CostTypeDetailManager")
public class CostTypeDetailManagerImpl implements CostTypeDetailManager {

	@Resource
	private CostTypeDetailService costTypeDetailService;

	@Override
	public boolean doAddCostTypeDetail(Integer userId,CostTypeDetail costTypeDetail) {
		boolean result = false;
		result = costTypeDetailService.doAdd(userId,costTypeDetail);

		return result;
	}

	@Override
	public boolean doDeleteCostTypeDetail(Integer userId,String costTypeDetailId) {
		boolean result = false;
		result = costTypeDetailService.doRemove(userId,costTypeDetailId);
		return result;
	}

	@Override
	public boolean doUpdateCostTypeDetail(Integer userId,CostTypeDetail costTypeDetail) {
		return costTypeDetailService.doUpdate(userId, costTypeDetail);
	}

	@Override
	public JSONArray getCostTypeDetailList(int page, int limit, String search) {
		return costTypeDetailService.getPagedList(page, limit, search);
	}

	@Override
	public List<CostTypeDetail> getAllList() {
		return costTypeDetailService.getAllList();
	}

	@Override
	public CostTypeDetail getCostTypeDetail(String costTypeDetailId) {
		CostTypeDetail costTypeDetail =costTypeDetailService.getOneById(costTypeDetailId);
		return costTypeDetail;
	}

	@Override
	public List<CostTypeDetail> getListByTypeId(String typeId) {
		return costTypeDetailService.getListByTypeId(typeId);
	}

}
