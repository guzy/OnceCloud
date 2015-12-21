package com.oncecloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.CostTypeDAO;
import com.oncecloud.entity.CostType;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.service.CostTypeService;
import com.oncecloud.util.JsonUtils;

@Component("CostTypeService")
public class CostTypeServiceImpl implements CostTypeService {

	@Resource
	private CostTypeDAO costTypeDAO;
	@Resource
	private MessageUtil message;

	@Override
	public boolean doAdd(int userId,CostType costType) {
		boolean result = false;
		result = costTypeDAO.add(costType);
		if(result){
			message.pushSuccess(userId, "成本分类添加成功");
		}else{
			message.pushError(userId, "成本分类添加失败");
		}
		return result;
	}

	@Override
	public boolean doRemove(int userId,String costTypeId) {
		boolean result = false;
		result = costTypeDAO.remove(costTypeId);
		if(result){
			message.pushSuccess(userId, "成本分类删除成功");
		}else{
			message.pushError(userId, "成本分类删除失败");
		}
		return result;
	}

	@Override
	public boolean doUpdate(int userId,CostType costType) {

		boolean result = costTypeDAO.edit(costType);
		if (result) {
			message.pushSuccess(userId, "成本分类修改成功");
		} else {
			message.pushError(userId, "成本分类修改失败");
		}
		return result;
	}

	@Override
	public JSONArray getPagedList(int page, int limit, String search) {
		int totalNum = costTypeDAO.countAllList(search);
		List<CostType> costTypeList = costTypeDAO.getPagedList(page, limit, search);
		JSONArray ja=new JSONArray(JsonUtils.list2json(costTypeList));
		ja.put(totalNum);
		return ja;
	}

	@Override
	public List<CostType> getAllList() {
		List<CostType> costTypeList = costTypeDAO.getAllList();
		return costTypeList;
	}

	@Override
	public CostType getOneById(String costTypeId) {
		CostType costType = costTypeDAO.getOneByID(costTypeId);
		return costType;
	}

}
