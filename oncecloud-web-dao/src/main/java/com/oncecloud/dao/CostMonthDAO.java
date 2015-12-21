package com.oncecloud.dao;

import java.util.Date;
import java.util.List;

import com.oncecloud.entity.CostMonth;

/**
 * CostMonth DAO
 * @author zll
 * @Date 2015年5月27日
 */
public interface CostMonthDAO {

	/**
	 * 保存对象
	 * @param CostMonth
	 * @return 
	 * @throws OnceDAOException
	 */
	public abstract boolean add(CostMonth costMonth);
	    
    
    /**
	 * 移除对象
	 * @param CostMonth Id
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean remove(int id);
    
	
	/**
	 * 根据时间段获取CostMonth
   	 * @return List<CostDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<CostMonth> getAllList(Date start,Date end);
	
}
