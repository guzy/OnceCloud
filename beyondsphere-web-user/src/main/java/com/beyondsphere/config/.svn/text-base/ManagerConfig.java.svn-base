package com.beyondsphere.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beyondsphere.manager.ContainerManager;
import com.beyondsphere.manager.RegistryManager;
import com.beyondsphere.model.Register;
import com.beyondsphere.usercore.AlarmCore;
import com.beyondsphere.usercore.GroupCore;
import com.beyondsphere.usercore.ImageCore;
import com.beyondsphere.usercore.LogCore;
import com.beyondsphere.usercore.MessageCore;
import com.beyondsphere.usercore.SnapshotCore;
import com.beyondsphere.usercore.StatisticsCore;
import com.beyondsphere.usercore.UserCore;
import com.beyondsphere.usercore.UserLogCore;
import com.beyondsphere.usercore.VMCore;
import com.beyondsphere.usercore.VolumeCore;

@Component
public class ManagerConfig {
	private static Map<String, ArrayList<Register>> configMap = new HashMap<String, ArrayList<Register>>();

	private VMCore vmCore;
	private AlarmCore alarmcore;
	private LogCore logCore;
	private MessageCore messageCore;
	private UserCore userCore;
	private ImageCore imageCore;
	private SnapshotCore snapshotCore;
	private VolumeCore volumeCore;
	private StatisticsCore statisticsCore;
	private GroupCore groupCore;
	private ContainerManager containerManager;
    private RegistryManager registryManager;
    private UserLogCore userLogCore;
	@Autowired
	private ManagerConfig(VMCore vmCore, AlarmCore alarmcore, LogCore logCore,
			MessageCore messageCore, UserCore userCore, ImageCore imageCore,
			SnapshotCore snapshotCore, VolumeCore volumeCore,
			StatisticsCore statisticsCore, GroupCore groupCore,
			ContainerManager containerManager,RegistryManager registryManager, UserLogCore userLogCore) {
		this.setAlarmcore(alarmcore);
		this.setImageCore(imageCore);
		this.setLogCore(logCore);
		this.setMessageCore(messageCore);
		this.setUserCore(userCore);
		this.setVmCore(vmCore);
		this.setSnapshotCore(snapshotCore);
		this.setVolumeCore(volumeCore);
		this.setStatisticsCore(statisticsCore);
		this.setGroupCore(groupCore);
        this.setContainerManager(containerManager);
        this.setRegistryManager(registryManager);
        this.setUserLogCore(userLogCore);
        
		this.createVM();
		this.createGroup();
		this.bindGroup();
		this.unbindGroup();
		this.removeGroup();
		this.modifyGroup();
		this.destoryGroup();
		this.createAlarm();
		this.createAlarmRule();
		this.destoryAlarm();
		this.bindAlarm();
		this.unBindAlarm();
		this.deleteRule();
		this.modifyAlarm();
		this.modifyPeriod();
		this.modifyRule();
		this.shutdownVM();
		this.startVM();
		this.restartVM();
		this.deleteVM();
		this.adjustVM();
		this.checkLogin();
		this.createImage();
		this.deleteImage();
		this.createSnapshot();
		this.deleteSnapshot();
		this.destorySnapshot();
		this.roolbackSnapshot();
		this.createVolume();
		this.deleteVolume();
		this.bindVolume();
		this.unbindVolume();
		this.createContainer();
		this.deleteContainer();
		this.stopContainer();
		this.restartContainer();
		this.createRegistry();
		this.deleteRegistry();
		this.deleteLog();
		this.exportLog();
	}


	public static Map<String, ArrayList<Register>> getConfigMap() {
		return configMap;
	}

	public static void setConfigMap(Map<String, ArrayList<Register>> configMap) {
		ManagerConfig.configMap = configMap;
	}

	public VMCore getVmCore() {
		return vmCore;
	}

	public void setVmCore(VMCore vmCore) {
		this.vmCore = vmCore;
	}

	public AlarmCore getAlarmcore() {
		return alarmcore;
	}

	public void setAlarmcore(AlarmCore alarmcore) {
		this.alarmcore = alarmcore;
	}

	public LogCore getLogCore() {
		return logCore;
	}

	public void setLogCore(LogCore logCore) {
		this.logCore = logCore;
	}

	public MessageCore getMessageCore() {
		return messageCore;
	}

	public void setMessageCore(MessageCore messageCore) {
		this.messageCore = messageCore;
	}

	public UserCore getUserCore() {
		return userCore;
	}

	public void setUserCore(UserCore userCore) {
		this.userCore = userCore;
	}

