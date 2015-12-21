package com.beyondsphere.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.NetworkDAO;
import com.beyondsphere.entity.OCLog;
import com.beyondsphere.entity.OCNetwork;
import com.beyondsphere.log.LogAction;
import com.beyondsphere.log.LogObject;
import com.beyondsphere.log.LogRecord;
import com.beyondsphere.log.LogRole;
import com.beyondsphere.message.MessageUtil;
import com.beyondsphere.service.NetworkService;
import com.beyondsphere.util.TimeUtils;

@Component("NetworkService")
public class NetworkServiceImpl implements NetworkService {
	
	@Resource
	private NetworkDAO networkDAO;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;
	
	public JSONArray getOnePageListOfNetwork(int page, int limit, int vlanType)
			throws Exception {
		JSONArray ja = new JSONArray();
		int totalNum = countAllNetworkList(vlanType);
		ja.put(totalNum);
		List<OCNetwork> networkList = networkDAO.getOnePageListOfNetwork(page, limit);
		if (networkList != null) {
			for (OCNetwork network:networkList) {
				if (vlanType == network.getvlanType()) {
					ja.put(objectToJSONObject(network));
				}
			}
		}
		return ja;
	}

	public JSONArray getNetworkListByPoolUuid(int type) throws Exception {
		JSONArray ja = new JSONArray();
		List<OCNetwork> networkList = networkDAO.getNetworkListByPoolUuid(type);
		if (networkList != null) {
			for (OCNetwork network:networkList) {
				ja.put(objectToJSONObject(network));
			}
		}
		return ja;
	}

	public JSONArray getSwitch(String netId) throws Exception {
		JSONArray ja = new JSONArray();
		OCNetwork network = networkDAO.getSwitch(netId);
		if (network != null) {
			ja.put(objectToJSONObject(network));
		}
		return ja;
	}
	
	public OCNetwork getVnetwork(String netId) {
		OCNetwork  network = new OCNetwork();
		try {
			network =  networkDAO.getSwitch(netId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return network;
	}

	public OCNetwork getSwitchByTypeAndId(String vlanType, String vlanId)
			throws Exception {
		return networkDAO.getSwitchByTypeAndId(vlanType, vlanId);
	}

	public List<OCNetwork> getOnePageListOfNetwork(int page, int limit)
			throws Exception {
		return networkDAO.getOnePageListOfNetwork(page, limit);
	}

	public boolean createSwitch(OCNetwork network, int userId) throws Exception {
		boolean result = false;
		Date startTime = new Date();
		result =  networkDAO.insertSwitch(network);
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.NETWORK, network.getNetId().toString());
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.NETWORK,
					LogAction.CREATE, 
					infoArray.toString(), startTime, endTime);
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.NETWORK,
					LogAction.CREATE, 
					infoArray.toString(), startTime, endTime); 
			message.pushError(userId,TimeUtils.stickyToError(log.toString()));
		}
		return result;
	}

	public boolean updateSwitch(OCNetwork network, int userId) throws Exception {
		boolean result = false;
		Date startTime = new Date();
		result = networkDAO.updateSwitch(network);
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.NETWORK, network.getNetId().toString());
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.NETWORK,
					LogAction.UPDATE, 
					infoArray.toString(), startTime, endTime);
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.NETWORK,
					LogAction.UPDATE, 
					infoArray.toString(), startTime, endTime); 
			message.pushError(userId,TimeUtils.stickyToError(log.toString()));
		}
		return result;
	}

	public boolean destorySwitchByNetid(String netId, int userId)
			throws Exception {
		boolean result = false;
		Date startTime = new Date();
		result =  networkDAO.deleteSwitchByNetid(netId);
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.NETWORK, netId);
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.NETWORK,
					LogAction.DESTORY, 
					infoArray.toString(), startTime, endTime);
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.NETWORK,
					LogAction.DESTORY, 
					infoArray.toString(), startTime, endTime); 
			message.pushError(userId,TimeUtils.stickyToError(log.toString()));
		}
		return result;
	}
	
	private JSONObject objectToJSONObject(OCNetwork network){
		JSONObject jo = new JSONObject();
		jo.put("netId", network.getNetId());
		jo.put("switchType", network.getvlanType());
		jo.put("switchId", network.getvlanId());
		jo.put("switchStatus", network.getvlanStatus());
		jo.put("vlanRouter", network.getVlanRouter());
		jo.put("createTime", TimeUtils.formatTime(network.getCreateTime()));
		return jo;
	}
	
	private int countAllNetworkList(int switchType) throws Exception {
		return networkDAO.countNetworkListByType(switchType);
	}
	
	@Override
	public JSONObject getSwitchObj(String netId) {
		JSONObject jo = new JSONObject();
		OCNetwork network = networkDAO.getSwitch(netId);
		if (network != null) {
			jo.put("netid",network.getNetId());
			jo.put("vlantype", network.getvlanType());
			jo.put("vlanid", network.getvlanId());
		}
		return jo;
	}

}
