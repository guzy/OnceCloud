/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.manager.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oncecloud.manager.PowerManager;
import com.oncecloud.core.PowerCore;
import com.oncecloud.entity.Power;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.service.PowerService;

@Service("powerManager")
public class PowerManagerImpl implements PowerManager {

	@Resource
	private PowerService powerService;
	@Resource
	private PowerCore powerCore;
	@Resource
	private MessageUtil message;
	
	public boolean savePower(int userId, String powerUuid, String hostUuid,
			String motherboardIP, int powerPort, String powerUsername,
			String powerPassword, Integer powerValid) {
		return powerService.savePower(userId, powerUuid, hostUuid, motherboardIP, 
				powerPort, powerUsername, powerPassword, powerValid);
		
	}

	// 返回值 1 是已经开启， 0 是关闭 -1 验证不通过
	public int getStatusOfPower(String powerIP, int powerPort,
			String userName, String passWord) {
		return powerCore.getStatusOfPower(powerIP, powerPort, userName, passWord);
	}
	
	public boolean startPower(int userId, String hostUuid) {
		Power power = powerService.getPower(hostUuid);
		boolean result = powerCore.startup(userId, power.getMotherboardIP(), power.getPowerPort(), power.getPowerUsername(), power.getPowerPassword());
		if (result) {
			power.setPowerValid(1);
			powerService.updatePower(power);
			message.pushSuccess(userId, "服务器启动成功");
		}else {
			message.pushError(userId, "服务器启动失败");
		}
		return result;
	}

	public boolean stopPower(int userId, String hostUuid) {
		Power power = powerService.getPower(hostUuid);
		boolean result = powerCore.shutdown(userId, power.getMotherboardIP(), power.getPowerPort(), power.getPowerUsername(), power.getPowerPassword());
		if (result) {
			power.setPowerValid(0);
			powerService.updatePower(power);
			message.pushSuccess(userId, "服务器关闭成功");
		}else {
			message.pushError(userId, "服务器关闭失败");
		}
		return result;
	}

	public boolean deletePower(String hostUuid) {
		return powerService.deletePower(hostUuid);
	}

	public Power getPower(String hostUuid) {
		return powerService.getPower(hostUuid);
	}

	public boolean startPower(int userId, String powerIP, int powerPort,
			String userName, String passWord) {
		return powerCore.startup(userId, powerIP, powerPort, userName, passWord);
	}

	public boolean stopPower(int userId, String powerIP, int powerPort,
			String userName, String passWord) {
		return powerCore.shutdown(userId, powerIP, powerPort, userName, passWord);
	}

}
