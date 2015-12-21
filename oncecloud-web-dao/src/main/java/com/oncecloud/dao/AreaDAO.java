/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.dao;

import java.util.Date;
import java.util.List;



import com.oncecloud.entity.Area;

public interface AreaDAO {

	// 添加区域
	public abstract boolean insertArea(String areaId, String areaName,
			String areaDomain, String areaDesc, Date areaCreateTime);

	//删除区域
	public abstract boolean deleteArea(String areaId);
	
	// 更新区域
	public abstract boolean updateArea(String areaId, String areaName,
			String areaDomain, String areaDesc);

	// 获取一页区域列表
	public abstract List<Area> getOnePageAreaList(int page, int limit,
			String search);

	public abstract List<Area> getAreaAllList();
	// 获取全部区域列表
	public abstract List<Area> getCompanyAreaList(String search);

	// 获取配置区域总数
	public abstract int countAllAreaList(String search);

	//
	public abstract List<Area> getAreaList();
	
	//
	public abstract Area getArea(String areaId);
	
	public abstract Area queryArea(String areaDomain);

}
