package com.oncecloud.factory;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.factory.ServiceFactory;
import com.oncecloud.wrapper.VNCServiceWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/oncecloud/config/application-context.xml")
@WebAppConfiguration
public class VNCFactoryTest {

	private ServiceFactory vncFactory;
	
	private ServiceFactory getVncFactory() {
		return vncFactory;
	}

	@Autowired
	private void setVncFactory(ServiceFactory vncFactory) {
		this.vncFactory = vncFactory;
	}

	@Test
	@Ignore
	public void test() {
		try {
			for (Class<?> fc : this.getVncFactory().getVNCFactory().getClass().getInterfaces()) {
				for (Class<?> ifc : fc.getInterfaces()) {
					Assert.assertEquals(VNCServiceWrapper.class, ifc);
				}
			}
			this.getVncFactory().getVNCFactory().allocation("", "", null);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
