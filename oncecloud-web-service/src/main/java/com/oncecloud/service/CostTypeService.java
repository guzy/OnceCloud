package com.oncecloud.service;

import java.util.List;

import org.json.JSONArray;

import com.oncecloud.entity.CostType;

public interface CostTypeService {

	public abstract boolean doAdd(int userId,CostType costType);

	public abstract boolean doRemove(int userId,String costTypeId);

	public abstract boolean doUpdate(int userId,CostType costType);

	public abstract JSONArray getPagedList(int page, int limit, String search);
	
	public abstract List<CostType> getAllList();
	
	public abstract CostType getOneById(String costTypeId);
	
}

