package com.oncecloud.manager.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.manager.NetworkManager;
import com.oncecloud.manager.RouterManager;
import com.oncecloud.constants.LogConstant;
import com.oncecloud.constants.LogEnumConstant;
import com.oncecloud.dao.LogDAO;
import com.oncecloud.dao.NetworkDAO;
import com.oncecloud.dao.RouterDAO;
import com.oncecloud.entity.OCLog;
import com.oncecloud.entity.OCNetwork;
import com.oncecloud.message.MessagePush;
import com.oncecloud.service.NetworkService;
import com.oncecloud.service.PoolService;
import com.oncecloud.service.VMService;
import com.oncecloud.util.Utilities;

@Service("NetworkManager")
public class NetworkManagerImpl implements NetworkManager {
	
	@Resource
	private NetworkService networkService;
	@Resource
	private PoolService poolService;
	@Resource
	private VMService vmService;
	@Resource
	private NetworkDAO networkDAO;
	@Resource
	private LogDAO logDAO;
	@Resource
	private MessagePush messagePush;
	@Resource
	private RouterManager routerManager;
	@Resource
	private RouterDAO routerDAO;

	public JSONArray getOnePageListOfNetwork(int page, int limit, int vlanType) throws Exception {
		return networkService.getOnePageListOfNetwork(page, limit, vlanType);
	}

	public JSONArray getNetworkListByPoolUuid(int type)
			throws Exception {
		return networkService.getNetworkListByPoolUuid(type);
	}

	public JSONArray getSwitch(String netId) throws Exception {
		return networkService.getSwitch(netId);
	}
	
	public JSONArray getPool() throws Exception{
		return poolService.getAllPool();
	}

	public boolean createSwitch(OCNetwork network, int userId) throws Exception {
		return networkService.createSwitch(network, userId);
	}

	public boolean updateSwitch(OCNetwork network, int userId) throws Exception {
		return networkService.updateSwitch(network, userId);
	}

	public boolean destorySwitchByNetid(String netId, int userId) throws Exception {
		boolean result = false;
		//there is no vm in the switch, then can delete switch.
		if (vmService.countVlanNumberOfVM(netId) == 0) {
			result =  networkService.destorySwitchByNetid(netId, userId);
		}
		return result;
	}
	
	public List<OCNetwork> getOnePageListOfNetwork(int page, int limit) throws Exception {
		return networkService.getOnePageListOfNetwork(page, limit);
	}
	
	public OCNetwork getSwitchByTypeAndId(String vlanType, String vlanId)
			throws Exception {
		return networkService.getSwitchByTypeAndId(vlanType, vlanId);
	}

	public boolean isRouterHasVnets(String routerUuid, int userId) {
		int count = networkDAO.countVnetsOfRouter(routerUuid, userId);
		return count > 0;
	}
	
	public JSONObject linkRouter(int userId,String vnetUuid, String routerId,
			Integer net, Integer gate, Integer start, Integer end,
			Integer dhcpState, String content, String conid) {
//		int userId=route.getRouterUID();
//		int userId=1;
		Date startTime = new Date();
		JSONObject jo = routerManager.doLinkRouter(userId, vnetUuid,
				routerId, net, gate, start, end, dhcpState);
		// write log and push message
		Date endTime = new Date();
		int elapse = Utilities.timeElapse(startTime, endTime);
		JSONArray infoArray = new JSONArray();
		infoArray.put(Utilities.createLogInfo(
				LogEnumConstant.logObject.私有网络.toString(),
				"vn-" + vnetUuid));
		infoArray.put(Utilities.createLogInfo(
				LogConstant.logObject.Router.toString(),
				"rt-" + routerId.substring(0, 8)));
		if (jo.getBoolean("result")) {
			OCLog log = logDAO.insertLog(userId,
					LogConstant.logObject.Router.ordinal(),
					LogEnumConstant.logAction.连接.ordinal(),
					LogEnumConstant.logStatus.成功.ordinal(), infoArray.toString(),
					startTime, elapse);
			messagePush.pushMessageClose(userId, content, conid);
			messagePush.pushMessage(userId,
					Utilities.stickyToSuccess(log.toAString()));
		} else {
			OCLog log = logDAO.insertLog(userId,
					LogConstant.logObject.Router.ordinal(),
					LogEnumConstant.logAction.连接.ordinal(),
					LogEnumConstant.logStatus.失败.ordinal(), infoArray.toString(),
					startTime, elapse);
			messagePush.pushMessageClose(userId, content, conid);
			messagePush.pushMessage(userId,
					Utilities.stickyToError(log.toAString()));
		}
		return jo;
	}

