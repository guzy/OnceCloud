package com.beyondsphere.controller.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beyondsphere.manager.AccountManager;
import com.beyondsphere.manager.DatacenterManager;
import com.beyondsphere.manager.StatisticsManager;
import com.beyondsphere.manager.UserManager;
import com.beyondsphere.manager.VMManager;
import com.oncecloud.util.JsonUtils;
import com.oncecloud.util.TimeUtils;

/**
 * @author zll
 * 
 */
@RequestMapping(value="overviewAction")
@Controller
public class OverViewAction {
	
	@Resource
	private UserManager userManager;
	@Resource
	private AccountManager accountManager;
	@Resource
	private VMManager vmManager;
	@Resource
	private StatisticsManager statisticsManager;
	@Resource
	private DatacenterManager datacenterManager;
	final static int SHOWVMNUM=5;
	final static int MONTHNUM=3;
	/**
	 * 总数统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dataSum", method = { RequestMethod.GET })
	@ResponseBody
	public String dataSum(HttpServletRequest request) {
		Integer sum_enterprise=userManager.getEnterpriseCount(new Date());
		Map<String, Object> infrastrutureMap = accountManager.getInfrastructureInfos();
		infrastrutureMap.put("sum_enterprise", sum_enterprise);  //企业数
		return JsonUtils.map2json(infrastrutureMap);
	}
	
	/**
	 * 注册企业列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/enterpriseList", method = { RequestMethod.GET })
	@ResponseBody
	public String enterpriseList(HttpServletRequest request) {
		JSONArray ja = userManager.getEnterpriseList(SHOWVMNUM);
		return ja.toString();
	}
	
	/**
	 * 近3个月注册企业总数统计折线图
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/enterprise3month", method = { RequestMethod.GET })
	@ResponseBody
	public String enterprise3month(HttpServletRequest request) {
		JSONObject obj=new JSONObject();
		
		//近3个月的数据
		String sum_enterpriseStr="";
		//时间（统计图横坐标）
		String dateList="";
		
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM"); 
		for(int i=(MONTHNUM-1);i>=0;i--){
			Date date=TimeUtils.getFirstDayOfMonth(i);
			dateList+=format.format(date)+",";
			
			Date nextTime=TimeUtils.addMonthOfDate(date,1);
			sum_enterpriseStr+=userManager.getEnterpriseCount(nextTime)+",";
		}
		sum_enterpriseStr=sum_enterpriseStr.substring(0, sum_enterpriseStr.length()-1);
		dateList=dateList.substring(0, dateList.length()-1);

		obj.put("data_y",sum_enterpriseStr);
		obj.put("data_x",dateList);
		return obj.toString();
	}

	/**
	 * 虚拟机排名前五列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/VMtop5", method = { RequestMethod.GET })
	@ResponseBody
	public String vmtop5(HttpServletRequest request) {
		
		List<Map> list = vmManager.getAdminVMTop(SHOWVMNUM);
		JSONArray array=new JSONArray();
		if(list.size()>0){
			for (Map map : list) {
				JSONObject jo=new JSONObject();
				String userid=map.get("0").toString();
				JSONObject user=userManager.doGetOneUser(Integer.valueOf(userid));
				String username=(String) user.get("usercom");
				int count=Integer.valueOf(map.get("1").toString());
				
				jo.put("userid", userid);
				jo.put("username", username);
				jo.put("count", count);
				
				array.put(jo);
			}
		}
		
		return array.toString();
	}
	
	/**
	 * 近3个月虚拟机总数统计折线图
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/VM3month", method = { RequestMethod.GET })
	@ResponseBody
	public String VM3month(HttpServletRequest request) {
		JSONObject obj=new JSONObject();
		
		//近3个月的数据
		String sum_VMStr="";
		//时间（统计图横坐标）
		String dateList="";
		
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM"); 
		for(int i=(MONTHNUM-1);i>=0;i--){
			Date date=TimeUtils.getFirstDayOfMonth(i);
			dateList+=format.format(date)+",";
			
			Date nextTime=TimeUtils.addMonthOfDate(date,1);
			sum_VMStr+=vmManager.getSumVMCount(nextTime)+",";
		}
		sum_VMStr=sum_VMStr.substring(0, sum_VMStr.length()-1);
		dateList=dateList.substring(0, dateList.length()-1);

		obj.put("data_y", sum_VMStr);
		obj.put("data_x", dateList);
		return obj.toString();
	}

	/**
	 * 虚拟机风险评估统计饼图
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/riskpie", method = { RequestMethod.GET })
	@ResponseBody
	public String riskAssessment(HttpServletRequest request) {
		JSONObject obj=new JSONObject();
		DecimalFormat df=new DecimalFormat(".#");
		double	sum_VM=vmManager.getSumVMCount(new Date());
		double  vlanVMCount=vmManager.getVlanVMCount();
		
		obj.put("N_vlanVM", df.format(100.0-vlanVMCount/sum_VM*100));
		obj.put("Y_vlanVM", df.format(vlanVMCount/sum_VM*100));
		
		return obj.toString();
	}
	
	/**
	 * 近3个月收益变化统计折线图
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/income", method = { RequestMethod.GET })
	@ResponseBody
	public String income(HttpServletRequest request) {
		JSONObject obj=new JSONObject();
		//时间（统计图横坐标）
		String dateList="";
		//格式化横坐标的时间格式
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM"); 
		for(int i=(MONTHNUM-1);i>=0;i--){
			Date date=TimeUtils.getFirstDayOfMonth(i);
			dateList+=format.format(date)+",";
		}
		dateList=dateList.substring(0, dateList.length()-1);
		
		//计算起始时间
		Date beginTime=TimeUtils.getFirstDayOfMonth(MONTHNUM-1);
		obj=statisticsManager.income(beginTime);
		obj.put("data_x", dateList);
		return obj.toString();
	}
	
	/**
	 * 获取已消耗资源信息
	 * @return
	 */
	@RequestMapping(value="/SourceUsedList",method={ RequestMethod.GET })
	@ResponseBody
	public String SourceUsedList(){
		JSONArray ja = null;
		try{
			ja = datacenterManager.getResourceOfDatacenter();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ja.toString();
	}
}
