package com.oncecloud.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.oncecloud.dao.DatacenterDAO;
import com.oncecloud.dao.HostDAO;
import com.oncecloud.dao.ImageDAO;
import com.oncecloud.dao.PoolDAO;
import com.oncecloud.dao.StorageDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.service.AccountService;

@Component("AccountService")
public class AccountServiceImpl implements AccountService {

	@Resource
	private DatacenterDAO datacenterDAO;
	@Resource
	private PoolDAO poolDAO;
	@Resource
	private HostDAO hostDAO;
	@Resource
	private StorageDAO storageDAO;
	@Resource
	private ImageDAO imageDAO;
	@Resource
	private VMDAO vmDAO;
	
	public Map<String, Object> getInfrastructureInfos() {
		Integer datacenterNumber = datacenterDAO.countAllDatacenter("");
		Integer poolNumber = poolDAO.countAllPoolList("");
		Integer hostNumber = hostDAO.countAllHostList("");
		Integer storageNumber = storageDAO.countAllStorageList("");
		Integer imageNumber = imageDAO.countAllImageList();
		Integer vmNumber = vmDAO.countAllVms();
		Map<String, Object> infrastructureMap = new HashMap<String, Object>();
		infrastructureMap.put("datacenterNumber", datacenterNumber);
		infrastructureMap.put("poolNumber", poolNumber);
		infrastructureMap.put("hostNumber", hostNumber);
		infrastructureMap.put("storageNumber", storageNumber);
		infrastructureMap.put("imageNumber", imageNumber);
		infrastructureMap.put("vmNumber", vmNumber);
		return infrastructureMap;
	}

}
