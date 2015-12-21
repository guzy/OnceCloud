package com.oncecloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.oncecloud.dao.CostDetailDAO;
import com.oncecloud.dao.CostMonthDAO;
import com.oncecloud.dao.CostMonthDetailDAO;
import com.oncecloud.dao.DatacenterDAO;
import com.oncecloud.dao.HostDAO;
import com.oncecloud.dao.PoolDAO;
import com.oncecloud.dao.ProfitMonthDAO;
import com.oncecloud.dao.ProfitMonthDetailDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.dao.VolumeDAO;
import com.oncecloud.entity.CostDetail;
import com.oncecloud.entity.CostMonth;
import com.oncecloud.entity.CostMonthDetail;
import com.oncecloud.entity.Datacenter;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCPool;
import com.oncecloud.entity.OCVM;
import com.oncecloud.entity.ProfitMonth;
import com.oncecloud.entity.ProfitMonthDetail;
import com.oncecloud.entity.User;
import com.oncecloud.entity.Volume;
import com.oncecloud.model.CostPriceType;
import com.oncecloud.service.CostStatisticsService;
import com.oncecloud.util.TimeUtils;

@Component("CostStatisticsService")
public class CostStatisticsServiceImpl implements CostStatisticsService {
	
	static final int YEARNUM=5;

	@Resource
	private CostDetailDAO costDetailDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private CostMonthDetailDAO costMonthDetailDAO;
	@Resource
	private CostMonthDAO costMonthDAO;
	@Resource
	private ProfitMonthDAO ProfitMonthDAO;
	@Resource
	private ProfitMonthDetailDAO profitMonthDetailDAO;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private VolumeDAO volumeDAO;
	@Resource
	private DatacenterDAO datacenterDAO;
	@Resource
	private PoolDAO poolDAO;
	@Resource
	private HostDAO hostDAO;

