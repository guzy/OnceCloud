package com.beyondsphere.dao.impl;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.dao.LogDAO;
import com.oncecloud.model.LogConstant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class LogDAOImplTest {

	private LogDAO logDAO;
	
	private LogDAO getLogDAO() {
		return logDAO;
	}

	@Autowired
	private void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}

	@Test
	@Ignore
	public void test() {
		try {
			Assert.assertNotNull(this.getLogDAO().insertLog(1, LogConstant.logObject.Instance.ordinal(),
					LogConstant.logAction.ShutDown.ordinal(),
					LogConstant.logStatus.Succeed.ordinal(), "", new Date(), 2));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testGetLogList() {
		try {
			Assert.assertNotNull(this.getLogDAO().getLogList(1, 1, 0, 10,""));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
