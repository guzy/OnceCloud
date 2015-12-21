package com.oncecloud.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.CostDetailDAO;
import com.oncecloud.dao.CostTypeDAO;
import com.oncecloud.dao.StatisticsDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.dao.VolumeDAO;
import com.oncecloud.entity.CostDetail;
import com.oncecloud.entity.CostType;
import com.oncecloud.entity.OCStatistics;
import com.oncecloud.entity.OCVM;
import com.oncecloud.entity.Volume;
import com.oncecloud.model.CostPriceType;
import com.oncecloud.model.StatisticsType;
import com.oncecloud.service.StatisticsService;
import com.oncecloud.util.TimeUtils;

@Component("StatisticsService")
public class StatisticsServiceImpl implements StatisticsService {
	
	@Resource
	private StatisticsDAO statisticsDAO;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private VolumeDAO volumeDAO;
	@Resource
	private CostTypeDAO costtypeDAO;
	@Resource
	private CostDetailDAO costDetailDAO;
	
	public JSONArray getOnePageStatistics(Map<String, Object> params, int page,
			int limit) {
		JSONArray statisticsJson = new JSONArray();
		double totalprince = 0;
		int totalNum = statisticsDAO.getCount(params, "");
		List<OCStatistics>  statisticsList = statisticsDAO.getOnePageByUserId(params, limit, page);
		statisticsJson.put(totalNum);
		if (statisticsList != null) {
			for (OCStatistics statistics : statisticsList) {
				JSONObject jo = new JSONObject();
				jo.put("rsid", statistics.getUuid());
				jo.put("rsname", TimeUtils.encodeText(statistics.getName()));
				jo.put("createDate",TimeUtils.formatTime(statistics.getCreateDate()));
				if (statistics.getDeleteDate()!=null) {
					jo.put("status", 1);
					jo.put("timeused", TimeUtils.encodeText(TimeUtils.
							timeToUsed(statistics.getCreateDate(),statistics.getDeleteDate())));
					totalprince = TimeUtils.timeToUsedDay(statistics.getCreateDate(), 
							statistics.getDeleteDate()) * statistics.getUnitPrice();
				}else {
					jo.put("status", 0);
					jo.put("timeused", TimeUtils.encodeText(TimeUtils.timeToUsed(statistics.getCreateDate())));
					totalprince = TimeUtils.timeToUsedDay(statistics.getCreateDate(), 
							new Date()) * statistics.getUnitPrice();
				}
				jo.put("totalprince", TimeUtils.encodeText(String.format("%.2f", totalprince)));
				statisticsJson.put(jo);
			}
			
		}
		return statisticsJson;
	}

	public boolean addResource(String uuid, String name, int type, Date create,
			int userId) {
		OCStatistics ocStatistics = new OCStatistics();
		ocStatistics.setUuid(uuid);
		ocStatistics.setName(name);
		ocStatistics.setType(StatisticsType.values()[type]);
		ocStatistics.setCreateDate(create);
		ocStatistics.setUserId(userId);
		return statisticsDAO.save(ocStatistics);
	}

	public boolean updateResource(String uuid, int userId, Date delete) {
		OCStatistics ocStatistics = statisticsDAO.get(uuid);
		ocStatistics.setUuid(uuid);
		ocStatistics.setUserId(userId);
		ocStatistics.setDeleteDate(delete);
		return statisticsDAO.update(ocStatistics);
	}

	@Override
	public List<OCStatistics> getListForDate(Date startTime) {
		List<OCStatistics> list=statisticsDAO.getListForDate(startTime);
		return list;
	}

	@Override
	public List<OCStatistics> getListByUserId(int userid) {
		List<OCStatistics> list=statisticsDAO.getAllListByUserId(userid);
		Date now=new Date();
		for (OCStatistics ocStatistics : list) {
			if(ocStatistics.getDeleteDate()==null){
				ocStatistics.setDeleteDate(now);
			}
		}
		return list;
	}

