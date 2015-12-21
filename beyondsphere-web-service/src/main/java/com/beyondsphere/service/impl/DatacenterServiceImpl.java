package com.beyondsphere.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.DatacenterDAO;
import com.beyondsphere.dao.HostDAO;
import com.beyondsphere.dao.ImageDAO;
import com.beyondsphere.dao.NetworkDAO;
import com.beyondsphere.dao.PoolDAO;
import com.beyondsphere.dao.StorageDAO;
import com.beyondsphere.dao.VMDAO;
import com.beyondsphere.entity.Datacenter;
import com.beyondsphere.entity.OCHost;
import com.beyondsphere.entity.OCLog;
import com.beyondsphere.entity.OCPool;
import com.beyondsphere.entity.OCVM;
import com.beyondsphere.entity.Storage;
import com.beyondsphere.core.HostCore;
import com.beyondsphere.log.LogAction;
import com.beyondsphere.log.LogObject;
import com.beyondsphere.log.LogRecord;
import com.beyondsphere.log.LogRole;
import com.beyondsphere.message.MessageUtil;
import com.beyondsphere.service.DatacenterService;
import com.beyondsphere.service.LogService;
import com.beyondsphere.util.TimeUtils;

@Component("DatacenterService")
public class DatacenterServiceImpl implements DatacenterService {
	
	@Resource
	private DatacenterDAO datacenterDAO;
	@Resource
	private PoolDAO poolDAO;
	@Resource
	private HostDAO hostDAO;
	@Resource
	private LogService logDAO;
	@Resource
	private StorageDAO storageDAO;
	@Resource
	private ImageDAO imageDAO;
	@Resource
	private NetworkDAO networkDAO;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private MessageUtil message;
	@Resource
	private HostCore hostCore;
	@Resource
	private LogRecord logRecord;

