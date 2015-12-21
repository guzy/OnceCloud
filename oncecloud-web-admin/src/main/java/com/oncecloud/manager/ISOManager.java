package com.oncecloud.manager;

import org.json.JSONArray;
import org.json.JSONObject;

import com.oncecloud.entity.ISO;
import com.oncecloud.model.PagedList;

/**
 * @author 玉和
 * 2014-12-13
 */
public interface ISOManager {
    
	/**
	 * 获取ISO列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public abstract PagedList<ISO> getISOList(int page, int limit, String search);
	
	public abstract JSONArray getISOListAll(int userId);
	
	public abstract ISO addISO(String isoName, String isoFileSrc,Integer userId, String isoRemarks);
	
	public abstract JSONObject deleteISO(int userId,String isoUuid,String isoName);
	
	public abstract ISO getISO(String isoUuid);
}
