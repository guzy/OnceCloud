package com.beyondsphere.dao;

import java.util.Date;
import java.util.List;

import com.beyondsphere.entity.ProfitMonthDetail;

/**
 * CostMonth DAO
 * @author zll
 * @Date 2015年5月27日
 */
public interface ProfitMonthDetailDAO {

	/**
	 * 保存对象
	 * @param ProfitMonthDetail
	 * @return 
	 * @throws OnceDAOException
	 */
	public abstract boolean add(ProfitMonthDetail profitMonthDetail);
	    
    
    /**
	 * 移除对象
	 * @param ProfitMonthDetail Id
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean remove(int id);
    
	
	/**
	 * 根据时间段获取ProfitMonthDetail
   	 * @return List<ProfitMonthDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<ProfitMonthDetail> getAllList(Date start,Date end);
	
	/**
	 * 根据userId段获取ProfitMonthDetail
   	 * @return List<ProfitMonthDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<ProfitMonthDetail> getAllListByUserId(int userId);
	
	/**
	 * 根据costDetailId段获取ProfitMonthDetail
   	 * @return List<ProfitMonthDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<ProfitMonthDetail> getAllListByDetailId(String costDetailId);
	
	/**
	 * 根据costDetailId段获取ProfitMonthDetail
   	 * @return List<ProfitMonthDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<ProfitMonthDetail> getAllListByUserid(Date start,Date end,int userid);
	
}
