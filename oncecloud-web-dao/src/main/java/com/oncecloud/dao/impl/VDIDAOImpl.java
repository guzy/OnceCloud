package com.oncecloud.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.LogConstant;
import com.oncecloud.dao.VDIDAO;
import com.oncecloud.entity.OCVDI;
import com.oncecloud.helper.SessionHelper;

/**
 * @author hty
 */
@Component("VDIDAO")
public class VDIDAOImpl implements VDIDAO{
	@Resource
	private SessionHelper sessionHelper;

	/**
	 * 获取指定模板空闲VDI总数
	 * 
	 * @param tplUuid
	 * @return
	 */
	public int countFreeVDI(String tplUuid) {
		int count = -1;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from OCVDI where tplUuid= :tplUuid";
			Query query = session.createQuery(queryString);
			query.setString("tplUuid", tplUuid);
			count = ((Number) query.uniqueResult()).intValue();
		} catch (Exception e) {
			loginfo("Count free VDIs failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	/**
	 * 添加VDI
	 * 
	 * @param tplUuid
	 * @param vmUuid
	 * @return
	 */
	public OCVDI saveVDI(String tplUuid, String vmUuid) {
		OCVDI vdi = null;
		Session session = null;
		try {
			vdi = new OCVDI();
			vdi.setTplUuid(tplUuid);
			vdi.setVdiUuid(vmUuid);
			vdi.setCreateDate(new Date());
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(vdi);
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Save VDI failed");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vdi;
	}
	
	private void loginfo(String message){
		LogConstant.loginfo(message);
	}

	/**
	 * 删除VDI
	 * 
	 * @param vdi
	 * @return
	 */
	public boolean deleteVDI(OCVDI vdi) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.delete(vdi);
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
	
	/**
	 * 获取指定模板空闲VDI
	 * 
	 * @param tplUuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public OCVDI getFreeVDI(String tplUuid) {
		OCVDI vdi = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCVDI where tplUuid= :tplUuid";
			Query query = session.createQuery(queryString);
			query.setString("tplUuid", tplUuid);
			List<OCVDI> list = new ArrayList<OCVDI>();
			list = (List<OCVDI>)query.list();
			if (list.size() > 0)
				vdi = (OCVDI) query.list().get(0);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vdi;
	}

}
