package com.oncecloud.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.service.VolumeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/oncecloud/config/application-context.xml")
@WebAppConfiguration
public class VolumeServiceTest {
	@Autowired
	private VolumeService volumeService;
	
	@Test
	public final void testEmptyAttachedVolume() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testGetVolumeList() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testCreateVolume() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testDeleteVolume() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testBindVolume() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testUnbindVolume() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