	@Override
	public boolean saveCostMonthDetail() {
		boolean result = true;
		//上个月第一天
		Date startOfMonth=TimeUtils.getFirstDayOfMonth(1);
		//上个月最后一天（下个月第一天）
		Date endOfMonth=TimeUtils.getFirstDayOfMonth(0);
		
		//如果上个月已经计算过就返回false
		List<CostMonth> costmonthlist=costMonthDAO.getAllList(startOfMonth, endOfMonth);
		if(costmonthlist.size()>0){
			return false;
		}else{
			List<CostDetail> detaillist=costDetailDAO.getAllList();
			for (CostDetail costDetail : detaillist) {
				//定义CostMonthDetail，保存每个详细分类计算出的月投入
				CostMonthDetail costMonthDetail=new CostMonthDetail();
				//每个月每个详细分类总投入
				double priceOfMonth=0;
				Date start=costDetail.getCostCreatetime();
				Date end=null;
				
				if(costDetail.getCostPriceType()==CostPriceType.TOTAL){
					end=TimeUtils.AddYearForDate(costDetail.getCostCreatetime(), YEARNUM);
					//如果按总价计费，按照折旧周期初始化detail结束时间
					priceOfMonth=costDetail.getCostUnitPrice()*costDetail.getCostNumber()/YEARNUM/12;
				}else if(costDetail.getCostPriceType()==CostPriceType.MONTH){
					end=costDetail.getCostDeletetime();
					//计算投入的月数
					int dateSpace=TimeUtils.getDateSpace(costDetail.getCostCreatetime(), costDetail.getCostDeletetime(),costDetail.getCostPriceType().ordinal());
					//如果按月计费，投入金额就等于单价*数量/月数
					priceOfMonth=costDetail.getCostUnitPrice()*costDetail.getCostNumber()/dateSpace;
				}else if(costDetail.getCostPriceType()==CostPriceType.YEAR){
					end=costDetail.getCostDeletetime();
					//计算投入的年数
					int dateSpace=TimeUtils.getDateSpace(costDetail.getCostCreatetime(), costDetail.getCostDeletetime(),costDetail.getCostPriceType().ordinal());
					//计算出每月投入的金额
					priceOfMonth=costDetail.getCostUnitPrice()*costDetail.getCostNumber()/dateSpace/12;
				}
				//根据时间计算上个月是否需要均摊,并把需要均摊的详细分类存储在数据库中
				if(startOfMonth.before(start)){
					if(endOfMonth.after(start)){
						costMonthDetail.setCostTypeId(costDetail.getCostTypeId());
						costMonthDetail.setCostDetailId(costDetail.getCostDetailId());
						//保存时间为上个月最后一天
						costMonthDetail.setCreateTime(TimeUtils.AddDayForDate(endOfMonth, -1));
						costMonthDetail.setDetailCost(Math.round(priceOfMonth*100)/100.0);
						boolean relt=costMonthDetailDAO.add(costMonthDetail);
						if(!relt){
							result=false;
						}
					}
				}else{
					if(startOfMonth.before(end)&&endOfMonth.before(end)){
						costMonthDetail.setCostTypeId(costDetail.getCostTypeId());
						costMonthDetail.setCostDetailId(costDetail.getCostDetailId());
						costMonthDetail.setCreateTime(TimeUtils.AddDayForDate(endOfMonth, -1));
						costMonthDetail.setDetailCost(Math.round(priceOfMonth*100)/100.0);
						boolean relt=costMonthDetailDAO.add(costMonthDetail);
						if(!relt){
							result=false;
						}
					}
				} 
			}
			
		}
		return result;
		
	}
	@Override
	public boolean saveCostMonth() {
		boolean result = false;
		//需要保存的对象
		CostMonth costmonth=new CostMonth();
		//上个月第一天
		Date startOfMonth=TimeUtils.getFirstDayOfMonth(1);
		//上个月最后一天（下个月第一天）
		Date endOfMonth=TimeUtils.getFirstDayOfMonth(0);
		//上月总所有分类投入总和
		double sumPrice=0;
		List<CostMonthDetail> list=costMonthDetailDAO.getAllList(startOfMonth, endOfMonth);
		for (CostMonthDetail costMonthDetail : list) {
			sumPrice+=costMonthDetail.getDetailCost();
		}
		//每月总成本
		double totalcost= Math.round(sumPrice*100)/100.0;
		//每月均摊总成本
		double costShare=Math.round(totalcost*getCpuPrecent()*1.5*100)/100.0;
		
		costmonth.setTotalCost(totalcost);
		costmonth.setCostShare(costShare);
		costmonth.setCostCpu(Math.round(costShare*0.3*100)/100.0);
		costmonth.setCostMem(Math.round(costShare*0.5*100)/100.0);
		costmonth.setCostVol(Math.round(costShare*0.2*100)/100.0);
		costmonth.setCreateTime(TimeUtils.AddDayForDate(endOfMonth, -1));
		result=costMonthDAO.add(costmonth);
		
		return result;
	}


