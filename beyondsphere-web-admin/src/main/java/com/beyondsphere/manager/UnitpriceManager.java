package com.beyondsphere.manager;

import java.util.List;

import org.json.JSONArray;

import com.beyondsphere.entity.OCUnitprice;

public interface UnitpriceManager {
	
	/**
	 * @author lining
	 * 获取价格信息
	 * 
	 */
	public abstract JSONArray getPriceList();
	
	/**
	 * @author lining
	 * 更新价格信息
	 * 
	 */
	public abstract boolean updatePrice(List<OCUnitprice> priceList);
}