	public ImageCore getImageCore() {
		return imageCore;
	}

	public void setImageCore(ImageCore imageCore) {
		this.imageCore = imageCore;
	}

	public SnapshotCore getSnapshotCore() {
		return snapshotCore;
	}

	public void setSnapshotCore(SnapshotCore snapshotCore) {
		this.snapshotCore = snapshotCore;
	}

	public VolumeCore getVolumeCore() {
		return volumeCore;
	}

	public void setVolumeCore(VolumeCore volumeCore) {
		this.volumeCore = volumeCore;
	}

	public StatisticsCore getStatisticsCore() {
		return statisticsCore;
	}

	public void setStatisticsCore(StatisticsCore statisticsCore) {
		this.statisticsCore = statisticsCore;
	}

	public GroupCore getGroupCore() {
		return groupCore;
	}

	public void setGroupCore(GroupCore groupCore) {
		this.groupCore = groupCore;
	}

	public ContainerManager getContainerManager() {
		return containerManager;
	}

	public void setContainerManager(ContainerManager containerManager) {
		this.containerManager = containerManager;
	}

	public RegistryManager getRegistryManager() {
		return registryManager;
	}
	
	private void setRegistryManager(RegistryManager registryManager) {
		this.registryManager = registryManager;
	}
	
	public UserLogCore getUserLogCore() {
		return userLogCore;
	}

	public void setUserLogCore(UserLogCore userLogCore) {
		this.userLogCore = userLogCore;
	}


	private void createVM() {
		ArrayList<Register> createvmregister = new ArrayList<Register>();

		Register vmcreate = new Register();
		vmcreate.setObject(this.getVmCore());
		vmcreate.setMetodName("createVM");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		vmcreate.setParamsList(params);
		createvmregister.add(vmcreate);

		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("createVMLog");
		logcreate.setParamsList(params);
		createvmregister.add(logcreate);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("createVMMessage");
		messagecreate.setParamsList(params);
		createvmregister.add(messagecreate);

		Register statistics = new Register();
		statistics.setObject(this.getStatisticsCore());
		statistics.setMetodName("addStatistics");
		statistics.setParamsList(params);
		createvmregister.add(statistics);

		configMap.put("CreateVMHandler", createvmregister);
	}

	private void createGroup() {
		ArrayList<Register> createGroup = new ArrayList<Register>();

		Register groupCreate = new Register();
		groupCreate.setObject(this.getGroupCore());
		groupCreate.setMetodName("saveGroup");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		groupCreate.setParamsList(params);
		createGroup.add(groupCreate);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("createGroup");
		messagecreate.setParamsList(params);
		createGroup.add(messagecreate);

		configMap.put("CreateGroupHandler", createGroup);
	}

	private void bindGroup() {
		ArrayList<Register> bindgroupregister = new ArrayList<Register>();

		Register groupBind = new Register();
		groupBind.setObject(this.getGroupCore());
		groupBind.setMetodName("bindGroup");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		groupBind.setParamsList(params);
		bindgroupregister.add(groupBind);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("bindGroup");
		messagecreate.setParamsList(params);
		bindgroupregister.add(messagecreate);

		configMap.put("BindGroupHandler", bindgroupregister);
	}

	private void unbindGroup() {
		ArrayList<Register> bindgroupregister = new ArrayList<Register>();

		Register groupBind = new Register();
		groupBind.setObject(this.getGroupCore());
		groupBind.setMetodName("unbindGroup");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		groupBind.setParamsList(params);
		bindgroupregister.add(groupBind);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("unbindGroup");
		messagecreate.setParamsList(params);
		bindgroupregister.add(messagecreate);

		configMap.put("UnBindGroupHandler", bindgroupregister);
	}

	private void removeGroup() {
		ArrayList<Register> bindgroupregister = new ArrayList<Register>();

		Register groupBind = new Register();
		groupBind.setObject(this.getGroupCore());
		groupBind.setMetodName("removeGroup");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		groupBind.setParamsList(params);
		bindgroupregister.add(groupBind);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("unbindGroup");
		messagecreate.setParamsList(params);
		bindgroupregister.add(messagecreate);

		configMap.put("removeGroupHandler", bindgroupregister);
	}

	private void modifyGroup() {
		ArrayList<Register> modifyGroup = new ArrayList<Register>();

		Register groupModify = new Register();
		groupModify.setObject(this.getGroupCore());
		groupModify.setMetodName("modifyGroup");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		groupModify.setParamsList(params);
		modifyGroup.add(groupModify);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("modifyGroup");
		messagecreate.setParamsList(params);
		modifyGroup.add(messagecreate);

		configMap.put("ModifyGroupHandler", modifyGroup);
	}

