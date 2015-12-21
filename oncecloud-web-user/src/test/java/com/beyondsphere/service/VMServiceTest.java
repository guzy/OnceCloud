package com.beyondsphere.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.service.VMService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class VMServiceTest {
	@Autowired
	private VMService vmService;
	
	@Test
	public final void testDeleteVM() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testRestartVM() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testStartVM() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testShutdownVM() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testGetVMList() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testCreateVM() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testAdjustMemAndCPU() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testGetOCVM() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testDeleteOCVM() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testGetVMListOfVolume() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
