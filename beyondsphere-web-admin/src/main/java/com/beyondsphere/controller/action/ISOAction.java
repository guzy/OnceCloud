/**
 * @author cyh
 * @time 上午11:05:48
 * @date 2014年12月15日
 */
package com.beyondsphere.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.manager.ISOManager;
import com.oncecloud.entity.User;
import com.oncecloud.model.ListModel;

@RequestMapping("ISOAction")
@Controller
public class ISOAction {
	@Resource
	private ISOManager isoManager;
	
	@RequestMapping(value = "/ISOList", method = { RequestMethod.GET })
	@ResponseBody
	public String roleList(HttpServletRequest request, ListModel list) {
		User user = (User) request.getSession().getAttribute("user");
		int userId = user.getUserId();
		JSONArray array=new JSONArray();
		array=isoManager.getISOListAll(userId);
		return array.toString();
	}
	

//	@RequestMapping(value = "/Delete", method = { RequestMethod.GET })
//	@ResponseBody
//	public String delete(HttpServletRequest request,
//			@RequestParam("isoid") String isoid, @RequestParam String isoName) {
//		User user = (User) request.getSession().getAttribute("user");
//		int userId = user.getUserId();
//		JSONObject jo = isoManager.deleteISO(userId, isoid, isoName);
//		return jo.toString();
//	}

}