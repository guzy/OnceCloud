package com.beyondsphere.manager;

import org.json.JSONArray;
import org.json.JSONObject;

import com.beyondsphere.entity.HostInformation;

public interface HostInformationManager {

	/**
	 * 分页获取服务器列表
	 * @author lining
	 * @param page, limit, search
	 * @return
	 */
	public abstract JSONArray getOnePageList(int page, int limit);
	
	/**
	 * 新建对象
	 * @param info
	 * @return
	 */
	public abstract boolean saveInformation(HostInformation info);
	
	/**
	 * 删除对象
	 * @param info
	 * @return
	 */
	public abstract boolean doDeleteInfo(int infoid);
	
	/**
	 * 获取没有记录的user列表
	 * @param info
	 * @return
	 */
	public abstract JSONArray getUnBindUsers();
	
	/**
	 * 获取记录的user列表
	 * @param info
	 * @return
	 */
	public abstract JSONArray getBindUsers();
	
	/**
	 * 验证用户信息中是否包含对应的vlan
	 * @param info
	 * @return
	 */
	public abstract JSONObject checkSwitch(int netid);

	public abstract JSONArray getunBindRouterUsers();
}
