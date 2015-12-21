package com.oncecloud.usercore;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.VolumeDAO;
import com.oncecloud.entity.OCVM;
import com.oncecloud.entity.Volume;
import com.oncecloud.service.StatisticsService;
import com.oncecloud.service.UnitpriceService;
import com.oncecloud.service.VMService;

/**
 * @author hty
 * 
 */
@Component("StatisticsUserCore")
public class StatisticsCore {

	@Resource
	private VMService vmService;
	@Resource
	private VolumeDAO volumeDAO;
	@Resource
	private StatisticsService statisticsService;
	@Resource
	private UnitpriceService unitpriceService;

	public JSONObject addStatistics(JSONObject jo) {
		try {
			if (jo.getBoolean("result")) {
				String uuid = jo.getString("uuid");
				String name = jo.getString("name");
				int userId = jo.getInt("userId");
				int type = jo.getInt("statistics");
				double totalPrice = 0;
				Map<String, Double> priceMap = unitpriceService.getUnitpriceOfType();
				switch (type) {
				case 0:
					OCVM vm = vmService.getOCVM(uuid);
					totalPrice = vm.getVmCpu()* priceMap.get("cpu") + vm.getVmMem()/1024 * priceMap.get("mem");
					break;
				case 1:
					Volume volume = volumeDAO.getVolumeByUuid(uuid);
					totalPrice = volume.getVolumeSize()/10 * priceMap.get("volume");
					break;
				case 2:
					totalPrice = priceMap.get("snapshot");
					break;
				default:
					break;
				}
				statisticsService.addResource(uuid, name, type,
						new Date(), totalPrice, userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public JSONObject deleteStatistics(JSONObject jo) {
		try {
			if (jo.getBoolean("result")) {
				String uuid = jo.getString("uuid");
				int userId = jo.getInt("userId");
				statisticsService.updateResource(uuid, userId,
						new Date());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
}
