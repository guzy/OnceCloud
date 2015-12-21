package com.beyondsphere.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.query.QueryList;
import com.beyondsphere.model.CommonModifyModel;

@RequestMapping("/ModifyAction")
@Controller
public class ModifyAction {
	
	@Resource
	private QueryList queryList;

	@RequestMapping(value = "/ModifyBasicInfo", method = { RequestMethod.POST })
	@ResponseBody
	public String modifyBasicInfo(HttpServletRequest request, CommonModifyModel commonModifyModel) {
		String modifyType = commonModifyModel.getModifyType();
		String modifyUuid = commonModifyModel.getModifyUuid();
		String modifyName = commonModifyModel.getModifyName();
		String modifyDesc = commonModifyModel.getModifyDesc();
		modifyDesc = modifyDesc.trim();
		if (modifyDesc.equals("")) {
			modifyDesc = null;
		} else if (modifyDesc.length() > 80) {
			modifyDesc = modifyDesc.substring(0, 79);
		}
		JSONObject jo = queryList.queryBasicinfos(modifyUuid, modifyName, modifyDesc, modifyType);
		return jo.toString();
	}

}
