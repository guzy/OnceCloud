package com.beyondsphere.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.beyondsphere.dao.DatacenterDAO;
import com.beyondsphere.dao.HaDAO;
import com.beyondsphere.dao.HostDAO;
import com.beyondsphere.dao.VMDAO;
import com.beyondsphere.entity.OCHa;
import com.beyondsphere.log.LogRecord;
import com.beyondsphere.service.HaService;

@Component("HaService")
public class HaServiceImpl implements HaService {
	
	@Resource
	private DatacenterDAO datacenterDAO;
	@Resource
	private HostDAO hostDAO;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private HaDAO haDAO;
	@Resource
	private LogRecord logRecord;
	
	public OCHa getRecordOfHaPolicy(String poolUuid) {
		return haDAO.getHa(poolUuid);
	}

	public boolean saveHa(String poolUuid, String haPath) {
		boolean result = false;
		OCHa ha = new OCHa();
		ha.setPoolUuid(poolUuid);
		ha.setHaPath(haPath);
		ha.setHaStartFlag(1);
		ha.setHostMonitor(1);
		ha.setAccessControlPolicy(0);
		ha.setLeftHost(0);
		ha.setSlotPolicy(0);
		ha.setSlotCpu(0);
		ha.setSlotMemory(0);
		ha.setCpuPercent(0);
		ha.setMemoryPercent(0);
		ha.setStartDate(new Date());
		result = haDAO.insertHa(ha);
		return result;
	}

	public boolean updateHa(OCHa ha) {
		return haDAO.updateHa(ha);
	}
	
	public boolean cancelHa(String poolUuid) {
		return haDAO.deleteHa(poolUuid);
	}

}
