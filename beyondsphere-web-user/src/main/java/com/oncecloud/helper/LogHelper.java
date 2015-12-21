package com.oncecloud.helper;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;





import org.springframework.stereotype.Component;

import com.oncecloud.service.LogBackupService;
import com.oncecloud.service.LogService;
import com.oncecloud.util.Utilities;

@Component
public class LogHelper implements Runnable {
	
	private int days;
	@Resource
	private LogService logService;
	@Resource
	private LogBackupService logBackupService;
	
	private  boolean trsFlag = false;
	
	public void setDays(int days){
		this.days = days;
	}
	
	public void setTrsFlg(boolean trsFlag) {
		this.trsFlag = trsFlag;
	}
	
	@Override
	public void run() {
		Date logTime = Utilities.daySub(Calendar.DATE, days);
		while (trsFlag) {
			if (logBackupService.trsOvertimedLog(logTime)) {
				logService.deleteLogByTime(logTime);
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
