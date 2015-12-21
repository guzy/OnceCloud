package com.beyondsphere.dao.impl;



import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.CostDetailDAO;
import com.beyondsphere.entity.CostDetail;
import com.beyondsphere.helper.SessionHelper;
import com.beyondsphere.util.TimeUtils;


/**
 * @author zll
 * @Date 2015年5月11日
 */

@Component("CostDetailDAO")
public class CostDetailDAOImpl implements CostDetailDAO {

	@Resource
	private SessionHelper sessionHelper;

	public boolean add(CostDetail costDetail)  {
		boolean result = false;
		if (costDetail != null) {
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.save(costDetail);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Add CostDetail failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean edit(CostDetail costDetail) {
		boolean result = false;
		
		if (costDetail != null) {
		StringBuffer hql=new StringBuffer();
		hql.append("update CostDetail set");
		if(costDetail.getCostDetailName()!=null){
			hql.append(" costDetailName='"+costDetail.getCostDetailName()+"'");
		}
		if(costDetail.getCostDetailType()!=null){
			hql.append(",costDetailType='"+costDetail.getCostDetailType()+"'");
		}
		if(costDetail.getCostUnitPrice()!=0){
			hql.append(",costUnitPrice="+costDetail.getCostUnitPrice());
		}
		
		if(costDetail.getCostDeletetime()!=null){
			hql.append(",costDeletetime='"+TimeUtils.formatTime(costDetail.getCostDeletetime())+"'");
		}
		hql.append(",costCreatetime='"+TimeUtils.formatTime(costDetail.getCostCreatetime())+"'");
		hql.append(",costPriceType="+costDetail.getCostPriceType().ordinal());
		hql.append(",costNumber="+costDetail.getCostNumber());
		hql.append(" where costDetailId = '"+costDetail.getCostDetailId()+"'");
			Session session = null;
			try {
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				Query query=session.createQuery(hql.toString());
				int n=query.executeUpdate();
				session.getTransaction().commit();
				if(n>0){
					result = true;
				}
			} catch (Exception e) {
				loginfo("Edit CostDetail failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean remove(String id) {
		boolean result = false;
		Session session = null;
		session = sessionHelper.getMainSession();
		session.beginTransaction();
		Query query = session.createQuery("from CostDetail where costDetailId = :costDetailId");
		query.setString("costDetailId", id);
		CostDetail costDetail = (CostDetail) query.uniqueResult();
		if (costDetail != null) {
			try {
				session.delete(costDetail);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				 loginfo("remove CostDetail failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public CostDetail getOneByID(String id) {
		CostDetail costDetail = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session.createQuery("from CostDetail where costDetailId = :id");
			query.setString("id", id);
			costDetail = (CostDetail) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get CostDetail failed by costDetailId. ");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return costDetail;
	}

	@SuppressWarnings("unchecked")
	public List<CostDetail> getPagedList(int page, int limit, String search,String typeid) {
		List<CostDetail> costDetailList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from CostDetail where costTypeId=:typeid and costDetailName like :search order by costDetailId desc";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			query.setString("typeid", typeid);
			costDetailList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get CostDetail list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return costDetailList;
	}

	public int countAllList(String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from CostDetail where costDetailName like :search ");
			Query query = session.createQuery(builder.toString());
			query.setString("search", "%" + search + "%");
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count CostDetail list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CostDetail> getAllList() {
		List<CostDetail> costDetailList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from CostDetail";
			Query query = session.createQuery(queryString);
			costDetailList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all CostDetail list of Page failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return costDetailList;
	}

	private void loginfo(String message){
		LogConstant.loginfo(message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostDetail> getListByTypeDetailId(String typeDetailId) {
		List<CostDetail> costDetailList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from CostDetail where costTypeDetailId= :typeDetailId";
			Query query = session.createQuery(queryString);
			query.setString("typeDetailId", typeDetailId);
			costDetailList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get  CostDetail list by typeDetailId failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return costDetailList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostDetail> getListByTypeId(String typeid) {
		List<CostDetail> costDetailList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from CostDetail where costTypeId= :typeId";
			Query query = session.createQuery(queryString);
			query.setString("typeId", typeid);
			costDetailList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get  CostDetail list by typeDetailId failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return costDetailList;
	}

}
