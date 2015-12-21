package com.beyondsphere.manager;

import org.json.JSONObject;

/**
 * @author luogan
 * 2015年7月13日
 * 下午12:07:39
 */
public interface RegistryManager {
	
	public abstract JSONObject createRegistry(JSONObject jo);
	
	public abstract JSONObject deleteRegistry(JSONObject jo);
	
}