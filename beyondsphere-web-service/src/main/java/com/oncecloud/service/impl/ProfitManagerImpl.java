package com.oncecloud.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.CostDetailDAO;
import com.oncecloud.dao.ProfitMonthDAO;
import com.oncecloud.dao.StatisticsDAO;
import com.oncecloud.dao.UnitpriceDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.dao.VolumeDAO;
import com.oncecloud.entity.OCStatistics;
import com.oncecloud.entity.ProfitMonth;
import com.oncecloud.entity.User;
import com.oncecloud.model.StatisticsType;
import com.oncecloud.service.ProfitManager;
import com.oncecloud.util.Utilities;

@Component("ProfitManager")
public class ProfitManagerImpl implements ProfitManager {
	
	@Resource
	private VMDAO vmDAO;
	@Resource
	private StatisticsDAO statisticsDAO;
	@Resource
	private VolumeDAO volumeDAO;
	@Resource
	private UnitpriceDAO unitpriceDAO;
	@Resource
	private CostDetailDAO costDetailDAO;
	@Resource
	private ProfitMonthDAO profitMonthDAO;
	@Resource
	private UserDAO userDAO;


	private List<OCStatistics> getStatisticsListByUserId(int userid) {
		List<OCStatistics> list=new ArrayList<OCStatistics>();
		list=statisticsDAO.getAllListByUserId(userid);
		Date now=new Date();
		for (OCStatistics ocStatistics : list) {
			if(ocStatistics.getDeleteDate()==null){
				ocStatistics.setDeleteDate(now);
			}
		}
		return list;
	}

	@Override
	public JSONObject profitlist(Date startDate, Date endDate, int userid) {
		
		JSONObject obj=new JSONObject();
		
		List<ProfitMonth> list = profitMonthDAO.getAllListbyUserid(startDate, endDate, userid);
		User user=userDAO.getUser(userid);
		if(list.size()>0){
				ProfitMonth profitMonth=list.get(0);
				obj.put("userid", profitMonth.getUserId());
				obj.put("username", user.getUserName());
				obj.put("cpu", profitMonth.getProfitCpu());
				obj.put("mem", profitMonth.getProfitMem());
				obj.put("volume", profitMonth.getProfitVol());
				obj.put("total", profitMonth.getProfitCpu()+profitMonth.getProfitMem()+profitMonth.getProfitVol());
				obj.put("date", Utilities.formatTime2String( profitMonth.getCreateTime(),"yyyy/MM"));
			}
		return obj;
	}
		
		
		/*JSONObject json=new JSONObject();
		double sum_instance=0;
		double sum_volume=0;
		double sum_snapshot=0;
		
		JSONArray returnArry=new JSONArray();
		List<OCStatistics> list=this.getStatisticsListByUserId(userid);
		DecimalFormat df=new DecimalFormat("#.##");
		
		for (OCStatistics ocStatistics : list) {
			JSONObject obj=new JSONObject();
			
			double sumPrice=0;
			//虚拟机的状态，1代表正常，0代表销毁     -1表示不是主机
			int vmStatus=-1;
			int volumeStatus=-1;
			String timedifference="";

			Date start=ocStatistics.getCreateDate();
			Date end=ocStatistics.getDeleteDate();
			//时间差（x天x小时x分钟）
			timedifference=Utilities.timeToUsed(start,end);
			//时间差
			long time= 0;
			if(startDate==null){
				obj.put("startTime",Utilities.formatTime(start));
				if(endDate==null){
					time = Utilities.timeToUsedDay(start, end);
					timedifference=Utilities.timeToUsed(start,end);
					obj.put("endTime",Utilities.formatTime(end));
				}else{
					if(endDate.after(end)){
						time = Utilities.timeToUsedDay(start, end);
						timedifference=Utilities.timeToUsed(start,end);
						obj.put("endTime",Utilities.formatTime(end));
					}else{
						time = Utilities.timeToUsedDay(start, endDate);
						timedifference=Utilities.timeToUsed(start,endDate);
						obj.put("endTime",Utilities.formatTime(endDate));
					}
				}
			}else{
				if(startDate.before(start)){
					obj.put("startTime",Utilities.formatTime(start));
					if(endDate==null){
						time = Utilities.timeToUsedDay(start, end);
						timedifference=Utilities.timeToUsed(start,end);
						obj.put("endTime",Utilities.formatTime(end));
					}else{
						if(endDate.before(end)){
							time = Utilities.timeToUsedDay(start, endDate);
							timedifference=Utilities.timeToUsed(start,endDate);
							obj.put("endTime",Utilities.formatTime(endDate));
						}else {
							time = Utilities.timeToUsedDay(start, end);
							timedifference=Utilities.timeToUsed(start,end);
							obj.put("endTime",Utilities.formatTime(end));
						}
					}
				}else {
					obj.put("startTime",Utilities.formatTime(startDate));
					if(endDate==null){
						time = Utilities.timeToUsedDay(startDate, end)/60/24;
						timedifference=Utilities.timeToUsed(startDate,end);
						obj.put("endTime",Utilities.formatTime(end));
					}else{
						if(endDate.before(end)){
							time = Utilities.timeToUsedDay(startDate, endDate);
							timedifference=Utilities.timeToUsed(startDate,endDate);
							obj.put("endTime",Utilities.formatTime(endDate));
						}else {
							time = Utilities.timeToUsedDay(startDate, end);
							timedifference=Utilities.timeToUsed(startDate,end);
							obj.put("endTime",Utilities.formatTime(end));
						}
					}
				}
			}
			//计算每台主机的cpu 内存
			if(ocStatistics.getType()==StatisticsType.INSTANCE){
				OCVM vm=vmDAO.getVMByUuid(ocStatistics.getUuid());
				if(vm!=null){
					vmStatus=vm.getVmStatus().ordinal();
				}else{
					vmStatus=0;
				}
				sumPrice = ocStatistics.getUnitPrice() * time;
				
				sum_instance+=sumPrice;
			}else if(ocStatistics.getType()==StatisticsType.VOLUME){
				//硬盘
				sumPrice=ocStatistics.getUnitPrice()*time;
				Volume volume=volumeDAO.getVolumeByUuid(ocStatistics.getUuid());
				if(volume!=null){
					volumeStatus=volume.getVolumeStatus().ordinal();
				}
				
				sum_volume+=sumPrice;
			}else if(ocStatistics.getType()==StatisticsType.SNAPSHOT){
				//备份
				sumPrice=ocStatistics.getUnitPrice()*time;
				
				sum_snapshot+=sumPrice;
			}
			obj.put("vmStatus", vmStatus);
			obj.put("vmId", ocStatistics.getUuid());
			obj.put("vmType", ocStatistics.getType().ordinal());
			obj.put("vmPrice", df.format(sumPrice));
			obj.put("usedTime", timedifference);
			//硬盘状态
			obj.put("volumeStatus", volumeStatus);
			returnArry.put(obj);
		}

		json.put("vmlist", returnArry);
		json.put("sum_instance", df.format(sum_instance));
		json.put("sum_volume", df.format(sum_volume));
		json.put("sum_snapshot", df.format(sum_snapshot));
		json.put("sum_price", df.format(sum_instance+sum_volume+sum_snapshot));
		return json;*/
		/*List<CostDetail> detaillist=profitManager.getAllcostDetailList();
		JSONArray detailArray=new JSONArray();
		for (CostDetail costDetail : detaillist) {
			JSONObject obj=new JSONObject();
			double sumPrice=0;
			String timedifference="";

			Date start=costDetail.getCostCreatetime();
			Date end=costDetail.getCostDeletetime();
			if(end==null){
				end=new Date();
			}
			//时间差（x天x小时x分钟）
			timedifference=Utilities.timeToUsed(start,end);
			//时间差
			//long time=Utilities.timeToUsedMin(start, end)/60/24;
			if(costDetail.getCostPriceType().ordinal()==2){
				sumPrice=costDetail.getCostUnitPrice()*costDetail.getCostNumber();
			}else{
				sumPrice=costDetail.getCostUnitPrice()*Utilities.getDateSpace(start, end, costDetail.getCostPriceType().ordinal())*costDetail.getCostNumber();
			}
			
			obj.put("detailId", costDetail.getCostDetailId());
			obj.put("name", costDetail.getCostDetailName());
			DecimalFormat df=new DecimalFormat("#.##");
			obj.put("sumPrice", df.format(sumPrice));
			obj.put("startTime",Utilities.formatTime(start));
			obj.put("endTime", Utilities.formatTime(end));
			obj.put("usedTime", timedifference);
			
			if(startDate==null){
				if(endDate==null){
					detailArray.put(obj);
				}else{
					if(endDate.after(end)){
						detailArray.put(obj);
					}
				}
			}else{
				if(startDate.before(start)){
					if(endDate==null){
						detailArray.put(obj);
					}else{
						if(endDate.after(end)){
							detailArray.put(obj);
						}
					}
				}
			}
		}*/
		//returnobj.put("detaillist", detailArray);

