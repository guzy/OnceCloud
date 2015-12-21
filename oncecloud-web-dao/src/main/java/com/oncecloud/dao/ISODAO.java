package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.entity.ISO;


/**
 * ISO DAO
 * @author 玉和
 *
 * @Date 2014年12月13日
 */
public interface ISODAO {

	/**
	 * 保存对象
	 * @param action
	 * @return 
	 * @throws OnceDAOException
	 */
	public abstract boolean addISO(ISO iso);
	    
	/**
	 * 修改对象
	 * @param iso
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean editISO(ISO iso);
    
    /**
	 * 移除对象
	 * @param uuid
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean removeISO(String uuid);
    
    /**
	 * 根据主键 获取ISO
	 * @param id
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract ISO getISOByID(String id);
    
    /**
   	 * 分页获取列表
   	 * @param page
   	 * @param limit
   	 * @param search
   	 * @return List<OCAction>
   	 * @throws OnceDAOException
   	 */
    public abstract List<ISO> getPagedISOList(int page, int limit, String search);
    
    /**
	 * 获取列表的总记录数
	 * 
	 * @param search
	 * @return
	 */
	public int countAllISOList(String search);
}
