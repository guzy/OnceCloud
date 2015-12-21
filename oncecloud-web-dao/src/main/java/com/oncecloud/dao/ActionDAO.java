package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.entity.OCAction;


/**
 * Action DAO
 * @author 玉和
 *
 * @Date 2014年12月4日
 */
public interface ActionDAO {

	/**
	 * 保存对象
	 * @param action
	 * @return 
	 * @throws OnceDAOException
	 */
	public abstract boolean addOCAction(OCAction action);
	    
	/**
	 * 修改对象
	 * @param action
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean editOCAction(OCAction action);
    
    /**
	 * 移除对象
	 * @param action
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract boolean removeOCAction(OCAction action);
    
    /**
	 * 根据主键 获取Action
	 * @param id
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract OCAction getActionByID(int id);
    
    /**
   	 * 分页获取列表
   	 * @param page
   	 * @param limit
   	 * @param search
   	 * @return List<OCAction>
   	 * @throws OnceDAOException
   	 */
    public abstract List<OCAction> getPagedActionList(int page, int limit, String search);
    
    /**
	 * 获取列表的总记录数
	 * 
	 * @param search
	 * @return
	 */
	public int countAllActionList(String search);
}
