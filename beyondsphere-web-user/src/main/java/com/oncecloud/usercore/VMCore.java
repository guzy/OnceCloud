/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.oncecloud.usercore;

import java.util.Date;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.Xen.API.Connection;

import com.oncecloud.entity.OCVM;
import com.oncecloud.factory.ServiceFactory;
import com.oncecloud.model.VMResult;
import com.oncecloud.service.ConnectionService;
import com.oncecloud.service.NetworkService;
import com.oncecloud.service.VMService;
import com.oncecloud.service.VlanService;
import com.oncecloud.service.VolumeService;
import com.oncecloud.util.Utilities;
import com.oncecloud.utils.AllocateHost;

@Component("VMUserCore")
public class VMCore {

	@Resource
	private ConnectionService connectionService;
	@Resource
	private VMService vmService;
	@Resource
	private ServiceFactory serviceFactory;
	@Resource
	private VlanService vlanService;
	@Resource
	private VolumeService volumeService;
	@Resource
	private NetworkService networkService;
	@Resource
	private AllocateHost allocateHost;

	/**
	 * beyond sphere+ 创建虚拟机 [String vmUuid, String tplUuid, Integer userId,
	 * String vmName, Integer cpu, Double memory, String pwd]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject createVM(JSONObject jo) {
		Date startTime = new Date();
		boolean result = false;
		try {
			int memoryCapacity = (int) (jo.getDouble("memory") * 1024);
			Connection c = connectionService.getConnectionFromUser(jo.getInt("userId"));
			String hostUuid = null;
			//创建虚拟机的时候，添加对高可用资源的限制条件
			if (allocateHost.isHA(jo.getInt("userId"), memoryCapacity)) {
				hostUuid = allocateHost.getAllocateHost(c, memoryCapacity, jo.getInt("userId"));
			}
			String mac = serviceFactory.getMACFactory().getMac();
			jo.put("mac", mac);
			VMResult vmResult = new VMResult();
			if (c != null) {
				if (hostUuid != null) {
					try {
						if (vmService.createVM(
							jo.getString("uuid"),
							jo.getString("tplUuid"), jo.getInt("userId"),
							jo.getString("name"), jo.getInt("cpu"),
							memoryCapacity, jo.getString("pwd"), hostUuid,
							mac, vmResult, c)) {
							jo.put("plateform", vmResult.getPlateform());
							if (serviceFactory
									.getVNCFactory()
									.allocation(jo.getString("uuid"),
											hostUuid, c)) {
								result = true;
							} else {
								jo.put("error", "NOVNC配置失败");
							}
						} else {
							jo.put("error", vmResult.getResult());
						}
					} catch (Exception e) {
						e.printStackTrace();
						jo.put("error", "创建失败");
						vmService.deleteOCVM(jo.getInt("userId"),
								jo.getString("uuid"));
					}
				} else {
					jo.put("error", "未选取到空闲服务器");
				}
			} else {
				jo.put("error", "获取资源池失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("error", "创建失败");
		} finally {
			jo.put("start", startTime);
			Date endTime = new Date();
			jo.put("elapse",
					Utilities.timeElapse(startTime, endTime));
		}
		if (!result) {
			vmService.deleteOCVM(jo.getInt("userId"),
					jo.getString("uuid"));
		}
		jo.put("result", result);
		return jo;
	}

	/**
	 * beyond sphere+ 关闭虚拟机 [String uuid, String force, Integer userId]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject shutdownVM(JSONObject jo) {
		boolean result = false;
		Date startTime = new Date();
		try {
			String uuid = jo.getString("uuid");
			Connection c = connectionService.getConnectionFromUser(
					jo.getInt("userId"));
			if (vmService.getOCVM(uuid) != null) {
				if (vmService.shutdownVM(uuid, jo.getString("force"), c)) {
					result = serviceFactory.getVNCFactory()
							.deleteToken(uuid.substring(0, 8));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Date endTime = new Date();
			int elapse = Utilities.timeElapse(startTime, endTime);
			jo.put("startTime", startTime);
			jo.put("elapse", elapse);
		}
		jo.put("result", result);
		return jo;
	}

	/**
	 * beyond sphere+ 开启虚拟机 [String uuid, Integer userId]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject startVM(JSONObject jo) {
		boolean result = false;
		Date startTime = new Date();
		try {
			String uuid = jo.getString("uuid");
			Connection c = connectionService.getConnectionFromUser(
					jo.getInt("userId"));
			OCVM ocvm = vmService.getOCVM(jo.getString("uuid"));
			String vlan = "-1";
			if (!"-1".equals(ocvm.getVmVlan())) {
				vlan = String .valueOf(networkService.getVnetwork(ocvm.getVmVlan()).getvlanId());
			}
			if (c != null && ocvm != null) {
				String hostUuid = null;
				//创建虚拟机的时候，添加对高可用资源的限制条件
				if (allocateHost.isHA(jo.getInt("userId"), ocvm.getVmMem())) {
					hostUuid = allocateHost.getAllocateHost(c, ocvm.getVmMem(), jo.getInt("userId"));
				}
				if (hostUuid != null) {
					if (vmService.startVM(uuid, c, hostUuid)) {
						vlanService.vmBindVlan(uuid, vlan, c);
						result = serviceFactory.getVNCFactory()
								.allocation(uuid, hostUuid, c);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Date endTime = new Date();
			int elapse = Utilities.timeElapse(startTime, endTime);
			jo.put("startTime", startTime);
			jo.put("elapse", elapse);
		}
		jo.put("result", result);
		return jo;
	}

	/**
	 * beyond sphere+ 重启虚拟机 [String uuid, Integer userId]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject restartVM(JSONObject jo) {
		boolean result = false;
		Date startTime = new Date();
		try {
			String uuid = jo.getString("uuid");
			Connection c = connectionService.getConnectionFromUser(
					jo.getInt("userId"));
			OCVM ocvm = vmService.getOCVM(uuid);
			if (ocvm != null) {
				if (vmService.restartVM(uuid, c)) {
					ocvm = vmService.getOCVM(uuid);
					vlanService.vmBindVlan(uuid, ocvm.getVmVlan(), c);
					result = serviceFactory.getVNCFactory()
							.allocation(uuid, ocvm.getVmHostUuid(), c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Date endTime = new Date();
			int elapse = Utilities.timeElapse(startTime, endTime);
			jo.put("startTime", startTime);
			jo.put("elapse", elapse);
		}
		jo.put("result", result);
		return jo;
	}

	/**
	 * beyond sphere+ 销毁虚拟机 [String uuid, Integer userId]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject deleteVM(JSONObject jo) {
		boolean result = false;
		Date startTime = new Date();
		try {
			String uuid = jo.getString("uuid");
			Connection c = connectionService.getConnectionFromUser(
					jo.getInt("userId"));
			if (c != null) {
				if (vmService.deleteVM(jo.getInt("userId"), uuid, c)) {
					volumeService.emptyAttachedVolume(c, uuid);
					serviceFactory.getVNCFactory().deleteToken(uuid);
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Date endTime = new Date();
			int elapse = Utilities.timeElapse(startTime, endTime);
			jo.put("startTime", startTime);
			jo.put("elapse", elapse);
		}
		jo.put("result", result);
		return jo;
	}

	/**
	 * beyond sphere+ 调整虚拟机配置[String uuid, int userId, int cpu, int mem]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject adjustVMConfig(JSONObject jo) {
		Date startTime = new Date();
		try {
			Connection c = connectionService.getConnectionFromUser(
					jo.getInt("userId"));
			if (vmService.adjustMemAndCPU(jo.getString("uuid"),
					jo.getInt("userId"), jo.getInt("cpu"), jo.getInt("mem"), c)) {
				jo.put("result", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
		}finally{
			Date endTime = new Date();
			int elapse = Utilities.timeElapse(startTime, endTime);
			jo.put("startTime", startTime);
			jo.put("elapse", elapse);
		}
		return jo;
	}
	
}
