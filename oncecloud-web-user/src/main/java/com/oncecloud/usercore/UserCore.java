package com.oncecloud.usercore;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.entity.User;
import com.oncecloud.helper.HashHelper;
import com.oncecloud.model.UserStatus;
import com.oncecloud.service.UserService;
import com.oncecloud.util.Utilities;


/**
 * @author 玉和
 * @date 2014-12-8
 */
@Component("UserUCore")
public class UserCore {
  
	private final static Logger logger = Logger.getLogger(UserCore.class);
	
	@Resource
	private UserService userService;
	@Resource
	private HashHelper hashHelper; 

	/**
	 * 用户 登录
	 * 
	 * @author 玉和
	 * @date 2014-12-8
	 */
	public JSONObject checkLogin(JSONObject jo){
		Date startTime = new Date();
		try {
			if(jo!=null){
				int result = 1;
				User user = userService.getUser(jo.get("userName").toString());
				if (user == null) {
					// User does not exist
					result = 3;
				} else {
					if (user.getUserStatus() == UserStatus.DELETED) {
						result = 2;
					} else {
						String pass = user.getUserPass();
						if (pass.equals(hashHelper.md5Hash(jo.get("userPass").toString()))) {
							// Validated
							result = 0;
						} else {
							// Password is wrong
							result = 1;
						}
					}
				}
				jo.put("userId", user.getUserId());
				jo.put("result", result==0 ? true:false);
				jo.put("userObj", user);
				logger.info("Check Login: User [" + jo.get("userName").toString() + "] Result ["
						+ result + "], login success!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", -1);
		}
		finally{
			jo.put("startTime", startTime);
			Date endTime = new Date();
			jo.put("elapse",
					Utilities.timeElapse(startTime, endTime));
		}
		return jo;
	}
	
}
