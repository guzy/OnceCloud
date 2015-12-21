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

import com.beyondsphere.model.CaptchaNumber;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class CaptchaServiceTest {
	@Autowired
	private CaptchaService cap;

	@Test
	public final void testGetCaptchaNumber() {
		try {
			// step1: the object captchanumber is not null
			assertNotNull(cap.getCaptchaNumber());
			// step2: first + second = total
			CaptchaNumber number = cap.getCaptchaNumber();
			Integer sum = number.getFirst() + number.getSecond();
			Integer total = number.getTotal();
			assertEquals(sum, total);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testGetCaptchaHeaders() {
		try {
			//headers which meta setting is right
			assertEquals(cap.getCaptchaHeaders().getPragma().toString(), "No-cache");
			assertEquals(cap.getCaptchaHeaders().getCacheControl().toString(), "no-cache");
			assertEquals(cap.getCaptchaHeaders().getExpires(), -1);
			assertEquals(cap.getCaptchaHeaders().getContentType().toString(), "image/jpeg");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testGetImage() {
		try {
			//captcha's image
			assertNotNull(cap.getImage(1, 1).length);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
