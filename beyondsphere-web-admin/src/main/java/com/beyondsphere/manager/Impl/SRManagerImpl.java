/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.manager.Impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.Xen.API.SR;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.beyondsphere.entity.OCHost;
import com.beyondsphere.entity.OCLog;
import com.beyondsphere.entity.Storage;
import com.beyondsphere.log.LogAction;
import com.beyondsphere.log.LogObject;
import com.beyondsphere.log.LogRecord;
import com.beyondsphere.log.LogRole;
import com.beyondsphere.message.MessageUtil;
import com.beyondsphere.manager.SRManager;
import com.beyondsphere.constants.LogConstant;
import com.beyondsphere.core.SRCore;
import com.beyondsphere.service.HostService;
import com.beyondsphere.service.SRService;
import com.beyondsphere.util.TimeUtils;

@Service("SRManager")
public class SRManagerImpl implements SRManager {
	
	@Resource
	private HostService hostService;
	@Resource
	private SRService srService;
	@Resource
	private SRCore srCore;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;
	
	private boolean addStorage2Server(String srUuid, String hostUuid) {
		boolean result = false;
		Storage storage = srService.getStorage(srUuid);
		OCHost targetHost = hostService.getHostById(hostUuid);
		if (srCore.checkStorage(targetHost, storage)) {
			if (srCore.activeStorage(targetHost, storage)) {
				if (srService.addStrToHost(hostUuid, srUuid) != null) {
					result = true;
				}
			}
		}
		return result;
	}

	public boolean checkSREquals(String masterHost, String targetHost) {
		return srService.checkSREquals(masterHost, targetHost);
	}

	public JSONArray addStorage(int userId, String srname, String srAddress,
			String srDesc, String srType, String srDir) {
		return srService.addStorage(userId, srname, srAddress, srDesc, srType, srDir);
	}

	public JSONArray getStorageList(int page, int limit, String search) {
		return srService.getStorageList(page, limit, search);
	}

	public JSONArray deleteStorage(int userId, String srId, String srName) {
		return srService.deleteStorage(userId, srId, srName);
	}

	public JSONArray load2Server(int userId, String srUuid, String hostUuid) {
		JSONArray ja = new JSONArray();
		Date startTime = new Date();
		boolean result = this.addStorage2Server(srUuid, hostUuid);
		JSONObject jo = new JSONObject();
		jo.put("result", result);
		Date endTime = new Date();
		JSONArray infoArray = new JSONArray();
		infoArray.put(TimeUtils.createLogInfo(
				LogRole.STORAGE,
				"sr-" + srUuid.substring(0, 8)));
		infoArray.put(TimeUtils.createLogInfo(
				LogConstant.logObject.Host.toString(),
				"host-" + hostUuid.substring(0, 8)));
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.STORAGE, 
					LogAction.ADD, infoArray.toString(), startTime, endTime);
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.STORAGE, 
					LogAction.ADD, infoArray.toString(), startTime, endTime);
			message.pushError(userId, log.toString());
		}
		return ja;
	}

	public JSONArray getStorageByAddress(String address) {
		return srService.getStorageByAddress(address);
	}

	public void updateStorage(int userId, String srId, String srName,
			String srDesc) {
		srService.updateStorage(userId, srId, srName, srDesc);
	}
	
	public JSONArray getRealSRList(String poolUuid) {
		JSONArray ja = new JSONArray();
		Map<SR, SR.Record> srMap = srCore.getRealStorage(poolUuid);
		for (SR sr : srMap.keySet()) {
			SR.Record record = srMap.get(sr);		
			String srType = record.type;
			if (srType.equals("mfs") || srType.equals("ocfs2") || srType.equals("gpfs") || srType.equals("ceph")) { 
				JSONObject jo = new JSONObject();
				jo.put("type", srType);
				jo.put("uuid", record.uuid);
				jo.put("name", record.nameLabel);
				jo.put("size", record.physicalSize);
				ja.put(jo);
			}
		}
		return ja;
	}
	
}
