package com.oncecloud.dao;

import java.util.List;

/**
 * @author hty
 * 
 */
public interface QueryListDAO<E> {
	
	/**
	 * 根据用户计算提问的数量
	 * @param userId, search
	 * @return
	 */
	public abstract int countByUserId(int userId, String search);

	/**
	 * 根据用户获取提问详细列表
	 * @param userId, page, limit, search
	 * @return
	 */
	public abstract List<E> getOnePageByUserId(int userId, int page, int limit,
			String search);

}
