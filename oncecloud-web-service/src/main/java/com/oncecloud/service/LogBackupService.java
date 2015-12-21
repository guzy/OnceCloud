package com.oncecloud.service;

import java.util.Date;

public interface LogBackupService {
	/**
	 *转移过时的日志
	 *@author lining
	 *@param saveTime
	 *@return 
	 */
	public abstract boolean trsOvertimedLog(Date savetime);
}
