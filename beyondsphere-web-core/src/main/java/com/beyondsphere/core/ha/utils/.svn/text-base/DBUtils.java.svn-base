/**
 * Copyright (2014, ) Institute of Software, Chinese Academy of Sciences
 */
package com.beyondsphere.core.ha.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.beyondsphere.core.ha.ref.HostRef;
import com.beyondsphere.core.ha.ref.IPMIRef;
import com.beyondsphere.core.ha.ref.VMRef;
import com.beyondsphere.entity.Image;
import com.beyondsphere.entity.OCHa;
import com.beyondsphere.entity.OCHost;
import com.beyondsphere.entity.OCVM;
import com.beyondsphere.entity.Power;
import com.beyondsphere.helper.SessionHelper;

/**
 * @author henry
 * @date 2014年9月23日 和数据库操作相关的操作
 * @date 2014年11月15日
 */
public class DBUtils {

	private final static Logger m_logger = Logger.getLogger(DBUtils.class);
	
	public DBUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	/***************************************************************************
	 * 
	 *    GetAll Methods
	 * 
	 ***************************************************************************/
	
	/**
	 * 得到指定资源池的所有服务器
	 * 数据库连接不上，或者传入的poolUUID为空，不存在或者不合法，均返回大小为0的List。
	 * 
	 * @param poolUUID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HostRef> getAllServersOnPool(String poolUUID) {
		List<HostRef> hostRefs = new ArrayList<HostRef>();
		Session session = createSession();
		if(!invalid(session)) {
			Transaction transaction = createTransaction(session);
			try {
			String sql = "FROM OCHost where poolUuid = :pool_uuid";
			Query query = session.createQuery(sql);
			query.setString("pool_uuid", poolUUID);
			List<OCHost> ocHosts = query.list();
			for(OCHost ocHost : ocHosts) {
				HostRef hostRef = new HostRef();
				hostRef.setHostUUID(ocHost.getHostUuid());
				hostRef.setVMHostIP(ocHost.getHostIP());
				hostRefs.add(hostRef);
			}
			transaction.commit();
			} catch (Exception e) {
				rollback(transaction, e);
			}
		}  
		return hostRefs;
	}

	
	/**
	 * 得到指定服务器的所有虚拟机
	 * 数据库连接不上，或者传入的hostUUID为空，不存在或者不合法，均返回大小为0的List。
	 * 
	 * @param hostUUID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VMRef> getAllVMsOnHost(String hostUUID) {
		List<VMRef> VMRefs = new ArrayList<VMRef>();
		Session session = createSession();
		if(!invalid(session)) {
			Transaction transaction = createTransaction(session);
			try {
				String sql = "FROM OCVM WHERE vmHostUuid = :host_uuid";
				Query query = session.createQuery(sql);
				query.setString("host_uuid", hostUUID);
				List<OCVM> ocVMs = query.list();
				for(OCVM ocVM : ocVMs) {
					VMRef vmRef = new VMRef();
					vmRef.setVmUUID(ocVM.getVmUuid());
					vmRef.setHostUUID(ocVM.getVmHostUuid());
					vmRef.setVMPowerStatus(isStart(ocVM.getVmPower().ordinal()));
					VMRefs.add(vmRef);
				}
				transaction.commit();
			} catch (Exception e) {
				rollback(transaction, e);
			}  
		}
		return VMRefs;
	}
	
	
	/**
	 * 数据库连接不上，或者传入的hostUUID为空，不存在或者不合法，均null。
	 * 
	 * @param hostUUID
	 * @return
	 */
	public IPMIRef getIPMI(String hostUuid) {
		IPMIRef ipmi = new IPMIRef();
		Power power = null;
		Session session = null;
		try {
			session = createSession();
			session.beginTransaction();
			Query query = session.createQuery("from Power where hostUuid = :hostUuid");
			query.setString("hostUuid", hostUuid);
			power =(Power) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.getTransaction().rollback();
			}
		}
		ipmi.setUser(power.getPowerUsername());
		ipmi.setPwd(power.getPowerPassword());
		ipmi.setPort(power.getPowerPort());
		ipmi.setUrl(power.getMotherboardIP());
		return (ipmi.getUser() == null) ? null : ipmi;
	}
	
	/**
	 * 数据库连接不上，或者传入的hostUUID为空，不存在或者不合法，均null。
	 * 
	 * @param hostUUID
	 * @return
	 */
	public String getPassword(String hostUUID) {
		String password = null;
		Session session = createSession();
		if(!invalid(session)) {
			Transaction transaction = createTransaction(session);
			try {
				String sql = "FROM OCHost where hostUuid = :host_uuid";
				Query query = session.createQuery(sql);
				query.setString("host_uuid", hostUUID);
				OCHost ocHost = (OCHost) query.uniqueResult();
				password = secretToNormal(ocHost.getHostPwd());
				transaction.commit();
			} catch (Exception e) {
				rollback(transaction, e);
			}  
		}
		return password;
	}
	
	/**
	 * 数据库连接不上，或者传入的hostUUID为空，不存在或者不合法，均null。
	 * 
	 * @param hostUUID
	 * @return
	 */
	public String getHostUUID(String hostIP) {
		String hostUUID = null;
		Session session = createSession();
		if(!invalid(session)) {
			Transaction transaction = createTransaction(session);
			try {
				String sql = "FROM OCHost where host_ip = :host_IP";
				Query query = session.createQuery(sql);
				query.setString("host_IP", hostIP);
				OCHost ocHost = (OCHost) query.uniqueResult();
				hostUUID = ocHost.getHostUuid();
				transaction.commit();
			} catch (Exception e) {
				rollback(transaction, e);
			}  
		}
		return hostUUID;
	}
	
	/***************************************************************************
	 * 
	 *    Update Methods
	 * 
	 ***************************************************************************/
	/**
	 * 数据库连接不上，或者传入參數不存在或者不合法，均返回false。
	 * 
	 * @param conn
	 * @param vm
	 * @return
	 */
	
	public synchronized boolean updateVMOnHostInfo(String hostUUID, String vmUUID) {
		boolean sucessful = false;
		Session session = createSession();
		if(!invalid(session)) {
			Transaction transaction = createTransaction(session);
			try {
				String querysString = "UPDATE OCVM SET vmHostUuid = :host_uuid where vmUuid = :vm_uuid";
				Query query = session.createQuery(querysString);
				query.setString("host_uuid", hostUUID);
				query.setString("vm_uuid", vmUUID);
				query.executeUpdate();
				transaction.commit();
				sucessful = true;
			} catch (Exception e) {
				rollback(transaction, e);
			}  
		}
		return sucessful;
	}
	
	public synchronized boolean updateImageOnImageInfo(String hostUuid, String imageUuid){
		boolean result = false;
		Session session = createSession();
		if(!invalid(session)) {
			Transaction transaction = createTransaction(session);
			try {
				String querysString = "UPDATE Image SET hostUuid = :hostUuid where imageUuid = :imageUuid";
				Query query = session.createQuery(querysString);
				query.setString("hostUuid", hostUuid);
				query.setString("imageUuid", imageUuid);
				query.executeUpdate();
				transaction.commit();
				result = true;
			} catch (Exception e) {
				rollback(transaction, e);
			}  
		}
		return result;
	}


	/**
	 *  数据库连接不上，或者传入參數不存在或者不合法，均返回false。
	 * 
	 * @param masterUUID
	 * @param poolUUID
	 * @return
	 */
	public synchronized boolean updatePoolStatus(String masterUUID, String poolUUID) {
		boolean sucessful = false;
		Session session = createSession();
		if(!invalid(session)) {
			Transaction transaction = createTransaction(session);
			try {
				String querysString = "UPDATE OCPool SET poolMaster = :master_UUID where poolUuid = :pool_UUID";
				Query query = session.createQuery(querysString);
				query.setString("master_UUID", masterUUID);
				query.setString("pool_UUID", poolUUID);
				query.executeUpdate();
				transaction.commit();
				sucessful = true;
			} catch (Exception e) {
				rollback(transaction, e);
			}  
		}
		return sucessful;
	}

	/**
	 *  数据库连接不上，或者传入參數不存在或者不合法，均返回false。
	 * 
	 * @param hostUUID
	 * @param poolUUID
	 * @return
	 */
	public synchronized boolean updateServerStatus(String hostUUID, String poolUUID) {
		boolean sucessful = false;
		Session session = createSession();
		if(!invalid(session)) {
			Transaction transaction = createTransaction(session);
			try {
				String querysString = "UPDATE OCHost SET poolUuid = :poolUUID WHERE hostUuid = :hostUUID";
				Query query = session.createQuery(querysString);
				query.setString("hostUUID", hostUUID);
				query.setString("poolUUID", poolUUID);
				query.executeUpdate();
				transaction.commit();
				sucessful = true;
			} catch (Exception e) {
				rollback(transaction, e);
			}  
		}
		
		return sucessful;
	}
	
	/**
	 *  数据库连接不上，或者传入參數不存在或者不合法，均返回false。
	 * 
	 * @param vmUUID
	 * @param vmStarted
	 * @return
	 */
	public synchronized boolean updateVMStatus(String vmUUID, boolean vmStarted) {
		int status = vmStarted ? 1 : 0;
		boolean sucessful = false;
		Session session = createSession();
		if(!invalid(session)) {
			Transaction transaction = createTransaction(session);
			try {
				String querysString = "UPDATE OCVM SET vmUuid = :vm_uuid where vmPower = :vm_power";
				Query query = session.createQuery(querysString);
				query.setString("vm_uuid", vmUUID);
				query.setInteger("vm_power", status);
				query.executeUpdate();
				transaction.commit();
				sucessful = true;
			} catch (Exception e) {
				rollback(transaction, e);
			}  
		}
		
		return sucessful;
	}
	
	
	/***************************************************************************
	 *   Utils Methods
	 ***************************************************************************/
	
	private Session createSession() {
		try {
			SessionHelper sessionHelper = new SessionHelper();
			return sessionHelper.getMainSession();
		} catch (Exception e) {
			return null;
		}
	}
	
	private Transaction createTransaction(Session session) {
		try {
			return session.beginTransaction();
		} catch (Exception e) {
			return null;
		}
	}
	
	private void rollback(Transaction transaction, Exception e) {
		if(transaction != null) {
			try {
				transaction.rollback();
			} catch (Exception ex) {
				// That is ok.
			}
		}
		m_logger.error(e);
	}
	
	private static boolean isStart(int status) {
		return (status == 1) ? true : false;
	}
	
	/**
	 * 判断对象是否有效
	 * 
	 * @param obj
	 * @return
	 */
	private static boolean invalid(Object obj) {
		return (obj == null) ? true : false;
	}
	
	/**
	 * @author lining
	 * @param poolUuid
	 * 获取对应资源池的高可用服务
	 */
	public OCHa getHAService(String poolUuid) {
		OCHa ha = null;
		Session session = createSession();
		if(!invalid(session)) {
			Transaction transaction = createTransaction(session);
			try {
				StringBuilder builder = new StringBuilder("from OCHa where poolUuid = :poolUuid");
				Query query = session.createQuery(builder.toString());
				query.setString("poolUuid", poolUuid);
				ha = (OCHa) query.uniqueResult();
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				if (session != null) {
					transaction.rollback();
				}
			}
		}
		return ha;
	}
	
	/**
	 * @author lining
	 * @param hostId 
	 * 根据主机的id获取对应的主机
	 */
	public HostRef getAppointedHost(String hostUuid){
		HostRef hostRef = new HostRef();
		OCHost appointedHost = null;
		Session session = createSession();
		if(!invalid(session)) {
			Transaction transaction = createTransaction(session);
			try {
				String sql = "FROM OCHost where hostUuid = :hostUuid";
				Query query = session.createQuery(sql);
				query.setString("hostUuid", hostUuid);
				appointedHost = (OCHost) query.uniqueResult();
				hostRef.setHostUUID(appointedHost.getHostUuid());
				hostRef.setVMHostIP(appointedHost.getHostIP());
				transaction.commit();
			} catch (Exception e) {
				rollback(transaction, e);
			}  
		}
		return hostRef;
	}
	
	/**
	 * @author lining
	 * @param hostUuid
	 * 根据hostUuid获取宕掉服务器上存在的模板信息
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Image> getCrashedImagesByHostUuid(String hostUuid){
		List<Image> images = new ArrayList<Image>();
		Session session = createSession();
		if(!invalid(session)) {
			Transaction transaction = createTransaction(session);
			try {
				String sql = "FROM Image WHERE imageStatus = 1 and hostUuid = :hostUuid";
				Query query = session.createQuery(sql);
				query.setString("hostUuid", hostUuid);
				images = query.list();
				transaction.commit();
			} catch (Exception e) {
				rollback(transaction, e);
			}  
		}
		return images;
	}
	
	// 加密后解密   
	 private String secretToNormal(String inStr) {   
		StringBuffer sbu = new StringBuffer();  
	    String[] chars = inStr.split(",");  
	    for (int i = 0; i < chars.length; i++) {  
	        sbu.append((char) Integer.parseInt(chars[i]));  
	    }  
	    return sbu.toString();    
	 }
	 
}
