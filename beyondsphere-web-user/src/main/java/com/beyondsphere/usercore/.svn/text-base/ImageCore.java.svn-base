/**
 * @author hty
 * @time 上午9:06:35
 * @date 2014年12月9日
 */
package com.beyondsphere.usercore;

import java.util.Date;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.service.ConnectionService;
import com.beyondsphere.service.ImageService;
import com.beyondsphere.util.Utilities;

@Component("ImageUserCore")
public class ImageCore {
	
	@Resource
	private ImageService imageService;
	@Resource
	private ConnectionService connectionService;

	/**
	 * beyond sphere+ 制作模板 [String vmUuid, int userId, String imageName, String
	 * imageDesc]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject createImage(JSONObject jo) {
		Date startTime = new Date();
		try {
			Connection c = connectionService.getConnectionFromUser(jo.getInt("userId"));
			if (c != null) {
				jo.put("result",
						imageService.makeImage(
								jo.getString("vmUuid"), jo.getInt("userId"),
								jo.getString("imageName"),
								jo.getString("imageDesc"), c)
					  );
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
		} finally {
			Date endTime = new Date();
			jo.put("startTime", startTime);
			jo.put("elapse", Utilities.timeElapse(startTime, endTime));
		}
		return jo;
	}

	/**
	 * beyond sphere+ 删除模板[int userId, String imageUuid, String imageName]
	 * 
	 * @param jo
	 * @return
	 */
	public JSONObject deleteImage(JSONObject jo) {
		Date startTime = new Date();
		try {
			Connection c = connectionService.getConnectionFromUser(
					jo.getInt("userId"));
			jo.put("result",
					imageService.deleteImage(c,
							jo.getString("imageUuid"), jo.getInt("userId"))
				   );
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
		} finally {
			Date endTime = new Date();
			jo.put("startTime", startTime);
			jo.put("elapse", Utilities.timeElapse(startTime, endTime));
		}
		return jo;
	}

}
