package com.oncecloud.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.model.AlarmRuleConstant;
import com.oncecloud.service.AlarmService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/oncecloud/config/application-context.xml")
@WebAppConfiguration
public class AlarmServiceImplTest {

	private AlarmService alarmService;
	
	private AlarmService getAlarmService() {
		return alarmService;
	}

	@Autowired
	private void setAlarmService(AlarmService alarmService) {
		this.alarmService = alarmService;
	}

	@Test
	@Ignore
	public void test() {
		/*Object[] alarmRule = new Object[6];
		alarmRule[0]="11bc0d15-8e7e-42a1-924b-79022e792433";
		alarmRule[1]=AlarmRuleConstant.RuleName.CPURate;
		alarmRule[2]="00bc0d05-8e7e-42a1-924b-79022e792433";
		alarmRule[3]=AlarmRuleConstant.RulePriority.SmallThan;
		alarmRule[4]=70;
		alarmRule[5]=1;
		List<Object> alarmRuleList = new ArrayList<Object>();
		alarmRuleList.add(alarmRule);
		this.getAlarmService().saveAlarm("00bc0d05-8e7e-42a1-924b-79022e792433","testserviceaddalarm",AlarmConstant.AlarmSameRule.Any,AlarmConstant.AlarmPeriod.OneMinute,42,alarmRuleList);
	*/}

}
