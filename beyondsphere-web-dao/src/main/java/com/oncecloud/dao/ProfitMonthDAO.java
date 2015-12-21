package com.oncecloud.dao;

import java.util.Date;
import java.util.List;

import com.oncecloud.entity.ProfitMonth;

/**
 * ProfitMonth DAO
 * @author zll
 * @Date 2015年5月27日
 */
public interface ProfitMonthDAO {

	/**
	 * 保存对象
	 * @param ProfitMonth
	 * @return 
	 * @throws OnceDAOException
	 */
	public abstract boolean add(ProfitMonth profitMonth);
	    
    
    /**
	 * 移除对象
	 * @param start<Date>
	 * @param end<Date>
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean remove(Date start,Date end);
    
	
	/**
	 * 根据时间段获取ProfitMonth
   	 * @return List<CostDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<ProfitMonth> getAllList(Date start,Date end);
	
	/**
	 * 根据、userid、时间段获取ProfitMonth
   	 * @return List<CostDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<ProfitMonth> getAllListbyUserid(Date start,Date end,int userid);

}
