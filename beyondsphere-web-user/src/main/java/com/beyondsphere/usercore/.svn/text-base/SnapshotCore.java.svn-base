/**
 * @author hty
 * @time 下午2:38:55
 * @date 2014年12月11日
 */
package com.beyondsphere.usercore;

import java.util.Date;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import org.Xen.API.Connection;
import com.beyondsphere.service.ConnectionService;
import com.beyondsphere.service.SnapshotService;
import com.beyondsphere.util.Utilities;

@Component
public class SnapshotCore {

	@Resource
	private SnapshotService snapshotService;
	@Resource
	private ConnectionService connectionService;

	/**
	 * 创建备份 [int userId, String snapshotId, String snapshotName, String
	 * resourceUuid, String resourceType]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject createSnapshot(JSONObject jo) {
		Date startTime = new Date();
		try {
			Connection c = connectionService.getConnectionFromUser(
					jo.getInt("userId"));
			if (jo.getString("resourceType").equals("instance")) {
				jo.put("result",
						snapshotService.createVMSnapshot(
								jo.getInt("userId"),
								jo.getString("uuid"),
								jo.getString("name"),
								jo.getString("resourceUuid"), c));
			} else if (jo.getString("resourceType").equals("volume")) {
				jo.put("result",
						snapshotService.createVolumeSnapshot(
								jo.getInt("userId"),
								jo.getString("uuid"),
								jo.getString("name"),
								jo.getString("resourceUuid"), c));
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
		} finally {
			jo.put("startTime", startTime);
			String timeUsed = Utilities.encodeText(Utilities
					.dateToUsed(startTime));
			jo.put("backupDate", timeUsed);
			Date endTime = new Date();
			int elapse = Utilities.timeElapse(startTime, endTime);
			jo.put("elapse", elapse);
		}
		return jo;
	}

	/**
	 * 删除备份[int userId, String resourceUuid, String resourceType]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject deleteSnapshotSeries(JSONObject jo) {
		Date startTime = new Date();
		try {
			Connection c = connectionService.getConnectionFromUser(
					jo.getInt("userId"));
			if (jo.getString("resourceType").equals("instance")) {
				jo.put("result",
						snapshotService.deleteVMSnapshotSeries(
								jo.getInt("userId"),
								jo.getString("resourceUuid"), c));
			} else if (jo.getString("resourceType").equals("volume")) {
				jo.put("result",
						snapshotService.deleteVolumeSnapshotSeries(
								jo.getInt("userId"),
								jo.getString("resourceUuid"), c));
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
		} finally {
			jo.put("startTime", startTime);
			Date endTime = new Date();
			int elapse = Utilities.timeElapse(startTime, endTime);
			jo.put("elapse", elapse);
		}
		return jo;
	}

	/**
	 * 回滚备份[int userId, String snapshotId, String resourceUuid, String
	 * resourceType]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject rollbackSnapshot(JSONObject jo) {
		Date startTime = new Date();
		try {
			Connection c = connectionService.getConnectionFromUser(
					jo.getInt("userId"));
			if (jo.getString("resourceType").equals("instance")) {
				snapshotService.rollbackVMSnapshot(jo, c);
			} else if (jo.getString("resourceType").equals("volume")) {
				snapshotService.rollbackVolumeSnapshot(jo, c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
		} finally {
			jo.put("startTime", startTime);
			Date endTime = new Date();
			int elapse = Utilities.timeElapse(startTime, endTime);
			jo.put("elapse", elapse);
		}
		return jo;
	}

	/**
	 * 删除一个备份点[int userId, String snapshotId, String resourceUuid, String
	 * resourceType]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject destorySnapshot(JSONObject jo) {
		Date startTime = new Date();
		try {
			Connection c = connectionService.getConnectionFromUser(
					jo.getInt("userId"));
			if (jo.getString("resourceType").equals("instance")) {
				jo.put("result",
						snapshotService.destoryVMSnapshot(jo, c));
			} else if (jo.getString("resourceType").equals("volume")) {
				jo.put("result", snapshotService
						.destoryVolumeSnapshot(jo, c));
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
		} finally {
			jo.put("startTime", startTime);
			Date endTime = new Date();
			int elapse = Utilities.timeElapse(startTime, endTime);
			jo.put("elapse", elapse);
		}
		return jo;
	}
}
