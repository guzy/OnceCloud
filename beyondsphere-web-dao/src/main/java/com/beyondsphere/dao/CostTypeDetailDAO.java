package com.beyondsphere.dao;

import java.util.List;

import com.beyondsphere.entity.CostTypeDetail;


/**
 * CostTypeDetail DAO
 * @author zll
 *
 * @Date 2015年5月11日
 */
public interface CostTypeDetailDAO {

	/**
	 * 保存对象
	 * @param CostType
	 * @return 
	 * @throws OnceDAOException
	 */
	public abstract boolean add(CostTypeDetail costTypeDetail);
	    
	/**
	 * 修改对象
	 * @param CostType
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean edit(CostTypeDetail costTypeDetail);
    
    /**
	 * 移除对象
	 * @param CostTypeDetail Id
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean remove(String id);
    
    /**
	 * 根据主键 获取CostTypeDetail
	 * @param id
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract CostTypeDetail getOneByID(String id);
    
    /**
   	 * 分页获取列表
   	 * @param page
   	 * @param limit
   	 * @param search
   	 * @return List<CostTypeDetail>
   	 * @throws OnceDAOException
   	 */
    public abstract List<CostTypeDetail> getPagedList(int page, int limit, String search);
    
    /**
	 * 获取列表的总记录数
	 * @param search
	 * @return
	 */
	public int countAllList(String search);
	
	/**
	 * 获取所有CostTypeDetail
   	 * @return List<CostTypeDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<CostTypeDetail> getAllList();
	
	/**
	 * 根据type_uuid获取CostTypeDetail
   	 * @return List<CostTypeDetail>
   	 * @throws OnceDAOException
	 */
	public abstract List<CostTypeDetail> getListByTypeId(String typeId);
}
