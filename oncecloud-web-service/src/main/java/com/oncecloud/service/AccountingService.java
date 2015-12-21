package com.oncecloud.service;

import org.json.JSONArray;

public interface AccountingService {
	/**
	 * 获取资源使用信息列表
	 * @param page
	 * @param limit
	 * @param type
	 * @return
	 */
	public JSONArray getOnePageListOfAccounting(int page, int limit,String search, int type);
}