	public boolean saveProfitMonthDetail(){
		boolean result = true;
		//上个月第一天
		Date startOfMonth=TimeUtils.getFirstDayOfMonth(1);
		//上个月最后一天（下个月第一天）
		Date endOfMonth=TimeUtils.getFirstDayOfMonth(0);
		//用户使用的内存总和
		double vmMemSum=0;
		//用户使用的CPU总和
		double vmCpuSum=0;
		//用户使用硬盘总和
		double volumeSum=0;
		
		List<OCVM> allVmlist=vmDAO.getAllVmList();
		for (OCVM ocvm : allVmlist) {
			vmMemSum+=ocvm.getVmMem();
			vmCpuSum+=ocvm.getVmCpu();
		}
		
		List<Volume> allvolist=volumeDAO.getAllList();
		for (Volume volume : allvolist) {
			volumeSum+=volume.getVolumeSize();
		}
		
		List<User> userlist=userDAO.getUserList();
		for (User user : userlist) {
			ProfitMonth profitmonth=new ProfitMonth();
			
			//每个用户当前使用的内存
			double vmMemNum=0;
			//每个用户当前使用的CPU
			double vmCpuNum=0;
			//每个用户当前使用硬盘
			double volumeNum=0;
			//计算每个用户当前使用的内存和cpu
			List<OCVM> vmlist=vmDAO.getVMsByUser(user.getUserId());
			for (OCVM ocvm : vmlist) {
				vmMemNum+=ocvm.getVmMem();
				vmCpuNum+=ocvm.getVmCpu();
			}
			//计算每个用户当前使用的硬盘
			List<Volume> volist=volumeDAO.getListbyUserid(user.getUserId());
			for (Volume volume : volist) {
				volumeNum+=volume.getVolumeSize();
			}
			
			double percent=(double)(Math.round((vmCpuNum/vmCpuSum+vmMemNum/vmMemSum+volumeNum/volumeSum)*100)/100.0);
			
			List<CostMonth> costmonthlist=costMonthDAO.getAllList(startOfMonth, endOfMonth);
			if(costmonthlist.size()>0){
				CostMonth col=costmonthlist.get(0);
				//每个用户当前使用的内存费用
				double vmMemPrice=0;
				//每个用户当前使用的CPU费用
				double vmCpuPrice=0;
				//每个用户当前使用硬盘费用
				double volumePrice=0;
				
				System.out.println("cpu: "+vmCpuNum/vmCpuSum+"  mem:"+vmMemNum/vmMemSum+"   volume:"+volumeNum/volumeSum);
				vmMemPrice=Math.round(vmMemNum/vmMemSum*col.getCostMem()*100)/100.0;
				vmCpuPrice=Math.round(vmCpuNum/vmCpuSum*col.getCostCpu()*100)/100.0;
				volumePrice=Math.round(volumeNum/volumeSum*col.getCostVol()*100)/100.0;
				profitmonth.setProfitTotal(vmMemPrice+vmCpuPrice+volumePrice);
				profitmonth.setProfitCpu(vmCpuPrice);
				profitmonth.setProfitMem(vmMemPrice);
				profitmonth.setProfitVol(volumePrice);
			}else{
				return false;
			}
			
			profitmonth.setUserId(user.getUserId());
			profitmonth.setProfitPercent(percent);
			profitmonth.setCreateTime(TimeUtils.AddDayForDate(endOfMonth, -1));
			
			//先删除上个月的数据，然后再添加
			boolean res=ProfitMonthDAO.add(profitmonth);
			if(!res){
				result=false;
			}
			
			
			//保存每个用户消费的详细信息
			List<CostMonthDetail> monthdetaillist=costMonthDetailDAO.getAllList(startOfMonth, endOfMonth);
			for (CostMonthDetail costMonthDetail : monthdetaillist) {
				//保存profitMonthDetail
				ProfitMonthDetail profitMonthDetail=new ProfitMonthDetail();
				profitMonthDetail.setCostDetailId(costMonthDetail.getCostDetailId());
				profitMonthDetail.setDetailProfit(Math.round(percent*costMonthDetail.getDetailCost()*1.5*100)/100.0);
				profitMonthDetail.setUserId(user.getUserId());
				profitMonthDetail.setCreateTime(TimeUtils.AddDayForDate(endOfMonth, -1));
				boolean resl=profitMonthDetailDAO.add(profitMonthDetail);
				if(!resl){
					result=false;
				}
			}
			
		}
		
		return result;
	}
	
	
	//获取用户使用cpu核数占资源中心cpu的比例
	private double getCpuPrecent(){
		//获取总cpu核数
		double sumcpu=0;
		List<Datacenter> dcList = datacenterDAO.getAllPageDCList();
		if (dcList != null) {
			for (Datacenter dc : dcList) {
				List<OCPool> poolList = poolDAO.getPoolListOfDC(dc.getDcUuid());
				if (poolList != null) {
					for (OCPool pool : poolList) {
						List<OCHost> hostList = hostDAO.getHostListOfPool(pool.getPoolUuid());
						if (hostList != null) {
							for (OCHost host : hostList) {
								sumcpu += host.getHostCpu();
							}
						}
					}
				}
			}
		}
		//当前使用的CPU总核数
		//获取用户使用cpu总和数
		double vmCpuNum=0;
		List<User> userlist=userDAO.getUserList();
		if(userlist!=null){
			for (User user : userlist) {
				//计算每个用户当前使用的内存和cpu
				List<OCVM> vmlist=vmDAO.getVMsByUser(user.getUserId());
				if(vmlist!=null){
					for (OCVM ocvm : vmlist) {
						vmCpuNum+=ocvm.getVmCpu();
					}
				}
			}
		}
		double percent=0;
		if(sumcpu!=0){
			percent=vmCpuNum/(sumcpu*3);
		}
		return percent;
	}
	
}
