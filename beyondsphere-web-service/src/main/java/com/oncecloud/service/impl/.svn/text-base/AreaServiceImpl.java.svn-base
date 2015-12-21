package com.beyondsphere.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.AreaDAO;
import com.beyondsphere.dao.LogDAO;
import com.beyondsphere.dao.PoolDAO;
import com.beyondsphere.dao.RoleDAO;
import com.beyondsphere.dao.UserDAO;
import com.beyondsphere.dao.VMDAO;
import com.beyondsphere.entity.Area;
import com.beyondsphere.helper.HashHelper;
import com.beyondsphere.log.LogRecord;
import com.beyondsphere.message.MessageUtil;
import com.beyondsphere.service.AreaService;
import com.beyondsphere.util.TimeUtils;

@Component("AreaService")
public class AreaServiceImpl implements AreaService {

	@Resource
	private AreaDAO areaDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private LogDAO logDAO;
	@Resource
	private PoolDAO poolDAO;
	@Resource
	private RoleDAO roleDAO;
	@Resource
	private MessageUtil message;
	@Resource
	private HashHelper hashHelper;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private LogRecord logRecord;

	@Override
	public boolean doCreateArea(Integer userId,String areaId, String areaName,
			String areaDomain, String areaDesc) {
		boolean result = false;
		result = areaDAO.insertArea(areaId, areaName, areaDomain, areaDesc,new Date());
		if(result){
			message.pushSuccess(userId, "区域创建成功");
		}else{
			message.pushError(userId, "区域创建失败");
		}
		return result;
	}

	@Override
	public boolean doDeleteArea(Integer userId,String areaId) {
		boolean result = false;
		result = areaDAO.deleteArea(areaId);
		if(result){
			message.pushSuccess(userId, "区域删除成功");
		}else{
			message.pushError(userId, "区域删除失败");
		}
		return result;
	}

	@Override
	public void doUpdateArea(Integer userId,String areaId, String areaName, String areaDomain,
			String areaDesc) {

		boolean result = areaDAO.updateArea(areaId, areaName, areaDomain,
				areaDesc);

		if (result) {
			message.pushSuccess(userId, "区域信息修改成功");
		} else {
			message.pushError(userId, "区域信息修改失败");
		}
	}

	@Override
	public JSONArray getAreaList(int page, int limit, String search) {
		JSONArray ja = new JSONArray();
		int totalNum = areaDAO.countAllAreaList(search);
		ja.put(totalNum);
		List<Area> areaList = areaDAO.getOnePageAreaList(page, limit, search);
		if (areaList != null) {
			for (Area myarea : areaList) {
				JSONObject jo = new JSONObject();
				jo.put("areaId", myarea.getAreaId());
				jo.put("areaName", myarea.getAreaName());
				jo.put("areaDomain", myarea.getAreaDomain());
				jo.put("areaDesc", myarea.getAreaDesc());
				jo.put("areaCreateTime",
						TimeUtils.formatTime(myarea.getAreaCreateTime()));

				ja.put(jo);
			}
		}
		return ja;
	}

	@Override
	public JSONArray getAreaAllList() {
		JSONArray ja = new JSONArray();
		//int totalNum = areaDAO.countAllAreaList(search);
		//ja.put(totalNum);
		List<Area> areaList = areaDAO.getAreaAllList();
		if (areaList != null) {
			for (Area myarea : areaList) {
				JSONObject jo = new JSONObject();
				jo.put("areaId", myarea.getAreaId());
				jo.put("areaName", myarea.getAreaName());
				jo.put("areaDomain", myarea.getAreaDomain());
				jo.put("areaDesc", myarea.getAreaDesc());
				jo.put("areaCreateTime",
						TimeUtils.formatTime(myarea.getAreaCreateTime()));

				ja.put(jo);
			}
		}
		return ja;
	}

	@Override
	public Area getArea(String areaId) {
		Area area = areaDAO.getArea(areaId);
		return area;
	}

	@Override
	public JSONArray queryArea(String areaDomain) {
		
		JSONArray ja = new JSONArray();
		Area areaResult = areaDAO.queryArea(areaDomain);
		JSONObject jo = new JSONObject();
		if (areaResult != null) {
			jo.put("exist", true);
		} else {
			jo.put("exist", false);
		}
		ja.put(jo);
		return ja;
	}

}
