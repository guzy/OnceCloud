package com.oncecloud.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.Host;
import org.Xen.API.Types;
import org.Xen.API.VM;
import org.Xen.API.VMUtil;
import org.Xen.API.Types.BadServerResponse;
import org.Xen.API.Types.XenAPIException;
import org.Xen.API.VM.Record;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.HostDAO;
import com.oncecloud.dao.ImageDAO;
import com.oncecloud.dao.NetworkDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.dao.VDIDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.dao.VolumeDAO;
import com.oncecloud.entity.Image;
import com.oncecloud.entity.OCGroup;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCNetwork;
import com.oncecloud.entity.OCVDI;
import com.oncecloud.entity.OCVM;
import com.oncecloud.entity.User;
import com.oncecloud.log.LogRecord;
import com.oncecloud.message.MessagePush;
import com.oncecloud.message.MessageUtil;
import com.oncecloud.model.ImagePlatform;
import com.oncecloud.model.VMPlatform;
import com.oncecloud.model.VMPower;
import com.oncecloud.model.VMResult;
import com.oncecloud.model.VMStatus;
import com.oncecloud.service.ConnectionService;
import com.oncecloud.service.GroupService;
import com.oncecloud.service.VMService;
import com.oncecloud.util.FileUtil;
import com.oncecloud.util.TimeUtils;
import com.oncecloud.util.Utilities;

@Component("VMService")
public class VMServiceImpl implements VMService {
	
	private final static Logger logger = Logger.getLogger(VMServiceImpl.class);
	@Resource
	private HostDAO hostDAO;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private NetworkDAO networkDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private LogRecord logRecord;
	@Resource
	private MessageUtil message;
	@Resource
	private FileUtil fileHandlerUtil;
	@Resource
	private ImageDAO imageDAO;
	@Resource
	private VolumeDAO volumeDAO;
	@Resource
	private VDIDAO vdiDAO;
	@Resource
	private ConnectionService conService;
	@Resource
	private GroupService groupService;
	@Resource
	private MessagePush messagePush;
	
	public OCVM getVm(String vmUuid) {
		return vmDAO.getVM(vmUuid);
	}
	
	public boolean updateVm(OCVM vm) {
		return vmDAO.updateVM(vm);
	}
	
	public boolean removeVm(Integer userId, String vmUuid) {
		boolean result = false;
		vmDAO.removeVM(userId, vmUuid);
		result = true;
		return result;
	}
	
	public void removeVm(OCVM vm){
		vmDAO.deleteVM(vm, false);
	}
	
	public boolean updateStatusOfPower(String vmUuid, int status) {
		return vmDAO.updatePowerStatus(vmUuid, status);
	}

	public String getPoolUuidOfVM(String vmUuid) {
		String poolUuid = "";
		OCVM ocvm = vmDAO.getVM(vmUuid);
		if (ocvm != null) {
			String hostUuid = ocvm.getVmHostUuid();
			poolUuid = hostDAO.getHost(hostUuid).getPoolUuid();
		}
		return poolUuid;
	}
	
	public void updatePowerAndHost(String uuid, int power, String hostUuid) {
		vmDAO.updatePowerAndHost(uuid, power, hostUuid);
	}

	public boolean updateHostUuidOfVM(String vmUuid, String hostUuid) {
		return vmDAO.updateHostUuid(vmUuid, hostUuid);
	}

	public boolean preCreateVM(String vmUuid, String vmPWD, Integer vmUID,
			String vmName, Integer vmPlatform, String vmMac, Integer vmMem,
			Integer vmCpu, Integer vmPower, Integer vmStatus, Date createDate) {
		return vmDAO.preCreateVM(vmUuid, vmPWD, vmUID, vmName, vmPlatform, vmMac, vmMem, vmCpu, vmPower, vmStatus, createDate);
	}

	public int countVMNumberOfHost(String hostUuid) {
		return vmDAO.countVMsOfHost(hostUuid);
	}

