package com.beyondsphere.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.beyondsphere.dao.LogBackupDAO;
import com.beyondsphere.service.LogBackupService;
@Service
public class LogBackupServiceImpl implements LogBackupService {
	
	@Resource
	private LogBackupDAO logBackupDAO;
	
	@Override
	public boolean trsOvertimedLog(Date savetime) {
		return logBackupDAO.insertLogBackup(savetime);
	}

}
