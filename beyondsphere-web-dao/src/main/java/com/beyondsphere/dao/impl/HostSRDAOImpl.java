/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.HostSRDAO;
import com.beyondsphere.entity.HostSR;
import com.beyondsphere.helper.SessionHelper;

@Component("HostSRDAO")
public class HostSRDAOImpl implements HostSRDAO {
	@Resource
	private SessionHelper sessionHelper;

	public HostSR createHostSR(String hostUuid, String srUuid) {
		HostSR hostSR = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			hostSR = new HostSR();
			hostSR.setHostUuid(hostUuid);
			hostSR.setSrUuid(srUuid);
			session.save(hostSR);
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("create logic storage of host failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return hostSR;
	}

	@SuppressWarnings("unchecked")
	public Set<String> getSRList(String hostUuid) {
		Set<String> srList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			srList = new HashSet<String>();
			Query query = session.createQuery("from HostSR where hostUuid = '"
					+ hostUuid + "'");
			List<HostSR> hsrList = query.list();
			for (HostSR hsr : hsrList) {
				srList.add(hsr.getSrUuid());
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get storage list by hostId failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return srList;
	}

	@SuppressWarnings("unchecked")
	public Set<String> getHostList(String srUuid) {
		Set<String> hostList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			hostList = new HashSet<String>();
			Query query = session.createQuery("from HostSR where srUuid = '"
					+ srUuid + "'");
			List<HostSR> hsrList = query.list();
			for (HostSR hsr : hsrList) {
				hostList.add(hsr.getHostUuid());
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get host list by Storage id failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return hostList;
	}
	
	private void loginfo(String message){
		LogConstant.loginfo(message);
	}
}
