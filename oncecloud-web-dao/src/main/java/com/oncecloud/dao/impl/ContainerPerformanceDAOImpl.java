package com.oncecloud.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.ContainerPerformanceDAO;
import com.oncecloud.helper.SessionHelper;
import com.oncecloud.model.performance.ContainerInfo;

@Component("ContainerPerformance")
public class ContainerPerformanceDAOImpl implements ContainerPerformanceDAO {
	
	@Resource
	private SessionHelper sessionHelper;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContainerInfo> getContainerInfos(String containerName) {
		
		List<ContainerInfo> containerInfos = null;
		Session session = null;
		session = sessionHelper.getPerformaceSession();
		session.beginTransaction();
		String queryString = "from ContainerInfo where containerName =:containerName order by time desc ";
		Query query = session.createQuery(queryString);
		query.setString("containerName", containerName);
		containerInfos = query.list();
		session.getTransaction().commit();
		return containerInfos;
	}

}