	public int countVlanNumberOfVM(String netId) {
		return vmDAO.countNumberOfVMByVlan(netId);
	}

	public JSONArray getAdminVMList(int page, int limit, String host,
			int importance, int userid, String type) {
		JSONArray ja = new JSONArray();
		int totalNum = vmDAO.countVMsOfAdmin(host, importance, userid);
		List<OCVM> vmList = vmDAO.getOnePageVMsOfAdmin(page, limit,
				host, importance, userid);
		ja.put(totalNum);
		if (vmList != null) {
			for (int i = 0; i < vmList.size(); i++) {
				JSONObject jo = new JSONObject();
				OCVM ocvm = vmList.get(i);
				jo.put("vmid", ocvm.getVmUuid());
				jo.put("vmname", TimeUtils.encodeText(ocvm.getVmName()));
				jo.put("vmUid", ocvm.getVmUid());
				jo.put("state", ocvm.getVmPower().ordinal());
				jo.put("cpu", ocvm.getVmCpu());
				jo.put("memory", ocvm.getVmMem());
				String timeUsed = TimeUtils.encodeText(TimeUtils
						.dateToUsed(ocvm.getVmCreateDate()));
				String[] vlans = ocvm.getVmVlan().split(",");
				String vmVlanString = "";
				for (String vlan : vlans) {
					if (vlan.equals("-1")) {
						vmVlanString = vmVlanString + "vlan号-"+vlan + ",";
					}else {
						OCNetwork network = networkDAO.getSwitch(vlan);
						if (network.getvlanType() == 1) {
							vmVlanString += "vlan号-"+network.getvlanId() + ",";
						}else {
							vmVlanString += "Vxlan号-"+network.getvlanId() + ",";
						}
					}
				}
				jo.put("vlan", vmVlanString);
				jo.put("createdate", timeUsed);
				jo.put("importance", ocvm.getVmImportance());
				User user = userDAO.getUser(ocvm.getVmUid());
				jo.put("userName", TimeUtils.encodeText(user.getUserName()));
				jo.put("level", user.getUserLevel().ordinal());
				ja.put(jo);
			}
		}
		return ja;
	}

