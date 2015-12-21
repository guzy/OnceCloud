package com.oncecloud.dao.impl;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.dao.HostDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/oncecloud/config/application-context.xml")
@WebAppConfiguration
public class HostDAOImplTest {

	private HostDAO hostDAO;
	
	private HostDAO getHostDAO() {
		return hostDAO;
	}

	@Autowired
	private void setHostDAO(HostDAO hostDAO) {
		this.hostDAO = hostDAO;
	}

	@Test
	@Ignore
	public void test() {
		try {
			Assert.assertNotNull(this.getHostDAO().getHost("58c70510-db16-12d4-f1d9-3676d852c5ec"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
