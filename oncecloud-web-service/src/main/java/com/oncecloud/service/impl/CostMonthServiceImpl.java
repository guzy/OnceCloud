package com.oncecloud.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.CostMonthDAO;
import com.oncecloud.dao.CostMonthDetailDAO;
import com.oncecloud.dao.CostTypeDAO;
import com.oncecloud.dao.ProfitMonthDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.entity.CostMonth;
import com.oncecloud.entity.CostMonthDetail;
import com.oncecloud.entity.CostType;
import com.oncecloud.entity.ProfitMonth;
import com.oncecloud.entity.User;
import com.oncecloud.service.CostMonthService;
import com.oncecloud.util.TimeUtils;

@Component("CostMonthService")
public class CostMonthServiceImpl implements CostMonthService {
	
	@Resource
	private CostMonthDAO costMonthDAO;
	@Resource
	private CostMonthDetailDAO costMonthDetailDAO;
	@Resource
	private ProfitMonthDAO profittMonthDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private CostTypeDAO costTypeDAO;

	public JSONObject getPriceList() {
		JSONObject jo = new JSONObject();
		//上个月第一天
		Date startDate=TimeUtils.getFirstDayOfMonth(1);
		//上个月最后一天（下个月第一天）
		Date endDate=TimeUtils.getFirstDayOfMonth(0);
		List<CostMonth> list = costMonthDAO.getAllList(startDate, endDate);
		if(list!=null&&list.size()>0){
			CostMonth costMonth=list.get(0);
			jo.put("cpu", costMonth.getCostCpu());
			jo.put("mem", costMonth.getCostMem());
			jo.put("volume", costMonth.getCostVol());
			jo.put("start", TimeUtils.formatTime2String(startDate ,"yyyy/MM"));
			jo.put("end", TimeUtils.formatTime2String(endDate ,"yyyy/MM"));
		}
		return jo;
	}

	@Override
	public JSONArray profitMonthList() {
		JSONArray arr=new JSONArray();
		//上个月第一天
		Date startDate=TimeUtils.getFirstDayOfMonth(1);
		//上个月最后一天（下个月第一天）
		Date endDate=TimeUtils.getFirstDayOfMonth(0);
		List<ProfitMonth> list = profittMonthDAO.getAllList(startDate, endDate);
		if(list.size()>0){
			for (ProfitMonth profitMonth : list) {
				JSONObject obj=new JSONObject();
				User user=userDAO.getUser(profitMonth.getUserId());
				obj.put("userid", profitMonth.getUserId());
				obj.put("username", user.getUserName());
				obj.put("cpu", profitMonth.getProfitCpu());
				obj.put("mem", profitMonth.getProfitMem());
				obj.put("volume", profitMonth.getProfitVol());
				obj.put("total", profitMonth.getProfitCpu()+profitMonth.getProfitMem()+profitMonth.getProfitVol());
				obj.put("date", TimeUtils.formatTime2String( profitMonth.getCreateTime(),"yyyy/MM"));
				arr.put(obj);
			}
		}
		return arr;
	}

	@Override
	public JSONObject getProfitMonth() {
		JSONObject obj=new JSONObject();
		Date endDate=TimeUtils.getFirstDayOfMonth(0);
		Date startDate=TimeUtils.getFirstDayOfYear(0);
		
		//时间（统计图横坐标）
		String dateList="";
		//格式化横坐标的时间格式
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM"); 
		
		String cpu_sumStr="";		//主机
		String mem_sumStr="";		//内存
		String volume_sumStr="";		//硬盘
		
		Date flagDate=startDate;
		while(endDate.after(flagDate)){
			flagDate=TimeUtils.AddMonthForDate(flagDate, 1);
			List<CostMonth> list = costMonthDAO.getAllList(startDate, flagDate);
			dateList+=format.format(startDate)+",";
			startDate=flagDate;
			
			if(list.size()>0){
				CostMonth pro=list.get(0);
				cpu_sumStr+=pro.getCostCpu()+",";
				mem_sumStr+=pro.getCostMem()+",";
				volume_sumStr+=pro.getCostVol()+",";
			}else{
				cpu_sumStr+=0+",";
				mem_sumStr+=0+",";
				volume_sumStr+=0+",";
			}
		}
		
		cpu_sumStr=cpu_sumStr.substring(0, cpu_sumStr.length()-1);
		mem_sumStr=mem_sumStr.substring(0, mem_sumStr.length()-1);
		volume_sumStr=volume_sumStr.substring(0, volume_sumStr.length()-1);
		dateList=dateList.substring(0, dateList.length()-1);
		
		obj.put("cpu_y", cpu_sumStr);
		obj.put("volume_y", volume_sumStr);
		obj.put("mem_y", mem_sumStr);
		obj.put("data_x", dateList);
		
		return obj;
	}
	
	@Override
	public JSONObject getCostMonthList() {
		JSONObject obj=new JSONObject();
		JSONArray array=new JSONArray();
		
		//时间（统计图横坐标）
		String dateList="";
		//格式化横坐标的时间格式
		boolean dateflag=true; //用于记录一次横坐标
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM"); 
		
		
		List<CostType> typelist=costTypeDAO.getAllList();
		if(typelist.size()>0){
			for (CostType costType : typelist) {
				Date endDate=TimeUtils.getFirstDayOfMonth(0);
				Date startDate=TimeUtils.getFirstDayOfYear(0);
				
				JSONObject json=new JSONObject();
				json.put("name", costType.getCostTypeName());
				String typeprices="";		//分类金额
				
				Date flagDate=startDate;
				while(endDate.after(flagDate)){
					flagDate=TimeUtils.AddMonthForDate(flagDate, 1);
					List<CostMonthDetail> list = costMonthDetailDAO.getListByTypeId(startDate, flagDate,costType.getCostTypeId());
					if(list.size()>0){
						double price=0;
						for (CostMonthDetail costMonthDetail : list) {
							price+=costMonthDetail.getDetailCost();
						}
						typeprices+=Math.round(price*100)/100.0+",";
					}else{
						typeprices+=0+",";
					}
					
					
					if(dateflag){
						dateList+=format.format(startDate)+",";
					}
					startDate=flagDate;
				}
				
				typeprices=typeprices.substring(0, typeprices.length()-1);
				json.put("data", typeprices);
				array.put(json);
				
				dateflag=false;
			}
		}
		
		dateList=dateList.substring(0, dateList.length()-1);
		
		obj.put("data_x", dateList);
		obj.put("datalist", array);
		
		return obj;
	}


}
