package com.oncecloud.manager.Impl;

/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */


import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.Host;
import org.Xen.API.Network;
import org.Xen.API.SR;
import org.Xen.API.Types;
import org.Xen.API.VBD;
import org.Xen.API.Types.BadServerResponse;
import org.Xen.API.Types.XenAPIException;
import org.Xen.API.VIF;
import org.Xen.API.VM;
import org.Xen.API.VM.Record;
import org.Xen.API.VMUtil;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.manager.VMManager;
import com.oncecloud.manager.component.Compensator;
import com.oncecloud.vsphere.VMWareUtil;
import com.oncecloud.constants.LoggerConfig;
import com.oncecloud.constants.PowerConstant;
import com.oncecloud.constants.SystemConstant;
import com.oncecloud.core.HostCore;
import com.oncecloud.core.ISOCore;
import com.oncecloud.core.VMCore;
import com.oncecloud.core.constant.Constant;
import com.oncecloud.dao.HostSRDAO;
import com.oncecloud.dao.NetworkDAO;
import com.oncecloud.dao.StorageDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.entity.Image;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCLog;
import com.oncecloud.entity.OCNetwork;
import com.oncecloud.entity.OCVM;
import com.oncecloud.entity.Storage;
import com.oncecloud.log.LogAction;
import com.oncecloud.log.LogObject;
import com.oncecloud.log.LogRecord;
import com.oncecloud.log.LogRole;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.model.ImagePlatform;
import com.oncecloud.model.ImageStatus;
import com.oncecloud.model.SwitchType;
import com.oncecloud.model.VMPlatform;
import com.oncecloud.model.VMPower;
import com.oncecloud.model.VMStatus;
import com.oncecloud.service.HostService;
import com.oncecloud.service.ImageService;
import com.oncecloud.service.NetworkService;
import com.oncecloud.service.PoolService;
import com.oncecloud.service.UserService;
import com.oncecloud.service.VMService;
import com.oncecloud.tools.NoVNC;
import com.oncecloud.util.TimeUtils;
import com.vmware.vim25.VirtualMachineConfigInfo;


@Service("VMManager")
public class VMManagerImpl<ManagedObjectReference> implements VMManager {
	private final static Logger logger = Logger.getLogger(VMManagerImpl.class);
	private final static long MB = 1024 * 1024;

	@Resource
	private PoolService poolService;
	@Resource
	private HostService hostService;
	@Resource
	private ImageService imageService;
	@Resource
	private VMService vmService;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private UserService userService;
	@Resource
	private NetworkService networkService;
	@Resource
	private HostCore hostCore;
	@Resource
	private ISOCore isoCore;
	@Resource
	private VMCore vmCore;
	@Resource
	private LogRecord logRecord;
	@Resource
	private Constant constant;
	@Resource
	private MessageUtil message;
	@Resource
	private Compensator compensator;
	@Resource
	private NetworkDAO networkDAO;
	@Resource
	private LoggerConfig loggerConfig;
	@Resource
	private HostSRDAO hostSrDAO;
	@Resource
	private  StorageDAO storageDAO;

