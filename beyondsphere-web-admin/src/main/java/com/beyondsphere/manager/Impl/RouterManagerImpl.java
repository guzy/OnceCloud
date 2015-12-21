/**
 * @author zll
 * @time 上午15:22:48
 * @date 2015年5月11日
 */
package com.beyondsphere.manager.Impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.Host;
import org.Xen.API.Types;
import org.Xen.API.VIF;
import org.Xen.API.VM;
import org.Xen.API.Types.BadServerResponse;
import org.Xen.API.Types.XenAPIException;
import org.Xen.API.VM.Record;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.beyondsphere.factory.ServiceFactory;
import com.beyondsphere.manager.RouterManager;
import com.beyondsphere.manager.VMManager;
import com.oncecloud.constants.LogConstant;
import com.oncecloud.constants.LogEnumConstant;
import com.oncecloud.core.constant.Constant;
import com.oncecloud.dao.HostInformationDAO;
import com.oncecloud.dao.ImageDAO;
import com.oncecloud.dao.LogDAO;
import com.oncecloud.dao.NetworkDAO;
import com.oncecloud.dao.QuotaDAO;
import com.oncecloud.dao.RouterDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.entity.HostInformation;
import com.oncecloud.entity.Image;
import com.oncecloud.entity.OCLog;
import com.oncecloud.entity.OCNetwork;
import com.oncecloud.entity.OCVMip;
import com.oncecloud.entity.Router;
import com.oncecloud.entity.User;
import com.oncecloud.message.MessagePush;
import com.oncecloud.model.SwitchType;
import com.oncecloud.service.ConnectionService;
import com.oncecloud.service.VMService;
import com.oncecloud.service.VMipService;
import com.oncecloud.util.Utilities;

@Service("RouterManager")
public class RouterManagerImpl implements RouterManager {
	private final static Logger logger = Logger.getLogger(RouterManagerImpl.class);
	public static int[] capacity = { 250, 500, 1000 };
	public final static int POWER_HALTED = 0;
	public final static int POWER_RUNNING = 1;
	public final static int POWER_CREATE = 2;
	public final static int POWER_DESTROY = 3;
	public final static int POWER_BOOT = 4;
	public final static int POWER_SHUTDOWN = 5;
	
	@Resource
	private ServiceFactory serviceFactory;
	@Resource
	private QuotaDAO quotaDAO;
	@Resource
	private LogDAO logDAO;
	@Resource
	private MessagePush messagePush;
	@Resource
	private ImageDAO imageDAO;
	@Resource
	private RouterDAO routerDAO;
	@Resource
	private VMManager vmManager;
	@Resource
	private VMService vmService;
	@Resource
	private Constant constant;
	@Resource
	private NetworkDAO networkDAO;
	@Resource
	private VMipService vmipService;
	@Resource
	private ConnectionService connService;
	@Resource
	private HostInformationDAO hostInformationDAO;
	@Resource
	private UserDAO userDAO;
	
	@Override
	public JSONArray routerQuota(int userId) {
		JSONArray ja = new JSONArray();
		int free = quotaDAO.getQuotaTotal(userId).getQuotaRoute()
				- quotaDAO.getQuotaUsed(userId).getQuotaRoute();
		JSONObject jo = new JSONObject();
		jo.put("free", free);
		if (free < 1) {
			jo.put("result", false);
		} else {
			jo.put("result", true);
		}
		ja.put(jo);
		return ja;
	}