	public JSONArray createDatacenter(String dcName, String dcLocation,
			String dcDesc, int userid) {
		Date startTime = new Date();
		JSONArray ja = new JSONArray();
		Datacenter result = datacenterDAO.createDatacenter(dcName,
				dcLocation, dcDesc);
		if (result != null) {
			JSONObject jo = new JSONObject();
			jo.put("dcname", TimeUtils.encodeText(dcName));
			jo.put("dcid", result.getDcUuid());
			jo.put("dclocation", TimeUtils.encodeText(dcLocation));
			jo.put("dcdesc", TimeUtils.encodeText(dcDesc));
			jo.put("createdate", TimeUtils.formatTime(result.getCreateDate()));
			ja.put(jo);
		}
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.DATACENTER, dcName);
		if (result != null) {
			OCLog log = logRecord.addSuccessLog(userid, LogObject.DATACENTER, 
					LogAction.CREATE, 
					infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userid, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userid, LogObject.DATACENTER, 
					LogAction.CREATE, 
					infoArray.toString(), startTime, endTime);
			message.pushError(userid, log.toString());
		}
		return ja;
	}

	public JSONArray getDatacenterList(int page, int limit, String search) {
		JSONArray ja = new JSONArray();
		int totalNum = datacenterDAO.countAllDatacenter(search);
		List<Datacenter> dcList = datacenterDAO.getOnePageDCList(
				page, limit, search);
		ja.put(totalNum);
		if (dcList != null) {
			for (Datacenter dc : dcList) {
				JSONObject jo = new JSONObject();
				jo.put("dcname", TimeUtils.encodeText(dc.getDcName()));
				jo.put("dcid", dc.getDcUuid());
				jo.put("dclocation", TimeUtils.encodeText(dc.getDcLocation()));
				jo.put("dcdesc", TimeUtils.encodeText(dc.getDcDesc()));
				jo.put("createdate", TimeUtils.formatTime(dc.getCreateDate()));
				List<Integer> volumeList = getDCVolume(dc.getDcUuid());
				jo.put("totalcpu", volumeList.get(0));
				jo.put("totalmem", volumeList.get(1));
				ja.put(jo);
			}
		}
		return ja;
	}

	public JSONArray getDatacenterAllList() {
		JSONArray ja = new JSONArray();
		List<Datacenter> dcList = datacenterDAO.getAllPageDCList();
		if (dcList != null) {
			for (Datacenter dc : dcList) {
				JSONObject jo = new JSONObject();
				jo.put("dcname", TimeUtils.encodeText(dc.getDcName()));
				jo.put("dcid", dc.getDcUuid());
				ja.put(jo);
			}
		}
		return ja;
	}

	public JSONArray deleteDatacenter(String dcId, String dcName, int userid) {
		Date startTime = new Date();
		JSONArray ja = new JSONArray();
		boolean result = false;
		int poolNumOfDC = poolDAO.getPoolListOfDC(dcId).size();
		if (poolNumOfDC==0) {
			result = datacenterDAO.deleteDatacenter(dcId);
		}
		JSONObject jo = new JSONObject();
		jo.put("result", result);
		ja.put(jo);
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.DATACENTER, dcName);
		if (result) {
			OCLog log = logRecord.addSuccessLog(userid, LogObject.DATACENTER, 
					LogAction.DELETE,
					infoArray.toString(), 
					startTime, endTime);
			message.pushSuccess(userid, log.toString());
		} else {
			if (poolNumOfDC != 0) {
				message.pushError(userid, "数据中心中存在资源池，不能删除！");
			}else {
				OCLog log = logRecord.addFailedLog(userid, LogObject.DATACENTER, 
						LogAction.DELETE, 
						infoArray.toString(), startTime, endTime); 
				message.pushError(userid, log.toString());
			}
		}
		return ja;
	}

	public void updateDatacenter(String dcUuid, String dcName, String dcLocation,
			String dcDesc, int userid) {
		boolean result = datacenterDAO.updateDatacenter(dcUuid,
				dcName, dcLocation, dcDesc);
		if (result) {
			message.pushSuccess(userid, "数据中心更新成功");
		} else {
			message.pushError(userid, "数据中心更新失败");
		}
	}

	public JSONArray getPoolList(String dcid) {
		JSONArray ja = new JSONArray();
		List<OCPool> poolList = poolDAO.getPoolListOfDC(dcid);
		if (poolList != null) {
			for (OCPool pool : poolList) {
				JSONObject jo = new JSONObject();
				jo.put("poolobj", pool.toJsonString());
				List<Storage> srlist = storageDAO
						.getStorageListOfHost(pool.getPoolMaster());
				JSONArray jsonArrayStorage = new JSONArray();
				if (srlist != null) {
					for (Storage sr : srlist)
						jsonArrayStorage.put(sr.toJsonString());
				}
				jo.put("storagelist", jsonArrayStorage);

				JSONArray jsonArrayServer = new JSONArray();
				List<OCHost> serverList = hostDAO.getHostListOfPool(
						pool.getPoolUuid());
				for (OCHost host : serverList) {
					JSONObject thost = new JSONObject();
					thost.put("hostobj", host.toJsonString());
					thost.put("imagecount",
							imageDAO.countByHost(host.getHostUuid()));
					thost.put("vmcount",
							vmDAO.countVMsOfHost(host.getHostUuid()));
					jsonArrayServer.put(thost);
				}

				jo.put("serverlist", jsonArrayServer);
				ja.put(jo);
			}

		}
		return ja;
	}

	public Datacenter getDatacenterBydcUuid(String dcUuid) {
		return datacenterDAO.getDatacenter(dcUuid);
	}

	@SuppressWarnings("unused")
	public Map<String, Object> getDatacenterInfos(String dcUuid) {
		Map<String, Object> result = new HashMap<String, Object>();
		int poolNum = 0, hostNum = 0, storageNum = 0, vmNum = 0, vlanNum = 0;
		List<OCPool> pools = poolDAO.getPoolListOfDC(dcUuid);
		poolNum = pools.size();
		if (pools.size() != 0) {
			for (OCPool pool : pools) {
				//存储方法
				//storageNum += getStorageNum(pool.getPoolUuid());
				try {
					vlanNum = networkDAO.countNetworkListByType(1);
					List<OCHost> hosts = hostDAO.getHostListOfPool(pool.getPoolUuid());
					if (hosts != null) {
						hostNum += hosts.size();
						for (OCHost host : hosts) {
							vmNum += vmDAO.countVMsOfHost(host.getHostUuid());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		result.put("poolNum", poolNum);
		result.put("hostNum", hostNum);
		//result.put("storageNum", storageNum);
		result.put("vmNum", vmNum);
		result.put("vlanNum", vlanNum);
		
		return result;
	}

	@SuppressWarnings("unused")
	public JSONArray getResourcePercentOfDatacenter(String dcUuid) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		double totalCpu = 0, usedCpu = 0, totalMem = 0, usedMem = 0, 
			totalVlan = 0, usedVlan = 0,totalStorage = 0, usedStorage = 0;
		List<OCPool> pools = poolDAO.getPoolListOfDC(dcUuid);
		if (pools.size() != 0) {
			for (OCPool pool : pools) {
				try {
					usedVlan = networkDAO.countNetworkListByType(1);
					List<OCHost> hosts = hostDAO.getHostListOfPool(pool.getPoolUuid());
					for(OCHost host : hosts){
						totalCpu += host.getHostCpu();
						totalMem += host.getHostMem();
					}
					List<OCVM> vmList = vmDAO.getVmListByPoolUuid(pool.getPoolUuid());
					for (OCVM vm : vmList) {
						usedCpu += vm.getVmCpu();
						usedMem += vm.getVmMem();
					}
					Map<String,Long> map= hostCore.getDiskInfo(pool.getPoolUuid());
					totalStorage+=map.get("sumTotalDisk");
					usedStorage+=map.get("sumUsedDisk");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		double cpuPercent = 0, memPercent = 0, vlanPercent = 0, storagePercent = 0;
		if (totalCpu != 0){
			cpuPercent = (double)usedCpu / (double)totalCpu / (double)3;
		}
		if (totalMem != 0) {
			memPercent = (double)usedMem / (double)totalMem;
		}
		if(totalStorage!=0){
			storagePercent = (double)usedStorage/(double)totalStorage;
		}
		vlanPercent = (double)(usedVlan) / (double)4096.0;
		
		NumberFormat nt = NumberFormat.getPercentInstance();
		//设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(2);
		jo.put("cpuPercent", nt.format(cpuPercent));
		jo.put("memPercent", nt.format(memPercent));
		jo.put("vlanPercent", nt.format(vlanPercent));
		//存储设备使用率计算
		jo.put("storagePercent", nt.format(storagePercent));
		ja.put(jo);
		return ja;
	}

	private List<Integer> getDCVolume(String dcUuid) {
		int totalCpu = 0;
		int totalMemory = 0;
		List<OCPool> poolList = poolDAO.getPoolListOfDC(dcUuid);
		if (poolList != null) {
			for (OCPool pool : poolList) {
				List<Integer> poolVolume = getPoolVolume(pool.getPoolUuid());
				totalCpu += poolVolume.get(0);
				totalMemory += poolVolume.get(1);
			}
		}
		List<Integer> volumeList = new ArrayList<Integer>();
		volumeList.add(totalCpu);
		volumeList.add(totalMemory);
		return volumeList;
	}
	
	private List<Integer> getPoolVolume(String poolUuid) {
		int totalCpu = 0;
		int totalMemory = 0;
		List<OCHost> hostList = hostDAO.getHostListOfPool(poolUuid);
		if (hostList != null) {
			for (OCHost host : hostList) {
				totalCpu += host.getHostCpu();
				totalMemory += host.getHostMem();
			}
		}
		List<Integer> volumeList = new ArrayList<Integer>();
		volumeList.add(totalCpu);
		volumeList.add(totalMemory);
		return volumeList;
	}

	@Override
	public JSONArray getResourceOfDatacenter() {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		double totalCpu = 0, usedCpu = 0, totalMem = 0, usedMem = 0;
		List<OCHost> hosts = hostDAO.getAllHost();
		for(OCHost host : hosts){
			totalCpu += host.getHostCpu();
			totalMem += host.getHostMem();
			List<OCVM> vmList = vmDAO.getVmListByhostUuid(host.getHostUuid());
			for (OCVM vm : vmList) {
				usedCpu += vm.getVmCpu();
				usedMem += vm.getVmMem();
			}
		}
		double cpuPercent = 0, memPercent = 0;
		if (totalCpu != 0){
			cpuPercent = (double)usedCpu / (double)totalCpu / (double)3;
		}
		if (totalMem != 0) {
			memPercent = (double)usedMem / (double)totalMem;
		}
		NumberFormat nt = NumberFormat.getPercentInstance();
		NumberFormat nt2 = NumberFormat.getNumberInstance();
		//设置百分数精确度2即保留两位小数
		nt.setMaximumFractionDigits(2);
		nt2.setMaximumFractionDigits(2);
		jo.put("totalCpu", totalCpu*3);
		jo.put("usedCpu", usedCpu);
		jo.put("leftCpu", totalCpu*3-usedCpu);
		jo.put("totalMem",nt2.format(totalMem/1024));
		jo.put("usedMem", nt2.format(usedMem/1024));
		jo.put("leftMem", nt2.format((totalMem-usedMem)/1024));
		jo.put("cpuPercent", nt.format(cpuPercent));
		jo.put("memPercent", nt.format(memPercent));
		ja.put(jo);
		return ja;
	}
	
}
