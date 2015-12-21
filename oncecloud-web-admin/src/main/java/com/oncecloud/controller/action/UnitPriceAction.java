package com.oncecloud.controller.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.manager.UnitpriceManager;
import com.oncecloud.entity.OCUnitprice;

@RequestMapping("unitPriceAction")
@Controller
public class UnitPriceAction {
	
	@Resource
	private UnitpriceManager unitprinceManager;
	
	@RequestMapping(value = "/priceList", method = { RequestMethod.GET })
	@ResponseBody
	public String priceList(HttpServletRequest request){
		return unitprinceManager.getPriceList().toString();
	}
	
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public void updatePrince(HttpServletRequest request,@RequestParam String cpuType,@RequestParam double cpuPrice,
			@RequestParam String memType,@RequestParam double memPrice,
			@RequestParam String volumeType,@RequestParam double volumePrice,
			@RequestParam String snapshotType,@RequestParam double snapshotPrice){
		
		List<OCUnitprice> priceList = new ArrayList<OCUnitprice>();
		priceList.add(new OCUnitprice(cpuType, cpuPrice));
		priceList.add(new OCUnitprice(memType, memPrice));
		priceList.add(new OCUnitprice(volumeType, volumePrice));
		priceList.add(new OCUnitprice(snapshotType, snapshotPrice));
		unitprinceManager.updatePrice(priceList);
	}
	
}
