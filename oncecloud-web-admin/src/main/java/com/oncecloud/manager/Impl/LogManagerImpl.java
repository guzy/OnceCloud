/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.manager.Impl;


import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import com.oncecloud.manager.LogManager;
import com.oncecloud.service.LogService;

@Service("LogManager")
public class LogManagerImpl implements LogManager {
	
	@Resource
	private LogService logService;

	public JSONArray getLogList(int userId, int status, int start, int num,String search) {
		return logService.getLogList(userId, status, start, num,search);
	}

}
