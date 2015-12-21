/**
 * @author hty
 * @time 上午10:43:25
 * @date 2014年12月13日
 */
package com.oncecloud.usercore;

import java.util.Date;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.service.ConnectionService;
import com.oncecloud.service.VMService;
import com.oncecloud.service.VolumeService;
import com.oncecloud.util.Utilities;

@Component
public class VolumeCore {
	
	@Resource
	private VolumeService volumeService;
	@Resource
	private ConnectionService connectionService;
	@Resource
	private VMService vmService;

	/**
	 * 创建硬盘[int userId, String volumeUuid, String volumeName, int volumeSize]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject createVolume(JSONObject jo) {
		Date startTime = new Date();
		try {
			jo.put("result",
					volumeService.createVolume(
							jo.getInt("userId"),
							jo.getString("uuid"),
							jo.getString("name"),
							jo.getInt("volumeSize"),
							connectionService.getConnectionFromUser(
									jo.getInt("userId")), startTime));
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
		} finally {
			jo.put("startTime", startTime);
			jo.put("elapse", Utilities.timeElapse(startTime, new Date()));
		}
		return jo;
	}

	/**
	 * 删除硬盘[int userId, String volumeUuid]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject deleteVolume(JSONObject jo) {
		Date startTime = new Date();
		try {
			jo.put("result",
					volumeService.deleteVolume(
							jo.getInt("userId"),
							jo.getString("uuid"),
							connectionService.getConnectionFromUser(
									jo.getInt("userId"))));
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
		} finally {
			jo.put("startTime", startTime);
			jo.put("elapse", Utilities.timeElapse(startTime, new Date()));
		}
		return jo;
	}

	/**
	 * 绑定硬盘[int userId, String volumeUuid, String vmUuid]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject bindVolume(JSONObject jo) {
		Date startTime = new Date();
		try {
			jo.put("result",
					volumeService.bindVolume(
							jo.getInt("userId"),
							jo.getString("volumeUuid"),
							jo.getString("vmUuid"),
							connectionService.getConnectionFromUser(
									jo.getInt("userId"))));
			jo.put("vmName", vmService.getOCVM(jo.getString("vmUuid")).getVmName());
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
		} finally {
			jo.put("startTime", startTime);
			jo.put("elapse", Utilities.timeElapse(startTime, new Date()));
		}
		return jo;
	}

	/**
	 * 解绑硬盘[int userId, String volumeUuid]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject unbindVolume(JSONObject jo) {
		Date startTime = new Date();
		try {
			jo.put("result",
					volumeService.unbindVolume(
							jo.getInt("userId"),
							jo.getString("volumeUuid"),
							connectionService.getConnectionFromUser(
									jo.getInt("userId"))));
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
		} finally {
			jo.put("startTime", startTime);
			jo.put("elapse", Utilities.timeElapse(startTime, new Date()));
		}
		return jo;
	}
}
