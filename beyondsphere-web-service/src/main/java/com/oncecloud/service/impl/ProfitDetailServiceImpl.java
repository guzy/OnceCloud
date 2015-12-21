package com.oncecloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.CostDetailDAO;
import com.oncecloud.dao.CostMonthDAO;
import com.oncecloud.dao.CostMonthDetailDAO;
import com.oncecloud.dao.CostTypeDAO;
import com.oncecloud.dao.ProfitMonthDAO;
import com.oncecloud.dao.ProfitMonthDetailDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.entity.CostDetail;
import com.oncecloud.entity.CostMonth;
import com.oncecloud.entity.CostMonthDetail;
import com.oncecloud.entity.CostType;
import com.oncecloud.entity.ProfitMonth;
import com.oncecloud.entity.ProfitMonthDetail;
import com.oncecloud.entity.User;
import com.oncecloud.service.ProfitDetailService;
import com.oncecloud.util.TimeUtils;

@Component("ProfitDetailService")
public class ProfitDetailServiceImpl implements ProfitDetailService {

	@Resource
	private CostMonthDetailDAO costMonthDetailDAO;
	@Resource
	private CostMonthDAO costMonthDAO;
	@Resource
	private ProfitMonthDetailDAO profitMonthDetailDAO;
	@Resource
	private ProfitMonthDAO profitMonthDAO;
	@Resource
	private CostDetailDAO costDetailDAO;
	@Resource
	private CostTypeDAO costTypeDAO;
	@Resource
	private UserDAO userDAO;

	@Override
	public JSONObject getCostMonthDetail(Date start,Date end) {
		JSONObject return_obj=new JSONObject();
		
		JSONArray jsonarray=new JSONArray();
		
		//查询每项投入
		List<CostMonthDetail> monthdetailList=costMonthDetailDAO.getAllList(start, end);
		for (CostMonthDetail costMonthDetail : monthdetailList) {
			JSONObject obj=new JSONObject();
			String detailid=costMonthDetail.getCostDetailId();
			CostDetail costdetail=costDetailDAO.getOneByID(detailid);
			obj.put("detailid", detailid);
			obj.put("detailname", costdetail.getCostDetailName());
			CostType costtype=costTypeDAO.getOneByID(costdetail.getCostTypeId());
			obj.put("typename", costtype.getCostTypeName());
			obj.put("detailcost", costMonthDetail.getDetailCost());
			jsonarray.put(obj);
		}
		//查询总投入
		double sumprice=0;
		List<CostMonth> costmonthlist=costMonthDAO.getAllList(start, end);
		for (CostMonth costMonth : costmonthlist) {
			sumprice+=costMonth.getTotalCost();
		}
		return_obj.put("sumprice", sumprice);
		
		String starttime=TimeUtils.formatTime2String(start,"yyyy/MM");
		String endtime=TimeUtils.formatTime2String(TimeUtils.AddMonthForDate(end, -1),"yyyy/MM");
		if(starttime.equals(endtime)){
			return_obj.put("starttime", "");
		}else{
			return_obj.put("starttime", starttime);
		}
		return_obj.put("endtime", endtime);
		return_obj.put("detailcostlist", jsonarray);
		return return_obj;
	}

	@Override
	public JSONObject getProfitDetailList(Date start, Date end,int userid) {
		JSONObject return_obj=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		//查询所有用户每项的费用
		List<ProfitMonthDetail> monthdetailList=profitMonthDetailDAO.getAllListByUserid(start, end,userid);
		for (ProfitMonthDetail profitMonthDetail : monthdetailList) {
			JSONObject obj=new JSONObject();
			
			String detailid=profitMonthDetail.getCostDetailId();
			CostDetail costdetail=costDetailDAO.getOneByID(detailid);
			obj.put("detailid", detailid);
			obj.put("detailname", costdetail.getCostDetailName());
			CostType costtype=costTypeDAO.getOneByID(costdetail.getCostTypeId());
			obj.put("typename", costtype.getCostTypeName());
			obj.put("detailprofit", profitMonthDetail.getDetailProfit());
			
			jsonarray.put(obj);
		}
		
		String starttime=TimeUtils.formatTime2String(start,"yyyy/MM");
		String endtime=TimeUtils.formatTime2String(TimeUtils.AddMonthForDate(end, -1),"yyyy/MM");
		if(starttime.equals(endtime)){
			return_obj.put("starttime", "");
		}else{
			return_obj.put("starttime", starttime);
		}
		return_obj.put("endtime", endtime);
		return_obj.put("profitdetaillist", jsonarray);
		
		return return_obj;
	}

	@Override
	public JSONObject getProfitMonthList(Date start, Date end) {
		JSONObject return_obj=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		
		List<User> userlist=userDAO.getUserList();
		for (User user : userlist) {
			double monthProfit=0;
			//查询所有用户每项的费用
			List<ProfitMonth> monthList=profitMonthDAO.getAllListbyUserid(start, end,user.getUserId());
			for (ProfitMonth profitMonth : monthList) {
				monthProfit+=profitMonth.getProfitTotal();
			}
			JSONObject obj=new JSONObject();
			obj.put("userid", user.getUserId());
			obj.put("username", user.getUserName());
			obj.put("userprice", monthProfit);
			jsonarray.put(obj);
		}
		String starttime=TimeUtils.formatTime2String(start,"yyyy/MM");
		String endtime=TimeUtils.formatTime2String(TimeUtils.AddMonthForDate(end, -1),"yyyy/MM");
		if(starttime.equals(endtime)){
			return_obj.put("starttime", "");
		}else{
			return_obj.put("starttime", starttime);
		}
		return_obj.put("endtime", endtime);
		
		return_obj.put("monthprofitlist", jsonarray);
		return return_obj;
	}

}
