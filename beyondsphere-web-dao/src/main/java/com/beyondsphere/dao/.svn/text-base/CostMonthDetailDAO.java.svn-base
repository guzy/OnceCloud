package com.beyondsphere.dao;

import java.util.Date;
import java.util.List;

import com.beyondsphere.entity.CostMonthDetail;

/**
 * CostMonth DAO
 * @author zll
 * @Date 2015年5月27日
 */
public interface CostMonthDetailDAO {

	/**
	 * 保存对象
	 * @param CostMonthDeatil
	 * @return 
	 * @throws OnceDAOException
	 */
	public abstract boolean add(CostMonthDetail costMonthDetail);
	    
    
    /**
	 * 移除对象
	 * @param CostMonthDetail Id
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean remove(int id);
    
	
	/**
	 * 根据时间段获取CostMonthDetail
   	 * @return List<CostMonthDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<CostMonthDetail> getAllList(Date start,Date end);
	
	/**
	 * 根据cost_type_uuid段获取CostMonthDetail
   	 * @return List<CostMonthDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<CostMonthDetail> getListByTypeId(Date start,Date end,String costTypeId);
	
	/**
	 * 根据cost_type_uuid段获取CostMonthDetail
   	 * @return List<CostMonthDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<CostMonthDetail> getAllListByTypeId(String costTypeId);
	
	
	/**
	 * 根据cost_type_detail_uuid段获取CostMonthDetail
   	 * @return List<CostMonthDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<CostMonthDetail> getAllListByDetailId(String costDetailId);
	
}
