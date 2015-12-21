package com.oncecloud.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.oncecloud.entity.OCGroup;

public interface GroupService {
	
	/**
	 * 获取分组列表
	 * @author lining
	 * @param page, limit
	 * @return
	 */
	public abstract JSONArray getGroupJson(int userId, int page, int limit);
	
	/**
	 * 获取分组列表
	 * @author lining
	 * @param page, limit
	 * @return
	 */
	public abstract JSONArray getGroupJson(int userId);
	
	/**
	 * 获取分组列表
	 * @author lining
	 * @param page, limit
	 * @return
	 */
	public abstract List<OCGroup> getGroupList(int userId, int page, int limit);
	
	/**
	 * 获取可分组的虚拟机列表
	 * @author lining
	 * @param userId, page, limit
	 * @return
	 */
	public abstract JSONArray getVMGroupList(int page, int limit, Integer userId);
	
	/**
	 * 获取同一个分组的虚拟机列表
	 * @author lining
	 * @param groupUuid, userId
	 * @return
	 */
	public abstract JSONArray getVMListOfGroup(int page, int limit, String groupUuid, int userId);
	
	/**
	 * 获取分组列表
	 * @author lining
	 * @param 
	 * @return
	 */
	public abstract List<OCGroup> getGroupList();
	
	/**
	 * 获取分组列表
	 * @author lining
	 * @param 
	 * @return
	 */
	public abstract JSONArray getGroupJson();
	
	/**
	 * 获取分组信息
	 * @author lining
	 * @param groupUuid
	 * @return 
	 */
	public abstract JSONObject getGroupInfo(int userId, String groupUuid);
	
	/**
	 * 获取分组信息
	 * @author lining
	 * @param groupUuid
	 * @return 
	 */
	public abstract OCGroup getGroup(int userId, String groupUuid);
	
	/**
	 * 添加分组
	 * @author lining
	 * @param 
	 * @return
	 */
	public abstract boolean saveGroup(String groupUuid, int userId, String groupName, String groupColor, String groupLoc, String groupDes);
	
	/**
	 * 修改分组
	 * @author lining
	 * @param 
	 * @return
	 */
	public abstract boolean modifyGroup(String groupUuid, String groupName, String groupColor, String groupLoc, String groupDes);
	
	/**
	 * 删除分组
	 * @author lining
	 * @param groupUuid
	 * @return
	 */
	public abstract boolean deleteGroup(String groupUuid);
	
	/**
	 * 綁定分組
	 * @author lining
	 * @param userId, rsuuidStr, groupUuid
	 * @return
	 */
	public abstract boolean addVMtoGroup(int userId, String rsuuidStr, String groupUuid);
	
	/**
	 * 解绑分组
	 * @author lining
	 * @param vmUuid 
	 * @return
	 */
	public abstract boolean unbindVMFromGroup(String vmUuid);
	
	/**
	 * 删除VM上绑定的分组
	 * @author lining
	 * @param vmUuid 
	 * @return
	 */
	public abstract boolean removeGroupFromVM(String groupUuid);
}