	private void destoryGroup() {
		ArrayList<Register> destoryGroupregister = new ArrayList<Register>();

		Register groupDestory = new Register();
		groupDestory.setObject(this.getGroupCore());
		groupDestory.setMetodName("destoryGroup");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		groupDestory.setParamsList(params);
		destoryGroupregister.add(groupDestory);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("deleteGroup");
		messagecreate.setParamsList(params);
		destoryGroupregister.add(messagecreate);

		configMap.put("DestoryGroupHandler", destoryGroupregister);
	}
	
	private void createAlarm() {
		ArrayList<Register> createalarm = new ArrayList<Register>();
		Register alarmcreate = new Register();
		alarmcreate.setObject(this.getAlarmcore());
		alarmcreate.setMetodName("saveAlarm");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		alarmcreate.setParamsList(params);
		createalarm.add(alarmcreate);
		
		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("createAlarmLog");
		logcreate.setParamsList(params);
		createalarm.add(logcreate);
		
		Register messageCreateAlarm = new Register();
		messageCreateAlarm.setObject(this.getMessageCore());
		messageCreateAlarm.setMetodName("createAlarm");
		messageCreateAlarm.setParamsList(params);
		createalarm.add(messageCreateAlarm);

		configMap.put("CreateAlarmHandler", createalarm);
	}

	private void createAlarmRule() {
		ArrayList<Register> createalarmrule = new ArrayList<Register>();

		Register alarmrulecreate = new Register();
		alarmrulecreate.setObject(this.getAlarmcore());
		alarmrulecreate.setMetodName("createAlarmRule");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		alarmrulecreate.setParamsList(params);
		createalarmrule.add(alarmrulecreate);
		
		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("createAlarmRuleLog");
		logcreate.setParamsList(params);
		createalarmrule.add(logcreate);
		
		Register messageCreateRule = new Register();
		messageCreateRule.setObject(this.getMessageCore());
		messageCreateRule.setMetodName("createAlarmRule");
		messageCreateRule.setParamsList(params);
		createalarmrule.add(messageCreateRule);

		configMap.put("CreateAlarmRuleHandler", createalarmrule);
	}

	private void modifyAlarm() {
		ArrayList<Register> modifyalarm = new ArrayList<Register>();

		Register alarmmodify = new Register();
		alarmmodify.setObject(this.getAlarmcore());
		alarmmodify.setMetodName("modifyAlarm");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		alarmmodify.setParamsList(params);
		modifyalarm.add(alarmmodify);
		
		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("modifyAlarmLog");
		logcreate.setParamsList(params);
		modifyalarm.add(logcreate);
		
		Register messageModifyAlarm = new Register();
		messageModifyAlarm.setObject(this.getMessageCore());
		messageModifyAlarm.setMetodName("modifyAlarm");
		messageModifyAlarm.setParamsList(params);
		modifyalarm.add(messageModifyAlarm);

		configMap.put("ModifyAlarmHandler", modifyalarm);
	}

	private void destoryAlarm() {
		ArrayList<Register> destoryalarmregister = new ArrayList<Register>();

		Register alarmdestory = new Register();
		alarmdestory.setObject(this.getAlarmcore());
		alarmdestory.setMetodName("destoryAlarm");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		alarmdestory.setParamsList(params);
		destoryalarmregister.add(alarmdestory);
		
		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("deleteAlarmLog");
		logcreate.setParamsList(params);
		destoryalarmregister.add(logcreate);
		
		Register messageDestoryAlarm = new Register();
		messageDestoryAlarm.setObject(this.getMessageCore());
		messageDestoryAlarm.setMetodName("deleteAlarm");
		messageDestoryAlarm.setParamsList(params);
		destoryalarmregister.add(messageDestoryAlarm);

		configMap.put("DestoryAlarmHandler", destoryalarmregister);
	}

	private void bindAlarm() {
		ArrayList<Register> bindalarmregister = new ArrayList<Register>();

		Register alarmBind = new Register();
		alarmBind.setObject(this.getAlarmcore());
		alarmBind.setMetodName("bindAlarm");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		alarmBind.setParamsList(params);
		bindalarmregister.add(alarmBind);
		
		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("bindAlarmLog");
		logcreate.setParamsList(params);
		bindalarmregister.add(logcreate);
		
		Register messageBindAlarm = new Register();
		messageBindAlarm.setObject(this.getMessageCore());
		messageBindAlarm.setMetodName("bindAlarm");
		messageBindAlarm.setParamsList(params);
		bindalarmregister.add(messageBindAlarm);

		configMap.put("BindAlarmHandler", bindalarmregister);
	}