	public boolean createRouter(String uuid, int userId, String name,String poolUuid) {
		Date startTime = new Date();
		JSONObject result = this.doCreateRouter(uuid, userId, name,poolUuid);
		// Begin to push message and write log
		Date endTime = new Date();
		int elapse = Utilities.timeElapse(startTime, endTime);
		JSONArray infoArray = new JSONArray();
		infoArray.put(Utilities.createLogInfo(
				LogConstant.logObject.Router.toString(),
				"rt-" + uuid.substring(0, 8)));
		infoArray.put(Utilities.createLogInfo("峰值带宽", capacity + "&nbsp;Mbps"));
		if (result.getBoolean("isSuccess") == true) {
			OCLog log = logDAO.insertLog(userId,
					LogConstant.logObject.Router.ordinal(),
					LogEnumConstant.logAction.创建.ordinal(),
					LogEnumConstant.logStatus.成功.ordinal(), infoArray.toString(),
					startTime, elapse);
			messagePush.editRowStatus(userId, uuid, "running", "活跃");
			messagePush.editRowIP(userId, uuid, "基础网络",
					result.getString("ip"));
			messagePush.pushMessage(userId,
					Utilities.stickyToSuccess(log.toString()));
		} else {
			infoArray.put(Utilities.createLogInfo("原因",result.has("error")?result.getString("error"):"未知"));
			OCLog log = logDAO.insertLog(userId,
					LogConstant.logObject.Router.ordinal(),
					LogEnumConstant.logAction.创建.ordinal(),
					LogEnumConstant.logStatus.失败.ordinal(), infoArray.toString(),
					startTime, elapse);
			messagePush.deleteRow(userId, uuid);
			messagePush.pushMessage(userId,
					Utilities.stickyToError(log.toString()));
		}
		
		return result.getBoolean("isSuccess");
	}
	private JSONObject doCreateRouter(String uuid, int userId, String name,
			String poolUuid) {
		Connection c = null;
		String allocateHost = null;
		String mac = null;
		JSONObject jo = new JSONObject();
		String OS = null;
		String imagePwd = null;
		String pwd = "onceas";
		String tplUuid = null;
		String backendName = "rt-" + uuid.substring(0, 8);
		Date createDate = new Date();
		try {
			Image rtImage = imageDAO.getRTImage(poolUuid);
			OS = "linux";
			imagePwd = rtImage.getImagePwd();
			tplUuid = rtImage.getImageUuid();
			mac = serviceFactory.getMACFactory().getMac();
			c = constant.getConnectionFromPool(poolUuid);
			allocateHost = vmManager.getAllocateHost(poolUuid, 1024);
			logger.info("Router [" + backendName + "] allocated to Host ["
					+ allocateHost + "]");
		} catch (Exception e) {
			e.printStackTrace();
			if (mac != null) {
				try {
//					dhcpDAO.returnDHCP(mac);
				} catch (Exception e1) {
					e.printStackTrace();
				}
				jo.put("isSuccess", false);
			}
			return jo;
		}
		if (allocateHost == null) {
			jo.put("isSuccess", false);
		} else {
			boolean dhcpRolrtack = false;
			boolean dbRolrtack = false;
			try {
				//路由器信息存入数据库
				boolean preCreate = routerDAO.preCreateRouter(uuid,
						pwd, userId, name, mac,
						POWER_CREATE, 1, createDate);
				
				Date preEndDate = new Date();
				int elapse = Utilities.timeElapse(createDate, preEndDate);
				logger.info("Router [" + backendName + "] Pre Create Time ["
						+ elapse + "]");
				if (preCreate == true) {
					//创建路由器
					Record rtrecord = createVMOnHost(c,
							uuid, tplUuid, "root", pwd, 1, 1024, mac, "", OS,
							allocateHost, imagePwd, backendName, true,"image");
					
					Date createEndDate = new Date();
					int elapse1 = Utilities.timeElapse(createDate,
							createEndDate);
					logger.info("Router [" + backendName + "] Create Time ["
							+ elapse1 + "]");
					if (rtrecord != null) {
						String hostuuid = rtrecord.residentOn.toWireString();
						if (hostuuid.equals(allocateHost)) {
							if (!rtrecord.setpasswd) {
								pwd = imagePwd;
							}
							
							//路由器绑定网址
							OCVMip ocvmip=new OCVMip();
							//获取该用户的记录
							HostInformation info=hostInformationDAO.getInformationByUserid(userId);
							ocvmip.setVmIp(info.getRouterIp());
							ocvmip.setVmMac(mac);
							ocvmip.setVmNetmask(info.getRouterNetmask());
							
							OCNetwork oc=networkDAO.getSwitch(String.valueOf(info.getNetId()));
							if(changeVMip(userId,ocvmip,uuid)){
								//获取网卡
								VM routerVm = VM.getByUuid(c, uuid);
								Set<VIF> vifs = routerVm.getVIFs(c);
								boolean vifflag=true;
								String vifUuid="";
								for (VIF vif : vifs) {
									if(vifflag){
										VIF.Record record = routerVm.getVIFRecord(c, vif);
										vifUuid=record.uuid;
										System.out.println(record.MAC + "|" + record.device + "|" + record.uuid);
										vifflag=false;
									}
								}
								//绑定vlan
								bindVlan(uuid,vifUuid,oc.getvlanId(),poolUuid);
								//重启网络
								restarnetwork(userId,uuid);
								//修改数据库路由信息
								routerDAO.updateRouter(userId,uuid,pwd, POWER_RUNNING,
										hostuuid,ocvmip.getVmIp()) ;
								jo.put("isSuccess", true);
								jo.put("ip", ocvmip.getVmIp());
							}else{
								jo.put("isSuccess", false);
							}
						} else {
							jo.put("error", "路由器后台启动位置错误");
							dhcpRolrtack = true;
							dbRolrtack = true;
						}
					} else {
						jo.put("error", "路由器后台创建失败");
						dhcpRolrtack = true;
						dbRolrtack = true;
					}
				} else {
					jo.put("error", "路由器预创建失败");
					dhcpRolrtack = true;
				}
			} catch (Exception e) {
				jo.put("error", "路由器创建异常");
				e.printStackTrace();
				dhcpRolrtack = true;
				dbRolrtack = true;
			}
			if (dhcpRolrtack == true) {
				jo.put("isSuccess", false);
			}
			if (dbRolrtack == true) {
				routerDAO.removeRouter(userId, uuid);
				jo.put("isSuccess", false);
			}
		}
		
		return jo;
	}
	
