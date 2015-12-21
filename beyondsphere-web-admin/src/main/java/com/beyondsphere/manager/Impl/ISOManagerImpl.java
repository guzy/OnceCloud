/**
 * 
 */
package com.beyondsphere.manager.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.beyondsphere.manager.ISOManager;
import com.oncecloud.core.ISOCore;
import com.oncecloud.entity.ISO;
import com.oncecloud.entity.OCPool;
import com.oncecloud.model.ISOModel;
import com.oncecloud.model.PagedList;
import com.oncecloud.service.ISOService;
import com.oncecloud.service.PoolService;

/**
 * @author 玉和
 * 2014-12-13 
 */
@Service("ISOManager")
public class ISOManagerImpl implements ISOManager{
	
	@Resource
	private PoolService poolService;
	@Resource
	private ISOService isoService;
	@Resource
	private ISOCore isoCore;
	
	public PagedList<ISO> getISOList(int page, int limit, String search) {
	    return isoService.getISOList(page, limit, search);
	}

	public ISO addISO(String isoName, String isoFileSrc, Integer userId,
			String isoRemarks) {
		return isoService.addISO(isoName, isoFileSrc, userId, isoRemarks);
	}
	
	public JSONObject deleteISO(int userId, String isoUuid,String isoName) {
		return isoService.deleteISO(userId, isoUuid, isoName);
	}


	public ISO getISO(String isoUuid) {
		return isoService.getISO(isoUuid);
	}

	public JSONArray getISOListAll(int userId) {
		JSONArray array=new JSONArray();
		try {
			List<ISOModel> list =new ArrayList<ISOModel>();
			List<OCPool> poollist = poolService.getPoolList();
			for(OCPool pool :poollist){
				if(pool.getPoolMaster()!=null){
					list=isoCore.getISOModelOfPool(list, pool);
				}
			}
			array.put(list.size());
			for (ISOModel isoinfo : list) {
				JSONObject obj=new JSONObject();
				obj.put("isoName", isoinfo.getIsoName());
				obj.put("poolName", isoinfo.getPoolName());
				obj.put("poolId", isoinfo.getPoolId());
				array.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return array;
	}

}
