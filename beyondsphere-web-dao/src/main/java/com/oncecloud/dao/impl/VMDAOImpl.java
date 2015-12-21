/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.oncecloud.constants.LogConstant;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.entity.OCVM;
import com.oncecloud.helper.SessionHelper;
import com.oncecloud.model.VMPlatform;
import com.oncecloud.model.VMPower;
import com.oncecloud.model.VMStatus;

@Component("vmDAO")
public class VMDAOImpl implements VMDAO {

	@Resource
	private SessionHelper sessionHelper;

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
			loginfo("Get vm failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vm;
	}

	public OCVM getAliveVM(String vmUuid) {
		OCVM vm = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from OCVM where vmUuid = :vmUuid and vmStatus = 1");
			query.setString("vmUuid", vmUuid);
			vm = (OCVM) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get alive vm failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vm;
	}

	@SuppressWarnings("unchecked")
	public List<OCVM> getOnePageVMs(int userId, int page, int limit,
			String search) {
		List<OCVM> vmList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from OCVM where vmUID = :userId and vmName like :search and vmStatus = 1 order by createDate desc";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			query.setString("search", "%" + search + "%");
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			vmList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all vm list of user failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vmList;
	}
	
	@SuppressWarnings("unchecked")
	public List<OCVM> getVMsByUser(int userId){
		List<OCVM> vmList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCVM where vmUid = :userId and vmStatus = 1 order by vmCreateDate desc";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			vmList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all vm list of user failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vmList;
	}

	@SuppressWarnings("unchecked")
	public List<OCVM> getOnePageVMsOfAdmin(int page, int limit, String host,
			int importance, int userid) {
		List<OCVM> vmList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryStr = "from OCVM where vmStatus>0 ";
			if (!host.equals("all")) {
				queryStr+="and vmHostUuid='"+host+"' ";
			}
			if (importance == 0) {
				queryStr+="and vmImportance='' ";
			}else if (importance != 6) {
				queryStr+="and vmImportance="+importance+" ";
			}
			if (userid != 0) {
				queryStr+="and vmUid="+userid;
			}
				queryStr+=" order by vmCreateDate desc";
					
					
			Query query = session.createQuery(queryStr);
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			vmList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count vm list of admin failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vmList;
	}

	public int countVMsOfAdmin(String host, int importance, int userid) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = null;
			String queryStr = "select count(*) from OCVM where vmStatus>0 ";
			if (!host.equals("all")) {
				queryStr+="and vmHostUuid='"+host+"' ";
			}
			if (importance != 6) {
				queryStr+="and vmImportance="+importance+" ";
			}
			if (userid != 0) {
				queryStr+="and vmUid="+userid;
			}
			
