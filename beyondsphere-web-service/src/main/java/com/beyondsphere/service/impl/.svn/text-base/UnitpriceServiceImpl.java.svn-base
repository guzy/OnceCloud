package com.beyondsphere.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.UnitpriceDAO;
import com.beyondsphere.entity.OCUnitprice;
import com.beyondsphere.service.UnitpriceService;

@Component("UnitpriceService")
public class UnitpriceServiceImpl implements UnitpriceService {
	
	@Resource
	private UnitpriceDAO unitpriceDAO;

	public JSONArray getPriceList() {
		JSONArray ja = new JSONArray();
		List<OCUnitprice> princeList = unitpriceDAO.getOcUnitpriceList();
		for (OCUnitprice prince : princeList) {
			JSONObject jo = new JSONObject();
			jo.put("pricetype", prince.getPriceType());
			jo.put("pricevalue", prince.getPriceValue());
			ja.put(jo);
		}
		return ja;
	}

	public boolean updatePrice(List<OCUnitprice> priceList) {
		boolean result = false;
		for (OCUnitprice prince : priceList) {
			result = unitpriceDAO.updateUnitprice(prince.getPriceType(), prince.getPriceValue());
		}
		return result;
	}
	
	@Override
	public Map<String, Double> getUnitpriceOfType() {
		List<OCUnitprice> unitprices = unitpriceDAO.getOcUnitpriceList();
		Map<String, Double> priceMap = new HashMap<String, Double>();
		for(OCUnitprice unitprice : unitprices){
			priceMap.put(unitprice.getPriceType(), unitprice.getPriceValue());
		}
		return priceMap;
	}

}
