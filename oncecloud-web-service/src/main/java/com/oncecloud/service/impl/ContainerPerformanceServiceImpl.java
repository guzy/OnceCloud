package com.oncecloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.dao.ContainerPerformanceDAO;
import com.oncecloud.model.performance.ContainerInfo;
import com.oncecloud.service.ContainerPerformanceService;

@Service
public class ContainerPerformanceServiceImpl implements ContainerPerformanceService {

	@Resource
	private ContainerPerformanceDAO containerPerformanceDAO;
	
	@Override
	public JSONArray getContainerInfos(String containerName) {
		List<ContainerInfo> containerInfos = containerPerformanceDAO.getContainerInfos(containerName);
		JSONArray conArray = new JSONArray();
		if (containerInfos != null) {
			for(ContainerInfo conInfo : containerInfos){
				JSONObject conObject = new JSONObject();
				conObject.put("times", conInfo.getTime());
				conObject.put("cpuPercent", conInfo.getCpuUsagePercent());
				conObject.put("memUsage", conInfo.getMemUsage()/1024/1024/1024);
				conObject.put("packageSend", conInfo.getPackageSendCount());
				conObject.put("packageRecv", conInfo.getPackageRecvCount());
				conArray.put(conObject);
			}
		}
		return conArray;
	}

}
