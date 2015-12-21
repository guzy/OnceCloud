package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.entity.CostType;


/**
 * CostType DAO
 * @author zll
 *
 * @Date 2015年5日
 */
public interface CostTypeDAO {

	/**
	 * 保存对象
	 * @param CostType
	 * @return 
	 * @throws OnceDAOException
	 */
	public abstract boolean add(CostType costType);
	    
	/**
	 * 修改对象
	 * @param CostType
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean edit(CostType costType);
    
    /**
	 * 移除对象
	 * @param action
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean remove(String id);
    
    /**
	 * 根据主键 获取CostType
	 * @param id
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract CostType getOneByID(String id);
    
    /**
   	 * 分页获取列表
   	 * @param page
   	 * @param limit
   	 * @param search
   	 * @return List<CostType>
   	 * @throws OnceDAOException
   	 */
    public abstract List<CostType> getPagedList(int page, int limit, String search);
    
    /**
	 * 获取列表的总记录数
	 * @param search
	 * @return
	 */
	public int countAllList(String search);
	
	/**
	 * 获取所有costType
   	 * @return List<CostType>
   	 * @throws OnceDAOException
	 */
	public abstract List<CostType> getAllList();
}
