package com.beyondsphere.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author hty
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class AllocateMACFromRandomTest {

	@Autowired
	private AllocateMACFromRandom mac;

	/**
	 * Test method for
	 * {@link com.beyondsphere.service.AllocateMACFromRandom#getMac()}.
	 */
	@Test
	public final void testGetMac() {
		try {
			// this method return a mac address which is not null
			assertNotNull(mac.getMac());
			// this method return a mac address which may be same
			assertEquals(mac.getMac(), mac.getMac());
			// this method return a mac address which format **:**:**:**:**:**(*is hex & lower)
			assertEquals(mac.getMac().matches(
					"^([0-9a-f]{2})(([:][0-9a-f]{2}){5})$"), true);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