	public void adminDestoryVM(int userId, String uuid) {
		
		//根据vmuuid查询主机类型是否是vsphere
		String hosttype = vmService.getHostType(uuid);
		if(hosttype!=null&&hosttype.equals(SystemConstant.VSPHERE)){
			try {
				this.vSphereDestroy(userId,uuid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(hosttype!=null&&hosttype.equals(SystemConstant.BEUONDCLOUD)){
			String poolUuid = vmService.getPoolUuidOfVM(uuid);
			synchronized (this) {
				this.destoryVM(userId, uuid, poolUuid);
			}
		}		
		
	}

	private void destoryVM(int userId, String uuid, String poolUuid) {
		Date startTime = new Date();
		boolean result = this.doDestoryVM(userId, uuid, poolUuid);
		//第一次操作没有成功
		if (!result) {
			//修复资源池
			compensator.compensate(poolUuid);
			//再次执行操作
			result = this.doDestoryVM(userId, uuid, poolUuid);
		}
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.INSTANCE, 
				"i-" + uuid.substring(0, 8)); 
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.INSTANCE, 
					LogAction.DESTORY, 
					infoArray.toString(), startTime, endTime); 		
			message.deleteRow(userId, uuid);
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.INSTANCE, 
					LogAction.DESTORY, 
					infoArray.toString(), startTime, endTime); 		
			message.pushError(userId, log.toString());
		}
	}

	private boolean doDestoryVM(int userId, String uuid, String poolUuid) {
		boolean result = false;
		if (vmService.updateStatusOfPower(uuid, PowerConstant.POWER_DESTROY)) {
			if (vmCore.destory(poolUuid, uuid)) {
				result = vmService.removeVm(userId, uuid);
			}
		}
		return result;
	}

	public boolean doAdminStartVm(int userId, String uuid) {
		boolean result=false;
		//根据vmuuid查询主机类型是否是vsphere
		String hosttype = vmService.getHostType(uuid);
		if(hosttype!=null&&hosttype.equals(SystemConstant.VSPHERE)){
			try {
				this.runOperation(SystemConstant.POWERON, uuid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(hosttype!=null&&hosttype.equals(SystemConstant.BEUONDCLOUD)){
			String poolUuid = vmService.getPoolUuidOfVM(uuid);
			synchronized (this) {
				result=this.startVM(userId, uuid, poolUuid);
			}
		}
		return result;
		
	}
	
	//恢复虚拟主机
			public void doRestartHostVm(int userId, String uuid, String force) {
				String hosttype = vmService.getHostType(uuid);
				if(hosttype!=null&&hosttype.equals(SystemConstant.VSPHERE)){
					try {
						this.runOperation(SystemConstant.POWEROFF, uuid);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(hosttype!=null&&hosttype.equals(SystemConstant.BEUONDCLOUD)){
					String poolUuid = vmService.getPoolUuidOfVM(uuid);
					synchronized (this) {
						this.restartVm(userId, uuid, force, poolUuid);
					}
				}				
				
			}
	//暂停虚拟主机
		public void doAdminStopHost(int userId, String uuid, String force) {
			String hosttype = vmService.getHostType(uuid);
			if(hosttype!=null&&hosttype.equals(SystemConstant.VSPHERE)){
				try {
					this.runOperation(SystemConstant.POWEROFF, uuid);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(hosttype!=null&&hosttype.equals(SystemConstant.BEUONDCLOUD)){
				String poolUuid = vmService.getPoolUuidOfVM(uuid);
				synchronized (this) {
					this.pauseVm(userId, uuid, force, poolUuid);
				}
			}				
			
		}
	public void doAdminShutDown(int userId, String uuid, String force) {
		//根据vmuuid查询主机类型是否是vsphere
		String hosttype = vmService.getHostType(uuid);
		if(hosttype!=null&&hosttype.equals(SystemConstant.VSPHERE)){
			try {
				this.runOperation(SystemConstant.POWEROFF, uuid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(hosttype!=null&&hosttype.equals(SystemConstant.BEUONDCLOUD)){
			String poolUuid = vmService.getPoolUuidOfVM(uuid);
			synchronized (this) {
				this.shutdownVM(userId, uuid, force, poolUuid);
			}
		}				
		
	}

	private boolean startVM(int userId, String uuid, String poolUuid) {
		Date startTime = new Date();
		boolean result = this.doStartVM(uuid, poolUuid);
		//第一次操作没有成功
		if (!result) {
			//修复资源池
			compensator.compensate(poolUuid);
			//再次执行操作
			result = this.doStartVM(uuid, poolUuid);
		}
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.INSTANCE, 
				"i-" + uuid.substring(0, 8)); 
		if (result) {
			message.editRowStatus(userId, uuid, "running", "正常运行");
			message.editRowConsole(userId, uuid, "add");
			OCLog log = logRecord.addSuccessLog(userId, LogObject.INSTANCE, 
					LogAction.START, 
					infoArray.toString(), startTime, endTime); 		
			message.pushSuccess(userId, log.toString());
		} else {
			message.editRowStatus(userId, uuid, "stopped", "已关机");
			OCLog log = logRecord.addFailedLog(userId, LogObject.INSTANCE, 
					LogAction.START, 
					infoArray.toString(), startTime, endTime); 		
			message.pushError(userId, log.toString());
		}
		return result;
	}
	//虚拟机操作
	public void runOperation(String operation,String vmuuid) {
		boolean b = false;
		try {
			Map<String, com.vmware.vim25.ManagedObjectReference>  vmlist =  VMWareUtil.getAllVms();
			for (Entry<String, com.vmware.vim25.ManagedObjectReference> entry : vmlist.entrySet()) {
				com.vmware.vim25.ManagedObjectReference vm = entry.getValue();
				VirtualMachineConfigInfo vminfo = new VirtualMachineConfigInfo();
				vminfo = (VirtualMachineConfigInfo) VMWareUtil.getDynamicProperty(vm, "config");
				if(vminfo.getUuid().equals(vmuuid))	{
					b = VMWareUtil.runOperation(operation, vminfo.getName());
					
					if(operation.contains("poweron")&&b){
						vmService.updateStatusOfPower(vminfo.getUuid(),PowerConstant.POWER_RUNNING);
					}
					if(operation.contains("poweroff")&&b){
						vmService.updateStatusOfPower(vminfo.getUuid(),PowerConstant.POWER_HALTED);
					}
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		
	}	
	//虚拟机销毁
	@SuppressWarnings("unchecked")
	public void vSphereDestroy(int userId,String vmuuid) {
		boolean b = false;
		try {
			Map<String, ManagedObjectReference>  vmlist = (Map<String, ManagedObjectReference>) VMWareUtil.getAllVms();
			for (Entry<String, ManagedObjectReference> entry : vmlist.entrySet()) {
				ManagedObjectReference vm = entry.getValue();
				VirtualMachineConfigInfo vminfo = new VirtualMachineConfigInfo();
				vminfo = (VirtualMachineConfigInfo) VMWareUtil.getDynamicProperty((com.vmware.vim25.ManagedObjectReference) vm, "config");
				if(vminfo.getUuid().equals(vmuuid))	{
					
					String operation = VMWareUtil.callWaitForUpdatesEx("60", "1",vminfo.getName())  ;
					//1、关机
					if(operation.contains("POWERED_ON")){
						b = VMWareUtil.runOperation(SystemConstant.POWEROFF, vminfo.getName());
					}
					//2、销毁
					if(b){
						b = VMWareUtil.deleteManagedEntity(vminfo.getName());
					}
					//3、删除数据库
					if(b){
						vmService.removeVm(userId,vminfo.getUuid());
					}
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		
	}		
	/*public boolean modifyVnet(String uuid, String type, String vnetid,
			String vifUuid) {
		String poolUuid = this.getPoolUuid(uuid, type);
		if (!poolUuid.equals("")) {
			Connection conn = null;
			try {
				conn = systemConstant.getConnectionFromPool(poolUuid);
				VM vm = VM.getByUuid(conn, uuid);
				VIF vif = VIF.getByUuid(conn, vifUuid);
				vm.setTag(conn, vif, vnetid);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		} else {
			return false;
		}
	}*/

	private boolean doStartVM(String uuid, String poolUuid) {
		boolean result = false;
		String hostUuid = null; 
		String powerState = null;
		OCVM currentVM = null;
		Connection conn = null;
		try {
			currentVM = vmService.getVm(uuid);
			if (currentVM != null) {
				if (vmService.updateStatusOfPower(uuid, PowerConstant.POWER_BOOT)) {
					conn = constant.getConnectionFromPool(poolUuid);
					VM thisVM = VM.getByUuid(conn, uuid);
					powerState = thisVM.getPowerState(conn).toString();
					if (!powerState.equals("Running")) {
						hostUuid = getAllocateHost(conn, currentVM.getVmMem());
						Host allocateHost = hostCore.getHost(hostUuid);
						thisVM.startOn(conn, allocateHost, false, true);
					} else {
						hostUuid = thisVM.getResidentOn(conn).toWireString();
					}
					vmService.updateHostUuidOfVM(uuid, hostUuid);
					vmService.updateStatusOfPower(uuid,PowerConstant.POWER_RUNNING);
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (powerState != null) {
				if (powerState.equals("Running")) {
					vmService.updateStatusOfPower(uuid, PowerConstant.POWER_RUNNING);
				} else {
					vmService.updateStatusOfPower(uuid, PowerConstant.POWER_HALTED);
				}
			} else {
				vmService.updateStatusOfPower(uuid, PowerConstant.POWER_HALTED);
			}
		} finally {
			if (result) {
				setVlan(uuid, currentVM.getVmVlan(), conn);
				String hostAddress = getHostAddress(hostUuid);
				int port = getVNCPort(uuid, poolUuid);
				loggerConfig.logDebug("Override Token: Token [" + uuid.substring(0, 8)
						+ "] Host Address [" + hostAddress + "] Port [" + port
						+ "]");
				NoVNC.createToken(uuid.substring(0, 8), hostAddress, port);
			}
		}
		return result;
	}
    
	
	private void restartVm(int userId, String uuid, String force,
			String poolUuid) {
		Date startTime = new Date();
		boolean result = this.dostartVM(uuid,force, poolUuid);
		//第一次操作没有成功
		if (!result) {
			//修复资源池
			compensator.compensate(poolUuid);
			//再次执行操作
			result = this.dostartVM(uuid,force, poolUuid);
		}
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.INSTANCE, 
				"i-" + uuid.substring(0, 8)); 
		if (result) {
			message.editRowStatus(userId, uuid, "running", "已恢复");
			message.editRowConsole(userId, uuid, "add");
			OCLog log = logRecord.addSuccessLog(userId, LogObject.INSTANCE, 
					LogAction.RESTARTED, 
					infoArray.toString(), startTime, endTime); 		
			message.pushSuccess(userId, log.toString());
		} else {
			message.editRowStatus(userId, uuid, "stopped", "已关机");
			OCLog log = logRecord.addFailedLog(userId, LogObject.INSTANCE, 
					LogAction.RESTARTED, 
					infoArray.toString(), startTime, endTime); 		
			message.pushError(userId, log.toString());
		}
	}
	private void pauseVm(int userId, String uuid, String force,
			String poolUuid) {
		Date startTime = new Date();
		boolean result = this.doPauseVM(uuid, force, poolUuid);
	
		//第一次操作没有成功
		if (!result) {
			//修复资源池
			compensator.compensate(poolUuid);
			//再次执行操作
			result = this.doPauseVM(uuid, force, poolUuid);
		}
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.INSTANCE,
				"i-" + uuid.substring(0, 8));
		if (result) {
			message.editRowStatus(userId, uuid, "stopped", "已暂停");
			OCLog log = logRecord.addSuccessLog(userId, LogObject.INSTANCE,
					LogAction.PAUSE,infoArray.toString(), startTime, endTime);
			message.pushSuccess(userId, log.toString());
		} else {
			message.editRowStatus(userId, uuid, "running", "正常运行");
			message.editRowConsole(userId, uuid, "add");
			OCLog log = logRecord.addFailedLog(userId, LogObject.INSTANCE,
					LogAction.PAUSE,infoArray.toString(), startTime, endTime);
			message.pushError(userId, log.toString());
		}
	}
	private void shutdownVM(int userId, String uuid, String force,
			String poolUuid) {
		Date startTime = new Date();
		boolean result = this.doShutdownVM(uuid, force, poolUuid);
		//第一次操作没有成功
		if (!result) {
			//修复资源池
			compensator.compensate(poolUuid);
			//再次执行操作
			result = this.doShutdownVM(uuid, force, poolUuid);
		}
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.INSTANCE,
				"i-" + uuid.substring(0, 8));
		if (result) {
			message.editRowStatus(userId, uuid, "stopped", "已关机");
			OCLog log = logRecord.addSuccessLog(userId, LogObject.INSTANCE,
					LogAction.SHUTDOWN, 
					infoArray.toString(), startTime, endTime);
			message.pushSuccess(userId, log.toString());
		} else {
			message.editRowStatus(userId, uuid, "running", "正常运行");
			message.editRowConsole(userId, uuid, "add");
			OCLog log = logRecord.addFailedLog(userId, LogObject.INSTANCE,
					LogAction.SHUTDOWN, 
					infoArray.toString(), startTime, endTime);
			message.pushError(userId, log.toString());
		}
	}

	
	
	private boolean dostartVM(String uuid, String force, String poolUuid) {
		boolean result = false;
		String hostUuid = null;
		String powerState = null;
		OCVM currentVM = null;
		Connection conn = null;
		try {
			currentVM = vmService.getVm(uuid);
			if (currentVM != null) {
				if (vmService.updateStatusOfPower(uuid, PowerConstant.POWER_BOOT)) {
					conn = constant.getConnectionFromPool(poolUuid);
					VM thisVM = VM.getByUuid(conn, uuid);
					powerState = thisVM.getPowerState(conn).toString();
					if (!powerState.equals("Running")) {
						hostUuid = getAllocateHost(conn, currentVM.getVmMem());
						
						thisVM.unpause(conn);
					} else {
						hostUuid = thisVM.getResidentOn(conn).toWireString();
					}
					vmService.updateHostUuidOfVM(uuid, hostUuid);
					vmService.updateStatusOfPower(uuid,PowerConstant.POWER_RUNNING);
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (powerState != null) {
				if (powerState.equals("Running")) {
					vmService.updateStatusOfPower(uuid, PowerConstant.POWER_RUNNING);
				} else {
					vmService.updateStatusOfPower(uuid, PowerConstant.POWER_HALTED);
				}
			} else {
				vmService.updateStatusOfPower(uuid, PowerConstant.POWER_HALTED);
			}
		} finally {
			if (result) {
				setVlan(uuid, currentVM.getVmVlan(), conn);
				String hostAddress = getHostAddress(hostUuid);
				int port = getVNCPort(uuid, poolUuid);
				loggerConfig.logDebug("Override Token: Token [" + uuid.substring(0, 8)
						+ "] Host Address [" + hostAddress + "] Port [" + port
						+ "]");
				NoVNC.createToken(uuid.substring(0, 8), hostAddress, port);
			}
		}
		return result;
	}
	private boolean doPauseVM(String uuid, String force, String poolUuid) {
		boolean result = false;
		String powerState = null;
		String hostUuid = null;
		try {
			OCVM currentVM = vmService.getVm(uuid);
			if (currentVM != null) {
				//boolean preShutdownVM = vmService.updateStatusOfPower(uuid,
						//PowerConstant.POWER_SHUTDOWN);
				//if (preShutdownVM ) {
					Connection conn = constant.getConnectionFromPool(
							poolUuid);
					VM thisVM = VM.getByUuid(conn, uuid);
					
					powerState = thisVM.getPowerState(conn).toString();
					hostUuid = thisVM.getResidentOn(conn).toWireString();
					if (powerState.equals("Running")) {
						if (force.equals("true")) {
							thisVM.pause(conn);
							
						} else {
							if (thisVM.cleanShutdown(conn)) {
							} else {
								thisVM.pause(conn);
								
							}
						}
					}
					vmService.updateHostUuidOfVM(uuid, hostUuid);
					vmService.updateStatusOfPower(uuid, PowerConstant.POWER_pause);
					result = true;
				//}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (powerState != null) {
				if (powerState.equals("Running")) {
					vmService.updateStatusOfPower(uuid, PowerConstant.POWER_RUNNING);
				} else {
					vmService.updateStatusOfPower(uuid, PowerConstant.POWER_HALTED);
				}
			} else {
				vmService.updateStatusOfPower(uuid, PowerConstant.POWER_RUNNING);
			}
		} finally {
			if (result == true) {
				NoVNC.deleteToken(uuid.substring(0, 8));
			}
		}
		return result;
	}

	private boolean doShutdownVM(String uuid, String force, String poolUuid) {
		boolean result = false;
		String powerState = null;
		String hostUuid = null;
		try {
			OCVM currentVM = vmService.getVm(uuid);
			if (currentVM != null) {
				boolean preShutdownVM = vmService.updateStatusOfPower(uuid,
						PowerConstant.POWER_SHUTDOWN);
				if (preShutdownVM ) {
					Connection conn = constant.getConnectionFromPool(
							poolUuid);
					VM thisVM = VM.getByUuid(conn, uuid);
					powerState = thisVM.getPowerState(conn).toString();
					hostUuid = thisVM.getResidentOn(conn).toWireString();
					if (powerState.equals("Running")) {
						if (force.equals("true")) {
							thisVM.hardShutdown(conn);
						} else {
							if (thisVM.cleanShutdown(conn)) {
							} else {
								thisVM.hardShutdown(conn);
							}
						}
					}
					vmService.updateHostUuidOfVM(uuid, hostUuid);
					vmService.updateStatusOfPower(uuid, PowerConstant.POWER_HALTED);
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (powerState != null) {
				if (powerState.equals("Running")) {
					vmService.updateStatusOfPower(uuid, PowerConstant.POWER_RUNNING);
				} else {
					vmService.updateStatusOfPower(uuid, PowerConstant.POWER_HALTED);
				}
			} else {
				vmService.updateStatusOfPower(uuid, PowerConstant.POWER_RUNNING);
			}
		} finally {
			if (result == true) {
				NoVNC.deleteToken(uuid.substring(0, 8));
			}
		}
		return result;
	}

	public JSONArray getAdminVMList(int page, int limit, String host,
			int importance, int userid, String type) {
		return vmService.getAdminVMList(page, limit, host, importance, userid, type);
	}

	public JSONArray getISOList(String poolUuid) {
		return isoCore.getISOArray(poolUuid); 
	}

	public synchronized void createVMByISO(String vmUuid, String isoUuid, int isotype, String srUuid,
			String name, int cpu, int memory, int volumeSize, String poolUuid,
			int userId) {
		int memoryCapacity = (int) (memory * 1024);
		Date startTime = new Date();
		boolean result = doCreateVMByISO(vmUuid, isoUuid, isotype, srUuid, name, cpu,
				memoryCapacity, volumeSize, poolUuid, userId);
		//第一次操作没有成功
		if (!result) {
			//修复资源池
			compensator.compensate(poolUuid);
			//再次执行操作
			result = doCreateVMByISO(vmUuid, isoUuid, isotype, srUuid, name, cpu,
					memoryCapacity, volumeSize, poolUuid, userId);
		}
		// write log and push message
		Date endTime = new Date();
		JSONArray infoArray = new JSONArray();
		infoArray.put(TimeUtils.createLogInfo(
				LogRole.INSTANCE,
				"i-" + vmUuid.substring(0, 8)));
		infoArray.put(TimeUtils.createLogInfo("配置", cpu + " 核， " + memory + " GB"));
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.INSTANCE, 
					LogAction.CREATE, 
					infoArray.toString(), startTime, endTime);
			message.editRowStatus(userId, vmUuid, "running", "正常运行");
			message.editRowConsole(userId, vmUuid, "add");
			message.pushSuccess(userId, log.toString());
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.INSTANCE, 
					LogAction.CREATE, 
					infoArray.toString(), startTime, endTime);
			message.deleteRow(userId, vmUuid);
			message.pushError(userId, log.toString());
		}
	}

	private boolean doCreateVMByISO(String vmUuid, String isoUuid, int isotype,
			String srUuid, String name, int cpu, int memory, int volumeSize,
			String poolUuid, int userId) {
		Connection conn = null;
		boolean result = false;
		boolean preCreate = false;
		boolean dbRollback = true;
		try {
			preCreate = vmService
					.preCreateVM(vmUuid, null, userId, name, isotype, null, memory,
							cpu, PowerConstant.POWER_CREATE, 1, new Date());
			if (preCreate) {
				conn = constant.getConnectionFromPool(poolUuid);
				String hostUuid = this.getAllocateHost(conn, memory);
				// Get Disk
				SR sr = SR.getByUuid(conn, srUuid);
				String type = sr.getType(conn);
				// Create VM By ISO
				VM.Record record = VM.createVMFromISO(vmUuid,
						"i-" + vmUuid.substring(0, 8), cpu, memory, conn,
						hostUuid, isoUuid, volumeSize, srUuid, type);
				if (record != null) {
					OCVM ocvm = vmService.getVm(vmUuid);
					ocvm.setVmPwd("onceas");
					ocvm.setVmPower(VMPower.POWER_RUNNING);
					ocvm.setVmHostUuid(hostUuid);
					result = vmService.updateVm(ocvm);
					String hostAddress = getHostAddress(hostUuid);
					int port = getVNCPort(vmUuid, poolUuid);
					NoVNC.createToken(vmUuid.substring(0, 8), hostAddress, port);
					dbRollback = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbRollback) {
				vmService.removeVm(userId, vmUuid);
			}
		}
		return result;
	}

	private String getPoolUuid(String uuid, String type) {
		String poolUuid = "";
		if (type.equals("instance")) {
			poolUuid = vmService.getPoolUuidOfVM(uuid);
		}
		return poolUuid;
	}

	public boolean addMac(String uuid, String type, String physical,
			String vnetid) {
		String poolUuid = this.getPoolUuid(uuid, type);
		if (!poolUuid.equals("")) {
			String mac = vmCore.addMAC(poolUuid, uuid, physical, vnetid);
			OCVM ocvm = vmService.getVm(uuid);
			ocvm.setVmMac(ocvm.getVmMac() + ", " + mac);
			vmService.updateVm(ocvm);
			return true;
		} else {
			return false;
		}
	}

	/*public boolean modifyVnet(String uuid, String type, String vnetid,
			String vifUuid) {
		String poolUuid = this.getPoolUuid(uuid, type);
		if (!poolUuid.equals("")) {
			Connection conn = null;
			try {
				conn = systemConstant.getConnectionFromPool(poolUuid);
				VM vm = VM.getByUuid(conn, uuid);
				VIF vif = VIF.getByUuid(conn, vifUuid);
				vm.setTag(conn, vif, vnetid);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		} else {
			return false;
		}
	}*/

	public boolean modifyPhysical(String uuid, String type, String physical,
			String vifUuid) {
		String poolUuid = this.getPoolUuid(uuid, type);
		if (!poolUuid.equals("")) {
			Connection conn = null;
			try {
				conn = constant.getConnectionFromPool(poolUuid);
				VM vm = VM.getByUuid(conn, uuid);
				VIF vif = VIF.getByUuid(conn, vifUuid);
				vif.set_physical_network(conn, vm, physical);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteMac(String uuid, String type, String vifUuid) {
		String poolUuid = this.getPoolUuid(uuid, type);
		if (!poolUuid.equals("")) {
			Connection conn = null;
			String deleteMac = "";
			try {
				conn = constant.getConnectionFromPool(poolUuid);
				VM vm = VM.getByUuid(conn, uuid);
				VIF vif = VIF.getByUuid(conn, vifUuid);
				VIF.Record record = vm.getVIFRecord(conn, vif);
				deleteMac = record.MAC;
				vm.destroyVIF(conn, vif);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			OCVM ocvm = vmService.getVm(uuid);
			String[] macs = ocvm.getVmMac().split(",");
			for (String mac:macs) {
				if (!mac.equals(deleteMac)) {
					ocvm.setVmMac(mac + ",");
				}
			}
			vmService.updateVm(ocvm);
			return true;
		} else {
			return false;
		}
	}

	public JSONArray getMacs(String uuid, String type) {
		String poolUuid = this.getPoolUuid(uuid, type);
		JSONArray ja = new JSONArray();
		if (!poolUuid.equals("")) {
			Connection conn = null;
			try {
				conn = constant.getConnectionFromPool(poolUuid);
				VM vm = VM.getByUuid(conn, uuid);
				Set<VIF> vifset = vm.getVIFs(conn);
				for (VIF vif : vifset) {
					JSONObject jo = new JSONObject();
					VIF.Record record = vm.getVIFRecord(conn, vif);
					String tag = vm.getTag(conn, vif);
					Network.Record record2 = vm.getNetworkRecord(conn, vif);
					jo.put("vifuuid", record.uuid);
					jo.put("device", record.device);
					jo.put("mac", record.MAC);
					jo.put("tag", tag);
					jo.put("physical", record2.nameLabel);
					ja.put(jo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ja;
	}

	public JSONArray getNets(String uuid, String type) {
		JSONArray ja = new JSONArray();
		String poolUuid = this.getPoolUuid(uuid, type);
		if (!poolUuid.equals("")) {
			Connection conn = null;
			try {
				conn = constant.getConnectionFromPool(poolUuid);
				Map<Network, Network.Record> map = Network.getAllRecords(conn);
				Collection<Network.Record> collection = map.values();
				Iterator<Network.Record> it = collection.iterator();
				while (it.hasNext()) {
					JSONObject jo = new JSONObject();
					jo.put("nets", it.next().nameLabel);
					ja.put(jo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ja;
	}

	public void saveToDataBase(String vmUuid, String vmPWD, int vmUID,
			int vmPlatform, String vmName, String vmIP) {
		Connection conn = null;
		try {
			conn = constant.getConnection(vmUID);
			VM vm = VM.getByUuid(conn, vmUuid);
			VM.Record record = vm.getRecord(conn);
			String vmMac = null;
			for (String temMac : record.MAC) {
				vmMac = temMac;
				break;
			}
			OCVM ocvm = new OCVM();
			ocvm.setVmUuid(vmUuid);
			ocvm.setVmPwd(vmPWD);
			ocvm.setVmUid(vmUID);
			ocvm.setVmName(vmName);
			ocvm.setVmPlatform(VMPlatform.values()[vmPlatform]);
			ocvm.setVmMac(vmMac);
			ocvm.setVmMem((int) (record.memoryStaticMax / (1024 * 1024)));
			ocvm.setVmCpu((record.VCPUsMax).intValue());
			ocvm.setVmPower(VMPower.POWER_RUNNING);
			ocvm.setVmStatus(VMStatus.EXIST);
			ocvm.setVmHostUuid(record.residentOn.toWireString());
			ocvm.setVmCreateDate(new Date());
			vmService.saveVM(ocvm);
		} catch (BadServerResponse e) {
			e.printStackTrace();
		} catch (XenAPIException e) {
			e.printStackTrace();
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
	}

	public boolean templateToVM(String uuid, int userId) {
		Date startTime = new Date();
		boolean result = doTemplateToVM(uuid);
		// write log and push message
		Date endTime = new Date();
		JSONArray infoArray = new JSONArray();
		infoArray.put(TimeUtils.createLogInfo(
				LogRole.IMAGE,
				"image-" + uuid.substring(0, 8)));
		infoArray.put(TimeUtils.createLogInfo("转换","主机" + "i-" + uuid.substring(0, 8)));
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.IMAGE, 
					LogAction.CHANGE,
					infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userId, log.toString());	
			return true;
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.IMAGE, 
					LogAction.CHANGE,
					infoArray.toString(), startTime, endTime); 
			message.pushError(userId, log.toString());
			return false;
		}
	}
	
	private boolean doTemplateToVM(String uuid) {
		Image image = imageService.getImage(uuid);
		String poolUuid = image.getPoolUuid();
		Connection conn = null;
		boolean result = false;
		try {
			conn = constant.getConnectionFromPool(poolUuid);
			VM vm = VM.getByUuid(conn, uuid);
			VM.Record record = vm.getRecord(conn);
			vm.setIsATemplate(conn, false);
			String vmMac = null;
			for (String temMac : record.MAC) {
				vmMac = temMac;
				break;
			}
			image.setImageStatus(ImageStatus.DELETED);
			imageService.updateImage(image);
			OCVM ocvm = new OCVM(uuid, image.getImagePwd(),null,
					image.getImageUID(), image.getImageName(),
					VMPlatform.values()[image.getImagePlatform().ordinal()], vmMac,
					(int) (record.memoryStaticMax / (1024 * 1024)),
					(record.VCPUsMax).intValue(), VMPower.POWER_HALTED,VMStatus.EXIST, image.getCreateDate(),null);
			String hostUuid = record.residentOn.toWireString();
			ocvm.setVmHostUuid(hostUuid);
			vmService.saveVM(ocvm);
			String hostAddress = getHostAddress(hostUuid);
			int port = getVNCPort(uuid, poolUuid);
			NoVNC.createToken(uuid.substring(0, 8), hostAddress, port);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean vmToTemplate(String uuid, int userId) {
		Date startTime = new Date();
		boolean result = doVmToTemplate(uuid);
		// write log and push message
		Date endTime = new Date();
		JSONArray infoArray = new JSONArray();
		infoArray.put(TimeUtils.createLogInfo(
				LogRole.INSTANCE,
				"i-" + uuid.substring(0, 8)));
		infoArray.put(TimeUtils.createLogInfo("转换","映像" + "image-" + uuid.substring(0, 8)));
		if (result) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.INSTANCE, 
					LogAction.CHANGE, 
					infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userId, log.toString());
			return true;
		} else {
			
			OCLog log = logRecord.addFailedLog(userId, LogObject.INSTANCE, 
					LogAction.CHANGE, 
					infoArray.toString(), startTime, endTime); 
			message.pushError(userId, log.toString());
			return false;
		}
	}
	
	private boolean doVmToTemplate(String uuid) {
		boolean result = false;
		if (imageService.checkImage(uuid)) {
			OCVM ocvm = vmService.getVm(uuid);
			Image image = imageService.getImage(uuid);
			String poolUuid = image.getPoolUuid();
			Connection conn = null;
			try {
				conn = constant.getConnectionFromPool(poolUuid);
				VM vm = VM.getByUuid(conn, uuid);
				vm.setNameLabel(conn, "image" + uuid.substring(0, 8));
				vm.setIsATemplate(conn, true);
				image.setImageStatus(ImageStatus.EXIST);
				image.setHostUuid(ocvm.getVmHostUuid());
				imageService.updateImage(image);
				vmService.removeVm(ocvm);
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		} else {
			OCVM ocvm = vmService.getVm(uuid);
			String poolUuid = vmService.getPoolUuidOfVM(uuid);
			Connection conn = null;
			try {
				conn = constant.getConnectionFromPool(poolUuid);
				VM vm = VM.getByUuid(conn, uuid);
				vm.setIsATemplate(conn, true);
				Image image = new Image(ocvm.getVmUuid(), ocvm.getVmName(),
						ocvm.getVmPwd(), ocvm.getVmUid(), 20,
						ImagePlatform.values()[ocvm.getVmPlatform().ordinal()], ImageStatus.EXIST, poolUuid, ocvm.getVmDesc(),
						ocvm.getVmHostUuid(),ocvm.getVmCreateDate(), null);
				
				imageService.saveImage(image);
				vmService.removeVm(ocvm);
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	}

	public boolean migrate(int userId, String uuid, String tarHost,
			String content, String conid) {
		Connection conn = null;
		OCVM ocvm = vmService.getVm(uuid); 
		OCHost ocHost = hostService.getHostById(tarHost);
		try {
			conn = constant.getConnection(userId);
			VM vm = VM.getByUuid(conn, uuid);
			VM.Record record = vm.getRecord(conn);
			double vmMemory = 0;
			if (record != null) {
				vmMemory = record.memoryDynamicMax;
			}
			Host host = Host.getByUuid(conn, tarHost);
			Host.Record hrecord = host.getRecord(conn);
			double freemem = hrecord.memoryFree;
			if (freemem > vmMemory) {
				try {
					Map<String, String> op = new HashMap<String, String>();
					vm.poolMigrate(conn, host, op);
					ocvm.setVmHostUuid(tarHost);
					if (vmService.updateVm(ocvm)) {
						message.pushClose(userId, content, conid);
						message.pushSuccess(userId, "迁移成功");
						return true;
					} else {
						message.pushClose(userId, content, conid);
						message.pushError(userId, "数据信息更新失败");
						return false;
					}
				} catch (Exception e) {
					message.pushClose(userId, content, conid);
					message.pushError(userId, "迁移中出现未知错误");
					e.printStackTrace();
					return false;
				}
			} else {
				message.pushClose(userId, content, conid);
				message.pushError(userId, "内存空间不足，迁移失败");
				return false;
			}
		} catch (Exception e) {
			message.pushClose(userId, content, conid);
			message.pushError(userId, "无法建立连接");
			e.printStackTrace();
			return false;
		}finally{
			int port = getVNCPort(uuid, ocHost.getPoolUuid());
			NoVNC.deleteToken(uuid.substring(0, 8));
			NoVNC.createToken(uuid.substring(0, 8), ocHost.getHostIP(), port);
		}
	}
	
	public int getDiskLimit(String vmUuid, String type, String ioType) {
		Connection connection = null;
		int speed = 100;
		String poolUuid = this.getPoolUuid(vmUuid, "instance");				
		try {
			connection = constant.getConnectionFromPool(poolUuid);
			VM vm = Types.toVM(vmUuid);
			speed = vm.getDiskIORateLimit(connection, type, ioType);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return speed;
	}
	
	public boolean limitDisk(String vmUuid, int rate, String type, String ioType, int userID) {
		Connection connection = null;
		String poolUuid = this.getPoolUuid(vmUuid, "instance");				
		try {
			connection = constant.getConnectionFromPool(poolUuid);
			VM vm = VM.getByUuid(connection, vmUuid);
			vm.setDiskIORateLimit(connection, type, String.valueOf(rate), ioType);
			message.pushSuccess(userID, "硬盘限速成功");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			message.pushError(userID, "硬盘限速失败");
			return false;
		}
	}
	
	public boolean clearLimit(String vmUuid, int userID) {
		Connection connection = null;
		String poolUuid = this.getPoolUuid(vmUuid, "instance");				
		try {
			connection = constant.getConnectionFromPool(poolUuid);
			VM vm = Types.toVM(vmUuid);
			vm.clearIORateLimit(connection, "read", "MBps");
			vm.clearIORateLimit(connection, "read", "iops");
			vm.clearIORateLimit(connection, "write", "MBps");
			vm.clearIORateLimit(connection, "write", "iops");
			message.pushSuccess(userID, "取消硬盘限速成功");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			message.pushError(userID, "取消硬盘限速失败");
			return false;
		}
	}

	public boolean limitNetwork(String vmUuid, String vifUuid, int rate,
			String type, int userID) {
		Connection connection = null;
		String poolUuid = this.getPoolUuid(vmUuid, "instance");		
		try {
			connection = constant.getConnectionFromPool(poolUuid);
			VM vm = VM.getByUuid(connection, vmUuid);
			vm.setNetworkIORate(connection, type,
					VIF.getByUuid(connection, vifUuid), String.valueOf(rate));
			message.pushSuccess(userID, "网卡限速成功");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			message.pushError(userID, "网卡限速失败");
			return false;
		}
	}

	public Map<String, Object> getMaxCpuAndMemByPoolUuid(String poolUuid) {
		return poolService.getMaxCpuAndMemOfPool(poolUuid); 
	}

	public int countVMsOfHost(String hostUuid) {
		return vmService.countVMNumberOfHost(hostUuid);
	}

	public int countVMsOfVlan(String netId) {
		return vmService.countVlanNumberOfVM(netId);
	}

	public JSONArray getSwitchListByvm(String vmid, int type) {
		JSONArray ja = null;
		try {
			ja = networkService.getNetworkListByPoolUuid(type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja;
	}

	public boolean joinVlan(int userId, String vmid, String vifid, int switchid, int vlanid, int type) {
		boolean result = false;
		OCVM ocvm = vmService.getVm(vmid);
		OCNetwork network = new OCNetwork();
		String networkId = "";
		String hostUuid = ocvm.getVmHostUuid();
		String poolUuid = hostService.getHostById(hostUuid).getPoolUuid();
		if (!poolUuid.equals("")) {
			Connection conn = null;
			try {
				conn = constant.getConnectionFromPool(poolUuid);
				VM vm = VM.getByUuid(conn, vmid);
				VIF vif = Types.toVIF(vifid);
				VIF.Record record = vm.getVIFRecord(conn, vif);
				Date startTime = new Date();
				Set<VIF> vifs = vm.getVIFs(conn);
				String[] vlans = ocvm.getVmVlan().split(",");
				if (vifs.size()== 1) {
					ocvm.setVmVlan(String.valueOf(switchid));
				}else {
					for(int i=0; i < vlans.length; i++){
						if (vlans[i].equals("-1")) {
							network = networkService.getVnetwork("1");
						}else {
							network = networkService.getVnetwork(vlans[i]);
						}
						Iterator<VIF> iterator = vifs.iterator();
						while (iterator.hasNext()) {
							VIF vif2 = iterator.next();
							VIF.Record record2 = vm.getVIFRecord(conn, vif2);
							if (record.MAC.equals(record2.MAC)) {
								if (type == network.getvlanType() && (vm.getTag(conn, vif2).equals(String.valueOf(network.getvlanId())))){
									vlans[i] = String.valueOf(switchid);
									iterator.remove();
								}
							}
						}
					}
					for (String vlan : vlans) {
						networkId += vlan + ",";
					}
					ocvm.setVmVlan(networkId);
				}
				vm.setTag(conn, vif, String.valueOf(vlanid), switchTypeToWords(type));
				result = vmService.updateVm(ocvm);
				joinNetworkLog(startTime, String.valueOf(vlanid), userId, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return result;
	}
	
	/**
	 * @author lining
	 * @param startTime poolUuid userId result
	 * 日志操作和推送消息
	 * 
	 */
	private void joinNetworkLog(Date startTime, String netId, int userId, boolean flag){
		Date endTime = new Date();
		JSONArray infoArray = logRecord.createLoginfos(LogRole.NETWORK, netId); 
		if (flag) {
			OCLog log = logRecord.addSuccessLog(userId, LogObject.NETWORK, 
					LogAction.ADD, 
					infoArray.toString(), startTime, endTime); 
			message.pushSuccess(userId, log.toString());		
		} else {
			OCLog log = logRecord.addFailedLog(userId, LogObject.NETWORK, 
					LogAction.ADD, 
					infoArray.toString(), startTime, endTime); 
			message.pushError(userId, log.toString());		
		}
	}
	
	private String switchTypeToWords(int type){
		if (type == 1) {
			return SwitchType.SWITCH;
		}else {
			return SwitchType.DISTRIBUTED;
		}
	}
	
	public boolean checkVMStatus(int userId, String vmUuid) {
		boolean result = false;
		Connection conn = null;
		try {
			conn = constant.getConnection(userId);
			VM thisVM = VM.getByUuid(conn, vmUuid);
			if (thisVM != null) {
				int currentPowerStatus=0;
				
				String hostuuid=thisVM.getRecord(conn).residentOn.toWireString();
				String status=thisVM.getPowerState(conn).toString();
				switch (status) {
				case "Running":
					currentPowerStatus=VMPower.POWER_RUNNING.ordinal();
					break;
				case "Paused":
					currentPowerStatus=VMPower.POWER_PAUSE.ordinal();
					break;
				default:
					currentPowerStatus=VMPower.POWER_HALTED.ordinal();
					break;
				}
				
				OCVM ocvm = vmService.getVm(vmUuid);
				//校验所在主机
				if(!(hostuuid==null||"".equals(hostuuid))){
					if(!hostuuid.equals(ocvm.getVmHostUuid())){
						vmService.updateHostUuidOfVM(vmUuid, hostuuid);
					}
				}
				//校验电源状态
				int powerStatus = ocvm.getVmPower().ordinal();
				if (currentPowerStatus != powerStatus) {
					vmService.updateStatusOfPower(vmUuid, currentPowerStatus);
				}
				
				result = true;
			}else {
				result = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (result) {
			message.pushSuccess(userId, "虚拟机状态校验成功！");
		}else {
			message.pushError(userId, "虚拟机状态校验失败！");
		}
		return result;
	}
	
	private String getHostAddress(String hostUuid) {
		String hostIP = null;
		OCHost host = hostService.getHostById(hostUuid);
		if (host != null) {
			hostIP = host.getHostIP();
		}
		return hostIP;
	}

	private int getVNCPort(String uuid, String poolUuid) {
		int port = 0;
		try {
			VM vm = Types.toVM(uuid);
			String location = vm.getVNCLocation(constant.getConnectionFromPool(poolUuid));
			port = 5900;
			int len = location.length();
			if (len > 5 && location.charAt(len - 5) == ':') {
				port = Integer.parseInt(location.substring(len - 4));
			}
		} catch (Exception e) {
		}
		return port;
	}

	private boolean setVlan(String uuid, String vlanId, Connection c) {
		try {
			VM vm = VM.getByUuid(c, uuid);
			vm.setTag(c, vm.getVIFs(c).iterator().next(),
					vlanId, SwitchType.SWITCH);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private String getAllocateHost(Connection conn, int memory) {
		String host = null;
		try {
			Map<Host, Host.Record> hostMap = Host.getAllRecords(conn);
			long maxFree = 0;
			for (Host thisHost : hostMap.keySet()) {
				Host.Record hostRecord = hostMap.get(thisHost);
				long memoryFree = hostRecord.memoryFree;
				if (memoryFree > maxFree) {
					maxFree = memoryFree;
					host = thisHost.toWireString();
				}
			}
			if ((int) (maxFree / MB) >= memory) {
				return host;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getAdminVMTop(int num) {
		return vmService.getVMCountTop(num);
	}

	@Override
	public Integer getSumVMCount(Date endTime) {
		return vmService.getSumVMCount(endTime);
	}

	@Override
	public Integer getVlanVMCount() {
		return vmService.getVlanVMCount();
	}

	@Override
	public JSONObject exportVM() {
		return vmService.exportVM();
	}
	@Override
	public OCVM getVMbyId(String uuid) {
		return vmService.getVm(uuid);
	}

	@Override
	public JSONObject distributeVM2User(String vmuuid, int userid) {
		return vmService.distributeVM2User(vmuuid,userid);
	}
	
	public Record createVMOnHost(Connection c, String vmUuid, String tplUuid,
			String loginName, String loginPwd, long cpuCore,
			long memoryCapacity, String mac, String ip, String OS,
			String hostUuid, String imagePwd, String vmName, boolean ping) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cpuNumber", cpuCore);
		map.put("memoryValue", memoryCapacity);
		map.put("newUuid", vmUuid);
		map.put("MAC", mac);
		map.put("IP", ip);
		map.put("type", OS);
		map.put("passwd", loginPwd);
		map.put("origin_passwd", imagePwd);
		Host host = Types.toHost(hostUuid);
		Record vmrecord=null;
		try {
			vmrecord = VM.createOnFromTemplate(c, host, tplUuid, vmName,
					map, ping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vmrecord;
	}
	
	public String getAllocateHost(String poolUuid, int memory) {
		String host = null;
		try {
			Connection conn = constant.getConnectionFromPool(poolUuid);
			Map<Host, Host.Record> hostMap = Host.getAllRecords(conn);
			long maxFree = 0;
			for (Host thisHost : hostMap.keySet()) {
				Host.Record hostRecord = hostMap.get(thisHost);
				long memoryFree = hostRecord.memoryFree;
				if (memoryFree > maxFree) {
					maxFree = memoryFree;
					host = thisHost.toWireString();
				}
			}
			if ((int) (maxFree / MB) >= memory) {
				return host;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public void assginIpAddress(Connection c, String url, String subnet,
			String vnUuid) {
		List<OCVM> vmList = vmDAO.getVmListByVlan(vnUuid);
		if (vmList != null) {
			try {
				for (OCVM vm : vmList) {
					String mac = vm.getVmMac();
					Host.assignIpAddress(c, url, mac, subnet);
					restartNetwork(c, vm.getVmUuid(), false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean restartNetwork(Connection c, String vmuuid, boolean sync) {
		boolean result = false;
		try {
			VM temVM = VM.getByUuid(c, vmuuid);
			JSONObject temjo = new JSONObject();
			temjo.put("requestType", "Agent.RestartNetwork");
			temVM.sendRequestViaSerial(c, temjo.toString(), sync);
		} catch (BadServerResponse e) {
			e.printStackTrace();
		} catch (XenAPIException e) {
			e.printStackTrace();
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean add2Router(String vmuuid, int netid, String routerid,
			String ip, String netmask, String gateway) {
		boolean returnflag=false;
		
		OCNetwork network=networkDAO.getSwitch(String.valueOf(netid));
		String vlanid=String.valueOf(network.getvlanId());
		//创建网卡
		String poolUuid = this.getPoolUuid(vmuuid, "instance");
		if (!poolUuid.equals("")) {
			Connection conn = null;
			String mac = "";
			try {
				conn = constant.getConnectionFromPool(poolUuid);
				VM vm = VM.getByUuid(conn, vmuuid);
				mac = TimeUtils.randomMac();
				//创建网卡
				VIF vif = VIF.createBindToPhysicalNetwork(conn, vm, SwitchType.DISTRIBUTED,
						mac);
				//绑定Vxlan
				vm.setTag(conn, vif, vlanid, SwitchType.DISTRIBUTED);
				//修改ip
				JSONObject obj=new JSONObject();
				obj.put("requestType", "Network.EnableStatic");
				obj.put("mac",mac);
				obj.put("ipAddress",ip);
				obj.put("netmask",netmask);
				obj.put("gateway", gateway);
				boolean vmresult=false;
				try {
					vmresult=vm.sendRequestViaSerial(conn, obj.toString(), false);
				} catch (Exception e) {
					logger.error("rt"+vmuuid.substring(0, 8)+"修改ip异常!");
					e.printStackTrace();
				}
				//重启网络
				if(vmresult){
					JSONObject netobj=new JSONObject();
					netobj.put("requestType", "Network.RestartNetwork");
					boolean restartwork=false;
					try {
						restartwork=vm.sendRequestViaSerial(conn, obj.toString(), false);
					} catch (Exception e) {
						logger.error("rt"+vmuuid.substring(0, 8)+"重启网络异常!");
						e.printStackTrace();
					}
					
					if(restartwork){
						OCVM ocvm = vmService.getVm(vmuuid);
						ocvm.setVmMac(ocvm.getVmMac() + "," + mac);
						ocvm.setVmVlan(ocvm.getVmVlan()+","+netid);
						vmService.updateVm(ocvm);
						returnflag=true;
					}else{
						logger.error("rt"+vmuuid.substring(0, 8)+"重启网络失败!");
					}
				}else{
					logger.error("rt"+vmuuid.substring(0, 8)+"修改ip失败!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return returnflag;
	}
	
	public boolean offmigrate(int userId, String uuid, String tarHost,
			String content, String conid){
		boolean result = false;
		Connection conn = null;
		String powerState = null;
		OCVM ocvm = vmService.getVm(uuid); 
		if(ocvm==null){
			return false;
		}
		//获取数据库中主机对象
		OCHost ocHost = hostService.getHostById(tarHost);
		String poolUuid=ocHost.getPoolUuid();
		
		try {
			conn = constant.getConnection(userId);
			VM vm = VM.getByUuid(conn, uuid);
			VM.Record record = vm.getRecord(conn);
			
			//判断迁移的主机内存是否可用
			double vmMemory = 0;
			if (record != null) {
				vmMemory = record.memoryDynamicMax;
			}
			Host host = Host.getByUuid(conn, tarHost);
			Host.Record hrecord = host.getRecord(conn);
			double freemem = hrecord.memoryFree;
			if (freemem > vmMemory) {
				//启动虚拟机操作
				try {
					if (ocvm != null) {
						if (vmService.updateStatusOfPower(uuid, PowerConstant.POWER_BOOT)) {
							powerState = vm.getPowerState(conn).toString();
							if (!powerState.equals("Running")) {
								Host allocateHost = hostCore.getHost(tarHost);
								logger.info("VM"+uuid+":开始启动虚拟机");
								vm.startOn(conn, allocateHost, false, true);
								logger.info("VM"+uuid+":更新hostuuid数据信息");
								vmService.updateHostUuidOfVM(uuid, tarHost);
								if(vmService.updateStatusOfPower(uuid,PowerConstant.POWER_RUNNING)){
									logger.info("VM"+uuid+":迁移成功");
									message.pushClose(userId, content, conid);
									message.pushSuccess(userId, "迁移成功");
									result = true;
								} else {
									logger.info("VM"+uuid+":更新电源状态失败");
									message.pushClose(userId, content, conid);
									message.pushError(userId, "数据信息更新失败");
								}
							}else{
								logger.info("VM"+uuid+":电源处于开机状态，不能离线迁移");
								message.pushClose(userId, content, conid);
								message.pushSuccess(userId, "电源处于开机状态，不能离线迁移");
							}
						}
					}
					
				} catch (Exception e) {
					logger.error("迁移中出现未知错误", e);
					message.pushClose(userId, content, conid);
					message.pushError(userId, "迁移中出现未知错误");
					if (powerState != null) {
						if (powerState.equals("Running")) {
							vmService.updateStatusOfPower(uuid, PowerConstant.POWER_RUNNING);
						} else {
							vmService.updateStatusOfPower(uuid, PowerConstant.POWER_HALTED);
						}
					} else {
						vmService.updateStatusOfPower(uuid, PowerConstant.POWER_HALTED);
					}
				} finally {
					if (result) {
						setVlan(uuid, ocvm.getVmVlan(), conn);
						String hostAddress = getHostAddress(tarHost);
						int port = getVNCPort(uuid, poolUuid);
						loggerConfig.logDebug("Override Token: Token [" + uuid.substring(0, 8)
								+ "] Host Address [" + hostAddress + "] Port [" + port
								+ "]");
						NoVNC.createToken(uuid.substring(0, 8), hostAddress, port);
					}
				}
				
			} else {
				logger.info("Host-"+tarHost+":内存空间不足，迁移失败");
				message.pushClose(userId, content, conid);
				message.pushError(userId, "内存空间不足，迁移失败");
				return false;
			}
		} catch (Exception e) {
			logger.error("userid-"+userId+":无法建立连接", e);
			message.pushClose(userId, content, conid);
			message.pushError(userId, "无法建立连接");
			return false;
		}finally{
			int port = getVNCPort(uuid, ocHost.getPoolUuid());
			NoVNC.deleteToken(uuid.substring(0, 8));
			NoVNC.createToken(uuid.substring(0, 8), ocHost.getHostIP(), port);
		}
		
		
		return result;
	}

	@Override
	public JSONArray getUsbList(String uuid, String type) {
		JSONArray ja = new JSONArray();
		
		Connection conn = null;
		String poolUuid = this.getPoolUuid(uuid, type);
		if("".equals(poolUuid)){
			logger.info("VM-"+uuid+":poolUuid为"+poolUuid+",获取USB列表失败！");
		}else{
			try {
				conn = constant.getConnectionFromPool(poolUuid);
				VM vm = VM.getByUuid(conn, uuid);
				logger.info("查询usb列表");
				Set<VBD> vbdset=vm.getUsbScsi(conn);
				logger.info("查询usb列表结果长度为："+vbdset.size());
				for (VBD vbd : vbdset) {
					JSONObject jo = new JSONObject();
					VBD.Record record = vm.getVBDRecord(conn, vbd);
					String url=record.userdevice;
					
					
					jo.put("vbduuid", record.uuid);
					jo.put("device", record.device);
					jo.put("address", url.subSequence(4, url.length()));
					jo.put("type",record.type);
					jo.put("format","EXT4");
					jo.put("name","金士顿");
					System.out.println(record.toString());
					
					ja.put(jo);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ja;
	}

	@Override
	public boolean createUSB(String uuid, String type,String address) {
		boolean result=false;
		
		Connection conn = null;
		String poolUuid = this.getPoolUuid(uuid, type);
		if("".equals(poolUuid)){
			logger.info("VM-"+uuid+":poolUuid为"+poolUuid+",挂载usb失败！");
		}else{
			try {
				OCVM ocvm = vmService.getVm(uuid);
				//获取连接
				conn = constant.getConnectionFromPool(poolUuid); 
				//获取主机id
				String hostuuid=ocvm.getVmHostUuid();
				Host host = Types.toHost(hostuuid);
				logger.info("查询主机【"+hostuuid+"】的存储列表");
				Set<String> set=hostSrDAO.getSRList(hostuuid);
				if(set.size()>0){
					String sruuid=set.iterator().next();
					//获取数据库存储对象
					Storage storage=storageDAO.getStorage(sruuid);
					//获取存储对象
					SR sr=Types.toSR(storage.getDiskuuid());
					//获取vm操作对象
					VM vm = VM.getByUuid(conn, uuid);
					result=VMUtil.createUsbScsi(host, vm.getNameLabel(conn), vm, sr, conn,address);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public JSONArray hostUSBlist(String uuid, String type) {
		JSONArray array=new JSONArray();
		
		Connection conn = null;
		String poolUuid = this.getPoolUuid(uuid, type);
		if("".equals(poolUuid)){
			logger.info("VM-"+uuid+":poolUuid为"+poolUuid+",查询主机usb列表！");
		}else{
			try {
				//获取连接
				conn = constant.getConnectionFromPool(poolUuid); 
				
				OCVM ocvm = vmService.getVm(uuid);
				String hostuuid=ocvm.getVmHostUuid();
				Host host = Types.toHost(hostuuid);
				Set<String> arr=host.getAvailUsbScsi(conn);
				for (String string : arr) {
					JSONObject obj = new JSONObject();
					obj.put("key", string);
					array.put(obj);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return array;
	}
	
	@Override
	public boolean deleteUSB(String uuid, String type,String usbid) {
		boolean result=false;
		
		Connection conn = null;
		String poolUuid = this.getPoolUuid(uuid, type);
		if("".equals(poolUuid)){
			logger.info("VM-"+uuid+":poolUuid为"+poolUuid+",移除usb失败！");
		}else{
			try {
				//获取连接
				conn = constant.getConnectionFromPool(poolUuid); 
				//获取vm操作对象
				VM vm = VM.getByUuid(conn, uuid);
				vm.destroyUsbScsi(conn, Types.toVBD(usbid));
				result=true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
