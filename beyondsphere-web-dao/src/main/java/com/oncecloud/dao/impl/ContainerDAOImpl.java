package com.oncecloud.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.ContainerDAO;
import com.oncecloud.entity.OCVM;
import com.oncecloud.helper.SessionHelper;
import com.oncecloud.model.VMStatus;

/**
 * @author luogan 
 * 2015年5月19日
 * 下午2:15:39
 */
@Component("ContainerDAO")
public class ContainerDAOImpl implements ContainerDAO {

	@Resource
	private SessionHelper sessionHelper;

	@Override
	public boolean createContainer(String vmUuid, String tplUuid, int userId,
			int cpu, int memory, String pwd, String name, String desc, String statistics, String port, Date conCreateTime) {
		Session session = null;
		boolean result = false;
		try {
			// OCVM vm = new
			// OCVM(vmUuid,tplUuid,userId,cpu,memory,pwd,name,statistics);
			OCVM vm = new OCVM(vmUuid, pwd, null, userId, name, null, null,
					memory, cpu, null, VMStatus.valueOf(statistics), conCreateTime, null);
			vm.setVmImportance(port);
			vm.setVmDesc(desc);
			session = sessionHelper.getMainSession();
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(vm);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			// loginfo("Pre create vm failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			result = false;
		}
		return result;
	}

	@Override
	public boolean stopContainer(int userId, String containerId) {
		boolean result = false;
		String vmUuid = containerId;
		OCVM vm = this.getVM(vmUuid);
		Session session = null;
		if (vm != null) {
			try {
				/*
				 * OCVM vm = new OCVM(containerId, null, null, userId, null,
				 * null, null, null, null, null, VMStatus.valueOf("DELETED"),
				 * null, null);
				 */
				vm.setVmStatus(VMStatus.valueOf("DELETED"));
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.update(vm);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				// loginfo("Update vm failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
				result = false;
			}
		}
		return result;
	}


	public OCVM getVM(String vmUuid) {
		OCVM vm = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from OCVM where vmUuid = :vmUuid");
			query.setString("vmUuid", vmUuid);
			vm = (OCVM) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			//loginfo("Get vm failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vm;
	}

	@Override
	public boolean deleteContainer(int userId, String containerId) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String string = "delete OCVM where vmUuid = :containerId ";
			// String string
			// ="delete OCVM where vmUuid = :containerId and vmUid = :userId";
			Query query = session.createQuery(string);
			// query.setInteger("userId", userId);
			query.setString("containerId", containerId);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			// loginfo("Delete vm failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			result = false;
		}
		return result;
	}

	@Override
	public boolean startContainer(int userId, String containerId) {

		boolean result = false;
		String vmUuid = containerId;
		OCVM vm = this.getVM(vmUuid);
		Session session = null;
		if (vm != null) {
			try {
				/*
				 * OCVM vm = new OCVM(containerId, null, null, userId, null,
				 * null, null, null, null, null, VMStatus.valueOf("DELETED"),
				 * null, null);
				 */
				vm.setVmStatus(VMStatus.valueOf("EXIST"));
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.update(vm);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				// loginfo("Update vm failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
				result = false;
			}
		}
		return result;
	
	}

	@Override
	public int countByUserId(int userId, String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from OCVM where vmUid = :userId ";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			// query.setString("search", "%" + search + "%");
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OCVM> getOnePageByUserId(int userId, int page, int limit,
			String groupUuid, String search) {
		List<OCVM> vmList = new ArrayList<OCVM>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			StringBuilder queryString = new StringBuilder(
					"from OCVM where vmUid = :userId ");
			if (!groupUuid.equals("all")) {
				queryString.append(" and vmGroup = :groupUuid ");
			}
			// queryString.append("order by vmCreateDate desc");
			Query query = session.createQuery(queryString.toString());
			query.setInteger("userId", userId);
			// if (!groupUuid.equals("all")) {
			// query.setString("groupUuid", groupUuid);
			// }
			// query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			vmList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vmList;
	}

	@Override
	public List<OCVM> getOnePageByUserId(int userId, int page, int limit,
			String search) {
		// TODO Auto-generated method stub
		return null;
	}

}
