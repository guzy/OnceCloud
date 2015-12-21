package com.oncecloud.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.AlarmRuleDAO;
import com.oncecloud.entity.AlarmRule;
import com.oncecloud.helper.SessionHelper;
import com.oncecloud.model.AlarmRuleConstant.RuleName;
import com.oncecloud.model.AlarmRuleConstant.RuleTerm;


@Component("AlarmRuleDAO")
public class AlarmRuleDAOImpl implements AlarmRuleDAO{
	
	@Resource
	private SessionHelper sessionHelper;

	public boolean addRule(String ruleId, RuleName ruleName,String alarmUuid,
			RuleTerm ruleTerm,Integer ruleThreshold) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			AlarmRule rule = new AlarmRule(ruleId, ruleName, alarmUuid,
					ruleTerm, ruleThreshold);
			session.save(rule);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return false;
	}

	public int countAlarmRulesList(String alarmUuid) {
		Session session = null;
		int ruleNum = 0;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from AlarmRule where alarmUuid = :alarmUuid";
			Query query = session.createQuery(queryString);
			query.setString("alarmUuid", alarmUuid);
			ruleNum = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return ruleNum;
	}

	public AlarmRule getAlarmRule(String ruleId) {
		Session session = null;
		AlarmRule rule = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			rule = (AlarmRule) session.get(AlarmRule.class, ruleId);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return rule;
	}
	
	@SuppressWarnings("unchecked")
	public List<AlarmRule> getRules(String alarmUuid) {
		Session session = null;
		List<AlarmRule> rules = new ArrayList<AlarmRule>();
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from AlarmRule where alarmUuid = :alarmUuid";
			Query query = session.createQuery(queryString);
			query.setString("alarmUuid", alarmUuid);
			rules = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return rules;
	}

	public boolean removeAlarmRule(AlarmRule alarmRule) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			if (alarmRule != null){
				session.delete(alarmRule);
			}
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean removeRules(String alarmUuid) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from AlarmRule where alarmUuid = :alarmUuid";
			Query query = session.createQuery(queryString);
			query.setString("alarmUuid", alarmUuid);			
			List<AlarmRule> rules = query.list();
			for (AlarmRule alarmRule : rules) {
				session.delete(alarmRule);
			}
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return false;
	}

	public boolean updateRule(AlarmRule alarmRule) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			if (alarmRule != null) {
				session.update(alarmRule);
			}
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return false;
	}

}
