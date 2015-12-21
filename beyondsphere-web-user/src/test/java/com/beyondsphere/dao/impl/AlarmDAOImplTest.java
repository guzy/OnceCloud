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

import com.beyondsphere.dao.AlarmDAO;
import com.beyondsphere.entity.Alarm;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/beyondsphere/config/application-context.xml")
@WebAppConfiguration
public class AlarmDAOImplTest {
	
	private AlarmDAO alarmDAO;
	
	private AlarmDAO getAlarmDAO() {
		return alarmDAO;
	}

	@Autowired
	private void setAlarmDAO(AlarmDAO alarmDAO) {
		this.alarmDAO = alarmDAO;
	}
	
	@Test
	@Ignore
	public void testAddAlarm() {
		try {
			Alarm alarm = this.getAlarmDAO().getAlarm("08bc0d00-8e7e-42a1-924b-79022e792433");
			this.getAlarmDAO().destoryAlarm(alarm);
			Assert.assertNotNull(this.getAlarmDAO().addAlarm("08bc0d00-8e7e-42a1-924b-79022e792433","test7",42));
		} catch (Exception e) {
			fail(e.getMessage());
			
		}
	}
	
	@Test
	//@Ignore
	public void testCountAlarmList(){
		try{
//			testAddAlarm();
			Assert.assertNotNull(this.getAlarmDAO().countAlarmList(2,""));
			System.out.println("+++++"+this.getAlarmDAO().countAlarmList(2,""));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testGetAlarm(){
		try{
			testAddAlarm();
			Assert.assertNotNull(this.getAlarmDAO().getAlarm("08bc0d00-8e7e-42a1-924b-79022e792433"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testAlarmList(){
		try{
			Assert.assertNotNull(this.getAlarmDAO().getAlarmList(2,"",2,2));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testRemoveAlarm(){
		try{
			testAddAlarm();
			Alarm alarm =  this.getAlarmDAO().getAlarm("08bc0d00-8e7e-42a1-924b-79022e792433");
			Assert.assertNotNull(this.getAlarmDAO().destoryAlarm(alarm));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testUpdateAlarm(){
		try{
			testAddAlarm();
			Alarm alarm =  this.getAlarmDAO().getAlarm("08bc0d00-8e7e-42a1-924b-79022e792433");
			Assert.assertNotNull(this.getAlarmDAO().updateAlarm(alarm));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	

}
