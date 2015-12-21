package com.beyondsphere.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.ISODAO;
import com.beyondsphere.entity.ISO;
import com.beyondsphere.entity.OCLog;
import com.beyondsphere.log.LogAction;
import com.beyondsphere.log.LogObject;
import com.beyondsphere.log.LogRecord;
import com.beyondsphere.log.LogRole;
import com.beyondsphere.message.MessageUtil;
import com.beyondsphere.model.ISOModel;
import com.beyondsphere.model.PagedList;
import com.beyondsphere.service.ISOService;
import com.beyondsphere.util.TimeUtils;

@Component("ISOService")
public class ISOServiceImpl implements ISOService {
	
	@Resource
	private ISODAO isoDAO;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;
	
	public PagedList<ISO> getISOList(int page, int limit, String search) {
		PagedList<ISO> list = null;
		int totalCount = isoDAO.countAllISOList(search);
		list = new PagedList<ISO>(page, limit, search, totalCount);
		if (totalCount > 0) {
			list.setList((isoDAO.getPagedISOList(page, limit, search)));
		}
		return list;
	}

	public PagedList<ISOModel> getISOListAll(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public ISO addISO(String isoName, String isoFileSrc, Integer userId,
			String isoRemarks) {
		Date startTime = new Date();
		ISO result = isoRegister(isoName, isoFileSrc, userId,isoRemarks);
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.ISO, isoName); 
		if (result != null) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.ISO, 
					LogAction.UPLOAD,
					infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userId,
					TimeUtils.stickyToSuccess(log.toString()));
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.ISO, 
					LogAction.UPLOAD,
					infoArray.toString(), startTime, endTime); 
			message.pushError(userId, log.toString());
		}
		return result;
	}

	public JSONObject deleteISO(int userId, String isoUuid, String isoName) {
		JSONObject jo = new JSONObject();
		boolean result =false;
		Date startTime = new Date();
		result= isoDAO.removeISO(isoUuid);
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.ISO, isoName); 
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.ISO, 
					LogAction.DELETE,
					infoArray.toString(), startTime, endTime);  
			message.pushSuccess(userId,log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.ISO, 
					LogAction.DELETE,
					infoArray.toString(), startTime, endTime); 
			message.pushError(userId, log.toString());
		}
		jo.put("result", result);
		return jo;
	}

	public ISO updateISO(ISO iso) {
		// TODO Auto-generated method stub
		return null;
	}

	public ISO getISO(String isoUuid) {
		return isoDAO.getISOByID(isoUuid);
	}
	
	private ISO isoRegister(String isoName, String isoFileSrc, Integer userId,
			String isoRemarks) {
		try {
			if (isoName == null || isoName.equals("")) {
				return null;
			}
			ISO iso= new ISO(UUID.randomUUID().toString(),isoName,isoFileSrc,new Date(),userId,isoRemarks);
			if (isoDAO.addISO(iso)) {
				return null;
			}
			return iso;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
