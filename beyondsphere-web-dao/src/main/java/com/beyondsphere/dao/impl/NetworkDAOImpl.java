package com.beyondsphere.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.dao.NetworkDAO;
import com.beyondsphere.entity.OCNetwork;
import com.beyondsphere.helper.SessionHelper;

@Component("networkDAO")
public class NetworkDAOImpl implements NetworkDAO {

	@Resource
	private SessionHelper sessionHelper;
	
	@SuppressWarnings("unchecked")
	public List<OCNetwork> getOnePageListOfNetwork(int page, int limit) throws Exception {
		List<OCNetwork> networkList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			int startPos = (page - 1) * limit;
			String queryString = "from OCNetwork order by createTime desc";
			Query query = session.createQuery(queryString);
			query.setFirstResult(startPos);
			query.setMaxResults(limit);
			networkList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get vlan or vxlan list failed by page.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return networkList;
	}

	@SuppressWarnings("unchecked")
	public List<OCNetwork> getNetworkListByPoolUuid(int type)
			throws Exception {

		List<OCNetwork> networkList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCNetwork where vlanType = :type order by createTime desc";
			Query query = session.createQuery(queryString);
			query.setInteger("type", type);
			networkList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get vlan or vxlan list failed by poolUuid.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return networkList;
	}

	public OCNetwork getSwitch(String netId){
		OCNetwork network = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String builder = "from OCNetwork where netId = :netId";
			Query query = session.createQuery(builder);
			query.setString("netId", netId);
			network = (OCNetwork) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get vlan or vxlan failed by id.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return network;
	}
	
	public OCNetwork getSwitchByTypeAndId(String vlanType, String vlanId)
			throws Exception {
		OCNetwork network = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String builder = "from OCNetwork where vlanType = :vlanType and vlanId = :vlanId";
			Query query = session.createQuery(builder);
			query.setString("vlanType", vlanType);
			query.setString("vlanId", vlanId);
			network = (OCNetwork) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get vlan or vxlan failed by id and type.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return network;
	}
	