	private void unBindAlarm() {
		ArrayList<Register> unbindalarmregister = new ArrayList<Register>();

		Register alarmUnBind = new Register();
		alarmUnBind.setObject(this.getAlarmcore());
		alarmUnBind.setMetodName("unBindAlarm");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		alarmUnBind.setParamsList(params);
		unbindalarmregister.add(alarmUnBind);
		
		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("unbindAlarmLog");
		logcreate.setParamsList(params);
		unbindalarmregister.add(logcreate);
		
		Register messageUnBindAlarm = new Register();
		messageUnBindAlarm.setObject(this.getMessageCore());
		messageUnBindAlarm.setMetodName("unbindAlarm");
		messageUnBindAlarm.setParamsList(params);
		unbindalarmregister.add(messageUnBindAlarm);

		configMap.put("UnBindAlarmHandler", unbindalarmregister);
	}

	private void deleteRule() {
		ArrayList<Register> deleteruleregister = new ArrayList<Register>();

		Register ruleDelete = new Register();
		ruleDelete.setObject(this.getAlarmcore());
		ruleDelete.setMetodName("deleteRule");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		ruleDelete.setParamsList(params);
		deleteruleregister.add(ruleDelete);
		
		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("deleteRuleLog");
		logcreate.setParamsList(params);
		deleteruleregister.add(logcreate);
		
		Register messageDeleteRule = new Register();
		messageDeleteRule.setObject(this.getMessageCore());
		messageDeleteRule.setMetodName("deleteRule");
		messageDeleteRule.setParamsList(params);
		deleteruleregister.add(messageDeleteRule);

		configMap.put("DeleteRuleHandler", deleteruleregister);
	}

	private void modifyPeriod() {
		ArrayList<Register> modifyperiodregister = new ArrayList<Register>();

		Register periodmodify = new Register();
		periodmodify.setObject(this.getAlarmcore());
		periodmodify.setMetodName("modifyPeriod");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		periodmodify.setParamsList(params);
		modifyperiodregister.add(periodmodify);

		configMap.put("ModifyPeriodHandler", modifyperiodregister);
	}

	private void modifyRule() {
		ArrayList<Register> modifyruleregister = new ArrayList<Register>();

		Register rulemodify = new Register();
		rulemodify.setObject(this.getAlarmcore());
		rulemodify.setMetodName("modifyRule");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		rulemodify.setParamsList(params);
		modifyruleregister.add(rulemodify);
		
		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("modifyRuleLog");
		logcreate.setParamsList(params);
		modifyruleregister.add(logcreate);
		
		Register messageModifyRule = new Register();
		messageModifyRule.setObject(this.getMessageCore());
		messageModifyRule.setMetodName("modifyRule");
		messageModifyRule.setParamsList(params);
		modifyruleregister.add(messageModifyRule);

		configMap.put("ModifyRuleHandler", modifyruleregister);
	}

	private void shutdownVM() {
		ArrayList<Register> shutdownvmregister = new ArrayList<Register>();

		Register vmshutdown = new Register();
		vmshutdown.setObject(this.getVmCore());
		vmshutdown.setMetodName("shutdownVM");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		vmshutdown.setParamsList(params);
		shutdownvmregister.add(vmshutdown);

		Register logshutdown = new Register();
		logshutdown.setObject(this.getLogCore());
		logshutdown.setMetodName("shutdownVMLog");
		logshutdown.setParamsList(params);
		shutdownvmregister.add(logshutdown);

		Register messageshutdown = new Register();
		messageshutdown.setObject(this.getMessageCore());
		messageshutdown.setMetodName("shutdownVMMessage");
		messageshutdown.setParamsList(params);
		shutdownvmregister.add(messageshutdown);

		configMap.put("ShutdownVMHandler", shutdownvmregister);
	}

	private void startVM() {
		ArrayList<Register> startvmregister = new ArrayList<Register>();

		Register vmstart = new Register();
		vmstart.setObject(this.getVmCore());
		vmstart.setMetodName("startVM");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		vmstart.setParamsList(params);
		startvmregister.add(vmstart);

		Register logstart = new Register();
		logstart.setObject(this.getLogCore());
		logstart.setMetodName("startVMLog");
		logstart.setParamsList(params);
		startvmregister.add(logstart);

		Register messagestart = new Register();
		messagestart.setObject(this.getMessageCore());
		messagestart.setMetodName("startVMMessage");
		messagestart.setParamsList(params);
		startvmregister.add(messagestart);

		configMap.put("StartVMHandler", startvmregister);
	}

