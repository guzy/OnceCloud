package com.beyondsphere.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.service.StatisticsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class StatisticsServiceTest {
	@Autowired
	private StatisticsService statisticsService;
	
	@Test
	public final void testGetOnePageStatistics() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testAddResource() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public final void testUpdateResource() {
		try {

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
