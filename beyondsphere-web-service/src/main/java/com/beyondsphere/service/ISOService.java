package com.beyondsphere.service;

import org.json.JSONObject;

import com.beyondsphere.entity.ISO;
import com.beyondsphere.model.ISOModel;
import com.beyondsphere.model.PagedList;

public interface ISOService {
	
	/**
	 * 分页获取ISO列表
	 * @param page, limit, search
	 * @return
	 */
	public abstract PagedList<ISO> getISOList(int page, int limit, String search);
	
	/**
	 * 根据用户id获取ISO列表
	 * @param userId
	 * @return
	 */
	public abstract PagedList<ISOModel> getISOListAll(int userId);
	
	/**
	 * 增加iso
	 * @param isoName, isoFileSrc, userId, isoRemarks
	 * @return
	 */
	public abstract ISO addISO(String isoName, String isoFileSrc,Integer userId, String isoRemarks);
	
	/**
	 * 删除iso
	 * @param userId, isoUuid, isoName
	 * @return
	 */
	public abstract JSONObject deleteISO(int userId,String isoUuid,String isoName);
	
	/**
	 * 更新iso
	 * @param iso
	 * @return
	 */
	public abstract ISO updateISO(ISO iso);
	
	/**
	 * 获取iso
	 * @param isoUuid
	 * @return
	 */
	public abstract ISO getISO(String isoUuid);
}