	private void restartVM() {
		ArrayList<Register> restartvmregister = new ArrayList<Register>();

		Register vmrestart = new Register();
		vmrestart.setObject(this.getVmCore());
		vmrestart.setMetodName("restartVM");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		vmrestart.setParamsList(params);
		restartvmregister.add(vmrestart);

		Register logrestart = new Register();
		logrestart.setObject(this.getLogCore());
		logrestart.setMetodName("restartVMLog");
		logrestart.setParamsList(params);
		restartvmregister.add(logrestart);

		Register messagerestart = new Register();
		messagerestart.setObject(this.getMessageCore());
		messagerestart.setMetodName("restartVMMessage");
		messagerestart.setParamsList(params);
		restartvmregister.add(messagerestart);

		configMap.put("RestartVMHandler", restartvmregister);
	}

	private void deleteVM() {
		ArrayList<Register> deletevmregister = new ArrayList<Register>();

		Register vmdelete = new Register();
		vmdelete.setObject(this.getVmCore());
		vmdelete.setMetodName("deleteVM");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		vmdelete.setParamsList(params);
		deletevmregister.add(vmdelete);

		Register logdelete = new Register();
		logdelete.setObject(this.getLogCore());
		logdelete.setMetodName("deleteVMLog");
		logdelete.setParamsList(params);
		deletevmregister.add(logdelete);

		Register messagedelete = new Register();
		messagedelete.setObject(this.getMessageCore());
		messagedelete.setMetodName("deleteVMMessage");
		messagedelete.setParamsList(params);
		deletevmregister.add(messagedelete);

		Register statistics = new Register();
		statistics.setObject(this.getStatisticsCore());
		statistics.setMetodName("deleteStatistics");
		statistics.setParamsList(params);
		deletevmregister.add(statistics);

		configMap.put("DeleteVMHandler", deletevmregister);
	}

	private void adjustVM() {
		ArrayList<Register> adjustvmregister = new ArrayList<Register>();

		Register vmadjust = new Register();
		vmadjust.setObject(this.getVmCore());
		vmadjust.setMetodName("adjustVMConfig");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		vmadjust.setParamsList(params);
		adjustvmregister.add(vmadjust);

		Register logupdate = new Register();
		logupdate.setObject(this.getLogCore());
		logupdate.setMetodName("updateVmLog");
		logupdate.setParamsList(params);
		adjustvmregister.add(logupdate);

		Register messageadjust = new Register();
		messageadjust.setObject(this.getMessageCore());
		messageadjust.setMetodName("adjustVMMessage");
		messageadjust.setParamsList(params);
		adjustvmregister.add(messageadjust);

		configMap.put("AdjustVMHandler", adjustvmregister);
	}

	private void checkLogin() {
		ArrayList<Register> checkloginregister = new ArrayList<Register>();

		Register checklogin = new Register();
		checklogin.setObject(this.getUserCore());
		checklogin.setMetodName("checkLogin");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		checklogin.setParamsList(params);
		checkloginregister.add(checklogin);
		
		Register loginRegister = new Register();
		loginRegister.setObject(this.getLogCore());
		loginRegister.setMetodName("checkLogin");
		loginRegister.setParamsList(params);
		checkloginregister.add(loginRegister);

		configMap.put("checkLoginHandler", checkloginregister);
	}

	private void createImage() {
		ArrayList<Register> createimageregister = new ArrayList<Register>();

		Register imagecreate = new Register();
		imagecreate.setObject(this.getImageCore());
		imagecreate.setMetodName("createImage");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		imagecreate.setParamsList(params);
		createimageregister.add(imagecreate);

		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("createImageLog");
		logcreate.setParamsList(params);
		createimageregister.add(logcreate);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("defaultMessage");
		messagecreate.setParamsList(params);
		createimageregister.add(messagecreate);

		configMap.put("CreateImageHandler", createimageregister);
	}

