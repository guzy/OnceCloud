package com.beyondsphere.dao;

import java.util.List;

import com.beyondsphere.entity.CostDetail;


/**
 * CostDetail DAO
 * @author zll
 *
 * @Date 2015年5月11日
 */
public interface CostDetailDAO {

	/**
	 * 保存对象
	 * @param CostDetail
	 * @return 
	 * @throws OnceDAOException
	 */
	public abstract boolean add(CostDetail costDetail);
	    
	/**
	 * 修改对象
	 * @param CostDetail
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean edit(CostDetail costDetail);
    
    /**
	 * 移除对象
	 * @param costDetail Id
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean remove(String id);
    
    /**
	 * 根据主键 获取costDetail
	 * @param id
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract CostDetail getOneByID(String id);
    
    /**
   	 * 分页获取列表
   	 * @param page
   	 * @param limit
   	 * @param search
   	 * @return List<costDetail>
   	 * @throws OnceDAOException
   	 */
    public abstract List<CostDetail> getPagedList(int page, int limit, String search,String typeid);
    
    /**
	 * 获取列表的总记录数
	 * @param search
	 * @return
	 */
	public int countAllList(String search);
	
	/**
	 * 获取所有CostDetail
   	 * @return List<CostDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<CostDetail> getAllList();
	
	/**
	 * 根据typeDetailId获取CostDetailList
   	 * @return List<CostDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<CostDetail> getListByTypeDetailId(String typeDetailId);
	
	/**
	 * 根据typeId获取CostDetailList
   	 * @return List<CostDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<CostDetail> getListByTypeId(String typeid);
}
