/**
 * @author zll
 * @time 上午15:22:48
 * @date 2015年5月11日
 */
package com.beyondsphere.manager.Impl;


import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import com.beyondsphere.entity.CostType;
import com.beyondsphere.manager.CostTypeManager;
import com.beyondsphere.service.CostTypeService;

@Service("CostTypeManager")
public class CostTypeManagerImpl implements CostTypeManager {

	@Resource
	private CostTypeService costTypeService;

	@Override
	public boolean doAddCostType(Integer userId,CostType costType) {
		boolean result = false;
		result = costTypeService.doAdd(userId,costType);

		return result;
	}

	@Override
	public boolean doDeleteCostType(Integer userId,String costTypeId) {
		boolean result = false;
		result = costTypeService.doRemove(userId,costTypeId);
		return result;
	}

	@Override
	public boolean doUpdateCostType(Integer userId,CostType costType) {
		return costTypeService.doUpdate(userId, costType);
	}

	@Override
	public JSONArray getCostTypeList(int page, int limit, String search) {
		return costTypeService.getPagedList(page, limit, search);
	}

	@Override
	public List<CostType> getAllList() {
		return costTypeService.getAllList();
	}

	@Override
	public CostType getCostType(String costTypeId) {
		CostType costType =costTypeService.getOneById(costTypeId);
		return costType;
	}

}
