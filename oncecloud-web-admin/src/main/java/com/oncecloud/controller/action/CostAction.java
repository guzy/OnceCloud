/**
 * @author zll
 * @time 下午17:13
 * @date 2015年5月11日
 */
package com.oncecloud.controller.action;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oncecloud.manager.CostDetailManager;
import com.oncecloud.manager.CostTypeDetailManager;
import com.oncecloud.manager.CostTypeManager;
import com.oncecloud.entity.CostDetail;
import com.oncecloud.entity.CostType;
import com.oncecloud.entity.CostTypeDetail;
import com.oncecloud.entity.User;
import com.oncecloud.model.CostPriceType;
import com.oncecloud.model.CostState;
import com.oncecloud.util.JsonUtils;
import com.oncecloud.util.TimeUtils;

@RequestMapping("CostAction")
@Controller
public class CostAction {

	@Resource
	private CostTypeManager costTypeManager;
	@Resource
	private CostTypeDetailManager costTypeDetailManager;
	@Resource
	private CostDetailManager costDetailManager;

	@RequestMapping(value = "/CreateType", method = { RequestMethod.POST })
	@ResponseBody
	public String create(HttpServletRequest request,
			@RequestParam String costTypeId, @RequestParam String costTypeName,
			@RequestParam String costTypeDes) {
		User user = (User) request.getSession().getAttribute("user");
		CostType costType=new CostType();
		costType.setCostTypeId(costTypeId);
		costType.setCostTypeName(costTypeName);
		costType.setCostTypeDes(costTypeDes);
		
		boolean result = false;
		result = costTypeManager.doAddCostType(user.getUserId(),costType);

		return String.valueOf(result);
	}
	
	@RequestMapping(value = "/CreateTypeDetail", method = { RequestMethod.POST })
	@ResponseBody
	public String createTypeDetail(HttpServletRequest request,@RequestParam String costTypeDetailId,
			@RequestParam String costTypeId, @RequestParam String costTypeDetailName,
			@RequestParam String costTypeDetailDes) {
		User user = (User) request.getSession().getAttribute("user");
		CostTypeDetail typeDetail=new CostTypeDetail();
		typeDetail.setCostTypeDetailId(costTypeDetailId);
		typeDetail.setCostTypeId(costTypeId);
		typeDetail.setCostTypeDetailName(costTypeDetailName);
		typeDetail.setCostTypeDetailDes(costTypeDetailDes);
		
		boolean result = false;
		result = costTypeDetailManager.doAddCostTypeDetail(user.getUserId(), typeDetail);

		return String.valueOf(result);
	}

	@RequestMapping(value = "/CreateDetail", method = { RequestMethod.POST })
	@ResponseBody
	public String createDetail(HttpServletRequest request,@RequestParam String costDetailId,
			@RequestParam String costTypeId,@RequestParam String costTypeDetailId,@RequestParam String costDetailName,
			@RequestParam String costDetailType,@RequestParam String costUnitPrice,@RequestParam String costNumber,
			@RequestParam String costPriceType,@RequestParam String costCreatetime,@RequestParam String costDeletetime){
		User user = (User) request.getSession().getAttribute("user");
		CostDetail costdetail=new CostDetail();
		costdetail.setCostDetailId(costDetailId);
		costdetail.setCostTypeDetailId(costTypeDetailId);
		costdetail.setCostTypeId(costTypeId);
		costdetail.setCostDetailName(costDetailName);
		costdetail.setCostDetailType(costDetailType);
		costdetail.setCostUnitPrice(Integer.valueOf(costUnitPrice));
		if("0".equals(costPriceType)){
			costdetail.setCostPriceType(CostPriceType.MONTH);
		}else if("1".equals(costPriceType)){
			costdetail.setCostPriceType(CostPriceType.YEAR);
		}else {
			costdetail.setCostPriceType(CostPriceType.TOTAL);
		}
		costdetail.setCostNumber(Integer.valueOf(costNumber));
		costdetail.setCostCreatetime(TimeUtils.stringToDate(costCreatetime));
		costdetail.setCostDeletetime(TimeUtils.stringToDate(costDeletetime));

		costdetail.setCostState(CostState.UNUSUAL);
		
		boolean result = false;
		result = costDetailManager.doAddCostDetail(user.getUserId(),costdetail);

		return String.valueOf(result);
	}

