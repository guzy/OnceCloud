package com.oncecloud.timertask;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.oncecloud.manager.CostStatisticsManager;

@Component("costStatistics")
public class CostStatistics {
	private static final Logger logger = Logger.getLogger(CostStatistics.class);
	
	@Resource
	private CostStatisticsManager costStatisticsManager;
	
	@Scheduled(cron="0 0 1 1 * ? ") //一分钟执行一次    （0 0 1 1 * ?）每月1号执行
	public void  work() {
		//计算并保存每个分类每个月的总投入
		boolean monthde=saveCostMonthDetail();
		if(monthde){
			//计算并保存每个月所有分类的总投入
			if(saveCostMonth()){
				//计算并保存每个月每个用户的总费用和每个分类的费用
				saveProfitMonthDetail();
			}
		}
		
	}
	
	/**
	 * 计算并保存每个分类每个月的总投入
	 */
	 private boolean saveCostMonthDetail(){
		 boolean result=costStatisticsManager.saveCostMonthDetail();
		 if(result){
			 logger.info("saveCostMonthDetail success");
		 }else{
			 logger.info("saveCostMonthDetail failed");
		 }
		 return result;
	 }
	 /**
	 * 计算并保存每个月所有分类的总投入
	 */
	 private boolean saveCostMonth(){
		 boolean result=costStatisticsManager.saveCostMonth();
		 if(result){
			 logger.info("saveCostMonth success");
		 }else{
			 logger.info("saveCostMonth failed");
		 }
		 return result;
	 }
	 
	 /**
	 * 计算并保存每个月每个用户的总费用
	 */
	 private boolean saveProfitMonthDetail(){
		 boolean result=costStatisticsManager.saveProfitMonthDetail();
		 if(result){
			 logger.info("saveProfitMonthDetail success");
		 }else{
			 logger.info("saveProfitMonthDetail failed");
		 }
		 return result;
	 }
}