	private void deleteImage() {
		ArrayList<Register> deleteimageregister = new ArrayList<Register>();

		Register imagedelete = new Register();
		imagedelete.setObject(this.getImageCore());
		imagedelete.setMetodName("deleteImage");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		imagedelete.setParamsList(params);
		deleteimageregister.add(imagedelete);

		Register logdelete = new Register();
		logdelete.setObject(this.getLogCore());
		logdelete.setMetodName("deleteImageLog");
		logdelete.setParamsList(params);
		deleteimageregister.add(logdelete);

		Register messagedelete = new Register();
		messagedelete.setObject(this.getMessageCore());
		messagedelete.setMetodName("defaultMessage");
		messagedelete.setParamsList(params);
		deleteimageregister.add(messagedelete);

		configMap.put("DeleteImageHandler", deleteimageregister);
	}

	private void createSnapshot() {
		ArrayList<Register> createsnapshot = new ArrayList<Register>();

		Register snapshotcreate = new Register();
		snapshotcreate.setObject(this.getSnapshotCore());
		snapshotcreate.setMetodName("createSnapshot");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		snapshotcreate.setParamsList(params);
		createsnapshot.add(snapshotcreate);

		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("createSnapshot");
		logcreate.setParamsList(params);
		createsnapshot.add(logcreate);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("defaultMessage");
		messagecreate.setParamsList(params);
		createsnapshot.add(messagecreate);

		Register statistics = new Register();
		statistics.setObject(this.getStatisticsCore());
		statistics.setMetodName("addStatistics");
		statistics.setParamsList(params);
		createsnapshot.add(statistics);

		configMap.put("CreateSnapshotHandler", createsnapshot);
	}

	private void deleteSnapshot() {
		ArrayList<Register> deletesnapshot = new ArrayList<Register>();

		Register snapshotdelete = new Register();
		snapshotdelete.setObject(this.getSnapshotCore());
		snapshotdelete.setMetodName("deleteSnapshotSeries");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		snapshotdelete.setParamsList(params);
		deletesnapshot.add(snapshotdelete);

		Register logdelete = new Register();
		logdelete.setObject(this.getLogCore());
		logdelete.setMetodName("destorySnapshot");
		logdelete.setParamsList(params);
		deletesnapshot.add(logdelete);

		Register messagedelete = new Register();
		messagedelete.setObject(this.getMessageCore());
		messagedelete.setMetodName("defaultMessage");
		messagedelete.setParamsList(params);
		deletesnapshot.add(messagedelete);

		configMap.put("DeleteSnapshotHandler", deletesnapshot);
	}

	private void destorySnapshot() {
		ArrayList<Register> deletesnapshot = new ArrayList<Register>();

		Register snapshotdelete = new Register();
		snapshotdelete.setObject(this.getSnapshotCore());
		snapshotdelete.setMetodName("destorySnapshot");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		snapshotdelete.setParamsList(params);
		deletesnapshot.add(snapshotdelete);

		Register logdelete = new Register();
		logdelete.setObject(this.getLogCore());
		logdelete.setMetodName("deleteSnapshotSeries");
		logdelete.setParamsList(params);
		deletesnapshot.add(logdelete);

		Register messagedelete = new Register();
		messagedelete.setObject(this.getMessageCore());
		messagedelete.setMetodName("defaultMessage");
		messagedelete.setParamsList(params);
		deletesnapshot.add(messagedelete);

		configMap.put("DestorySnapshotHandler", deletesnapshot);
	}

	private void roolbackSnapshot() {
		ArrayList<Register> roolbacksnapshot = new ArrayList<Register>();

		Register snapshotroolback = new Register();
		snapshotroolback.setObject(this.getSnapshotCore());
		snapshotroolback.setMetodName("rollbackSnapshot");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		snapshotroolback.setParamsList(params);
		roolbacksnapshot.add(snapshotroolback);

		Register logrollback = new Register();
		logrollback.setObject(this.getLogCore());
		logrollback.setMetodName("rollbackSnapshot");
		logrollback.setParamsList(params);
		roolbacksnapshot.add(logrollback);

		Register messagerollback = new Register();
		messagerollback.setObject(this.getMessageCore());
		messagerollback.setMetodName("defaultMessage");
		messagerollback.setParamsList(params);
		roolbacksnapshot.add(messagerollback);

		configMap.put("RollbackSnapshotHandler", roolbacksnapshot);
	}

	private void createVolume() {
		ArrayList<Register> createvolume = new ArrayList<Register>();

		Register volume = new Register();
		volume.setObject(this.getVolumeCore());
		volume.setMetodName("createVolume");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		volume.setParamsList(params);
		createvolume.add(volume);

		Register log = new Register();
		log.setObject(this.getLogCore());
		log.setMetodName("createVolume");
		log.setParamsList(params);
		createvolume.add(log);

		Register message = new Register();
		message.setObject(this.getMessageCore());
		message.setMetodName("createVolume");
		message.setParamsList(params);
		createvolume.add(message);

		Register statistics = new Register();
		statistics.setObject(this.getStatisticsCore());
		statistics.setMetodName("addStatistics");
		statistics.setParamsList(params);
		createvolume.add(statistics);

		configMap.put("CreateVolumeHandler", createvolume);
	}

