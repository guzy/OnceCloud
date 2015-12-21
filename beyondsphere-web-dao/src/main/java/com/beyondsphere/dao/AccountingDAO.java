package com.beyondsphere.dao;

import java.util.List;

import com.beyondsphere.entity.OCStatistics;

public interface AccountingDAO {
	
	/**
	 * 获取计费总数
	 * @author mayh
	 * @param search
	 */
	public int countAllAccountingList(String search);

	/**
	 * 分页获取计费信息
	 * @author mayh
	 * @param page limit search
	 */
	public List<OCStatistics> getOnePageListOfAccounting(int page, int limit,String search);

	/**
	 * 根据用户获取统计信息
	 * @author mayh
	 * @param userId type
	 */
	public List<Object> getStatisticsByUser(int userId, int type);
	
}
