/**
 * @author zll
 * @time 上午11:05:48
 * @date 2015年5月28日
 */
package com.oncecloud.manager;



public interface CostStatisticsManager {

	public abstract boolean saveCostMonthDetail();

	public abstract boolean saveCostMonth();
	
	public abstract boolean saveProfitMonthDetail();

}