	@Override
	public JSONObject getProfitListData(Date startDate, Date endDate, int userid) {
		JSONObject return_obj=new JSONObject();
		double sum_instance=0;
		double sum_volume=0;
		double sum_snapshot=0;
		
		
		DecimalFormat df=new DecimalFormat("#.##");
		JSONArray returnArry=new JSONArray();
		List<OCStatistics> list=this.getListByUserId(userid);
		//JSONArray priceList=unitpriceManager.getPriceList();
		for (OCStatistics ocStatistics : list) {
			
			JSONObject obj=new JSONObject();
			double sumPrice=0;
			//虚拟机的状态，1代表正常，0代表销毁     -1表示不是主机
			int vmStatus=0;
			
			int volumeStatus=-1;
			String timedifference="";

			Date start=ocStatistics.getCreateDate();
			Date end=ocStatistics.getDeleteDate();
			if(end==null){
				end=new Date();
			}
			//时间差（x天x小时x分钟）
			
			//时间差
			long time= 0;
			
			Date d_start=null;
			Date d_end=null;
			if(startDate==null){
				d_start=start;
				if(endDate==null){
					d_end=end;
				}else{
					if(endDate.after(end)){
						d_end=end;
					}else{
						d_end=endDate;
					}
				}
			}else{
				if(startDate.before(start)){
					d_start=start;
					if(endDate==null){
						d_end=end;
					}else{
						if(endDate.before(end)){
							d_end=endDate;
						}else {
							d_end=end;
						}
					}
				}else {
					d_start=startDate;
					if(endDate==null){
						d_end=end;
					}else{
						if(endDate.before(end)){
							d_end=endDate;
						}else {
							d_end=end;
						}
					}
				}
			}
			time = TimeUtils.timeToUsedDay(d_start, d_end);
			timedifference=TimeUtils.timeToUsed(d_start,d_end);
			obj.put("startTime",TimeUtils.formatTime(d_start));
			obj.put("endTime",TimeUtils.formatTime(d_end));
			//计算每台主机的cpu 内存
			if(ocStatistics.getType()==StatisticsType.INSTANCE){
				OCVM vm=vmDAO.getVM(ocStatistics.getUuid());
				if(vm!=null){
					vmStatus=vm.getVmStatus().ordinal();
				}
				sumPrice = ocStatistics.getUnitPrice() * time;
				
				sum_instance+=sumPrice;
			}else if(ocStatistics.getType()==StatisticsType.VOLUME){
				//硬盘
				sumPrice=ocStatistics.getUnitPrice()*time;
				Volume volume=volumeDAO.getOneByID(ocStatistics.getUuid());
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
		return_obj.put("vmlist", returnArry);
		return_obj.put("sum_instance", df.format(sum_instance));
		return_obj.put("sum_volume", df.format(sum_volume));
		return_obj.put("sum_snapshot", df.format(sum_snapshot));
		return_obj.put("sum_price", df.format(sum_instance+sum_volume+sum_snapshot));
		
		
		return return_obj;
		
		//分类成本计算
		/*List<CostDetail> detaillist=costDetailMAnager.getAllList();
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
			//long time=Utilities.timeToUsedMin(start, end)/60;
			sumPrice=costDetail.getCostUnitPrice()*Utilities.getDateSpace(start, end, costDetail.getCostPriceType().ordinal());
			
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
	}

	@Override
	public JSONObject getProfitChartData(Date startDate, Date endDate) {
		JSONObject obj=new JSONObject();
		List<OCStatistics> list=this.getListByUserId(0);
		//计算起始时间
		Date nextDate=TimeUtils.addMonthOfDate(startDate,1);
		//近6个月的数据INSTANCE, VOLUME, SNAPSHOT
		String instance_sumStr="";		//主机
		String volume_sumStr="";		//硬盘
		String snapshot_sumStr="";		//备份
		//当前时间
		DecimalFormat df=new DecimalFormat("#.##");
		while(startDate.before(endDate)){
			double instance_price=0;	//主机每月总价
			double volume_price=0;		//硬盘每月总价
			double snapshot_price=0;    //备份每月总价
			
			//遍历计算每个月每种类型及硬件总价
			for (OCStatistics ocStatistics : list) {
				if(ocStatistics.getType()==StatisticsType.INSTANCE){
					instance_price+=getOneincome(startDate,nextDate,ocStatistics);
				}else if(ocStatistics.getType()==StatisticsType.VOLUME){
					volume_price+=getOneincome(startDate,nextDate,ocStatistics);
				}else if(ocStatistics.getType()==StatisticsType.SNAPSHOT){
					snapshot_price+=getOneincome(startDate,nextDate,ocStatistics);
				}
				
			}
			//把每个月的价格拼接成字符串
			instance_sumStr+=df.format(instance_price)+",";
			volume_sumStr+=df.format(volume_price)+",";
			snapshot_sumStr+=df.format(snapshot_price)+",";
			
			//startDate、nextDate各加一个月，继续遍历
			startDate=nextDate;
			nextDate=TimeUtils.addMonthOfDate(nextDate,1);
		}
		
		instance_sumStr=instance_sumStr.substring(0, instance_sumStr.length()-1);
		volume_sumStr=volume_sumStr.substring(0, volume_sumStr.length()-1);
		snapshot_sumStr=snapshot_sumStr.substring(0, snapshot_sumStr.length()-1);
		
		obj.put("instance_y", instance_sumStr);
		obj.put("volume_y", volume_sumStr);
		obj.put("snapshot_y", snapshot_sumStr);
		return obj;
	}
	
	
	@Override
	public JSONObject getProfitPieData() {
		JSONObject obj=new JSONObject();
		//获取detailtype的列表
		List<CostType> typelist=costtypeDAO.getAllList();
		String typeNames="";
		String typeids="";
		double[] sumprices=new double[typelist.size()];
		
		//遍历typelist分别计算每个分类的总价
		for (int i=0;i<typelist.size();i++) {
			CostType costType=typelist.get(i);
			//分类名称
			typeNames+=costType.getCostTypeName()+",";
			typeids+=costType.getCostTypeId()+",";
			double price=0;
			List<CostDetail> detaillist=costDetailDAO.getListByTypeId(costType.getCostTypeId());
			for (CostDetail costDetail : detaillist) {
				if(costDetail.getCostPriceType()==CostPriceType.TOTAL){
//					price+=costDetail.getCostUnitPrice()*costDetail.getCostNumber();
					//计算年度投入
					if(costDetail.getCostCreatetime().after(TimeUtils.getFirstDayOfYear(0))){
						price+=costDetail.getCostUnitPrice()*costDetail.getCostNumber()/5;
					}
				}else{
//					price+=Utilities.getDateSpace(costDetail.getCostCreatetime(), costDetail.getCostDeletetime(), costDetail.getCostPriceType().ordinal())
//							*costDetail.getCostUnitPrice()*costDetail.getCostNumber();
					//计算年度投入
					if(costDetail.getCostDeletetime().after(TimeUtils.getFirstDayOfYear(0))){
						if(costDetail.getCostCreatetime().before(TimeUtils.getFirstDayOfYear(0))){
							int other_dataSpace=TimeUtils.getDateSpace(TimeUtils.getFirstDayOfYear(0),costDetail.getCostDeletetime() , costDetail.getCostPriceType().ordinal());
							price+=costDetail.getCostUnitPrice()*other_dataSpace*costDetail.getCostNumber();
						}else{
							price+=costDetail.getCostUnitPrice()*TimeUtils.getDateSpace(costDetail.getCostCreatetime(), costDetail.getCostDeletetime(), costDetail.getCostPriceType().ordinal())
									*costDetail.getCostNumber();
						}
					}
				}
			}
			//总价
			sumprices[i]=price;
		}
		//计算每种分类价钱的百分比
		DecimalFormat df=new DecimalFormat("#.##");
		String priceStr="";
		for (int i = 0; i < sumprices.length; i++) {
			priceStr+=df.format(sumprices[i])+",";
		}
		priceStr=priceStr.substring(0, priceStr.length()-1);
		typeNames=typeNames.substring(0, typeNames.length()-1);
		typeids=typeids.substring(0, typeids.length()-1);
		
		obj.put("typePrices", priceStr);
		obj.put("typeNames", typeNames);
		obj.put("typeids", typeids);
		return obj;
	}
	
	
	/**
	 * 计算每月的金额
	 * @param now
	 * @param start
	 * @param end
	 * @param os
	 * @return
	 */
	private double getOneincome(Date start,Date end,OCStatistics os){
		double sumPrice=0;
		Date delete=os.getDeleteDate();
		Date create=os.getCreateDate();
		double unitPrice=os.getUnitPrice();
		
		if(start.before(create)&&end.after(create)){
			if(delete.before(end)){
				sumPrice+=TimeUtils.timeToUsedDay(create,delete) * unitPrice;
			}else{
				sumPrice+=TimeUtils.timeToUsedDay(create,end) * unitPrice;
			}
		}else if(create.before(start)){
			if(delete.after(end)){
				sumPrice+=TimeUtils.timeToUsedDay(start,end) * unitPrice;
			}else if(delete.after(start)&&delete.before(end)){
				sumPrice+=TimeUtils.timeToUsedDay(start,delete) * unitPrice;
			}else{
				sumPrice+=0;
			}
		}
		return sumPrice;
	}

	@Override
	public JSONObject income(Date beginTime) {
		JSONObject obj=new JSONObject();
		
		//近3个月的数据
		String sum_priceStr="";
		//计算起始时间
		List<OCStatistics> list=this.getListForDate(beginTime);
		Date nextTime=TimeUtils.addMonthOfDate(beginTime,1);
		//当前时间
		Date now=new Date();
		DecimalFormat df=new DecimalFormat("#.##");
		while(beginTime.before(now)){
			double nowPrice=0;
			for (OCStatistics ocStatistics : list) {
				nowPrice+=getOneincome(now,beginTime,nextTime,ocStatistics);
			}
			
			sum_priceStr+=df.format(nowPrice)+",";
			
			beginTime=nextTime;
			nextTime=TimeUtils.addMonthOfDate(nextTime,1);
		}
		sum_priceStr=sum_priceStr.substring(0, sum_priceStr.length()-1);
		obj.put("data_y", sum_priceStr);
		return obj;
	}

	/**
	 * 计算每月的金额
	 * @param now
	 * @param start
	 * @param end
	 * @param os
	 * @return
	 */
	private double getOneincome(Date now,Date start,Date end,OCStatistics os){
		double sumPrice=0;
		Date delete=os.getDeleteDate();
		Date create=os.getCreateDate();
		double unitPrice=os.getUnitPrice();
		
		if(start.before(create)&&end.after(create)){
			if(delete!=null){
				if(delete.before(end)){
					sumPrice+=TimeUtils.timeToUsedDay(create,delete) * unitPrice;
				}else{
					sumPrice+=TimeUtils.timeToUsedDay(create,end) * unitPrice;
				}
			}else{
				if(now.after(start)&&now.before(end)){
					sumPrice+=TimeUtils.timeToUsedDay(create,now) * unitPrice;
				}else{
					sumPrice+=TimeUtils.timeToUsedDay(create,end) * unitPrice;
				}
			}
		}else if(create.before(start)){
			if(delete!=null){
				if(delete.after(end)){
					sumPrice+=TimeUtils.timeToUsedDay(start,end) * unitPrice;
				}else if(delete.after(start)&&delete.before(end)){
					sumPrice+=TimeUtils.timeToUsedDay(start,delete) * unitPrice;
				}else{
					sumPrice+=0;
				}
			}else{
				sumPrice+=TimeUtils.timeToUsedDay(start,end) * unitPrice;
			}
		}
		return sumPrice;
	}
	
	@Override
	public boolean addResource(String uuid, String name, int type, Date create, double totalPrince,
			int userId) {
		OCStatistics ocStatistics = new OCStatistics();
		ocStatistics.setUuid(uuid);
		ocStatistics.setName(name);
		ocStatistics.setType(StatisticsType.values()[type]);
		ocStatistics.setCreateDate(create);
		ocStatistics.setUnitPrice(totalPrince);
		ocStatistics.setUserId(userId);
		return statisticsDAO.save(ocStatistics);
	}
}
