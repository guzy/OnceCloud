package com.beyondsphere.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.service.LogService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class LogServiceTest {
	@Autowired
	private LogService logService;
	
	@Test
	public final void testGetLogList() {
		try {
			//step1: get a user from db
			//step2: test the method logService.getLogList(userId, logStatus, start, num)
			//the param logStatus : success/fail LogConstant.logStatus
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testInsertLog() {
		try {
			//step1: get a user from db
			//step2: test the method logService.insertLog(userId, logObject, logAction, logStatus, logInfo, logTime, logElapse)
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
