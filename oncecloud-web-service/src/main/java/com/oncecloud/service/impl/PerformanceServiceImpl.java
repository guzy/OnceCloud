package com.oncecloud.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.PerformanceDAO;
import com.oncecloud.model.performance.Cpu;
import com.oncecloud.model.performance.Memory;
import com.oncecloud.model.performance.Pif;
import com.oncecloud.model.performance.Vbd;
import com.oncecloud.model.performance.Vif;
import com.oncecloud.service.PerformanceService;

@Component("PerformanceService")
public class PerformanceServiceImpl implements PerformanceService {
	
	@Resource
	private PerformanceDAO performanceDAO;
	
	public JSONObject getCpu(String uuid, String type) {
		List<Cpu> cpuList = null;
		if (type.equals("sixhours")) {
			cpuList = performanceDAO.getCpuList4Sixhours(uuid);
		} else if (type.equals("oneday")) {
			cpuList = performanceDAO.getCpuList4Oneday(uuid);
		} else if (type.equals("twoweeks")) {
			cpuList = performanceDAO.getCpuList4Twoweeks(uuid);
		} else if (type.equals("onemonth")) {
			cpuList = performanceDAO.getCpuList4Onemonth(uuid);
		} else if (type.equals("thirtymin")) {
			cpuList = performanceDAO.getCpuList(uuid);
		}
		JSONObject cpuPicData = new JSONObject();
		Set<Long> timeset = new HashSet<Long>();
		if (cpuList != null) {
			for (int i=(cpuList.size()-1);i>=0;i-- ) {
				Cpu cpu=cpuList.get(i);
				String cpuId = cpu.getCpuId().toString();
				JSONArray ja = null;
				long cpuTime = cpu.getTime();
				double cpuUsage = cpu.getUsage();
				if (timeset.contains(cpuTime)) {
				} else {
					timeset.add(cpuTime);
				}
				JSONObject jo = new JSONObject();
				jo.put("times", cpuTime);
				jo.put("usage", cpuUsage * 100);
				if (cpuPicData.has(cpuId)) {
					ja = cpuPicData.getJSONArray(cpuId);
					ja.put(jo);
				} else {
					ja = new JSONArray();
					ja.put(jo);
					cpuPicData.put(cpuId, ja);
				}
			}
		}
		return cpuPicData;
	}

	public JSONArray getMemory(String uuid, String type) {
		List<Memory> memoryList = null;
		if (type.equals("sixhours")) {
			memoryList = performanceDAO.getMemoryList4Sixhours(uuid);
		} else if (type.equals("oneday")) {
			memoryList = performanceDAO.getMemoryList4Oneday(uuid);
		} else if (type.equals("twoweeks")) {
			memoryList = performanceDAO.getMemoryList4Twoweeks(uuid);
		} else if (type.equals("onemonth")) {
			memoryList = performanceDAO.getMemoryList4Onemonth(uuid);
		} else if (type.equals("thirtymin")) {
			memoryList = performanceDAO.getMemoryList(uuid);
		}
		JSONArray memoryPicData = new JSONArray();
		if (memoryList != null) {
			for (int i=(memoryList.size()-1);i>=0;i-- ) {
				Memory memory = memoryList.get(i);
				Double totalMemory = memory.getTotalSize() / 1024 / 1024;
				Double freeMemory = memory.getFreeSize() / 1024 / 1024;
				Double usedMemory = totalMemory - freeMemory;
				long memoryTime = memory.getTime();
				JSONObject jo = new JSONObject();
				jo.put("times", memoryTime);
				jo.put("total", totalMemory);
				jo.put("used", usedMemory);
				memoryPicData.put(jo);
			}
		}
		return memoryPicData;
	}