	@RequestMapping(value = "/DeleteType", method = { RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request, @RequestParam String costTypeIds) {
		User user = (User) request.getSession().getAttribute("user");
		
		boolean result = true;
		String[] strs= costTypeIds.split(",");
		for (String costTypeId : strs) {
			List<CostTypeDetail> tds=costTypeDetailManager.getListByTypeId(costTypeId);
			if(tds.size()>0){
				result=false;
			}else{
				boolean flag=costTypeManager.doDeleteCostType(user.getUserId(),costTypeId);
				if(!flag){
					 result=false;
				}
			}
		}
		return String.valueOf(result);
	}
	
	@RequestMapping(value = "/DeleteTypeDetail", method = { RequestMethod.POST })
	@ResponseBody
	public String deleteTypeDetail(HttpServletRequest request, @RequestParam String typeDetailIds) {
		User user = (User) request.getSession().getAttribute("user");
		
		boolean result = true;
		String[] strs= typeDetailIds.split(",");
		for (String typeDetailId : strs) {
			List<CostDetail> tds=costDetailManager.getListByTypeDetailId(typeDetailId);
			if(tds.size()>0){
				result=false;
			}else{
				boolean flag=costTypeDetailManager.doDeleteCostTypeDetail(user.getUserId(),typeDetailId);
				if(!flag){
					 result=false;
				}
			}
		}
		return String.valueOf(result);
	}
	
	@RequestMapping(value = "/DeleteDetail", method = { RequestMethod.POST })
	@ResponseBody
	public String deletDetail(HttpServletRequest request, @RequestParam String detailIds) {
		User user = (User) request.getSession().getAttribute("user");
		
		boolean result = true;
		String[] strs= detailIds.split(",");
		for (String typeDetailId : strs) {
			boolean flag=costDetailManager.doDeleteCostDetail(user.getUserId(),typeDetailId);
			if(!flag){
				 result=false;
			}
		}
		return String.valueOf(result);
	}

	@RequestMapping(value = "/UpdateType", method = { RequestMethod.POST })
	@ResponseBody
	public String update(HttpServletRequest request, @RequestParam String costTypeId,
			@RequestParam String costTypeName, @RequestParam String costTypeDes) {
		User user = (User) request.getSession().getAttribute("user");
		CostType costType=new CostType();
		costType.setCostTypeId(costTypeId);
		costType.setCostTypeName(costTypeName);
		costType.setCostTypeDes(costTypeDes);
		
		boolean result=costTypeManager.doUpdateCostType(user.getUserId(),costType);
		return String.valueOf(result);
	}
	
	@RequestMapping(value = "/UpdateTypeDetail", method = { RequestMethod.POST })
	@ResponseBody
	public String updateTypeDetail(HttpServletRequest request,@RequestParam String costTypeDetailId,
			@RequestParam String costTypeId, @RequestParam String costTypeDetailName,
			@RequestParam String costTypeDetailDes) {
		User user = (User) request.getSession().getAttribute("user");
		CostTypeDetail typeDetail=new CostTypeDetail();
		typeDetail.setCostTypeDetailId(costTypeDetailId);
		typeDetail.setCostTypeId(costTypeId);
		typeDetail.setCostTypeDetailName(costTypeDetailName);
		typeDetail.setCostTypeDetailDes(costTypeDetailDes);
		
		boolean result=costTypeDetailManager.doUpdateCostTypeDetail(user.getUserId(),typeDetail);
		return String.valueOf(result);
	}
	
	@RequestMapping(value = "/UpdateDetail", method = { RequestMethod.POST })
	@ResponseBody
	public String UpdateDetail(HttpServletRequest request,@RequestParam String costDetailId,
			@RequestParam String costDetailName, @RequestParam String costDetailType,
			@RequestParam int costUnitPrice, @RequestParam int costPriceType, @RequestParam int costNumber,
			@RequestParam String costCreatetime, @RequestParam String costDeletetime) {
		User user = (User) request.getSession().getAttribute("user");
		CostDetail detail=new CostDetail();
		detail.setCostDetailId(costDetailId);
		detail.setCostDetailName(costDetailName);
		detail.setCostDetailType(costDetailType);
		detail.setCostUnitPrice(costUnitPrice);
		detail.setCostNumber(costNumber==0?1:costNumber);
		detail.setCostPriceType(CostPriceType.values()[costPriceType]);
		detail.setCostCreatetime(TimeUtils.stringToDate(costCreatetime));
		if(!"".equals(costDeletetime)){
			detail.setCostDeletetime(TimeUtils.stringToDate(costDeletetime));
		}
		
		boolean result=costDetailManager.doUpdateCostDetail(user.getUserId(),detail);
		return String.valueOf(result);
	}

	@RequestMapping(value = "/TypeList", method = { RequestMethod.GET })
	@ResponseBody
	public String typeList(HttpServletRequest request) {
		List<CostType> list = costTypeManager.getAllList();
		return JsonUtils.list2json(list);
	}
	
	@RequestMapping(value = "/allPriceList", method = { RequestMethod.GET })
	@ResponseBody
	public String allPriceList(HttpServletRequest request) {
		List<Map<String, String>> returnList=new ArrayList<Map<String, String>>();
		
		List<CostType> list = costTypeManager.getAllList();
		
		if(list.size()>0){
			for (CostType costType : list) {
				Map<String, String> map=new HashMap<String,String>();
				
				String typeId= costType.getCostTypeId();
				double price=0;
				//当年投入金额
				double yearprice=0;
				DecimalFormat df=new DecimalFormat(".##");
				
				
				List<CostTypeDetail> typeDetailList =costTypeDetailManager.getListByTypeId(typeId);
				for (CostTypeDetail costTypeDetail : typeDetailList) {
					String costTypeDetailId=costTypeDetail.getCostTypeDetailId();
					List<CostDetail> detailList=costDetailManager.getListByTypeDetailId(costTypeDetailId);
					for (CostDetail costDetail : detailList) {
						if(costDetail.getCostPriceType()== CostPriceType.TOTAL){
							price+=costDetail.getCostUnitPrice()*costDetail.getCostNumber();
							//计算年度投入
							if(costDetail.getCostCreatetime().after(TimeUtils.getFirstDayOfYear(0))){
								yearprice+=costDetail.getCostUnitPrice()*costDetail.getCostNumber()/5;
							}
						}else{
							int dataSpace=TimeUtils.getDateSpace(costDetail.getCostCreatetime(),costDetail.getCostDeletetime() , costDetail.getCostPriceType().ordinal());
							price+=costDetail.getCostUnitPrice()*dataSpace*costDetail.getCostNumber();
							//计算年度投入
							if(costDetail.getCostDeletetime().after(TimeUtils.getFirstDayOfYear(0))){
								if(costDetail.getCostCreatetime().before(TimeUtils.getFirstDayOfYear(0))){
									int other_dataSpace=TimeUtils.getDateSpace(TimeUtils.getFirstDayOfYear(0),costDetail.getCostDeletetime() , costDetail.getCostPriceType().ordinal());
									yearprice+=costDetail.getCostUnitPrice()*other_dataSpace*costDetail.getCostNumber();
								}else{
									yearprice+=costDetail.getCostUnitPrice()*dataSpace*costDetail.getCostNumber();
								}
							}
								
						}
					}
				}
				
				map.put("typeId", typeId);
				map.put("yearprice", df.format(yearprice));
				map.put("price", df.format(price));
				map.put("typeName",costType.getCostTypeName());
				returnList.add(map);
			}
		}
		
		return JsonUtils.list2json(returnList);
	}

	@RequestMapping(value = "/TypeDetailList", method = { RequestMethod.GET })
	@ResponseBody
	public String typeDetailList(HttpServletRequest request,@RequestParam String costTypeId) {
		CostType costtype=costTypeManager.getCostType(costTypeId);
		List<CostTypeDetail> list = costTypeDetailManager.getListByTypeId(costTypeId);
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("costtype", costtype);
		map.put("typedetail", list);
		
		return JsonUtils.map2json(map);
	}
	
	/*@RequestMapping(value = "/DetailList", method = { RequestMethod.GET })
	@ResponseBody
	public String detailList(HttpServletRequest request,@RequestParam String costTypeId) {
		
		JSONArray array=new JSONArray();
		List<CostTypeDetail> list = costTypeDetailManager.getListByTypeId(costTypeId);
		if(list.size()>0){
			for (CostTypeDetail costTypeDetail : list) {
				Map<String,Object> map=new HashMap<String, Object>();
				List<CostDetail> detaillist = costDetailManager.getListByTypeDetailId(costTypeDetail.getCostTypeDetailId());
				
				List<Map<String,String>> mapList=new ArrayList<Map<String,String>>();
				for (CostDetail costDetail : detaillist) {
					Map<String,String> obj=new HashMap<String, String>();
					obj.put("costDetailId", costDetail.getCostDetailId());
					obj.put("costTypeDetailId", costDetail.getCostTypeDetailId());
					obj.put("costDetailName", costDetail.getCostDetailName());
					obj.put("costDetailType", costDetail.getCostDetailType());
					obj.put("costUnitPrice", String.valueOf(costDetail.getCostUnitPrice()));
					obj.put("costNumber", String.valueOf(costDetail.getCostNumber()));
					obj.put("costPriceType", String.valueOf(costDetail.getCostPriceType().ordinal()));
					obj.put("costState", String.valueOf(costDetail.getCostState().ordinal()));
					obj.put("costCreatetime", Utilities.formatTime(costDetail.getCostCreatetime()));
					obj.put("costDeletetime", Utilities.formatTime(costDetail.getCostDeletetime()));

					mapList.add(obj);
				}
				
				map.put("typedetail", costTypeDetail);
				map.put("detaillist", mapList);
				array.put(map);
			}
		}
		return array.toString();
	}
*/	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/DetailList", method = { RequestMethod.GET })
	@ResponseBody
	public String detailList(HttpServletRequest request,@RequestParam String costTypeId,@RequestParam int limit,
			@RequestParam int page,@RequestParam String search) {
		
		JSONObject jso=new JSONObject();
		
		JSONArray array=new JSONArray();
		
		Map detailMap = costDetailManager.getCostDetailList(page, limit, search,costTypeId);
		int totalNum= Integer.valueOf(detailMap.get("totalNum").toString());
		List<CostDetail> detallist=(List<CostDetail>) detailMap.get("detallist");
		List<CostTypeDetail> list = costTypeDetailManager.getListByTypeId(costTypeId);
		
		for (CostDetail costDetail : detallist) {

			Map<String,String> obj=new HashMap<String, String>();
			obj.put("costDetailId", costDetail.getCostDetailId());
			obj.put("costTypeDetailId", costDetail.getCostTypeDetailId());
			obj.put("costDetailName", costDetail.getCostDetailName());
			obj.put("costDetailType", costDetail.getCostDetailType());
			obj.put("costUnitPrice", String.valueOf(costDetail.getCostUnitPrice()));
			obj.put("costNumber", String.valueOf(costDetail.getCostNumber()));
			obj.put("costPriceType", String.valueOf(costDetail.getCostPriceType().ordinal()));
			obj.put("costState", String.valueOf(costDetail.getCostState().ordinal()));
			obj.put("costCreatetime", TimeUtils.formatTime(costDetail.getCostCreatetime()));
			if (costDetail.getCostDeletetime()!= null) {
				obj.put("costDeletetime", TimeUtils.formatTime(costDetail.getCostDeletetime()));
			}else{
				obj.put("costDeletetime","--");
			}
			for (CostTypeDetail costTypeDetail : list) {
				if(costTypeDetail.getCostTypeDetailId().equals(costDetail.getCostTypeDetailId())){
					obj.put("costTypeDetailName", costTypeDetail.getCostTypeDetailName());
					obj.put("costTypeDetailDes", costTypeDetail.getCostTypeDetailDes());
					break;
				}else{
					obj.put("costTypeDetailName", "");
					obj.put("costTypeDetailDes", "");

				}
			}
			array.put(obj);
		}
		
		jso.put("totalNum", totalNum);
		jso.put("detaillist", array);
		return jso.toString();
	}
}