package com.oncecloud.service;

import org.json.JSONObject;

import com.oncecloud.entity.OCVMip;

public interface VMipService {
	
	/**
	 * 更新ip地址
	 * @author lining
	 * @param vMip 
	 * @return
	 */
	public abstract JSONObject changeVMip(int userId, OCVMip vMip ,String uuid);
	
}
