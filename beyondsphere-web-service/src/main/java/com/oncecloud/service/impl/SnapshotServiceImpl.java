/**
 * @author hty
 * @time 下午12:31:19
 * @date 2014年12月11日
 */
package com.oncecloud.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.Xen.API.Connection;
import org.Xen.API.Types;
import org.Xen.API.VDI;
import org.Xen.API.VM;

import com.oncecloud.dao.SnapshotDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.dao.VolumeDAO;
import com.oncecloud.entity.Snapshot;
import com.oncecloud.model.VMPower;
import com.oncecloud.service.SnapshotService;
import com.oncecloud.util.Utilities;

@Component("SnapshotService")
public class SnapshotServiceImpl implements SnapshotService {

	@Resource
	private SnapshotDAO snapshotDAO;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private VolumeDAO volumeDAO;

	public JSONArray getSnapshotList(int userId, int page, int limit,
			String search) {
		JSONArray ja = new JSONArray();
		int totalNum = snapshotDAO.countAllSnapshotList(userId, search);
		int vmNum = snapshotDAO.countVMSnapshotList(userId, search);
		int offside = limit - vmNum % limit;
		ja.put(totalNum);
		List<Object> vmSnapshotList = new ArrayList<Object>();
		List<Object> volumeSnapshotList = new ArrayList<Object>();
		if (page <= vmNum / limit) {
			vmSnapshotList = snapshotDAO.getOnePageVMSnapshotList(
					userId, page, limit, search);
		} else if (page == vmNum / limit + 1) {
			vmSnapshotList = snapshotDAO.getOnePageVMSnapshotList(
					userId, page, limit, search);
			volumeSnapshotList = snapshotDAO.getOnePageVolumeSnapshotList(userId, 1, offside, search, 0);
		} else {
			volumeSnapshotList = snapshotDAO
					.getOnePageVolumeSnapshotList(userId,
							page - vmNum / limit - 2, limit, search, offside);
		}
		if (vmSnapshotList != null) {
			for (int i = 0; i < vmSnapshotList.size(); i++) {
				JSONObject jo = new JSONObject();
				Object[] obj = (Object[]) vmSnapshotList.get(i);
				jo.put("resourceUuid", (String) obj[0]);
				jo.put("resourceName", Utilities.encodeText((String) obj[1]));
				jo.put("resourceType", "instance");
				jo.put("snapshotCount", ((Long) obj[2]).intValue());
				jo.put("snapshotSize", ((Long) obj[3]).intValue());
				String timeUsed = Utilities.encodeText(Utilities
						.dateToUsed((Date) obj[4]));
				jo.put("backupDate", timeUsed);
				ja.put(jo);
			}
		}
		if (volumeSnapshotList != null) {
			for (int i = 0; i < volumeSnapshotList.size(); i++) {
				JSONObject jo = new JSONObject();
				Object[] obj = (Object[]) volumeSnapshotList.get(i);
				jo.put("resourceUuid", (String) obj[0]);
				jo.put("resourceName", Utilities.encodeText((String) obj[1]));
				jo.put("resourceType", "volume");
				jo.put("snapshotCount", ((Long) obj[2]).intValue());
				jo.put("snapshotSize", ((Long) obj[3]).intValue());
				String timeUsed = Utilities.encodeText(Utilities
						.dateToUsed((Date) obj[4]));
				jo.put("backupDate", timeUsed);
				ja.put(jo);
			}
		}
		return ja;
	}

	public JSONArray getDetailList(String resourceUuid, String resourceType) {
		JSONArray ja = new JSONArray();
		List<Snapshot> snapshotList = new ArrayList<Snapshot>();
		if (resourceType.equals("instance")) {
			snapshotList = snapshotDAO
					.getVmSnapshotList(resourceUuid);
		} else if (resourceType.equals("volume")) {
			snapshotList = snapshotDAO.getVolumeSnapshotList(
					resourceUuid);
		}
		if (snapshotList != null) {
			for (int i = 0; i < snapshotList.size(); i++) {
				JSONObject jo = new JSONObject();
				Snapshot ss = snapshotList.get(i);
				jo.put("snapshotId", ss.getSnapshotId());
				jo.put("snapshotName",
						Utilities.encodeText(ss.getSnapshotName()));
				jo.put("snapshotSize", ss.getSnapshotSize());
				jo.put("backupDate", Utilities.formatTime(ss.getBackupDate()));
				ja.put(jo);
			}
		}
		return ja;
	}

	public JSONObject getBasicList(int userId, String resourceUuid,
			String resourceType) {
		JSONObject jo = new JSONObject();
		Object[] obj = null;
		if (resourceType.equals("instance")) {
			obj = (Object[]) snapshotDAO.getOneVmSnapshot(
					resourceUuid);
		} else if (resourceType.equals("volume")) {
			obj = (Object[]) snapshotDAO.getOneVolumeSnapshot(
					resourceUuid);
		}
		jo.put("resourceName", Utilities.encodeText((String) obj[1]));
		jo.put("snapshotCount", ((Long) obj[2]).intValue());
		jo.put("snapshotSize", ((Long) obj[3]).intValue());
		String timeUsed = Utilities.encodeText(Utilities
				.dateToUsed((Date) obj[4]));
		jo.put("backupDate", timeUsed);
		jo.put("backStatus", obj[5]);
		return jo;
	}

