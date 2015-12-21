package com.oncecloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.VDI;
import org.Xen.API.VM;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.VMDAO;
import com.oncecloud.dao.VolumeDAO;
import com.oncecloud.entity.Volume;
import com.oncecloud.model.VolumeStatus;
import com.oncecloud.service.VolumeService;
import com.oncecloud.util.Utilities;

@Component("VolumeService")
public class VolumeServiceImpl implements VolumeService {

	@Resource
	private VMDAO vmDAO;
	@Resource
	private VolumeDAO volumeDAO;

	@Override
	public List<Volume> getAllList() {
		List<Volume> volumeList = volumeDAO.getAllList();
		return volumeList;
	}

	@Override
	public Volume getOneById(String uid) {
		Volume volume = volumeDAO.getOneByID(uid);
		return volume;
	}


	@Override
	public List<Volume> getListbyUserid(int userid) {
		List<Volume> volumeList = volumeDAO.getListbyUserid(userid);
		return volumeList;
	}

	public boolean emptyAttachedVolume(Connection c, String vmUuid) {
		boolean result = true;
		try {
			List<String> volumeList = volumeDAO.getVolumeUuidListOfVM(vmUuid);
			for (String volume : volumeList) {
				try {
					VM.deleteDataVBD(c, vmUuid, volume);
				} catch (Exception e) {
					e.printStackTrace();
				}
				result &= volumeDAO.emptyDependency(volume);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取用户硬盘列表
	 * 
	 * @param userId
	 * @param page
	 * @param limit
	 * @param search
	 * @return
	 */
	public JSONArray getVolumeList(int userId, int page, int limit,
			String search) {
		JSONArray ja = new JSONArray();
		int totalNum = volumeDAO.countByUserId(userId, search);
		List<Volume> volumeList = volumeDAO.getOnePageByUserId(userId, page, limit, search);
		ja.put(totalNum);
		if (volumeList != null) {
			for (int i = 0; i < volumeList.size(); i++) {
				JSONObject jo = new JSONObject();
				Volume volume = volumeList.get(i);
				jo.put("volumeid", volume.getVolumeUuid());
				jo.put("volumename",
						Utilities.encodeText(volume.getVolumeName()));
				String vmuuid = volume.getVolumeDependency();
				if (vmuuid != null) {
					jo.put("volumedepen", vmuuid);
					jo.put("depenname",
							Utilities.encodeText(vmDAO
									.getVMByUuid(vmuuid).getVmName()));
					jo.put("isused", true);
				} else {
					jo.put("volumedepen", "");
					jo.put("depenname", "");
					jo.put("isused", false);
				}
				jo.put("volState", volume.getVolumeStatus().ordinal());
				jo.put("volumesize", volume.getVolumeSize());
				String timeUsed = Utilities.encodeText(Utilities
						.dateToUsed(volume.getCreateDate()));
				jo.put("createdate", timeUsed);
				if (volume.getBackupDate() == null) {
					jo.put("backupdate", "");
				} else {
					timeUsed = Utilities.encodeText(Utilities.dateToUsed(volume
							.getBackupDate()));
					jo.put("backupdate", timeUsed);
				}
				ja.put(jo);
			}
		}
		return ja;
	}

	public boolean createVolume(int userId, String volumeUuid,
			String volumeName, int volumeSize, Connection c, Date startTime) {
		boolean result = false;
		try {
			if (volumeDAO.preCreateVolume(volumeUuid, volumeName,
					userId, volumeSize, startTime, VolumeStatus.STATUS_CREATE)) {
				if (VDI.createDataDisk(c, volumeUuid, (long) volumeSize) != null) {
					result = volumeDAO.updateVolumeStatus(volumeUuid,
							VolumeStatus.STATUS_FREE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!result) {
				vmDAO.deleteVMByUuid(userId, volumeUuid);
			}
		}
		return result;
	}

	public boolean deleteVolume(int userId, String volumeUuid, Connection c) {
		boolean result = false;
		try {
			if (volumeDAO.updateVolumeStatus(volumeUuid, VolumeStatus.STATUS_DESTORY)) {
				VDI.deleteDataDisk(c, volumeUuid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			result = volumeDAO.deleteVolume(userId, volumeUuid);
		}
		return result;
	}

	public boolean bindVolume(int userId, String volumeUuid, String vmUuid,
			Connection c) {
		boolean result = false;
		try {
			if (volumeDAO.updateVolumeStatus(volumeUuid, VolumeStatus.STATUS_MOUNTING)) {
				if (VM.createDataVBD(c, vmUuid, volumeUuid)) {
					result = volumeDAO.addDependency(volumeUuid,
							vmUuid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!result) {
				volumeDAO.updateVolumeStatus(volumeUuid, VolumeStatus.STATUS_FREE);
			}
		}
		return result;
	}

	public boolean unbindVolume(int userId, String volumeUuid, Connection c) {
		boolean result = false;
		try {
			if (volumeDAO.updateVolumeStatus(volumeUuid, VolumeStatus.STATUS_UNMOUNTING)) {
				if (VM.deleteDataVBD(c,
						volumeDAO.getVolumeByUuid(volumeUuid)
								.getVolumeDependency(), volumeUuid)) {
					result = volumeDAO.emptyDependency(volumeUuid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!result) {
				volumeDAO.updateVolumeStatus(volumeUuid, VolumeStatus.STATUS_MOUNTED);
			}
		}
		return result;
	}

	@Override
	public JSONArray getVolumesofVm(String vmUuid) {
		JSONArray ja = new JSONArray();
		List<Volume> volumeList = volumeDAO.getVolumeListOfVM(vmUuid);
		if (volumeList != null) {
			for (int i = 0; i < volumeList.size(); i++) {
				JSONObject jo = new JSONObject();
				Volume volume = volumeList.get(i);
				jo.put("volumeId", volume.getVolumeUuid());
				jo.put("volumeName",
						Utilities.encodeText(volume.getVolumeName()));
				jo.put("volumeSize", volume.getVolumeSize());
				ja.put(jo);
			}
		}
		return ja;
	}

	@Override
	public JSONArray getAvailableVolumes(int userId) {
		JSONArray ja = new JSONArray();
		List<Volume> volumeList = volumeDAO.getAbledVolumes(userId);
		if (volumeList != null) {
			for (int i = 0; i < volumeList.size(); i++) {
				JSONObject jo = new JSONObject();
				Volume volume = volumeList.get(i);
				jo.put("volumeId", volume.getVolumeUuid());
				jo.put("volumeName",
						Utilities.encodeText(volume.getVolumeName()));
				jo.put("volumeSize", volume.getVolumeSize());
				ja.put(jo);
			}
		}
		return ja;
	}

	@Override
	public JSONObject getVolumeDetail(String volumeUuid) {
		JSONObject jo = new JSONObject();
		Volume volume = volumeDAO.getVolumeByUuid(volumeUuid);
		if (volume != null) {
			jo.put("volumeName", Utilities.encodeText(volume.getVolumeName()));
			jo.put("volumeUID", volume.getVolumeUID());
			jo.put("volumeSize", volume.getVolumeSize());
			jo.put("volumeDependency",
					(null == volume.getVolumeDependency()) ? "&nbsp;" : volume
							.getVolumeDependency());
			jo.put("volumeDescription",
					(null == volume.getVolumeDescription()) ? "&nbsp;"
							: Utilities.encodeText(volume
									.getVolumeDescription()));
			jo.put("createDate", Utilities.formatTime(volume.getCreateDate()));
			String timeUsed = Utilities.encodeText(Utilities.dateToUsed(volume
					.getCreateDate()));
			if (volume.getBackupDate() == null) {
				jo.put("backupDate", "&nbsp;");
			} else {
				String tUsed = Utilities.encodeText(Utilities.dateToUsed(volume
						.getBackupDate()));
				jo.put("backupDate", tUsed);
			}
			jo.put("useDate", timeUsed);
			jo.put("volumeStatus", volume.getVolumeStatus());
		}
		return jo;
	}
}
