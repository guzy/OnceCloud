package com.beyondsphere.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.service.NOVNCService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class NOVNCServiceTest {
	@Autowired
	private NOVNCService novncService;
	
	@Test
	public final void testAllocation() {
		try {
			//step1: get a vmuuid from db
			//step2: get this vm's hostuuid
			//step3: create a connection from the host
			//step4: test the method novncService.allocation(vmUuid, hostUuid, c);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testDeleteToken() {
		try {
			//step5: delete the token by the method novncService.deleteToken(vmUuid)
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testGetPublicVNC() {
		try {
			//get a vncservice object
			//test the method novncService.getPublicVNC();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