	public boolean createVMSnapshot(int userId, String snapshotId,
			String snapshotName, String resourceUuid, Connection c) {
		boolean result = false;
		Date startTime = new Date();
		try {
			VM vm = Types.toVM(resourceUuid);
			if (vm.snapshot(c, snapshotId)) {
				result = snapshotDAO.insertSnapshot(snapshotId,
						snapshotName, 1, startTime, resourceUuid, null, userId);
				result &= vmDAO.updateVMBackdate(resourceUuid,
						startTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean createVolumeSnapshot(int userId, String snapshotId,
			String snapshotName, String resourceUuid, Connection c) {
		boolean result = false;
		Date startTime = new Date();
		try {
			VDI vdi = Types.toVDI(resourceUuid);
			if (vdi.snapshot(c, snapshotId)) {
				result = snapshotDAO.insertSnapshot(snapshotId,
						snapshotName, 1, startTime, null, resourceUuid, userId);
				result &= volumeDAO.updateVolumeBackdate(
						resourceUuid, startTime);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean deleteVMSnapshotSeries(int userId, String resourceUuid,
			Connection c) {
		boolean result = false;
		try {
			VDI vdi = Types.toVDI(resourceUuid);
			if (vdi.destroyAllSnapshots(c)) {
				result = snapshotDAO.deleteVmSnapshot(resourceUuid,
						userId);
				result &= vmDAO.updateVMBackdate(resourceUuid, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean deleteVolumeSnapshotSeries(int userId, String resourceUuid,
			Connection c) {
		boolean result = false;
		try {
			VDI vdi = Types.toVDI(resourceUuid);
			if (vdi.destroyAllSnapshots(c)) {
				result = snapshotDAO.deleteVolumeSnapshot(resourceUuid,
						userId);
				result &= volumeDAO.updateVolumeBackdate(
						resourceUuid, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private JSONObject defaultRollback(JSONObject jo, Connection c,
			boolean flag, String resourceUuid, boolean type) {
		try {
			if (flag) {
				VM vm = Types.toVM(resourceUuid);
				if (resourceUuid != null) {
					if (vmDAO.getVMByUuid(resourceUuid).getVmPower().equals(VMPower.POWER_RUNNING)) {
						vm.hardShutdown(c);
						vmDAO.updateVMPower(resourceUuid,
								VMPower.POWER_HALTED);
					}
				}
				this.defaultVDI(jo, c, type, vm);
			} else {
				jo.put("exist", false);
				jo.put("result", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("exist", true);
			jo.put("result", false);
		}
		return jo;
	}

	private JSONObject defaultVDI(JSONObject jo, Connection c, boolean type,
			VM vm) throws Exception {
		boolean result = false;
		if (type) { // volume
			VDI vdi = Types.toVDI(jo.getString("resourceUuid"));
			result = vdi.rollback(c, jo.getString("snapshotId"));
		} else { // vm
			result = vm.rollback(c, jo.getString("snapshotId"));
		}

		if (result) {
			jo.put("exist", true);
			jo.put("result", true);
		} else {
			jo.put("exist", true);
			jo.put("result", false);
		}
		return jo;
	}

	public JSONObject rollbackVMSnapshot(JSONObject jo, Connection c) {
		String resourceUuid = jo.getString("resourceUuid");
		this.defaultRollback(jo, c, vmDAO.isActive(resourceUuid),
				resourceUuid, false);
		return jo;
	}

	public JSONObject rollbackVolumeSnapshot(JSONObject jo, Connection c) {
		String resourceUuid = jo.getString("resourceUuid");
		this.defaultRollback(jo, c, volumeDAO.isActive(resourceUuid),
				resourceUuid, true);
		return jo;
	}

	private Date defaultDestory(JSONObject jo, Connection c, String resourceUuid) {
		Date maxdate = null;
		try {
			String snapshotId = jo.getString("snapshotId");
			VDI vdi = Types.toVDI(resourceUuid);
			if (vdi.destroySnapshot(c, snapshotId)) {
				Snapshot ss = snapshotDAO.getSnapshot(snapshotId);
				snapshotDAO.deleteOneSnapshot(ss);
				maxdate = snapshotDAO.getRecentVmSnapshotDate(
						resourceUuid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxdate;
	}

	public boolean destoryVMSnapshot(JSONObject jo, Connection c) {
		String resourceUuid = jo.getString("resourceUuid");
		return vmDAO.updateVMBackdate(resourceUuid,
				this.defaultDestory(jo, c, resourceUuid));
	}

	public boolean destoryVolumeSnapshot(JSONObject jo, Connection c) {
		String resourceUuid = jo.getString("resourceUuid");
		return volumeDAO.updateVolumeBackdate(resourceUuid,
				this.defaultDestory(jo, c, resourceUuid));
	}

}
