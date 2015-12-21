package com.beyondsphere.dao;

import com.beyondsphere.entity.OCVDI;

public interface VDIDAO {

	/**
	 * 获取指定模板空闲VDI总数
	 * 
	 * @param tplUuid
	 * @return
	 */
	public abstract int countFreeVDI(String tplUuid);

	/**
	 * 添加VDI
	 * 
	 * @param tplUuid
	 * @param vmUuid
	 * @return
	 */
	public abstract OCVDI saveVDI(String tplUuid, String vmUuid);

	/**
	 * 删除VDI
	 * 
	 * @param vdi
	 * @return
	 */
	public abstract boolean deleteVDI(OCVDI vdi);
	
	/**
	 * 获取指定模板空闲VDI
	 * 
	 * @param tplUuid
	 * @return
	 */
	public abstract OCVDI getFreeVDI(String tplUuid);


}