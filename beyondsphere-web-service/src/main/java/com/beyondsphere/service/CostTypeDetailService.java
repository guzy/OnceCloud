package com.beyondsphere.service;

import java.util.List;

import org.json.JSONArray;

import com.beyondsphere.entity.CostTypeDetail;

public interface CostTypeDetailService {

	public abstract boolean doAdd(int userId,CostTypeDetail costTypeDetail);

	public abstract boolean doRemove(int userId,String costTypeDetailId);

	public abstract boolean doUpdate(int userId,CostTypeDetail costType);

	public abstract JSONArray getPagedList(int page, int limit, String search);
	
	public abstract List<CostTypeDetail> getAllList();
	
	public abstract CostTypeDetail getOneById(String costTypeDetailId);
	
	public abstract List<CostTypeDetail> getListByTypeId(String typeId);
}