	public JSONObject getVbd(String uuid, String type) {
		List<Vbd> vbdList = null;
		if (type.equals("sixhours")) {
			vbdList = performanceDAO.getVbdList4Sixhours(uuid);
		} else if (type.equals("oneday")) {
			vbdList = performanceDAO.getVbdList4Oneday(uuid);
		} else if (type.equals("twoweeks")) {
			vbdList = performanceDAO.getVbdList4Twoweeks(uuid);
		} else if (type.equals("onemonth")) {
			vbdList = performanceDAO.getVbdList4Onemonth(uuid);
		} else if (type.equals("thirtymin")) {
			vbdList = performanceDAO.getVbdList(uuid);
		}
		JSONObject vbdPicData = new JSONObject();
		if (vbdList != null) {
			for (Vbd vbd : vbdList) {
				String vbdId = vbd.getVbdId();
				long vbdTime = vbd.getTime();
				Double vbdRead = vbd.getRead();
				Double vbdWrite = vbd.getWrite();
				JSONArray ja = null;
				JSONObject jo = new JSONObject();
				jo.put("times", vbdTime);
				jo.put("read", vbdRead);
				jo.put("write", vbdWrite);
				if (vbdPicData.has(vbdId)) {
					ja = vbdPicData.getJSONArray(vbdId);
					ja.put(jo);
				} else {
					ja = new JSONArray();
					ja.put(jo);
					vbdPicData.put(vbdId, ja);
				}
			}
		}
		return vbdPicData;
	}

	public JSONObject getVif(String uuid, String type) {
		List<Vif> vifList = null;
		if (type.equals("sixhours")) {
			vifList = performanceDAO.getVifList4Sixhours(uuid);
		} else if (type.equals("oneday")) {
			vifList = performanceDAO.getVifList4Oneday(uuid);
		} else if (type.equals("twoweeks")) {
			vifList = performanceDAO.getVifList4Twoweeks(uuid);
		} else if (type.equals("onemonth")) {
			vifList = performanceDAO.getVifList4Onemonth(uuid);
		} else if (type.equals("thirtymin")) {
			vifList = performanceDAO.getVifList(uuid);
		}
		JSONObject vifPicData = new JSONObject();
		if (vifList != null) {
			for (Vif vif : vifList) {
				String vifId = vif.getVifId().toString();
				long vifTime = vif.getTime();
				Double vifTx = vif.getTxd();
				Double vifRx = vif.getRxd();
				JSONArray ja = null;
				JSONObject jo = new JSONObject();
				jo.put("times", vifTime);
				jo.put("rx", vifRx);
				jo.put("tx", vifTx);
				if (vifPicData.has(vifId)) {
					ja = vifPicData.getJSONArray(vifId);
					ja.put(jo);
				} else {
					ja = new JSONArray();
					ja.put(jo);
					vifPicData.put(vifId, ja);
				}
			}
		}
		return vifPicData;
	}

	public JSONObject getPif(String uuid, String type) {
		List<Pif> pifList = null;
		if (type.equals("sixhours")) {
			pifList = performanceDAO.getPifList4Sixhours(uuid);
		} else if (type.equals("oneday")) {
			pifList = performanceDAO.getPifList4Oneday(uuid);
		} else if (type.equals("twoweeks")) {
			pifList = performanceDAO.getPifList4Twoweeks(uuid);
		} else if (type.equals("onemonth")) {
			pifList = performanceDAO.getPifList4Oneday(uuid);
		} else if (type.equals("thirtymin")) {
			pifList = performanceDAO.getPifList(uuid);
		}
		JSONObject vifPicData = new JSONObject();
		if (pifList != null) {
			for (Pif pif : pifList) {
				String pifId = pif.getPifId().toString();
				long pifTime = pif.getTime();
				Double pifTx = pif.getTxd();
				Double pifRx = pif.getRxd();
				JSONArray ja = null;
				JSONObject jo = new JSONObject();
				jo.put("times", pifTime);
				jo.put("rx", pifRx);
				jo.put("tx", pifTx);
				if (vifPicData.has(pifId)) {
					ja = vifPicData.getJSONArray(pifId);
					ja.put(jo);
				} else {
					ja = new JSONArray();
					ja.put(jo);
					vifPicData.put(pifId, ja);
				}
			}
		}
		return vifPicData;
	}

}
