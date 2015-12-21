package com.oncecloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.oncecloud.dao.PowerDAO;
import com.oncecloud.entity.Power;
import com.oncecloud.log.LogRecord;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.service.PowerService;

@Component("PowerService")
public class PowerServiceImpl implements PowerService {
	
	@Resource
	private PowerDAO powerDAO;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;
	
	public boolean savePower(int userId, String powerUuid, String hostUuid,
			String motherboardIP, int powerPort, String powerUsername,
			String powerPassword, Integer powerValid) {
		boolean result = false;
		Power power =new Power(powerUuid,hostUuid,motherboardIP,powerPort,powerUsername,powerPassword,powerValid);
		if(powerDAO.editPower(power)){
			message.pushSuccess(userId, "电源管理启动成功");
			result = true;
		}else {
			message.pushError(userId, "电源管理启动失败");
		}
		return result;
	}

	public boolean deletePower(String hostUuid) {
		Power power = powerDAO.getPowerByHostID(hostUuid);
		if(powerDAO.removePower(power)){
			return true;
		}
		return false;
	}

	public Power getPower(String hostUuid) {
		Power power = powerDAO.getPowerByHostID(hostUuid);
		return power;
	}

	public boolean updatePower(Power power) {
		return powerDAO.editPower(power);
	}
	
	
}
