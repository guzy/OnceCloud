package com.beyondsphere.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.beyondsphere.model.StatisticsType;
import com.beyondsphere.entity.OCStatistics;

/**
 * @author hty
 *
 */
public interface StatisticsDAO {

	/**
	 * 添加一条统计资源的信息
	 * @param ocStatistics
	 * @return
	 */
	public abstract boolean save(OCStatistics ocStatistics);
	
	/**
	 * 更新一条统计资源的信息
	 * @param ocStatistics
	 * @return
	 */
	public abstract boolean update(OCStatistics ocStatistics);
	
	/**
	 * 删除一条统计资源的信息
	 * @param ocStatistics
	 * @return
	 */
	public abstract boolean delete(OCStatistics ocStatistics);
	
	/**
	 * 根据用户id获取用户总数
	 * @param userId
	 * @param date
	 * @return
	 */
	public abstract int getCount(Map<String, Object> params, String search);
	
	/**
	 * 根据用户id和起始时间和类型，获取统计资源的列表
	 * @param userId
	 * @param date
	 * @return
	 */
	public abstract List<OCStatistics> getListByUserId(int userId, Date date, StatisticsType type);	
	
	/**
	 * 起止年月，用户id，类型，获取统计资源的列表
	 * @param params
	 * @param limit
	 * @param page
	 * @return
	 */
	public abstract List<OCStatistics> getOnePageByUserId(Map<String, Object> params, int limit, int page);
	
	/**
	 * 根据统计资源id，获取一个数据对象
	 * @param uuid
	 * @return
	 */
	public abstract OCStatistics get(String uuid);
	
	/**
	 * 获取某个时间之后的记录
	 * @param startTime
	 * @return
	 */
	public abstract List<OCStatistics> getListForDate(Date startTime);
	
	/**
	 * 获取某个时间之后的记录
	 * @param userid<int>
	 * @return List<OCStatistics>
	 */
	public abstract List<OCStatistics> getAllListByUserId(int userid);
}