	@Override
	public JSONObject profitPie(int userid) {
		JSONObject obj=new JSONObject();
		List<OCStatistics> list=this.getStatisticsListByUserId(userid);
		//近6个月的数据INSTANCE, VOLUME, SNAPSHOT
		double instance_price=0;	//主机
		double volume_price=0;		//硬盘
		double snapshot_price=0;	//备份
		
		for (OCStatistics ocStatistics : list) {
			double sum=Utilities.timeToUsedDay(ocStatistics.getCreateDate(),ocStatistics.getDeleteDate()) * ocStatistics.getUnitPrice();
			if(ocStatistics.getType()==StatisticsType.INSTANCE){
				instance_price+=sum;
			}else if(ocStatistics.getType()==StatisticsType.VOLUME){
				volume_price+=sum;
			}else if(ocStatistics.getType()==StatisticsType.SNAPSHOT){
				snapshot_price+=sum;
			}
		}
		
		DecimalFormat df=new DecimalFormat("#.##");
		obj.put("instance_y", df.format(instance_price));
		obj.put("volume_y", df.format(volume_price));
		obj.put("snapshot_y", df.format(snapshot_price));
		return obj;
	}
	
//	@Override
//	public JSONArray getUnitPriceList() {
//		JSONArray ja = new JSONArray();
//		List<OCUnitprice> princeList=new ArrayList<OCUnitprice>();
//		princeList = unitpriceDAO.getOcUnitpriceList();
//		for (OCUnitprice prince : princeList) {
//			JSONObject jo = new JSONObject();
//			jo.put("pricetype", prince.getPriceType());
//			jo.put("pricevalue", prince.getPriceValue());
//			ja.put(jo);
//		}
//		return ja;
//	}
//	@Override
//	public List<CostDetail> getAllcostDetailList() {
//		List<CostDetail> list=new ArrayList<CostDetail>();
//		list = costDetailDAO.getAllList();
//		return list;
//	}
}