	public int countNetworkListByType(int vlanType) throws Exception {
		Session session = null;
		int result = 0;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder(
					"select count(*) from OCNetwork where ");
			builder.append("vlanType = :vlanType");
			Query query = session.createQuery(builder.toString());
			query.setInteger("vlanType", vlanType);
			result = ((Number) query.uniqueResult()).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Count vlan or vxlan numbers failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public boolean insertSwitch(OCNetwork network) throws Exception {

		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			session.save(network);
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Insert vlan or vxlan failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public boolean updateSwitch(OCNetwork network) throws Exception {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder("update OCNetwork set vlanType = :vlanType, vlanId = :vlanId, "
					+ "vlanStatus = :vlanStatus");
			builder.append(" where netId = :netId");
			Query query = session.createQuery(builder.toString());
			query.setInteger("vlanType", network.getvlanType());
			query.setInteger("vlanId", network.getvlanId());
			query.setInteger("vlanStatus", network.getvlanStatus());
			query.setInteger("netId", network.getNetId());
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Update vlan or vxlan failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}
	
	
	public boolean updateVxlan(OCNetwork network) throws Exception {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder("update OCNetwork set vlanNet = :vlanNet, vlanGate = :vlanGate, "
					+ "dhcpStatus = :dhcpStatus,vlanRouter=:vlanRouter ,vlanUid=:vlanUid,vifUuid=:vifUuid,vifMac=:vifMac");
			builder.append(" where netId = :netId");
			Query query = session.createQuery(builder.toString());
			query.setInteger("netId", network.getNetId());
			query.setString("vlanNet", network.getVlanNet());
			query.setString("vlanGate", network.getVlanGate());
			query.setInteger("dhcpStatus", network.getDhcpStatus());
			query.setString("vlanRouter", network.getVlanRouter());
			query.setInteger("vlanUid", network.getVlanUid());
			query.setString("vifUuid", network.getVifUuid());
			query.setString("vifMac", network.getVifMac());
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Update vlan or vxlan failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return result;
	}

	public boolean deleteSwitchByNetid(String netId) throws Exception {
		Session session = null;
		boolean result = false;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String string = "delete from OCNetwork where netId = :netId";
			Query query = session.createQuery(string);
			query.setString("netId", netId);
			query.executeUpdate();
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			loginfo("Delete vlan or vxlan failed.");
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
	
	/**
	 * 获取路由器私有网络列表
	 * 
	 * @param routerUuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OCNetwork> getVnetsOfRouter(String routerUuid) {
		List<OCNetwork> vxnetsList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCNetwork where vlanRouter = :routerid";
			Query query = session.createQuery(queryString);
			query.setString("routerid", routerUuid);
			vxnetsList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return vxnetsList;
	}
	
	/**
	 * 获取路由器私有网络数目
	 * 
	 * @param routerUuid
	 * @param userId
	 * @return
	 */
	public int countVnetsOfRouter(String routerUuid, int userId) {
		Session session = null;
		int count = -1;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "select count(*) from OCNetwork where vlanRouter =:routerid and vlanUid =:vnetUID";
			Query query = session.createQuery(queryString);
			query.setString("routerid", routerUuid);
			query.setInteger("vnetUID", userId);
			count = Integer.parseInt(query.uniqueResult().toString());
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
	 * 私有网络连接路由器
	 * 
	 * @param vnetuuid
	 * @param routerUuid
	 * @param net
	 * @param gate
	 * @param start
	 * @param end
	 * @param dhcpStatus
	 * @param mac
	 * @return
	 */
	public boolean linkToRouter(String vnetuuid, String routerUuid,
			Integer net, Integer gate, Integer start, Integer end,
			Integer dhcpStatus, String vifUuid, String vifMac) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "update OCNetwork set vlanNet = :net, vlanGate = :gate, "
					+ "vlanStart = :start, vlanEnd = :end, dhcpStatus = :status, "
					+ "vlanRouter = :routerid where netId = :uuid";
			Query query = session.createQuery(queryString);
			query.setInteger("net", net);
			query.setInteger("gate", gate);
			query.setInteger("start", start);
			query.setInteger("end", end);
			query.setInteger("status", dhcpStatus);
			query.setString("uuid", vnetuuid);
			query.setString("routerid", routerUuid);
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
	public List<OCNetwork> getVlansOfNetwork() throws Exception {
		List<OCNetwork> networkList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCNetwork where vlanType = 1";
			Query query = session.createQuery(queryString);
			networkList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get vlan or vlan list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return networkList;
	}
	
	/**
	 * 私有网络离开路由器
	 * 
	 * @param vnetuuid
	 * @return
	 */
	public boolean unlinkRouter(String vnetuuid) {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder("update OCNetwork set vlanNet = null, vlanGate = null, "
					+ "dhcpStatus = null,vlanRouter=null ,vlanUid=null,vifUuid=null,vifMac=null");
			builder.append(" where netId = :netId");
			Query query = session.createQuery(builder.toString());
			query.setString("netId", vnetuuid);
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
	public List<OCNetwork> getVxlansByRouterid(String routerid)
			throws Exception {
		List<OCNetwork> networkList = null;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			String queryString = "from OCNetwork where vlanType = 2 and vlanRouter=:routerid ";
			Query query = session.createQuery(queryString);
			query.setString("routerid", routerid);
			networkList = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			loginfo("Get vlan or vlan list failed.");
			e.printStackTrace();
			if (session.isOpen()) {
				session.getTransaction().rollback();
			}
		}
		return networkList;
	}

	@Override
	public boolean clearRouter(String routerid) throws Exception {
		boolean result = false;
		Session session = null;
		try {
			session = sessionHelper.getMainSession();
			session.beginTransaction();
			StringBuilder builder = new StringBuilder("update OCNetwork set vlanNet = null, vlanGate = null, "
					+ "dhcpStatus = null,vlanRouter=null ,vlanUid=null,vifUuid=null,vifMac=null");
			builder.append(" where vlanType=2 and vlanRouter=:routerid");
			Query query = session.createQuery(builder.toString());
			query.setString("routerid", routerid);
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

	