	private void deleteVolume() {
		ArrayList<Register> deletevolume = new ArrayList<Register>();

		Register volume = new Register();
		volume.setObject(this.getVolumeCore());
		volume.setMetodName("deleteVolume");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		volume.setParamsList(params);
		deletevolume.add(volume);

		Register log = new Register();
		log.setObject(this.getLogCore());
		log.setMetodName("deleteVolume");
		log.setParamsList(params);
		deletevolume.add(log);

		Register message = new Register();
		message.setObject(this.getMessageCore());
		message.setMetodName("deleteVolume");
		message.setParamsList(params);
		deletevolume.add(message);

		Register statistics = new Register();
		statistics.setObject(this.getStatisticsCore());
		statistics.setMetodName("deleteStatistics");
		statistics.setParamsList(params);
		deletevolume.add(statistics);

		configMap.put("DeleteVolumeHandler", deletevolume);
	}

	private void bindVolume() {
		ArrayList<Register> bindvolume = new ArrayList<Register>();

		Register volume = new Register();
		volume.setObject(this.getVolumeCore());
		volume.setMetodName("bindVolume");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		volume.setParamsList(params);
		bindvolume.add(volume);

		Register log = new Register();
		log.setObject(this.getLogCore());
		log.setMetodName("bindVolume");
		log.setParamsList(params);
		bindvolume.add(log);

		Register message = new Register();
		message.setObject(this.getMessageCore());
		message.setMetodName("bindVolume");
		message.setParamsList(params);
		bindvolume.add(message);

		configMap.put("BindVolumeHandler", bindvolume);
	}

	private void unbindVolume() {
		ArrayList<Register> unbindvolume = new ArrayList<Register>();

		Register volume = new Register();
		volume.setObject(this.getVolumeCore());
		volume.setMetodName("unbindVolume");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		volume.setParamsList(params);
		unbindvolume.add(volume);

		Register log = new Register();
		log.setObject(this.getLogCore());
		log.setMetodName("unbindVolume");
		log.setParamsList(params);
		unbindvolume.add(log);

		Register message = new Register();
		message.setObject(this.getMessageCore());
		message.setMetodName("unbindVolume");
		message.setParamsList(params);
		unbindvolume.add(message);

		configMap.put("UnbindVolumeHandler", unbindvolume);
	}

	//
	private void createContainer() {
		ArrayList<Register> createconregister = new ArrayList<Register>();

		Register concreate = new Register();
		concreate.setObject(this.getContainerManager());
		concreate.setMetodName("createContainer");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		concreate.setParamsList(params);
		createconregister.add(concreate);

		/*Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("createVMLog");
		logcreate.setParamsList(params);
		createconregister.add(logcreate);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("createVMMessage");
		messagecreate.setParamsList(params);
		createconregister.add(messagecreate);

		Register statistics = new Register();
		statistics.setObject(this.getStatisticsCore());
		statistics.setMetodName("addStatistics");
		statistics.setParamsList(params);
		createconregister.add(statistics);*/

		configMap.put("CreateContainerHandler", createconregister);
	}
	private void deleteContainer() {
		ArrayList<Register> createconregister = new ArrayList<Register>();

		Register concreate = new Register();
		concreate.setObject(this.getContainerManager());
		concreate.setMetodName("deleteContainer");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		concreate.setParamsList(params);
		createconregister.add(concreate);

		/*Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("createVMLog");
		logcreate.setParamsList(params);
		createconregister.add(logcreate);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("createVMMessage");
		messagecreate.setParamsList(params);
		createconregister.add(messagecreate);

		Register statistics = new Register();
		statistics.setObject(this.getStatisticsCore());
		statistics.setMetodName("addStatistics");
		statistics.setParamsList(params);
		createconregister.add(statistics);
*/
		configMap.put("DeleteContainerHandler", createconregister);
	}
	
