package com.oncecloud.manager;

import org.json.JSONObject;

/**
 * @author luogan
 * 2015年6月19日
 * 下午3:22:12
 */
public interface ContainerManager {

	/**
	 * @param jo
	 * @return
	 */
	public abstract JSONObject createContainer(JSONObject jo);
	
	/**
	 * @param poolUuid
	 * @param imageName
	 * @param name
	 * @return
	 */
	public abstract JSONObject createContainer(JSONObject jo, String clusterUuid);
	
	/**
	 * @param jo
	 * @return
	 */
	public abstract JSONObject deleteContainer(JSONObject jo);
	
	/**
	 * @param jo
	 * @return
	 */
	public abstract JSONObject stopContainer(JSONObject jo);
	
	/**
	 * @param jo
	 * @return
	 */
	public abstract JSONObject restartContainer(JSONObject jo);
	
}