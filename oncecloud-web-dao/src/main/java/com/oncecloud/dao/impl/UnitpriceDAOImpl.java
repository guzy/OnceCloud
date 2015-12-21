package com.oncecloud.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.LogConstant;
import com.oncecloud.dao.UnitpriceDAO;
import com.oncecloud.entity.OCUnitprice;
import com.oncecloud.helper.SessionHelper;

@Component("UnitpriceDAO")
public class UnitpriceDAOImpl implements UnitpriceDAO {

	@Resource
	private SessionHelper sessionHelper;
	
	@SuppressWarnings("unchecked")
	public List<OCUnitprice> getOcUnitpriceList() {
		List<OCUnitprice> unitprinceList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCUnitprice";
			Query query = session.createQuery(queryString);
			unitprinceList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get unit price of resource failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return unitprinceList;
	}

	public boolean updateUnitprice(String priceType, double priceValue) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update OCUnitprice set priceValue = :priceValue where priceType = :priceType";
			Query query = session.createQuery(queryString);
			query.setString("priceType", priceType);
			query.setDouble("priceValue", priceValue);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Update unit price of resource failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	private void loginfo(String message){
		LogConstant.loginfo(message);
	}
}
