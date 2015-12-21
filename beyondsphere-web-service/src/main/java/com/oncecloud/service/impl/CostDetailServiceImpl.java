package com.oncecloud.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.oncecloud.dao.CostDetailDAO;
import com.oncecloud.entity.CostDetail;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.service.CostDetailService;

@Component("CostDetailService")
public class CostDetailServiceImpl implements CostDetailService {

	@Resource
	private CostDetailDAO costDetailDAO;
	@Resource
	private MessageUtil message;

	@Override
	public boolean doAdd(int userId,CostDetail costDetail) {
		boolean result = false;
		result = costDetailDAO.add(costDetail);
		if(result){
			message.pushSuccess(userId, "成本信息创建成功");
		}else{
			message.pushError(userId, "成本信息创建失败");
		}
		return result;
	}

	@Override
	public boolean doRemove(int userId,String costDetailId) {
		boolean result = false;
		result = costDetailDAO.remove(costDetailId);
		if(result){
			message.pushSuccess(userId, "成本信息删除成功");
		}else{
			message.pushError(userId, "成本信息删除失败");
		}
		return result;
	}

	@Override
	public boolean doUpdate(int userId,CostDetail costDetail) {

		boolean result = costDetailDAO.edit(costDetail);
		if (result) {
			message.pushSuccess(userId, "成本信息修改成功");
		} else {
			message.pushError(userId, "成本信息修改失败");
		}
		
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getPagedList(int page, int limit, String search,String costTypeId) {
		
		Map map=new HashMap();
		
		int totalNum = costDetailDAO.countAllList(search);
		List<CostDetail> costDetailList = costDetailDAO.getPagedList(page, limit, search,costTypeId);
		map.put("totalNum", totalNum);
		map.put("detallist", costDetailList);
		return map;
	}

	@Override
	public List<CostDetail> getAllList() {
		List<CostDetail> costDetailList = costDetailDAO.getAllList();
		return costDetailList;
	}

	@Override
	public CostDetail getOneById(String costDetailId) {
		CostDetail costDetail = costDetailDAO.getOneByID(costDetailId);
		return costDetail;
	}

	@Override
	public List<CostDetail> getListByTypeDetailId(String typeDetailId) {
		List<CostDetail> costDetailList = costDetailDAO.getListByTypeDetailId(typeDetailId);
		return costDetailList;
	}

	@Override
	public List<CostDetail> getListByTypeId(String typeid) {
		List<CostDetail> costDetailList = costDetailDAO.getListByTypeId(typeid);
		return costDetailList;
	}

}
