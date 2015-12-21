package com.oncecloud.service;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.oncecloud.entity.OCUnitprice;

public interface UnitpriceService {
	
	/**
	 * 获取价格信息
	 * @author lining
	 * @return
	 */
	public abstract JSONArray getPriceList();
	
	/**
	 * 更新价格信息
	 * @author lining
	 * @param priceList
	 * @return
	 */
	public abstract boolean updatePrice(List<OCUnitprice> priceList);
	
	/**
	 * 获取不同资源的单价
	 * @author lining
	 * @param
	 * @return
	 */
	public abstract Map<String, Double> getUnitpriceOfType();
}