	@Override
	public JSONArray getVlansOfNetwork() throws Exception {
				
		JSONArray ja = new JSONArray();
		List<OCNetwork> list = networkDAO.getVlansOfNetwork();
		for (OCNetwork net : list) {
			JSONObject jo = new JSONObject();
			jo.put("netid", net.getNetId());
			jo.put("vlanid", net.getvlanId());
			ja.put(jo);
		}
		return ja;
	}
	
	public JSONObject unlinkRouter(String vnetUuid, int userId) {
		Date startTime = new Date();
		JSONObject jo = routerManager.doUnlinkRouter(vnetUuid, userId);
		// write log and push message
		Date endTime = new Date();
		int elapse = Utilities.timeElapse(startTime, endTime);
		JSONArray infoArray = new JSONArray();
		infoArray.put(Utilities.createLogInfo(
				LogEnumConstant.logObject.私有网络.toString(),
				"vn-" + vnetUuid));
		// push message
		if (jo.getBoolean("result")) {
			OCLog log = logDAO.insertLog(userId,
					LogConstant.logObject.Router.ordinal(),
					LogEnumConstant.logAction.离开.ordinal(),
					LogEnumConstant.logStatus.成功.ordinal(), infoArray.toString(),
					startTime, elapse);
			messagePush.pushMessage(userId,
					Utilities.stickyToSuccess(log.toString()));
		} else {
			OCLog log = logDAO.insertLog(userId,
					LogConstant.logObject.Router.ordinal(),
					LogEnumConstant.logAction.离开.ordinal(),
					LogEnumConstant.logStatus.失败.ordinal(), infoArray.toString(),
					startTime, elapse);
			messagePush.pushMessage(userId,
					Utilities.stickyToError(log.toString()));
		}
		return jo;
	}


	@Override
	public JSONArray getVxlansByRouterid(String routerid) throws Exception {
		JSONArray ja = new JSONArray();
		List<OCNetwork> list = networkDAO.getVxlansByRouterid(routerid);
		for (OCNetwork net : list) {
			JSONObject jo = new JSONObject();
			jo.put("netid", net.getNetId());
			jo.put("vlanid", net.getvlanId());
			jo.put("net", net.getVlanNet());
			jo.put("gate", net.getVlanGate());
			ja.put(jo);
		}
		return ja;
	}

	@Override
	public JSONObject checkRouter(String routerid) {
		JSONObject obj = new JSONObject();
		List<OCNetwork> list=new ArrayList<OCNetwork>();
		try {
			list = networkDAO.getVxlansByRouterid(routerid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.size()>0){
			obj.put("result", false);
			String netnames="";
			for (OCNetwork net : list) {
				netnames+="Vxlan-"+net.getvlanId()+",";
			}
			netnames=netnames.substring(0,netnames.length()-1);
			obj.put("netnames", netnames);
		}else{
			obj.put("result", true);
		}
		return obj;
	}

	@Override
	public JSONObject clearRouter(String routerid) {
		JSONObject obj=new JSONObject();
		boolean flag=false;
		try {
			flag = networkDAO.clearRouter(routerid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		obj.put("result", flag);
		return obj;
	}
}