			query = session.createQuery(queryStr);
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all vm numbers of admin failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	public int countVMsOfHost(String hostUuid) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from OCVM where vmStatus != 0 and vmHostUuid = :hostUuid";
			Query query = session.createQuery(queryString);
			query.setString("hostUuid", hostUuid);
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all vm numbers of same host failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}
	
	public int countAllVms() {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from OCVM where vmStatus != 0";
			Query query = session.createQuery(queryString);
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all vm numbers failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return count;
	}

	public boolean preCreateVM(String vmUuid, String vmPWD, Integer vmUID,
			String vmName, Integer vmPlatform, String vmMac, Integer vmMem,
			Integer vmCpu, Integer vmPower, Integer vmStatus, Date createDate) {
		Session session = null;
		boolean result = false;
		try {
			OCVM vm = new OCVM(vmUuid, vmPWD, null,vmUID, vmName, VMPlatform.values()[vmPlatform], vmMac,
					vmMem, vmCpu, VMPower.values()[vmPower], VMStatus.values()[vmStatus], createDate,null);
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(vm);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Pre create vm failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public void removeVM(int userId, String vmUuid) {
		OCVM toDelete = this.getVM(vmUuid);
		if (toDelete != null && toDelete.getVmStatus() != VMStatus.DELETED) {
			Session session = null;
			try {
				toDelete.setVmStatus(VMStatus.DELETED);
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.update(toDelete);
				session.getTransaction().commit();
			} catch (Exception e) {
				loginfo("Modify vm status failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
	}

	public boolean updateVM(OCVM vm) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.update(vm);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			loginfo("Update vm failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		}
	}

	public void saveVM(OCVM vm) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(vm);
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Save vm failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
	}

	public void deleteVM(OCVM vm, boolean flag) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String string = "";
			if (flag){
				string = "delete OCVM where vmUuid = :vmUuid and vmStatus = 0";
			} else {
				string = "delete OCVM where vmUuid = :vmUuid";
			}
			Query query = session.createQuery(string);
			query.setString("vmUuid", vm.getVmUuid());
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Delete vm failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
	}

	public boolean updateVM(String vmUuid, String vmPWD, int vmPower,
			String hostUuid) {
		boolean result = false;
		OCVM vm = this.getVM(vmUuid);
		if (vm != null) {
			Session session = null;
			try {
				vm.setVmPwd(vmPWD);
				vm.setVmPower(VMPower.values()[vmPower]);
				vm.setVmHostUuid(hostUuid);
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.update(vm);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Update vm infos failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}


	public void updatePowerAndHost(String uuid, int power, String hostUuid) {
		Session session = null; 
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update OCVM set vmPower = :power, vmHostUuid = :hostUuid where vmUuid = :uuid";
			Query query = session.createQuery(queryString);
			query.setInteger("power", power);
			query.setString("hostUuid", hostUuid);
			query.setString("uuid", uuid);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Update vm power infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
	}

	public boolean updatePowerStatus(String uuid, int powerStatus) {
		boolean result = false;
		OCVM vm = this.getVM(uuid);
		if (vm != null) {
			Session session = null;
			try {
				vm.setVmPower(VMPower.values()[powerStatus]);
				session = sessionHelper.getMainSession();
				session.beginTransaction();
				session.update(vm);
				session.getTransaction().commit();
				result = true;
			} catch (Exception e) {
				loginfo("Update vm power status failed.");
				e.printStackTrace();
				if (session.isOpen()) {
					session.getTransaction().rollback();
				}
			}
		}
		return result;
	}

	public boolean updateHostUuid(String uuid, String hostUuid) {
		boolean result = false;
		OCVM vm = this.getVM(uuid);
		Session session = null;
		try {
			vm.setVmHostUuid(hostUuid);
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.update(vm);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Update vm host infos failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllList() {
		List<String> list = new ArrayList<String>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "select vmUuid from OCVM";
			Query query = session.createQuery(str);
			list = (List<String>)query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get all vm ids failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMaxCpuAndMemByPoolUuid(String poolUuid) {
		Map<String, Object> resultMap = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String sql = "select new map(max(vm.vmMem) as maxMem, max(vm.vmCpu) as maxCpu) from OCVM as vm, OCHost as host "
						+ "where vm.vmHostUuid = host.hostUuid and host.poolUuid = :poolUuid";
			Query query = session.createQuery(sql);
			query.setString("poolUuid", poolUuid);
			resultMap = (HashMap<String, Object>) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get max vm memory and vm cpu of same pool failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	public List<OCVM> getVmListByPoolUuid(String poolUuid) {
		List<OCVM> list = new ArrayList<OCVM>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "from OCVM where vmStatus = 1 and vmHostUuid in (select hostUuid from OCHost where poolUuid = :poolUuid) ";
			Query query = session.createQuery(str);
			query.setString("poolUuid", poolUuid);
			list = (List<OCVM>)query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get vm list of same pool failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}

	public int countNumberOfVMByVlan(String netId) {
		int numberOfVMs = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String sql = "select count(*) from OCVM where vmStatus = 1 and vmVlan = :netId";
			Query query = session.createQuery(sql);
			query.setString("netId", netId);
			numberOfVMs = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get vm numers of same vlan failed.");
			e.printStackTrace();
		}
		return numberOfVMs;
	}

	@SuppressWarnings("unchecked")
	public List<OCVM> getVmListByhostUuid(String hostUuid) {
		List<OCVM> list = new ArrayList<OCVM>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "from OCVM where vmHostUuid = :hostUuid and vmStatus = 1";
			Query query = session.createQuery(str);
			query.setString("hostUuid", hostUuid);
			list = (List<OCVM>)query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get vm list of host failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}
	
	private void loginfo(String message){
		LogConstant.loginfo(message);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map> getVMCountTop(int num) {
		List<Map> list = new ArrayList<Map>();

		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String sql = "select new map(vmUid,count(*)) from OCVM where vmStatus=1 group by vmUid order by count(*) desc";
			Query query = session.createQuery(sql);
			query.setMaxResults(num);
			list=query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get vm numers of same vlan failed.");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer getSumVMCount(Date endTime) {
		int numberOfVMs = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String sql = "select count(*) from OCVM where vmStatus=1 and vmCreateDate<:endTime";
			Query query = session.createQuery(sql);
			query.setDate("endTime", endTime);
			numberOfVMs = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get sum vm numers of admin failed.");
			e.printStackTrace();
		}
		return numberOfVMs;
	}

	@Override
	public Integer getVlanVMCount() {
		int numberOfVMs = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String sql = "select count(*) from OCVM where vmStatus=1 and vmVlan<>-1";
			Query query = session.createQuery(sql);
			numberOfVMs = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get sum vlan vm numers of admin failed.");
			e.printStackTrace();
		}
		return numberOfVMs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OCVM> getAllVmList() {
		List<OCVM> list = new ArrayList<OCVM>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "from OCVM where vmStatus = 1 order by vmUid";
			Query query = session.createQuery(str);
			list = (List<OCVM>)query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get vm list of host failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}
	

	@Override
	public boolean saveVmFromVSphere(OCVM vm) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(vm);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Pre create vm failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}
	
	public boolean preCreateVM(String vmUuid, String vmPwd, Integer vmUid,
			String vmName, VMPlatform vmPlatform, String vmMac, Integer vmMem,
			Integer vmCpu, VMPower vmPower, VMStatus vmStatus,
			Date vmCreateDate, String tplUuid) {
		Session session = null;
		boolean result = false;
		try {
			OCVM vm = new OCVM(vmUuid, vmPwd, null, vmUid, vmName, vmPlatform, vmMac,
					vmMem, vmCpu, vmPower, vmStatus, vmCreateDate, tplUuid);
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(vm);
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

	public OCVM getVMByUuid(String vmUuid) {
		Session session = null;
		OCVM ocvm = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			ocvm = (OCVM) session.get(OCVM.class, vmUuid);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return ocvm;
	}

	public boolean deleteVMByUuid(int userId, String vmUuid) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "update OCVM set vmStatus = 0 where vmUuid = :vmUuid and vmUid = :userId";
			Query query = session.createQuery(str);
			query.setString("vmUuid", vmUuid);
			query.setInteger("userId", userId);
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

	public boolean updateVMPowerWhenCreateVM(int userId, String vmUuid,
			String pwd, VMPower vmPower, String hostUuid) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "update OCVM set vmPower = :vmPower, vmPwd = :pwd, vmHostUuid = :hostUuid where vmUuid = :vmUuid and vmUid = :userId";
			Query query = session.createQuery(str);
			query.setString("vmUuid", vmUuid);
			query.setParameter("vmPower", vmPower);
			query.setString("pwd", pwd);
			query.setString("hostUuid", hostUuid);
			query.setInteger("userId", userId);
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

	public boolean updateVMPower(String vmUuid, VMPower vmPower) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "update OCVM set vmPower = :vmPower where vmUuid = :vmUuid";
			Query query = session.createQuery(str);
			query.setParameter("vmPower", vmPower);
			query.setString("vmUuid", vmUuid);
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

	public boolean updateVMHost(String vmUuid, String vmHostUuid) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "update OCVM set vmHostUuid = :vmHostUuid where vmUuid = :vmUuid";
			Query query = session.createQuery(str);
			query.setParameter("vmHostUuid", vmHostUuid);
			query.setString("vmUuid", vmUuid);
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

	public boolean updateVMConfigure(String vmUuid, int vmMem, int vmCpu) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "update OCVM set vmMem = :vmMem, vmCpu = :vmCpu where vmUuid = :vmUuid";
			Query query = session.createQuery(str);
			query.setInteger("vmMem", vmMem);
			query.setInteger("vmCpu", vmCpu);
			query.setString("vmUuid", vmUuid);
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

	public int countByUserId(int userId, String search) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from OCVM where vmUid = :userId and vmName like :search and vmStatus = 1";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			query.setString("search", "%" + search + "%");
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
	public List<OCVM> getOnePageByUserId(int userId, int page, int limit,
			String search) {
		List<OCVM> vmList = new ArrayList<OCVM>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			StringBuilder queryString = new StringBuilder("from OCVM where vmStatus = 1 and vmUid= :userId and vmName like :search ");
			queryString.append("order by vmCreateDate desc");
			Query query = session.createQuery(queryString.toString());
			query.setInteger("userId", userId);
			query.setString("search", "%" + search + "%");
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
	
	@SuppressWarnings("unchecked")
	public List<OCVM> getOnePageByUserId(int userId, int page, int limit, String groupUuid,
			String search) {
		List<OCVM> vmList = new ArrayList<OCVM>();
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			StringBuilder queryString = new StringBuilder("from OCVM where vmStatus = 1 and vmUid= :userId and vmName like :search ");
			if (!groupUuid.equals("all")) {
				queryString.append(" and vmGroup = :groupUuid ");
			}
			queryString.append("order by vmCreateDate desc");
			Query query = session.createQuery(queryString.toString());
			query.setInteger("userId", userId);
			if (!groupUuid.equals("all")) {
				query.setString("groupUuid", groupUuid);
			}
			query.setString("search", "%" + search + "%");
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

	public boolean updateVMBackdate(String vmUuid, Date backdate) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update OCVM set vmBackUpDate = :date where vmUuid = :vmUuid";
			Query query = session.createQuery(queryString);
			query.setString("vmUuid", vmUuid);
			query.setTimestamp("date", backdate);
			query.executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		}
	}

	public boolean isActive(String vmUuid) {
		Session session = null;
		try {
			int count = 0;
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("select count(*) from OCVM where vmUuid = :vmUuid and vmStatus = 1");
			query.setString("vmUuid", vmUuid);
			count = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
			return false;
		}
	}

	@Override
	public int getUsedMemByHostUuid(String hostUuid) {
		int totalMem = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String str = "select sum(vmMem) as totalVmMem from OCVM where vmHostUuid = :hostUuid";
			Query query = session.createQuery(str);
			query.setString("hostUuid", hostUuid);
			totalMem = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return totalMem;
	}

	/**
	 * 获取设置了监控警告的主机列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OCVM> getVMsWithAlarm() {
		List<OCVM> list = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCVM where vmStatus = 1 and alarmUuid is not null order by vmCreateDate desc";
			Query query = session.createQuery(queryString);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return list;
	}
	
	/**
	 * 获取一页未设置监控警告的主机列表
	 * 
	 * @param page
	 * @param limit
	 * @param search
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OCVM> getOnePageVMsWithoutAlarm(int page, int limit,
			String search, int userId) {
		List<OCVM> vmList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from OCVM where vmUid = :userId and vmName like :search "
					+ "and vmStatus = 1 and alarmUuid is null order by vmCreateDate desc";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			query.setString("search", "%" + search + "%");
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

	/**
	 * 获取未设置监控警告的主机总数
	 * 
	 * @param search
	 * @param userId
	 * @return
	 */
	public int countVMsWithoutAlarm(String search, int userId) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from OCVM where vmUid = :userId and vmName like :search "
					+ "and vmStatus = 1 and alarmUuid is null";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
			query.setString("search", "%" + search + "%");
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
	
	/**
	 * 更新主机监控警告
	 * 
	 * @param vmUuid
	 * @param alarmUuid
	 */
	public boolean updateAlarm(String vmUuid, String alarmUuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update OCVM set alarmUuid = :alarmUuid where vmUuid = :vmUuid";
			Query query = session.createQuery(queryString);
			query.setString("alarmUuid", alarmUuid);
			query.setString("vmUuid", vmUuid);
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
	
	/**
	 * 获取对应监控警告的主机列表
	 * 
	 * @param vmUid
	 * @param alarmUuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OCVM> getVMsOfAlarm(int vmUid, String alarmUuid) {
		List<OCVM> vmList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCVM where vmUid = :vmUid and vmStatus = 1 and alarmUuid = :alarmUuid order by vmCreateDate desc";
			Query query = session.createQuery(queryString);
			query.setInteger("vmUid", vmUid);
			query.setString("alarmUuid", alarmUuid);
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
	
	/**
	 * 是否有主机具有该监控警告
	 * 
	 * @param alarmUuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isNotExistAlarm(String alarmUuid) {
		boolean result = true;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from OCVM where alarmUuid = :alarmUuid and vmStatus = 1");
			query.setString("alarmUuid", alarmUuid);
			List<OCVM> vmList = query.list();
			session.getTransaction().commit();
			if (vmList.size() > 0) {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	@Override
	public void updateName(String vmUuid, String vmName, String vmDesc) {
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update OCVM set vmName = :vmName, vmDesc = :vmDesc where vmUuid = :vmUuid";
			Query query = session.createQuery(queryString);
			query.setString("vmName", vmName);
			query.setString("vmUuid", vmUuid);
			query.setString("vmDesc", vmDesc);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
	}

	@Override
	public boolean updateGroup(String vmUuid, String groupUuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update OCVM set vmGroup = :groupUuid where vmUuid = :vmUuid";
			Query query = session.createQuery(queryString);
			query.setString("groupUuid", groupUuid);
			query.setString("vmUuid", vmUuid);
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
	
	@Override
	public boolean cleanVMGroup(String groupUuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update OCVM set vmGroup = null where vmGroup = :groupUuid";
			Query query = session.createQuery(queryString);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isNotExistGroup(String groupUuid) {
		boolean result = true;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from OCVM where vmGroup = :groupUuid and vmStatus = 1");
			query.setString("groupUuid", groupUuid);
			List<OCVM> vmList = query.list();
			session.getTransaction().commit();
			if (vmList.size() > 0) {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	@Override
	public int countVMsWithoutGroup(int userId) {
		int count = 0;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from OCVM where vmUid = :userId and vmStatus = 1 and vmGroup is null";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
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
	public List<OCVM> getOnePageVMsWithoutGroup(int page, int limit, int userId) {
		List<OCVM> vmList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from OCVM where vmUid = :userId and vmStatus = 1 and vmGroup is null order by vmCreateDate desc";
			Query query = session.createQuery(queryString);
			query.setInteger("userId", userId);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<OCVM> getVMsOfGroup(int page, int limit, int vmUid, String groupUuid) {
		List<OCVM> vmList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from OCVM where vmUid = :vmUid and vmStatus = 1 and vmGroup = :groupUuid order by vmCreateDate desc";
			Query query = session.createQuery(queryString);
			query.setInteger("vmUid", vmUid);
			query.setString("groupUuid", groupUuid);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<OCVM> getVMsOfGroup(int vmUid, String groupUuid) {
		List<OCVM> vmList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCVM where vmUid = :vmUid and vmStatus = 1 and vmGroup = :groupUuid order by vmCreateDate desc";
			Query query = session.createQuery(queryString);
			query.setInteger("vmUid", vmUid);
			query.setString("groupUuid", groupUuid);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<OCVM> getVmListByVlan(String netId) {
		List<OCVM> vmList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCVM where vmVlan = :netId order by vmCreateDate desc";
			Query query = session.createQuery(queryString);
			query.setString("netId", netId);
			vmList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count all vm list of netId failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vmList;
	}

}