	public void saveVM(OCVM vm) {
		vmDAO.saveVM(vm);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> getVMCountTop(int num) {
		List<Map> vmList = vmDAO.getVMCountTop(num);
		return vmList;
	}

	@Override
	public Integer getSumVMCount(Date endTime) {
		return vmDAO.getSumVMCount(endTime);
	}

	@Override
	public Integer getVlanVMCount() {
		return vmDAO.getVlanVMCount();
	}

	@Override
	public JSONObject exportVM() {
		String filename = fileHandlerUtil.exportFile(vmDAO.getAllVmList(), "虚拟机列表.xls", "Excel");
		JSONObject jo = new JSONObject();
		jo.put("filename", filename);
		return jo;
	}

	@Override
	public JSONObject distributeVM2User(String vmuuid, int userid) {
		boolean b = false;
		
		OCVM vm = vmDAO.getVM(vmuuid);
		if(vm==null){
			b=false;
		}else{
			vm.setVmUid(userid);
			b = vmDAO.updateVM(vm);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("issuccess", b);
		return jsonObject;
	}

	@Override
	public String getHostType(String uuid) {
		OCVM vm = vmDAO.getVM(uuid);
		OCHost host = hostDAO.getHost(vm.getVmHostUuid());
		String hosttype=host==null?null:host.getHostType();
		return  hosttype;
	}

	public boolean deleteVM(int userId, String uuid, Connection c) {
		boolean result = false;
		if (vmDAO.updateVMPower(uuid, VMPower.POWER_DESTROY)) {
			try {
				VM thisVM = VM.getByUuid(c, uuid);
				thisVM.hardShutdown(c);
				thisVM.destroy(c, true);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				result = vmDAO.deleteVMByUuid(userId, uuid);
			}
		}
		return result;
	}

	public boolean restartVM(String uuid, Connection c) {
		boolean result = false;
		String powerState = null;
		if (vmDAO.updateVMPower(uuid, VMPower.POWER_RESTART)) {
			try {
				VM thisVM = VM.getByUuid(c, uuid);
				powerState = thisVM.getPowerState(c).toString();
				String hostUuid = thisVM.getResidentOn(c).toWireString();
				if (powerState.equals("Running")) {
					boolean cleanReboot = thisVM.cleanReboot(c);
					if (cleanReboot == false) {
						thisVM.hardShutdown(c);
						thisVM.start(c, false, true);
					}
				} else {
					thisVM.start(c, false, true);
				}
				result = vmDAO.updateVMHost(uuid, hostUuid);
				result &= vmDAO.updateVMPower(uuid,
						VMPower.POWER_RUNNING);
			} catch (Exception e) {
				e.printStackTrace();
				if (powerState != null) {
					if (powerState.equals("Running")) {
						vmDAO.updateVMPower(uuid,
								VMPower.POWER_RUNNING);
					} else {
						vmDAO.updateVMPower(uuid,
								VMPower.POWER_HALTED);
					}
				} else {
					vmDAO.updateVMPower(uuid, VMPower.POWER_RUNNING);
				}
			}
		}
		return result;
	}

	public boolean startVM(String uuid, Connection c, String hostUuid) {
		String powerState = null;
		boolean result = false;
		if (vmDAO.updateVMPower(uuid, VMPower.POWER_BOOT)) {
			try {
				VM thisVM = VM.getByUuid(c, uuid);
				powerState = thisVM.getPowerState(c).toString();
				// 读取其所在物理服务器信息，若为关机状态则根据所选空闲物理服务器，在其上启动，
				// 否则读取其所在物理服务器信息，更新数据库即可
				if (!powerState.equals("Running")) {
					Host allocateHost = Types.toHost(hostUuid);
					thisVM.startOn(c, allocateHost, false, true);
				} else {
					hostUuid = thisVM.getResidentOn(c).toWireString();
				}
				result = vmDAO.updateVMHost(uuid, hostUuid);
				result &= vmDAO.updateVMPower(uuid,
						VMPower.POWER_RUNNING);
			} catch (Exception e) {
				e.printStackTrace();
				if (powerState != null) {
					if (powerState.equals("Running")) {
						vmDAO.updateVMPower(uuid,
								VMPower.POWER_RUNNING);
					} else {
						vmDAO.updateVMPower(uuid,
								VMPower.POWER_HALTED);
					}
				} else {
					vmDAO.updateVMPower(uuid, VMPower.POWER_HALTED);
				}
			}
		}
		return result;
	}

	public boolean shutdownVM(String uuid, String force, Connection c) {
		boolean result = false;
		String powerState = null;
		if (vmDAO.updateVMPower(uuid, VMPower.POWER_SHUTDOWN)) {
			try {
				VM thisVM = VM.getByUuid(c, uuid);
				powerState = thisVM.getPowerState(c).toString();
				String hostUuid = thisVM.getResidentOn(c).toWireString();
				if (powerState.equals("Running")) {
					if (force.equals("true")) {
						thisVM.hardShutdown(c);
					} else {
						if (thisVM.cleanShutdown(c)) {

						} else {
							thisVM.hardShutdown(c);
						}
					}
				}
				result = vmDAO.updateVMHost(uuid, hostUuid);
				result &= vmDAO.updateVMPower(uuid,
						VMPower.POWER_HALTED);
			} catch (Exception e) {
				e.printStackTrace();
				if (powerState != null) {
					if (powerState.equals("Running")) {
						vmDAO.updateVMPower(uuid,
								VMPower.POWER_RUNNING);
					} else {
						vmDAO.updateVMPower(uuid,
								VMPower.POWER_HALTED);
					}
				} else {
					vmDAO.updateVMPower(uuid, VMPower.POWER_RUNNING);
				}
			}
		}
		return result;
	}

	public JSONArray getVMList(int userId, int page, int limit, String groupUuid, String search) {
		JSONArray ja = new JSONArray();
		int totalNum = vmDAO.countByUserId(userId, search);
		List<OCVM> VMList = vmDAO.getOnePageByUserId(userId, page,
				limit, groupUuid, search);
		ja.put(totalNum);
		if (VMList != null) {
			for (OCVM ocvm : VMList) {
				JSONObject jo = new JSONObject();
				jo.put("vmid", ocvm.getVmUuid());
				jo.put("vmname", Utilities.encodeText(ocvm.getVmName()));
				jo.put("state", ocvm.getVmPower().ordinal());
				jo.put("cpu", ocvm.getVmCpu());
				jo.put("memory", ocvm.getVmMem());
				jo.put("vmplatform", ocvm.getVmPlatform().toString());
				jo.put("vmMac", ocvm.getVmMac());
				if (ocvm.getVmBackUpDate() == null) {
					jo.put("backupdate", "");
				} else {
					String timeUsed = Utilities.encodeText(Utilities
							.dateToUsed(ocvm.getVmBackUpDate()));
					jo.put("backupdate", timeUsed);
				}
				OCGroup group = groupService.getGroup(userId, ocvm.getVmGroup());
				if (group == null) {
					jo.put("groupName", "默认分组");
					jo.put("groupColor", "#ffffff");
				}else {
					jo.put("groupName", group.getGroupName());
					jo.put("groupColor", group.getGroupColor());
				}
				String timeUsed = Utilities.encodeText(Utilities
						.dateToUsed(ocvm.getVmCreateDate()));
				
				OCHost ocHost =   hostDAO.getHost(ocvm.getVmHostUuid());
				String hosttype = ocHost.getHostType();
				jo.put("hosttype", hosttype);
				jo.put("createdate", timeUsed);
				ja.put(jo);
			}
		}
		return ja;
	}

	public boolean createVM(String vmUuid, String tplUuid, int userId,
			String vmName, int cpu, int memory, String pwd, String hostUuid,
			String mac, VMResult vmresult, Connection c) {
		Image image = null;
		String vmBackendName = "i-" + vmUuid.substring(0, 8);
		Date createDate = new Date();
		String OS = null;
		String imagePwd = null;
		boolean result = false;

		// 检查模板信息是否有错误
		image = imageDAO.getImage(tplUuid);
		imagePwd = image.getImagePwd();
		logger.info("VM [" + vmBackendName + "] allocated to Host [" + hostUuid
				+ "]");
		// 预创建虚拟机，只填写数据表
		boolean preCreate = false;
		if (image.getImagePlatform().equals(ImagePlatform.LINUX)) {
			vmresult.setPlateform("LINUX");
			preCreate = vmDAO.preCreateVM(vmUuid, pwd, userId,
					vmName, VMPlatform.LINUX, mac, memory, cpu,
					VMPower.POWER_CREATE, VMStatus.EXIST, createDate, tplUuid);
			OS = "linux";
		} else if (image.getImagePlatform() == ImagePlatform.WINDOWS) {
			vmresult.setPlateform("WINDOWS");
			preCreate = vmDAO.preCreateVM(vmUuid, pwd, userId,
					vmName, VMPlatform.WINDOWS, mac, memory, cpu,
					VMPower.POWER_CREATE, VMStatus.EXIST, createDate, tplUuid);
			OS = "windows";
		}
		Date preEndDate = new Date();
		int elapse = Utilities.timeElapse(createDate, preEndDate);
		logger.info("VM [" + vmBackendName + "] Pre Create Time [" + elapse
				+ "]");
		if (preCreate) {
			try {
				// 预创建成功则调用xen创建虚拟机
				Record vmrecord = null;
				logger.info("VM Config: Template [" + tplUuid + "] CPU [" + cpu
						+ "] Memory [" + memory + "] Mac [" + mac + "] OS ["
						+ OS + "]");
				OCVDI ocvdi = vdiDAO.getFreeVDI(tplUuid);
				//VM.cloneSystemVDI(c, tplUuid, vmUuid);
				if (ocvdi != null) {
					vmrecord = createVMOnHost(c, ocvdi.getVdiUuid(), tplUuid, "root", pwd, cpu,
							memory, mac, "", OS, hostUuid, imagePwd, vmBackendName,
							false, "vdi");
					//vdi转换失败了 还能再次使用？
					vdiDAO.deleteVDI(ocvdi);
				} else {
					vmrecord = createVMOnHost(c, vmUuid, tplUuid, "root", pwd, cpu,
							memory, mac, "", OS, hostUuid, imagePwd, vmBackendName,
							false, "image");
				}
				Date createEndDate = new Date();
				int elapse1 = Utilities.timeElapse(createDate, createEndDate);
				logger.info("VM [" + vmBackendName + "] Create Time ["
						+ elapse1 + "]");
				if (vmrecord != null) {
					String realhost = vmrecord.residentOn.toWireString();
					if (realhost.equals(hostUuid)) {
						if (!vmrecord.setpasswd) {
							pwd = imagePwd;
						}
						result = vmDAO.updateVMPowerWhenCreateVM(
								userId, vmUuid, pwd, VMPower.POWER_RUNNING,
								hostUuid);
					} else {
						vmresult.setResult("主机启动位置错误");
						vmDAO.deleteVMByUuid(userId, vmUuid);
					}
				} else {
					vmresult.setResult("主机创建时失败");
					vmDAO.deleteVMByUuid(userId, vmUuid);
				}
			} catch (Exception e) {
				e.printStackTrace();
				vmresult.setResult("主机创建中出现未知错误");
				vmDAO.deleteVMByUuid(userId, vmUuid);
			}
		} else {
			vmresult.setResult("主机预创建时失败");
			vmDAO.deleteVMByUuid(userId, vmUuid);
		}
		return result;
	}

	public boolean adjustMemAndCPU(String uuid, int userId, int cpu, int mem,
			Connection c) {
		boolean result = false;
		try {
			VM vm = VM.getByUuid(c, uuid);
			if (VMUtil.AdjustCpuMemory(vm, cpu, mem * 1024, c)) {
				result = vmDAO.updateVMConfigure(uuid, mem * 1024,
						cpu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public OCVM getOCVM(String uuid) {
		return vmDAO.getVMByUuid(uuid);
	}

	/**
	 * 调用xenAPI创建虚拟机
	 * 
	 * @param c
	 * @param vmUuid
	 * @param tplUuid
	 * @param loginName
	 * @param loginPwd
	 * @param cpuCore
	 * @param memoryCapacity
	 * @param mac
	 * @param ip
	 * @param OS
	 * @param hostUuid
	 * @param imagePwd
	 * @param vmName
	 * @param ping
	 * @return
	 * @throws XmlRpcException
	 * @throws XenAPIException
	 * @throws BadServerResponse
	 */
	private Record createVMOnHost(Connection c, String vmUuid, String tplUuid,
			String loginName, String loginPwd, long cpuCore,
			long memoryCapacity, String mac, String ip, String OS,
			String hostUuid, String imagePwd, String vmName, boolean ping, String type)
			throws BadServerResponse, XenAPIException, XmlRpcException {
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
		Record vmrecord = null;
		if (type.equals("image")) {
			vmrecord = VM.createOnFromTemplate(c, host, tplUuid, vmName,
					map, ping);
		} else if (type.equals("vdi")) {
			vmrecord = VM.createWithVDI(c, host, tplUuid, vmName, map, ping);
		}
		return vmrecord;
	}

	public boolean deleteOCVM(int userId, String vmUuid) {
		return vmDAO.deleteVMByUuid(userId, vmUuid);
	}

	public JSONArray getVMListOfVolume(int userId, int page, int limit,
			String search) {
		JSONArray ja = new JSONArray();
		int totalNum = vmDAO.countByUserId(userId, search);
		List<OCVM> VMList = vmDAO.getOnePageByUserId(userId, page,
				limit, search);
		ja.put(totalNum);
		if (VMList != null) {
			for (OCVM ocvm : VMList) {
				if (volumeDAO.getVolumeUuidListOfVM(ocvm.getVmUuid())
						.size() < 6) {
					JSONObject jo = new JSONObject();
					jo.put("vmid", ocvm.getVmUuid());
					jo.put("vmname", Utilities.encodeText(ocvm.getVmName()));
					ja.put(jo);
				} else {
					totalNum--;
				}
			}
		}
		return ja;
	}

	@Override
	public boolean checkVMStatus(int userId, String vmUuid) {
		boolean result = false;
		Connection conn = null;
		try {
			conn = conService.getConnectionFromUser(userId);
			VM thisVM = VM.getByUuid(conn, vmUuid);
			if (thisVM != null) {
				int currentPowerStatus = thisVM.getPowerState(conn).toString().equals("Running") ? 1 : 0;
				OCVM ocvm = vmDAO.getVMByUuid(vmUuid);
				int powerStatus = ocvm.getVmPower().ordinal();
				if (currentPowerStatus != powerStatus) {
					if (currentPowerStatus == 1) {
						vmDAO.updateVMPower(vmUuid, VMPower.POWER_RUNNING);
					}else {
						vmDAO.updateVMPower(vmUuid, VMPower.POWER_HALTED);
					}
				}
				result = true;
			}else {
				result = false;
			}
			pushMessage(userId, result);
			
		} catch (Exception e) {
			pushMessage(userId, result);
			e.printStackTrace();
		} 
		return result;
	}
	
	private void pushMessage(int userId, boolean flag){
		if (flag) {
			messagePush.pushMessage(userId,
					Utilities.stickyToSuccess("虚拟机状态校验成功！"));
		}else {
			messagePush.pushMessage(userId,
					Utilities.stickyToError("虚拟机状态校验失败！"));
		}
	}

	@Override
	public JSONObject getVMDetail(String vmUuid) {
		JSONObject jo = new JSONObject();
		OCVM ocvm = vmDAO.getVMByUuid(vmUuid);
		if (ocvm != null) {
			jo.put("instanceName", Utilities.encodeText(ocvm.getVmName()));
			jo.put("instanceDesc", (ocvm.getVmDesc() == null) ? "&nbsp;"
					: Utilities.encodeText(ocvm.getVmDesc()));
			jo.put("instanceState", ocvm.getVmPower().ordinal());
			jo.put("createDate", Utilities.formatTime(ocvm.getVmCreateDate()));
			String timeUsed = Utilities.encodeText(Utilities.dateToUsed(ocvm
					.getVmCreateDate()));
			List<String> volList = volumeDAO.getVolumeUuidListOfVM(vmUuid);
			if (volList == null || volList.size() == 0) {
				jo.put("volList", "&nbsp;");
			} else {
				jo.put("volList", volList);
			}
			if (ocvm.getVmBackUpDate() == null) {
				jo.put("backupdate", "");
			} else {
				String tUsed = Utilities.encodeText(Utilities.dateToUsed(ocvm.getVmBackUpDate()));
				jo.put("backupdate", tUsed);
			}
			jo.put("useDate", timeUsed);
			jo.put("instanceCPU", ocvm.getVmCpu());
			jo.put("instanceMemory", ocvm.getVmMem());
			jo.put("instancevlan", ocvm.getVmVlan());
			jo.put("instanceMAC", ocvm.getVmMac());
			String vlan = ocvm.getVmVlan();
			if (("-1").equals(vlan)) {
				jo.put("vlan", "null");
			} else {
				OCNetwork vnet = networkDAO.getSwitch(ocvm.getVmVlan().toString());
				jo.put("vlan", vnet.getvlanId());
				jo.put("vlanUuid", vnet.getNetId());
				jo.put("vlantype", vnet.getvlanType());
			}
		}
		return jo;
	}

}
