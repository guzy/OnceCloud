package com.beyondsphere.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.AlarmLogDAO;
import com.beyondsphere.dao.AlarmRuleDAO;
import com.beyondsphere.dao.PerformanceDAO;
import com.beyondsphere.dao.UserDAO;
import com.beyondsphere.dao.VMDAO;
import com.beyondsphere.entity.AlarmLog;
import com.beyondsphere.entity.AlarmRule;
import com.beyondsphere.entity.OCVM;
import com.beyondsphere.entity.User;
import com.beyondsphere.model.performance.Memory;
import com.beyondsphere.model.performance.Vif;
import com.beyondsphere.service.AlarmLogService;
import com.beyondsphere.util.SendMail;

@Component("AlarmLogService")
public class AlarmLogServiceImpl implements AlarmLogService {

	@Resource
	private VMDAO vmDAO;
	@Resource
	private AlarmRuleDAO alarmRuleDAO;
	@Resource
	private PerformanceDAO performanceDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private AlarmLogDAO alarmLogDAO;

	/**
	 * 获得虚拟机对应的用户邮箱
	 * */
	private String getMail(String vmUuid) {
		String userMail = "";
		try {
			OCVM vm = vmDAO.getVMByUuid(vmUuid);
			if (vm != null) {
				Integer userId = vm.getVmUid();
				User user = userDAO.getUser(userId);
				if (user != null) {
					userMail = user.getUserMail();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userMail;
	}

	/**
	 * 获取最新一条记录
	 * */
	private JSONObject getItem(Integer ruleName, String vmUuid) {
		JSONObject jo = new JSONObject();
		if (ruleName == 0) {
			try {
				double cpuUsage = performanceDAO.getCurrentCpuUsage(vmUuid);
				if (cpuUsage != -1) {
					jo.put("item", "CPU利用率");
					jo.put("currentRecord", cpuUsage * 100);
				} else {
					jo.put("item", "");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (ruleName == 1) {
			try {
				Memory memoryItem = performanceDAO.getCurrentMemory(vmUuid);
				if (memoryItem != null) {
					jo.put("item", "内存使用率");
					jo.put("currentRecord",
							100
									* (memoryItem.getTotalSize() - memoryItem
											.getFreeSize())
									/ memoryItem.getTotalSize());
				} else {
					jo.put("item", "");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (ruleName == 2) {
			try {
				Memory memoryItem = performanceDAO.getCurrentMemory(vmUuid);
				if (memoryItem != null) {
					jo.put("item", "磁盘使用率");
					jo.put("currentRecord",
							100*(memoryItem.getTotalSize() - memoryItem.getFreeSize())
							/ memoryItem.getTotalSize());
				} else {
					jo.put("item", "");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Vif vif = performanceDAO.getCurrentVif(vmUuid);
				if (vif != null) {
					if (ruleName == 3) {
						jo.put("item", "网卡进流量");
						jo.put("currentRecord", vif.getRxd()/1000);
					}
					if (ruleName == 4) {
						jo.put("item", "网卡出流量");
						jo.put("currentRecord", vif.getTxd()/1000);
					}

				} else {
					jo.put("item", "");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jo;
	}

	private boolean addLog(String mailText, String ruleUuid) {
		AlarmLog alarmLog = alarmLogDAO.getAlarmLogByDesc(ruleUuid, mailText);
		if (alarmLog != null) {
			String logUuid = alarmLog.getLogUuid();
			long logTime = alarmLog.getLogTime().getTime();
			long timeNow = (new Date()).getTime();
			if (timeNow - logTime < 30 * 60000) {
				int logFlag = alarmLogDAO.getAlarmLog(logUuid).getLogFlag();
				alarmLogDAO.updateLogFlag(logUuid, logFlag + 1);
				return false;
			}
		}
		return alarmLogDAO.addAlarmLog(mailText, ruleUuid);
	}

	/**
	 * 判断监控值是否在规则既定的阈值内，并发送告警邮件
	 * */
	private void monitorVM(JSONObject jo, String vmUuid, String ruleUuid,
			int ruleName, int ruleTerm, double ruleThreshold, String userMail) {
		String item = jo.getString("item");
		if (item != "") {
			double currentRecord = jo.getDouble("currentRecord");
			String unit = null;
			if (ruleName == 0 || ruleName == 1 || ruleName == 2) {
				unit = "%";
			} else {
				unit = "Mbps";
			}
			if (ruleTerm == 0) {
				if (currentRecord < ruleThreshold) {
					SendMail se = new SendMail();
					String mailText = "主机" + vmUuid + "的" + item + "小于阈值"
							+ ruleThreshold + unit;
					if (addLog(mailText, ruleUuid)) {
						se.sendMail(mailText, userMail);
					}
				}
			} else {
				if (currentRecord > ruleThreshold) {
					String mailText = "主机" + vmUuid + "的" + item + "超出阈值"
							+ ruleThreshold + unit;
					SendMail se = new SendMail();
					if (addLog(mailText, ruleUuid)) {
						se.sendMail(mailText, userMail);
					}
				}
			}
		}
	}

	/**
	 * 监控各规则对应的虚拟机，超出设定阈值时发送警告邮件到虚拟机对应的用户邮箱
	 * */
	public void monitorAlarmRules() {
		List<OCVM> vmWithAlarmlist = new ArrayList<OCVM>();
		try {
			vmWithAlarmlist = vmDAO.getVMsWithAlarm();
			if (vmWithAlarmlist != null) {
				for (int i = 0; i < vmWithAlarmlist.size(); i++) {
					String alarmUuid = vmWithAlarmlist.get(i).getAlarmUuid();
					String vmUuid = vmWithAlarmlist.get(i).getVmUuid();
					String userMail = getMail(vmUuid);
					List<AlarmRule> rules = new ArrayList<AlarmRule>();
					rules = alarmRuleDAO.getRules(alarmUuid);
					if (rules != null) {
						for (int j = 0; j < rules.size(); j++) {
							String ruleUuid = rules.get(j).getRuleId();
							Integer ruleName = rules.get(j).getRuleName()
									.ordinal();
							Integer ruleTerm = rules.get(j).getRuleTerm()
									.ordinal();
							Integer ruleThreshold = rules.get(j)
									.getRuleThreshold();
							JSONObject jo = getItem(ruleName, vmUuid);
							monitorVM(jo, vmUuid, ruleUuid, ruleName, ruleTerm,
									ruleThreshold, userMail);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
