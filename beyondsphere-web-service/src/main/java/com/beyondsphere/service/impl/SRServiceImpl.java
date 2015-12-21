package com.beyondsphere.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.HostSRDAO;
import com.beyondsphere.dao.StorageDAO;
import com.beyondsphere.entity.HostSR;
import com.beyondsphere.entity.OCLog;
import com.beyondsphere.entity.Storage;
import com.beyondsphere.log.LogAction;
import com.beyondsphere.log.LogObject;
import com.beyondsphere.log.LogRecord;
import com.beyondsphere.log.LogRole;
import com.beyondsphere.message.MessageUtil;
import com.beyondsphere.service.SRService;
import com.beyondsphere.util.TimeUtils;

@Component("SRService")
public class SRServiceImpl implements SRService {
	
	@Resource
	private HostSRDAO hostSRDAO;
	@Resource
	private StorageDAO storageDAO;
	@Resource
	private HostSRDAO hostSrDAO;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;
	
	public boolean checkSREquals(String masterHostUuid, String targetHostUuid) {
		Set<String> masterSR = hostSRDAO.getSRList(masterHostUuid);
		Set<String> hostSR = hostSRDAO.getSRList(targetHostUuid);
		if (masterSR.size() != hostSR.size()) {
			return false;
		} else {
			masterSR.retainAll(hostSR);
			if (masterSR.size() != hostSR.size()) {
				return false;
			} else {
				return true;
			}
		}
	}

	public JSONArray addStorage(int userId, String srname, String srAddress,
			String srDesc, String srType, String srDir) {
		JSONArray ja = new JSONArray();
		Date startTime = new Date();
		Storage result = storageDAO.createStorage(srname, srAddress,
				srDesc, srType, srDir);
		if (result != null) {
			JSONObject tObj = new JSONObject();
			tObj.put("srname", TimeUtils.encodeText(result.getSrName()));
			tObj.put("srid", result.getSrUuid());
			tObj.put("srAddress", result.getSrAddress());
			tObj.put("createDate", TimeUtils.formatTime(result.getCreateDate()));
			tObj.put("srType", result.getSrtype());
			tObj.put("srDir", result.getSrdir());
			tObj.put("srDesc", TimeUtils.encodeText(result.getSrDesc()));
			tObj.put("srsize", 0);
			ja.put(tObj);
		}
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.STORAGE, srname);
		if (result != null) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.STORAGE, 
					LogAction.CREATE, 
					infoArray.toString(), startTime, endTime);
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.STORAGE, 
					LogAction.CREATE, 
					infoArray.toString(), startTime, endTime);
			message.pushError(userId, log.toString());
		}
		return ja;
	}

	public JSONArray getStorageList(int page, int limit, String search) {
		JSONArray ja = new JSONArray();
		int totalNum = storageDAO.countAllStorageList(search);
		ja.put(totalNum);
		List<Storage> srList = storageDAO.getOnePageStorageList(page,
				limit, search);
		if (srList != null) {
			for (Storage sr : srList) {
				JSONObject tObj = new JSONObject();
				String srUuid = sr.getSrUuid();
				tObj.put("srname", TimeUtils.encodeText(sr.getSrName()));
				tObj.put("srid", srUuid);
				tObj.put("srAddress", sr.getSrAddress());
				tObj.put("createDate", TimeUtils.formatTime(sr.getCreateDate()));
				tObj.put("srType", sr.getSrtype());
				tObj.put("srDir", sr.getSrdir());
				tObj.put("srsize",
						storageDAO.countHostsOfStorage(srUuid));
				tObj.put("srDesc", TimeUtils.encodeText(sr.getSrDesc()));
				ja.put(tObj);
			}
		}
		return ja;
	}

	public JSONArray deleteStorage(int userId, String srId, String srName) {
		JSONArray ja = new JSONArray();
		Date startTime = new Date();
		boolean result = storageDAO.removeStorage(srId);
		JSONObject jo = new JSONObject();
		jo.put("result", result);
		ja.put(jo);
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.STORAGE, srName);
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.STORAGE, 
					LogAction.DELETE, 
					infoArray.toString(), startTime, endTime);
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.STORAGE, 
					LogAction.DELETE, 
					infoArray.toString(), startTime, endTime);
			message.pushError(userId, log.toString());
		}
		return ja;
	}

	public JSONArray getStorageByAddress(String address) {
		JSONArray ja = new JSONArray();
		Storage sr = storageDAO.getStorageOfAddress(address);
		JSONObject jo = new JSONObject();
		if (sr != null) {
			jo.put("exist", true);
		} else {
			jo.put("exist", false);
		}
		ja.put(jo);
		return ja;
	}

	public void updateStorage(int userId, String srId, String srName,
			String srDesc) {
		boolean result = storageDAO.updateSR(srId, srName, srDesc);
		if (result) {
			message.pushSuccess(userId, "存储更新成功");
		} else {
			message.pushError(userId, "存储更新失败");
		}
	}

	public Storage getStorage(String srId) {
		return storageDAO.getStorage(srId);
	}

	public HostSR addStrToHost(String hostUuid, String srUuid) {
		return hostSRDAO.createHostSR(hostUuid,srUuid);
	}

}
