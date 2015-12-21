package com.oncecloud.service;

import java.util.List;
import java.util.Map;

import com.oncecloud.entity.CostDetail;

public interface CostDetailService {

	public abstract boolean doAdd(int userId,CostDetail costDetail);

	public abstract boolean doRemove(int userId,String costDetailId);

	public abstract boolean doUpdate(int userId,CostDetail costDetail);

	@SuppressWarnings("rawtypes")
	public abstract Map getPagedList(int page, int limit, String search,String costTypeId);
	
	public abstract List<CostDetail> getAllList();
	
	public abstract CostDetail getOneById(String costDetailId);
	
	public abstract List<CostDetail> getListByTypeDetailId(String typeDetailId);
	
	public abstract List<CostDetail> getListByTypeId(String typeid);
}

