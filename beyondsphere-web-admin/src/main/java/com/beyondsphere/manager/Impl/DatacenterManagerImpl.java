/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.beyondsphere.manager.Impl;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.SR;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.beyondsphere.entity.Datacenter;
import com.beyondsphere.entity.OCPool;
import com.beyondsphere.manager.DatacenterManager;
import com.beyondsphere.core.HostCore;
import com.beyondsphere.core.constant.Constant;
import com.beyondsphere.service.DatacenterService;
import com.beyondsphere.service.PoolService;

@Service("dataCenterManager")
public class DatacenterManagerImpl implements DatacenterManager {
	
	@Resource
	private DatacenterService datacenterService;
	@Resource
	private Constant constant;
	@Resource
	private HostCore hostCore;
	@Resource
	private PoolService poolService;
	public JSONArray createDatacenter(String dcName, String dcLocation,
			String dcDesc, int userid) {
		return datacenterService.createDatacenter(dcName, dcLocation, dcDesc, userid);
	}

	public JSONArray getDatacenterList(int page, int limit, String search) {
		return datacenterService.getDatacenterList(page, limit, search);
	}
	
	public JSONArray getResourcePercentOfDatacenter(String dcUuid) {
		return datacenterService.getResourcePercentOfDatacenter(dcUuid);
	}
	
	public JSONArray getDatacenterAllList() {
		return datacenterService.getDatacenterAllList();
	}

	public JSONArray deleteDatacenter(String dcId, String dcName, int userid) {
		return datacenterService.deleteDatacenter(dcId, dcName, userid);
	}

	public void update(String dcUuid, String dcName, String dcLocation,
			String dcDesc, int userid) {
		datacenterService.updateDatacenter(dcUuid, dcName, dcLocation, dcDesc, userid);
	}

	public JSONArray getPoolList(String dcid) {
		return datacenterService.getPoolList(dcid);
	}

	public Datacenter getDatacenterBydcUuid(String dcUuid) {
		return datacenterService.getDatacenterBydcUuid(dcUuid);
	}

	public Map<String, Object> getDatacenterInfos(String dcUuid) {
		return datacenterService.getDatacenterInfos(dcUuid);
	}
	
	@SuppressWarnings("unused")
	private int getStorageNum(String poolUuid){
		int storageNum = 0;
		Connection conn = null;
		try {
			conn = constant.getConnectionFromPool(poolUuid);
			if (conn != null) {
				Map<SR, SR.Record> srList = SR.getAllRecords(conn);
				storageNum = srList.keySet().size();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return storageNum;
		}
		return storageNum;
	}

	@Override
	public JSONArray getResourceOfDatacenter() {
		JSONArray ja = datacenterService.getResourceOfDatacenter();
		List<OCPool> pools =poolService.getPoolList();
		double sumTotal=0;
		double sumUsed=0;
		for(OCPool pool:pools){
			Map<String,Long> map= hostCore.getDiskInfo(pool.getPoolUuid());
			sumTotal+=map.get("sumTotalDisk");
			sumUsed+=map.get("sumUsedDisk");
		}
		double diskPercent=0; 
		if(sumTotal!=0){
			diskPercent = (double)sumUsed/(double)sumTotal;
		}
		NumberFormat nt = NumberFormat.getPercentInstance();
		NumberFormat nt2 = NumberFormat.getNumberInstance();
		//设置百分数精确度2即保留两位小数
		nt.setMaximumFractionDigits(2);
		nt2.setMaximumFractionDigits(2);
		JSONObject jo = new JSONObject();
		jo.put("totalDisk", nt2.format(sumTotal/1024/1024/1024));
		jo.put("usedDisk", nt2.format(sumUsed/1024/1024/1024));
		jo.put("leftDisk", nt2.format((sumTotal-sumUsed)/1024/1024/1024));
		jo.put("diskPercent", nt.format(diskPercent));
		ja.put(jo);
		return ja;
	}

	
}
