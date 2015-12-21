package com.oncecloud.service;

public interface AlarmLogService {

	/**
	 * 监控各规则对应的虚拟机，超出设定阈值时发送警告邮件到虚拟机对应的用户邮箱
	 */
	public abstract void monitorAlarmRules();

}