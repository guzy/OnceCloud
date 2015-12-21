package com.beyondsphere.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.AccountingDAO;
import com.beyondsphere.entity.OCStatistics;
import com.beyondsphere.entity.User;
import com.beyondsphere.helper.SessionHelper;
import com.beyondsphere.model.FinanceAnalysis;
@Component("AccountingDAO")
public class AccountingDAOImpl implements AccountingDAO {
	@Resource
	private SessionHelper sessionHelper;
	/*计费管理分页查询总页数*/
	@Override
	public int countAllAccountingList(String search) {
		Session session = null;
		String userids=getUsers(search);
		int totalNum=0;
		try{
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session.createQuery("from OCStatistics where userId in ("+userids+") group by userId");
			totalNum =  query.list().size();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get oc_statistics infos failed");
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		
		return totalNum;
	}
	/*计费管理分页查询*/
	@SuppressWarnings("unchecked")
	@Override
	public List<OCStatistics> getOnePageListOfAccounting(int page, int limit,String search) {
		Session session = null;
		List<OCStatistics> list = null;
		String userids=getUsers(search);
		try{
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session.createQuery("from OCStatistics where userId in ("+userids+") group by userId");
			query.setFirstResult((page-1)*limit);
			query.setMaxResults(limit);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get oc_statistics infos failed");
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return list;
	}
	/*根据用户id查询所属资源*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getStatisticsByUser(int userId,int type) {
		Session session = null;
		List<Object> list = null;
		try{
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String sql="";
			if(type == FinanceAnalysis.REAL_PROFIT.ordinal()){
				sql = "select sum(case when t.st_type=0 then t.unit_price else 0 end ) as type_0, "
						+ " sum(case when t.st_type=1 then t.unit_price else 0 end ) as type_1,  "
						+ " sum(case when t.st_type=2 then t.unit_price else 0 end ) as type_2,  "
						+ " sum(t.unit_price ) as total  "
						+ " from (select st_userId,st_type,case when st_delete is null and st_create is not null "
						+ " then ceil ((UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(st_create))/60/60/24)*unit_price"
						+ " when st_delete is not null then ceil ((UNIX_TIMESTAMP(st_delete)-UNIX_TIMESTAMP(st_create))/60/60/24)*unit_price "
						+ " else 0 end as unit_price from oc_statistics where st_userId="+userId+") t";
			}
			if(type == FinanceAnalysis.EXPECT_PROFIT.ordinal()){
				sql = "select sum(case when st_type=0 then unit_price else 0 end) as type_0,"
						+ " sum(case when st_type=1 then unit_price else 0 end) as type_1,"
						+ " sum(case when st_type=2 then unit_price else 0 end) as type_2,"
						+ " sum(unit_price) as total from oc_statistics where st_delete is null and st_userId="+userId;
			}
			Query query = session.createSQLQuery(sql);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get oc_statistics infos failed");
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return list;
	}
	
	
	private void loginfo(String message){
		LogConstant.loginfo(message);
	}
	
	@SuppressWarnings("unchecked")
	private String getUsers(String search){
		Session session = null;
		String str="";
		List<User> users=null;
		//先查根据用户名称查询userid
		try{
			session = sessionHelper.getPlatformSession();
			session.beginTransaction();
			String queryString = "from User where userName like :search ";
			Query query = session.createQuery(queryString);
			query.setString("search", "%" + search + "%");
			users = query.list();
			session.getTransaction().commit();
			
			for(User user:users){
				str+=user.getUserId()+",";
			}
		} catch (Exception e) {
			loginfo("Get User infos failed");
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally{
			if (session.isOpen()) {
				session.close();
			}
		}
		return str+"''";
		
	}
}
