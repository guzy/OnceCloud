package com.beyondsphere.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.service.ConnectionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class ConnectionServiceTest {
	@Autowired
	private ConnectionService conn;

	@Test
	public final void testGetConnectionFromUser() {
		try {
			//the method is wrapper from getConnectionFromPool
			//step5: create a user which belongs to the pool
			//step6: test the method : conn.getConnectionFromUser(userId);
			//step7: delete the user
			//step8: eject the host
			//step9: delete the host
			//step10: delete the pool
			//ending .... test ......
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testGetConnectionFromPool() {
		try {
			//starting .... test.....
			//step1: create a pool
			//step2: create a host
			//step3: join the host to the pool which is the master node
			//step4: test the method : conn.getConnectionFromPool(poolUuid); 
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
