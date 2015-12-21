package com.beyondsphere.manager;

import org.json.JSONArray;
import org.json.JSONObject;

public interface PerformanceManager {

	/**
	 * 获取cpu的信息
	 * @author lining
	 * @param uuid, type
	 * @return
	 */
	public abstract JSONObject getCpu(String uuid, String type);

	/**
	 * 获取内存的信息
	 * @author lining
	 * @param uuid, type
	 * @return
	 */
	public abstract JSONArray getMemory(String uuid, String type);

	/**
	 * 获取虚拟硬盘的信息
	 * @author lining
	 * @param uuid, type
	 * @return
	 */
	public abstract JSONObject getVbd(String uuid, String type);
	
	/**
	 * 获取虚拟网卡的信息
	 * @author lining
	 * @param uuid, type
	 * @return
	 */
	public abstract JSONObject getVif(String uuid, String type);

	/**
	 * 获取物理网卡的信息
	 * @author lining
	 * @param uuid, type
	 * @return
	 */
	public abstract JSONObject getPif(String uuid, String type);

}
