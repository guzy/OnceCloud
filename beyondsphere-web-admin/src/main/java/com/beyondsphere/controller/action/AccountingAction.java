package com.beyondsphere.controller.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.manager.AccountingManager;
import com.oncecloud.model.ListModel;

/**
 * 
 * @author mayh
 *	用于统计资源费用
 */
@Controller
@RequestMapping(value="AccountingAction")
public class AccountingAction {
	@Resource
	private AccountingManager accountingManager; 
	
	@RequestMapping(value = "/AccountingList", method = { RequestMethod.GET })
	@ResponseBody
	public String accountingList(HttpServletRequest request, ListModel list) {
		JSONArray ja = null;
		try{
			ja = accountingManager.getOnePageListOfAccounting(list.getPage(), list.getLimit(),list.getSearch(), Integer.valueOf(list.getType()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return ja.toString();
	}
	
}
