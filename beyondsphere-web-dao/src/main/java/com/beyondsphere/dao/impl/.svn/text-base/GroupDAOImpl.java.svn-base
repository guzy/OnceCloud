package com.beyondsphere.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.GroupDAO;
import com.beyondsphere.entity.OCGroup;
import com.beyondsphere.helper.SessionHelper;

@Component("GroupDAO")
public class GroupDAOImpl implements GroupDAO {
	
	@Resource
	private SessionHelper sessionHelper;
	
	@Override
	public int countGroupNum() {
		int groupNum = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String querySql = "select count(*) from OCGroup";
			Query query = session.createQuery(querySql);
			groupNum = ((Number)query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return groupNum;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OCGroup> getGroupList(int userId, int page, int limit) {
		List<OCGroup> groupList = new ArrayList<OCGroup>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String querySql = "from OCGroup where groupUid = :groupUid order by createTime desc";
			Query query = session.createQuery(querySql);
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			query.setInteger("groupUid", userId);
			groupList = query.list();
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return groupList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OCGroup> getGroupList(int userId) {
		List<OCGroup> groupList = new ArrayList<OCGroup>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String querySql = "from OCGroup where groupUid = :groupUid order by createTime desc";
			Query query = session.createQuery(querySql);
			query.setInteger("groupUid", userId);
			groupList = query.list();
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return groupList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OCGroup> getAllGroups() {
		List<OCGroup> groupList = new ArrayList<OCGroup>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String querySql = "from OCGroup order by createTime desc";
			Query query = session.createQuery(querySql);
			groupList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return groupList;
	}

	@Override
	public OCGroup getGroupById(String groupUuid) {
		OCGroup group = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String querySql = "from OCGroup where groupUuid = :groupUuid";
			Query query = session.createQuery(querySql);
			query.setString("groupUuid", groupUuid);
			group = (OCGroup) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return group;
	}

	@Override
	public boolean insertGroup(OCGroup group) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(group);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	@Override
	public boolean updateGroup(OCGroup group) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.update(group);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	@Override
	public boolean deleteGroup(String groupUuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String querySql = "delete from OCGroup where groupUuid = :groupUuid";
			Query query = session.createQuery(querySql);
			query.setString("groupUuid", groupUuid);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

}