	private void stopContainer() {
		ArrayList<Register> createconregister = new ArrayList<Register>();

		Register concreate = new Register();
		concreate.setObject(this.getContainerManager());
		concreate.setMetodName("stopContainer");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		concreate.setParamsList(params);
		createconregister.add(concreate);

		/*Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("createVMLog");
		logcreate.setParamsList(params);
		createconregister.add(logcreate);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("createVMMessage");
		messagecreate.setParamsList(params);
		createconregister.add(messagecreate);

		Register statistics = new Register();
		statistics.setObject(this.getStatisticsCore());
		statistics.setMetodName("addStatistics");
		statistics.setParamsList(params);
		createconregister.add(statistics);*/

		configMap.put("StopContainerHandler", createconregister);
	}
	private void restartContainer() {
		ArrayList<Register> createconregister = new ArrayList<Register>();

		Register concreate = new Register();
		concreate.setObject(this.getContainerManager());
		concreate.setMetodName("restartContainer");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		concreate.setParamsList(params);
		createconregister.add(concreate);

		/*Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("createVMLog");
		logcreate.setParamsList(params);
		createconregister.add(logcreate);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("createVMMessage");
		messagecreate.setParamsList(params);
		createconregister.add(messagecreate);

		Register statistics = new Register();
		statistics.setObject(this.getStatisticsCore());
		statistics.setMetodName("addStatistics");
		statistics.setParamsList(params);
		createconregister.add(statistics);*/

		configMap.put("RestartContainerHandler", createconregister);
	}
	//
	private void createRegistry() {
		ArrayList<Register> createconregistry = new ArrayList<Register>();

		Register registrycreate = new Register();
		registrycreate.setObject(this.getRegistryManager());
		registrycreate.setMetodName("createRegistry");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		registrycreate.setParamsList(params);
		createconregistry.add(registrycreate);

		/*Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("createVMLog");
		logcreate.setParamsList(params);
		createconregister.add(logcreate);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("createVMMessage");
		messagecreate.setParamsList(params);
		createconregister.add(messagecreate);

		Register statistics = new Register();
		statistics.setObject(this.getStatisticsCore());
		statistics.setMetodName("addStatistics");
		statistics.setParamsList(params);
		createconregister.add(statistics);
*/
		configMap.put("CreateRegistryHandler", createconregistry);
	}
	
	private void deleteRegistry() {
		ArrayList<Register> delregistry= new ArrayList<Register>();

		Register registrydel = new Register();
		registrydel.setObject(this.getRegistryManager());
		registrydel.setMetodName("deleteRegistry");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		registrydel.setParamsList(params);
		delregistry.add(registrydel);

		/*Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("createVMLog");
		logcreate.setParamsList(params);
		createconregister.add(logcreate);

		Register messagecreate = new Register();
		messagecreate.setObject(this.getMessageCore());
		messagecreate.setMetodName("createVMMessage");
		messagecreate.setParamsList(params);
		createconregister.add(messagecreate);

		Register statistics = new Register();
		statistics.setObject(this.getStatisticsCore());
		statistics.setMetodName("addStatistics");
		statistics.setParamsList(params);
		createconregister.add(statistics);*/

		configMap.put("DeleteRegistryHandler", delregistry);
	}
	
	public void deleteLog() {
		ArrayList<Register> logArrayList= new ArrayList<Register>();

		Register logRegister = new Register();
		logRegister.setObject(this.getUserLogCore());
		logRegister.setMetodName("deleteLog");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		logRegister.setParamsList(params);
		logArrayList.add(logRegister);
		
		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("deleteLog");
		logcreate.setParamsList(params);
		logArrayList.add(logcreate);
		
		Register messageCreateAlarm = new Register();
		messageCreateAlarm.setObject(this.getMessageCore());
		messageCreateAlarm.setMetodName("deleteLog");
		messageCreateAlarm.setParamsList(params);
		logArrayList.add(messageCreateAlarm);

		configMap.put("DeleteLogHandler", logArrayList);
	}
	
	public void exportLog() {
		ArrayList<Register> exportLog= new ArrayList<Register>();

		Register logRegister = new Register();
		logRegister.setObject(this.getUserLogCore());
		logRegister.setMetodName("exportLog");
		ArrayList<Class<?>> params = new ArrayList<Class<?>>();
		params.add(JSONObject.class);
		logRegister.setParamsList(params);
		exportLog.add(logRegister);
		
		Register logcreate = new Register();
		logcreate.setObject(this.getLogCore());
		logcreate.setMetodName("exportLog");
		logcreate.setParamsList(params);
		exportLog.add(logcreate);
		
		Register messageExportLog = new Register();
		messageExportLog.setObject(this.getMessageCore());
		messageExportLog.setMetodName("exportLog");
		messageExportLog.setParamsList(params);
		exportLog.add(messageExportLog);

		configMap.put("ExportLogHandler", exportLog);
	}
	
	
	//
	
}
