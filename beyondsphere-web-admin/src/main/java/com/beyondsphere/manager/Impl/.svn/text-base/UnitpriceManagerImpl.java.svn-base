package com.beyondsphere.manager.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import com.beyondsphere.entity.OCUnitprice;
import com.beyondsphere.manager.UnitpriceManager;
import com.beyondsphere.service.UnitpriceService;

@Service("UnitpriceManager")
public class UnitpriceManagerImpl implements UnitpriceManager {

	@Resource
	private UnitpriceService unitpriceService;
	
	public JSONArray getPriceList() {
		return unitpriceService.getPriceList();
	}

	public boolean updatePrice(List<OCUnitprice> priceList) {
		return unitpriceService.updatePrice(priceList);
	}

}