	private boolean changeVMip(int userId, OCVMip vMip,String uuid) {
		//添加日志
		//更新虚拟机操作
		boolean vmresult=false;
		JSONObject obj=new JSONObject();
		obj.put("requestType", "Network.EnableStatic");
		obj.put("mac", vMip.getVmMac());
		obj.put("ipAddress",vMip.getVmIp());
		obj.put("netmask", vMip.getVmNetmask());
		if(vMip.getVmGateway()!=null){
			obj.put("gateway", vMip.getVmGateway());
		}
		Connection conn = connService.getConnectionFromUser(userId);
		VM vm = Types.toVM(uuid);
		try {
			vmresult=vm.sendRequestViaSerial(conn, obj.toString(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			
		return vmresult;
		
	}

	
	public void startRouter(String uuid, int userId, String poolUuid) {
		Date startTime = new Date();
		boolean result = this.doStartRouter(uuid, poolUuid);
		// write log and push message
		Date endTime = new Date();
		int elapse = Utilities.timeElapse(startTime, endTime);
		JSONArray infoArray = new JSONArray();
		infoArray.put(Utilities.createLogInfo(
				LogEnumConstant.logObject.路由器.toString(),
				"rt-" + uuid.substring(0, 8)));
		if (result == true) {
			messagePush.editRowStatus(userId, uuid, "running", "活跃");
			OCLog log = logDAO.insertLog(userId,
					LogConstant.logObject.Router.ordinal(),
					LogEnumConstant.logAction.启动.ordinal(),
					LogEnumConstant.logStatus.成功.ordinal(), infoArray.toString(),
					startTime, elapse);
			messagePush.pushMessage(userId,
					Utilities.stickyToSuccess(log.toString()));
		} else {
			messagePush.editRowStatus(userId, uuid, "stopped", "已关机");
			OCLog log = logDAO.insertLog(userId,
					LogConstant.logObject.Router.ordinal(),
					LogEnumConstant.logAction.启动.ordinal(),
					LogEnumConstant.logStatus.失败.ordinal(), infoArray.toString(),
					startTime, elapse);
			messagePush.pushMessage(userId,
					Utilities.stickyToError(log.toString()));
		}
	}
	private boolean doStartRouter(String uuid, String poolUuid) {
		boolean result = false;
		String hostUuid = null;
		String powerState = null;
		try {
			Router currentRT = routerDAO.getRouter(uuid);
			if (currentRT != null) {
				boolean preStartRouter = routerDAO.updatePowerStatus(
						uuid, POWER_BOOT);
				if (preStartRouter == true) {
					Connection c = constant.getConnectionFromPool(
							poolUuid);
					VM thisVM = VM.getByUuid(c, uuid);
					powerState = thisVM.getPowerState(c).toString();
					if (!powerState.equals("Running")) {
						hostUuid = vmManager.getAllocateHost(
								poolUuid, 1024);
						Host allocateHost = Types.toHost(hostUuid);
						thisVM.startOn(c, allocateHost, false, true);
					} else {
						hostUuid = thisVM.getResidentOn(c).toWireString();
					}
					routerDAO.updateHostUuid(uuid, hostUuid);
					routerDAO.updatePowerStatus(uuid,
							POWER_RUNNING);
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (powerState != null) {
				if (powerState.equals("Running")) {
					routerDAO.updatePowerStatus(uuid,
							POWER_RUNNING);
				} else {
					routerDAO.updatePowerStatus(uuid,
							POWER_HALTED);
				}
			} else {
				routerDAO.updatePowerStatus(uuid,
						POWER_HALTED);
			}
		} finally {
			if (result = true) {
				List<OCNetwork> vnetList = networkDAO.getVnetsOfRouter(uuid);
				if (vnetList != null && vnetList.size() > 0) {
					for (OCNetwork vnet : vnetList) {
						String vifuuid=null;
						String vifMac=null;
						try {
							Connection conn = null;
							conn = constant.getConnectionFromPool(poolUuid);
							VM vm = VM.getByUuid(conn, uuid);
							Set<VIF> vifset = vm.getVIFs(conn);
							
							boolean flag=true;
							for (VIF vif : vifset) {
								if(flag){
									VIF.Record record = vm.getVIFRecord(conn, vif);
									vifuuid=record.uuid;
									vifMac=record.MAC;
									flag=false;
								}else{
									break;
								}
//								String tag = vm.getTag(conn, vif);
//								jo.put("vifuuid", record.uuid);
//								jo.put("device", record.device);
//								jo.put("mac", record.MAC);
//								jo.put("tag", tag);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						bindVlan(uuid, vifuuid, vnet.getvlanId(),
								poolUuid);
						logger.debug("Update Router Vlan: MAC ["
								+ vifMac + "] Vlan ["
								+ vnet.getvlanId() + "]");
					}
				}
			}
		}
		return result;
	}
	
	private void bindVlan(String routerUuid, String vifUuid, int vnetId,
			String poolUuid) {
		try {
			Connection c = constant.getConnectionFromPool(poolUuid);
			VM router = VM.getByUuid(c, routerUuid);
			router.setTag(c, Types.toVIF(vifUuid), String.valueOf(vnetId),SwitchType.SWITCH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRouter(String uuid, int userId, String poolUuid) {
		Date startTime = new Date();
		boolean result = this.doDeleteRouter(userId, uuid, poolUuid);
		// write log and push message
		Date endTime = new Date();
		int elapse = Utilities.timeElapse(startTime, endTime);
		JSONArray infoArray = new JSONArray();
		infoArray.put(Utilities.createLogInfo(
				LogConstant.logObject.Router.toString(),
				"rt-" + uuid.substring(0, 8)));
		if (result == true) {
			OCLog log = logDAO.insertLog(userId,
					LogConstant.logObject.Router.ordinal(),
					LogEnumConstant.logAction.销毁.ordinal(),
					LogEnumConstant.logStatus.成功.ordinal(), infoArray.toString(),
					startTime, elapse);
			messagePush.deleteRow(userId, uuid);
			messagePush.pushMessage(userId,
					Utilities.stickyToSuccess(log.toString()));
		} else {
			OCLog log = logDAO.insertLog(userId,
					LogConstant.logObject.Router.ordinal(),
					LogEnumConstant.logAction.销毁.ordinal(),
					LogEnumConstant.logStatus.失败.ordinal(), infoArray.toString(),
					startTime, elapse);
			messagePush.pushMessage(userId,
					Utilities.stickyToError(log.toString()));
		}
	}
	
	private boolean doDeleteRouter(int userId, String uuid, String poolUuid) {
		boolean result = false;
		Connection c = null;
		try {
			c = constant.getConnectionFromPool(poolUuid);
			boolean preDeleteRouter = routerDAO.updatePowerStatus(
					uuid, POWER_DESTROY);
			if (preDeleteRouter == true) {
				VM thisRouter = VM.getByUuid(c, uuid);
				thisRouter.hardShutdown(c);
				thisRouter.destroy(c, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				routerDAO.removeRouter(userId, uuid);
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
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

	/**
	 * 获取用户路由器列表
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public JSONArray getRouterList(int page, int limit,
			String search) {
		JSONArray ja = new JSONArray();
		int total = routerDAO.countRouters(search);
		List<Router> routerList = routerDAO.getOnePageRouters(page, limit, search);
		ja.put(total);
		
		if (routerList != null) {
			for (Router router : routerList) {
				JSONObject obj = new JSONObject();
				
				HostInformation info=hostInformationDAO.getInformationByUserid(router.getRouterUID());
				User user=userDAO.getUser(router.getRouterUID());
				String routerUuid = router.getRouterUuid();
				obj.put("uuid", routerUuid);
				obj.put("name", Utilities.encodeText(router.getRouterName()));
				obj.put("userid", router.getRouterUID());
				obj.put("username", Utilities.encodeText(user.getUserName()));
				if(info==null){
					obj.put("ips", "");
				}else{
					obj.put("ips", info.getIpSegment());
				}
				obj.put("power", router.getRouterPower());
				String ip = router.getRouterIP();
				if (ip == null) {
					obj.put("ip", "");
				} else {
					obj.put("ip", ip);
				}
				String timeUsed = Utilities.encodeText(Utilities.dateToUsed(router.getCreateDate()));
				obj.put("createdate", timeUsed);
				ja.put(obj);
			}
		}
		return ja;
	}
	
	
	public JSONArray getRoutersOfUser(){
		JSONArray ja = new JSONArray();
		List<Router> routerList = routerDAO.getAllRouters();
		for (Router router : routerList) {
			JSONObject itemjo = new JSONObject();
			itemjo.put("power",router.getRouterPower());;
			itemjo.put("uuid",router.getRouterUuid());
			itemjo.put("rtname", Utilities.encodeText(router.getRouterName()));
			ja.put(itemjo);
		}
		return ja;
	}
	
	public JSONObject doLinkRouter(int userId, String vnetUuid,
			String routerUuid, int net, int gate, int start, int end,
			int dhcpState) {
		JSONObject result = new JSONObject();
		result.put("result", false);
		try {
			logger.info("Link to Router: Router [rt-"
					+ routerUuid.substring(0, 8) + "] Vnet [vn-"
					+ vnetUuid+ "]");
			Connection c = constant.getConnection(userId);
			VM vm = VM.getByUuid(c, routerUuid);
			String mac = Utilities.randomMac();
			VIF vif = VIF.createBindToPhysicalNetwork(c, vm, SwitchType.DISTRIBUTED, mac);
			String vifUuid = vif.toWireString();
			OCNetwork vnet = networkDAO.getSwitch(vnetUuid);
			vm.setTag(c, vif, String.valueOf(vnet.getvlanId()),SwitchType.DISTRIBUTED);
			String eth = vm.getVIFRecord(c, vif).device;
			logger.info("Create VIF: Mac [" + mac + "] Ethernet [" + eth + "]");
			if (!eth.equals("")) {
				String url = "192.168." + net + "." + gate;;
				String netmask = "255.255.255.0";
				String gateway = "192.168." + net + "." + gate;
				logger.info("Configure Ethernet: URL [" + url + "] Gateway ["
						+ gateway + "] Netmask [" + netmask + "]");
				
				OCVMip ocvmip=new OCVMip();
				ocvmip.setVmIp(url);
				ocvmip.setVmMac(mac);
				ocvmip.setVmNetmask(netmask);
				ocvmip.setVmGateway(gateway);
				boolean addEthResult = changeVMip(userId,ocvmip,routerUuid);
				logger.info("Configure Ethernet Result: " + addEthResult);
				if (addEthResult) {
					
					boolean restartnet = true;
					
					//保存Vxlan信息
					Router route=routerDAO.getRouter(routerUuid);
					OCNetwork network=new OCNetwork();
					network.setNetId(Integer.valueOf(vnetUuid));
					network.setVlanNet(String.valueOf(net));
					network.setDhcpStatus(1);
					network.setVlanGate(String.valueOf(gate));
					network.setVlanUid(route.getRouterUID());
					network.setVlanRouter(routerUuid);
					network.setVifUuid(vifUuid);
					network.setVifMac(mac);
					try {
						//重启网络
						restartnet=restarnetwork(userId,routerUuid);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if (restartnet) {
						//写入数据库
						networkDAO.updateVxlan(network);
						networkDAO.linkToRouter(vnetUuid, routerUuid,
								net, gate, start, end, dhcpState, vifUuid, mac);
						result.put("result", true);
					}else{
						vm.destroyVIF(c, Types.toVIF(vifUuid));
					}
				}else{
					vm.destroyVIF(c, Types.toVIF(vifUuid));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public boolean isRouterHasVnets(String routerUuid, int userId) {
		int count = networkDAO.countVnetsOfRouter(routerUuid, userId);
		return count > 0;
	}
	
	public void shutdownRouter(String uuid, String force, int userId,
			String poolUuid) {
		Date startTime = new Date();
		boolean result = this.doShutdownRouter(uuid, force, poolUuid);
		// write log and push message
		Date endTime = new Date();
		int elapse = Utilities.timeElapse(startTime, endTime);
		JSONArray infoArray = new JSONArray();
		infoArray.put(Utilities.createLogInfo(
				LogConstant.logObject.Router.toString(),
				"rt-" + uuid.substring(0, 8)));
		if (result == true) {
			messagePush.editRowStatus(userId, uuid, "stopped", "已关机");
			OCLog log = logDAO.insertLog(userId,
					LogConstant.logObject.Router.ordinal(),
					LogEnumConstant.logAction.关闭.ordinal(),
					LogEnumConstant.logStatus.成功.ordinal(), infoArray.toString(),
					startTime, elapse);
			messagePush.pushMessage(userId,
					Utilities.stickyToSuccess(log.toString()));
		} else {
			messagePush.editRowStatus(userId, uuid, "running", "活跃");
			OCLog log = logDAO.insertLog(userId,
					LogConstant.logObject.Router.ordinal(),
					LogEnumConstant.logAction.关闭.ordinal(),
					LogEnumConstant.logStatus.失败.ordinal(), infoArray.toString(),
					startTime, elapse);
			messagePush.pushMessage(userId,
					Utilities.stickyToError(log.toString()));
		}
	}
	
	private boolean doShutdownRouter(String uuid, String force, String poolUuid) {
		boolean result = false;
		String powerState = null;
		String hostUuid = null;
		try {
			Router currentRT = routerDAO.getRouter(uuid);
			if (currentRT != null) {
				boolean preShutdownRouter = routerDAO
						.updatePowerStatus(uuid, POWER_SHUTDOWN);
				if (preShutdownRouter == true) {
					Connection c = constant.getConnectionFromPool(
							poolUuid);
					VM thisVM = VM.getByUuid(c, uuid);
					powerState = thisVM.getPowerState(c).toString();
					hostUuid = thisVM.getResidentOn(c).toWireString();
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
					routerDAO.updateHostUuid(uuid, hostUuid);
					routerDAO.updatePowerStatus(uuid,
							POWER_HALTED);
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (powerState != null) {
				if (powerState.equals("Running")) {
					routerDAO.updatePowerStatus(uuid,
							POWER_RUNNING);
				} else {
					routerDAO.updatePowerStatus(uuid,
							POWER_HALTED);
				}
			} else {
				routerDAO.updatePowerStatus(uuid,
						POWER_RUNNING);
			}
		}
		return result;
	}
	
	public JSONObject doUnlinkRouter(String vnetUuid, int userId) {
		JSONObject result = new JSONObject();
		result.put("result", false);
		try {
			logger.info("Unlink to Router: Vnet [vn-"
					+ vnetUuid+ "]");
			Connection c = constant.getConnection(userId);
			OCNetwork vnet = networkDAO.getSwitch(vnetUuid);
//			String vifMac = vnet.getVifMac();
			String vifUuid = vnet.getVifUuid();
			String routerUuid = vnet.getVlanRouter();
//			Router router = routerDAO.getAliveRouter(routerUuid);
			VM routerVm = VM.getByUuid(c, routerUuid);
			Set<VIF> vifs = routerVm.getVIFs(c);
			for (VIF vif : vifs) {
				VIF.Record record = routerVm.getVIFRecord(c, vif);
				System.out.println(record.MAC + "|" + record.device + "|" + record.uuid);
			}
			routerVm.destroyVIF(c, Types.toVIF(vifUuid));
			logger.info("Delete VIF: UUID [vif-" + vifUuid.substring(0, 8)
					+ "]");
//			String url = router.getRouterIP() + ":9090";
//			boolean delEthResult = Host.routeDelEth(c, url, vifMac);
//			if(delEthResult){
				networkDAO.unlinkRouter(vnetUuid);
				result.put("result", true);
//			}else{
//				
//			}
		} catch (Exception e) {
			result.put("result", false);
			e.printStackTrace();
		}
		return result;
	}
	
	
	private boolean restarnetwork(int userId, String uuid) {
	
		boolean vmresult=false;
		JSONObject obj=new JSONObject();
		obj.put("requestType", "Network.RestartNetwork");
		Connection conn = connService.getConnectionFromUser(userId);
		VM vm = Types.toVM(uuid);
		try {
			vmresult=vm.sendRequestViaSerial(conn, obj.toString(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return vmresult;
		
	}

	@Override
	public JSONObject checkRouterip(String routerip) {
		JSONObject obj=new JSONObject();
		
		List<HostInformation> infolist=hostInformationDAO.getAllList();
		boolean flag=true;
		if(infolist.size()>0){
			String starip="";
			for (HostInformation info : infolist) {
				starip=info.getRouterIp().substring(0,info.getRouterIp().lastIndexOf("."));
				if(routerip.equals(starip)){
					flag=false;
					break;
				}
			}
		}
		
		obj.put("result", flag);
		return obj;
	}

}
