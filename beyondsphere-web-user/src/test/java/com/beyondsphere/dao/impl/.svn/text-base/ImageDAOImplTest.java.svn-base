package com.beyondsphere.dao.impl;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.beyondsphere.dao.ImageDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class ImageDAOImplTest {

	private ImageDAO imageDAO;
	
	private ImageDAO getImageDAO() {
		return imageDAO;
	}

	@Autowired
	private void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}

	@Test
	@Ignore
	public void test() {
		try {
			Assert.assertNotNull(this.getImageDAO().getImage("ff9c5716-6a92-4961-b113-52195f27322e"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
