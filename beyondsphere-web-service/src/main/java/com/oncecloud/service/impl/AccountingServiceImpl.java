package com.oncecloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.oncecloud.dao.AccountingDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.entity.OCStatistics;
import com.oncecloud.entity.User;
import com.oncecloud.service.AccountingService;
@Service("AccountingService")
public class AccountingServiceImpl implements AccountingService {
	@Resource
	private AccountingDAO accountingDAO;
	@Resource
	private UserDAO userDAO;
	/*计费管理分页查询*/
	@Override
	public JSONArray getOnePageListOfAccounting(int page, int limit,String search, int type) {
		JSONArray jsonArray = new JSONArray();
		int totalNum = countAllAccountingList(search);
		jsonArray.put(totalNum);
		List<OCStatistics> ocs = accountingDAO.getOnePageListOfAccounting(page, limit,search);
		if(ocs!=null){
			for(OCStatistics oc:ocs){
				jsonArray.put(objectToJSONObject(oc,type));
			}
		}
		return jsonArray;
	}
	/*计费管理总页数*/
	public int countAllAccountingList(String search){
		int totalNum = accountingDAO.countAllAccountingList(search);
		return totalNum;
		
	}
	/*json对象转换*/
	private JSONObject objectToJSONObject(OCStatistics osc,int type){
		JSONObject jo = new JSONObject();
		jo.put("userId", osc.getUserId());
		//查询用户名
		User user =userDAO.getUser(osc.getUserId());
		jo.put("userName", user.getUserName());
		//查询用户使用的资源
		List<Object> objects = null;
		objects = accountingDAO.getStatisticsByUser(osc.getUserId(),type);
		for(Object object:objects){
			Object[] objectArr = (Object[]) object;
			jo.put("type_0", objectArr[0]==null?0:objectArr[0]);//主机
			jo.put("type_1", objectArr[1]==null?0:objectArr[1]);//硬盘
			jo.put("type_2", objectArr[2]==null?0:objectArr[2]);//备份
			jo.put("total", objectArr[3]==null?0:objectArr[3]);//总计
		}
		
		return jo;
		
	}
}
