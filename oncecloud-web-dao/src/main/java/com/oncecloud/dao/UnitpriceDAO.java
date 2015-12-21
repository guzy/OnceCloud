package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.entity.OCUnitprice;

public interface UnitpriceDAO {
	/**
	 * @author lining
	 * 获取价格信息
	 * 
	 */
	public abstract List<OCUnitprice> getOcUnitpriceList();
	
	/**
	 * @author lining
	 * 根据类型修改价格信息
	 * 
	 */
	public abstract boolean updateUnitprice(String priceType, double priceValue);
	
}
