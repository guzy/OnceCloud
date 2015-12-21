package com.beyondsphere.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class ImageServiceTest {
	@Autowired
	private ImageService imageService;
	
	@Test
	public final void testGetImageList() {
		try {
			//step1: get a user from db
			//step2: test the method imageService.getImageList(userId, page, limit, search, type)
			//the param type: system/user
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testMakeImage() {
		try {
			// this method can create an image from exists vm
			// test the method imageService.makeImage(vmUuid, userId, imageName, imageDesc, c)
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testDeleteImage() {
		try {
			// this method can delete an image which belongs to a user not admin
			// this method only delete the information of image from db
			// test the method imageService.deleteImage(imageUuid, userId)
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
