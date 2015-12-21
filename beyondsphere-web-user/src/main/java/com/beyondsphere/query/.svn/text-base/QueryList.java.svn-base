/**
 * @author hty
 * @time 下午2:02:58
 * @date 2014年12月8日
 */
package com.beyondsphere.query;

import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.model.PagedList;
import com.beyondsphere.service.AlarmService;
import com.beyondsphere.service.ContainerService;
import com.beyondsphere.service.GroupService;
import com.beyondsphere.service.ImageService;
import com.beyondsphere.service.LogService;
import com.beyondsphere.service.ModifyService;
import com.beyondsphere.service.NetworkService;
import com.beyondsphere.service.RegistryService;
import com.beyondsphere.service.RepositoryService;
import com.beyondsphere.service.RoleService;
import com.beyondsphere.service.SnapshotService;
import com.beyondsphere.service.StatisticsService;
import com.beyondsphere.service.VMService;
import com.beyondsphere.service.VolumeService;
import com.beyondsphere.vi.RoleActionView;

@Component
public class QueryList {

	@Resource
	private VMService vmService;
	@Resource
	private NetworkService networkService;
	@Resource
	private AlarmService alarmService;
	@Resource
	private VolumeService volumeService;
	@Resource
	private SnapshotService snapshotService;
	@Resource
	private ImageService imageService;
	@Resource
	private LogService logService;
	@Resource
	private RoleService roleService;
	@Resource
	private StatisticsService statisticsService;
	@Resource
	private ModifyService modifyService;
	@Resource
	private GroupService groupService;
	@Resource
	private ContainerService containerService;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private RegistryService registryService;
	
	public JSONArray queryVMList(int page, int limit, String search,
			String groupUuid, int userId) {
		return vmService.getVMList(userId, page, limit, groupUuid, search);
	}

	public JSONArray queryAlarmList(int userId, String search, int page,
			int limit) {
		return alarmService.getAlarmList(userId, search, page, limit);
	}

	public JSONArray queryAlarmVMList(String alarmUuid, int page, int limit,
			String search, Integer userId) {
		return alarmService.getAlarmVMList(alarmUuid, page, limit, search,
				userId);
	}

	public JSONObject queryAlarmBasicList(int userId, String alarmUuid) {
		return alarmService.getAlarm(userId, alarmUuid);
	}

	public JSONArray queryGroupList(int userId, int page, int limit) {
		return groupService.getGroupJson(userId, page, limit);
	}

	public JSONArray queryGroupList(int userId) {
		return groupService.getGroupJson(userId);
	}

	public JSONObject queryGroup(int userId, String groupUuid) {
		return groupService.getGroupInfo(userId, groupUuid);
	}

	public JSONArray queryRuleList(String alarmUuid, int page, int limit) {
		return alarmService.getRuleList(alarmUuid, page, limit);
	}

	public JSONArray queryImageList(int userId, int page, int limit,
			String search, String type) {
		return imageService.getImageList(userId, page, limit, search, type);
	}

	public JSONObject queryImagedetail(String imageUuid) {
		return imageService.getImagedetail(imageUuid);
	}

	public PagedList<RoleActionView> queryAuthorityList(int page, int limit,
			int roleId) {
		return roleService.getPagedRoleActionList(page, limit, roleId);
	}

	public JSONArray querySnapshotList(int userId, int page, int limit,
			String search) {
		return snapshotService.getSnapshotList(userId, page, limit, search);
	}

	public JSONObject querySnapshotBasicList(int userId, String resourceUuid,
			String resourceType) {
		return snapshotService.getBasicList(userId, resourceUuid, resourceType);
	}

	public JSONArray queryDetailSnapshotList(String resourceUuid,
			String resourceType) {
		return snapshotService.getDetailList(resourceUuid, resourceType);
	}

	public JSONArray queryVolumeList(int userId, int page, int limit,
			String search) {
		return volumeService.getVolumeList(userId, page, limit, search);
	}

	public JSONObject queryVolumeDetail(String volumeUuid) {
		return volumeService.getVolumeDetail(volumeUuid);
	}

	public JSONArray queryVolumeListByVm(String vmUuid) {
		return volumeService.getVolumesofVm(vmUuid);
	}

	public JSONArray queryVMListOfVolume(int userId, int page, int limit,
			String search) {
		return vmService.getVMListOfVolume(userId, page, limit, search);
	}

	public JSONArray queryLogList(int logUID, int logStatus, int start, int num) {
		return logService.getLogList(logUID, logStatus, start, num,"");
	}

	public JSONArray queryStatistics(Map<String, Object> params, int page,
			int limit) {
		return statisticsService.getOnePageStatistics(params, page, limit);
	}

	public JSONObject queryBasicinfos(String modifyUuid, String modifyName,
			String modifyDesc, String modifyType) {
		return modifyService.modifyBasicInfo(modifyUuid, modifyName,
				modifyDesc, modifyType);
	}

	public JSONObject queryNetworkById(String netId) {
		return networkService.getSwitchObj(netId);
	}

	public JSONArray queryAvaliableVolumes(int userId) {
		return volumeService.getAvailableVolumes(userId);
	}

	public JSONArray queryGroupVMList(int page, int limit, int userId) {
		return groupService.getVMGroupList(page, limit, userId);
	}

	public JSONArray queryVMListOfGroup(int page, int limit, int userId,
			String groupUuid) {
		return groupService.getVMListOfGroup(page, limit, groupUuid, userId);
	}
	
	public JSONArray ContainerTemplateList(int userId, int page, int limit,
			String search, String type) {
		return containerService.ContainerTemplateList(userId, page, limit, search, type);
	}
	
	public JSONArray ContainerRepoList(int userId, int page, int limit,
			String search, String type) {
		return repositoryService.ContainerRepoList(userId, page, limit, search, type);
	}
	
	public JSONArray registryRepoList(int userId, int page, int limit,
			String search, String type) {
		return registryService.regRepoList(userId, page, limit, search, type);
	}
	
	public JSONArray registryList(int userId, int page, int limit,
			String search, String type) {
		return registryService.registryList(userId, page, limit, search, type);
	}
	
	public JSONArray queryContainerList(int page, int limit, String search,
			String groupUuid, int userId) {
		return containerService.queryContainerList(userId, page, limit, groupUuid, search);
	}
	
	public JSONArray queryAllContainers(){
		return containerService.listConstainers();
	}

}
