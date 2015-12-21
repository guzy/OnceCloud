package com.oncecloud.dao.impl;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.dao.AlarmRuleDAO;
import com.oncecloud.entity.AlarmRule;
import com.oncecloud.model.AlarmRuleConstant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/oncecloud/config/application-context.xml")
@WebAppConfiguration
public class AlarmRuleDAOImplTest {
	
	private AlarmRuleDAO alarmRuleDAO;
	
	private AlarmRuleDAO getAlarmRuleDAO() {
		return alarmRuleDAO;
	}

	@Autowired
	private void setAlarmRuleDAO(AlarmRuleDAO alarmRuleDAO) {
		this.alarmRuleDAO = alarmRuleDAO;
	}
	
	@Test
	@Ignore
	public void testAddRule() {
		try {
			AlarmRule alarmRule = this.getAlarmRuleDAO().getAlarmRule("84eb681c-41d8-461b-9531-382fa21f7c24");
			this.getAlarmRuleDAO().removeAlarmRule(alarmRule);
			Assert.assertNotNull(this.getAlarmRuleDAO().addRule( "84eb681c-41d8-461b-9531-382fa21f7c24", AlarmRuleConstant.RuleName.CPURate, "07bc0d00-8e7e-42a1-924b-79022e792433", 
					AlarmRuleConstant.RuleTerm.SmallThan, 50));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testCountAlarmRulesList() {
		try {
			Assert.assertNotNull(this.getAlarmRuleDAO().countAlarmRulesList("07bc0d00-8e7e-42a1-924b-79022e792433"));
			System.out.println("07bc的策略有这么多条规则QAQ-->"+this.getAlarmRuleDAO().countAlarmRulesList("07bc0d00-8e7e-42a1-924b-79022e792433"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testgetAlarmRule() {
		try {
			Assert.assertNotNull(this.getAlarmRuleDAO().getAlarmRule("84eb681c-41d8-461b-9531-382fa21f7c24"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testgetRuleLsit() {
		try {
			Assert.assertNotNull(this.getAlarmRuleDAO().getRules("07bc0d00-8e7e-42a1-924b-79022e792433"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testRemoveAlarmRule() {
		try {
			AlarmRule alarmRule = this.getAlarmRuleDAO().getAlarmRule("05eb681c-41d8-461b-9531-382fa21f7c24");
			Assert.assertNotNull(this.getAlarmRuleDAO().removeAlarmRule(alarmRule));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testRemoveRules() {
		try {
			Assert.assertNotNull(this.getAlarmRuleDAO().removeRules(null));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	//@Ignore
	public void testUpdateRule() {
		try {
			AlarmRule alarmR = this.getAlarmRuleDAO().getAlarmRule("94eb681c-41d8-461b-9531-382fa21f7c24");			
			Assert.assertNotNull(this.getAlarmRuleDAO().updateRule(alarmR));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
