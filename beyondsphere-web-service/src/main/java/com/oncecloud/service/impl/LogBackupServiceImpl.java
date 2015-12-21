package com.oncecloud.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oncecloud.dao.LogBackupDAO;
import com.oncecloud.service.LogBackupService;
@Service
public class LogBackupServiceImpl implements LogBackupService {
	
	@Resource
	private LogBackupDAO logBackupDAO;
	
	@Override
	public boolean trsOvertimedLog(Date savetime) {
		return logBackupDAO.insertLogBackup(savetime);
	}

}
