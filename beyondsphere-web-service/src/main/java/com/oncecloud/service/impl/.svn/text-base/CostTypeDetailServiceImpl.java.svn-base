package com.beyondsphere.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.CostTypeDetailDAO;
import com.beyondsphere.entity.CostTypeDetail;
import com.beyondsphere.message.MessageUtil;
import com.beyondsphere.service.CostTypeDetailService;
import com.beyondsphere.util.JsonUtils;

@Component("CostTypeDetailService")
public class CostTypeDetailServiceImpl implements CostTypeDetailService {

	@Resource
	private CostTypeDetailDAO costTypeDetailDAO;
	@Resource
	private MessageUtil message;

	@Override
	public boolean doAdd(int userId,CostTypeDetail costTypeDetail) {
		boolean result = false;
		result = costTypeDetailDAO.add(costTypeDetail);
		if(result){
			message.pushSuccess(userId, "成本细化分类添加成功");
		}else{
			message.pushError(userId, "成本细化分类添加失败");
		}
		return result;
	}

	@Override
	public boolean doRemove(int userId,String costTypeDetailId) {
		boolean result = false;
		result = costTypeDetailDAO.remove(costTypeDetailId);
		if(result){
			message.pushSuccess(userId, "成本细化分类删除成功");
		}else{
			message.pushError(userId, "成本细化分类删除失败");
		}
		return result;
	}

	@Override
	public boolean doUpdate(int userId,CostTypeDetail costTypeDetail) {

		boolean result = costTypeDetailDAO.edit(costTypeDetail);
		if (result) {
			message.pushSuccess(userId, "成本细化分类修改成功");
		} else {
			message.pushError(userId, "成本细化分类修改失败");
		}
		return result;
	}

	@Override
	public JSONArray getPagedList(int page, int limit, String search) {
		
		int totalNum = costTypeDetailDAO.countAllList(search);
		List<CostTypeDetail> costTypeDetailList = costTypeDetailDAO.getPagedList(page, limit, search);
		
		JSONArray ja=new JSONArray(JsonUtils.list2json(costTypeDetailList));
		ja.put(totalNum);
		
		return ja;
	}

	@Override
	public List<CostTypeDetail> getAllList() {
		List<CostTypeDetail> costTypeDetailList = costTypeDetailDAO.getAllList();
		return costTypeDetailList;
	}

	@Override
	public CostTypeDetail getOneById(String costTypeDetailId) {
		CostTypeDetail costTypeDetail = costTypeDetailDAO.getOneByID(costTypeDetailId);
		return costTypeDetail;
	}

	@Override
	public List<CostTypeDetail> getListByTypeId(String typeId) {
		List<CostTypeDetail> costTypeDetailList = costTypeDetailDAO.getListByTypeId(typeId);
		return costTypeDetailList;
	}

}
