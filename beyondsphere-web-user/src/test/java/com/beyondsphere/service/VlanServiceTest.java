package com.beyondsphere.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class VlanServiceTest {
	@Autowired
	private VlanService vlanService;
	
	@Test
	public final void testVmBindVlan() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}