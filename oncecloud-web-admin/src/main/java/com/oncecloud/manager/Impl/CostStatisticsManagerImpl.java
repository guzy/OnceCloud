/**
 * @author zll
 * @time 上午11:05:48
 * @date 2015年5月28日
 */
package com.oncecloud.manager.Impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oncecloud.manager.CostStatisticsManager;
import com.oncecloud.service.CostStatisticsService;

@Service("CostStatisticsManager")
public class CostStatisticsManagerImpl implements CostStatisticsManager {

	@Resource
	private CostStatisticsService costStatisticsService;

	@Override
	public boolean saveCostMonthDetail() {
		boolean result = false;
		result = costStatisticsService.saveCostMonthDetail();
		return result;
	}

	@Override
	public boolean saveCostMonth() {
		boolean result = false;
		result = costStatisticsService.saveCostMonth();
		return result;
	}
	
	@Override
	public boolean saveProfitMonthDetail() {
		boolean result = false;
		result = costStatisticsService.saveProfitMonthDetail();
		return result;
	}

}
