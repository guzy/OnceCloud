package com.beyondsphere.dao;

import java.util.List;

import com.beyondsphere.entity.Repository;

/**
 * @author luogan 
 * 2015年5月19日 
 * 下午2:14:35
 */
public interface RepositoryDAO extends QueryListDAO<Repository> {

	// 查询个数
	public abstract int countByUserId(int userId, String search);

	// 添加镜像
	public abstract boolean insertImage(String repoId, String repoName,
			String repoVersion, String repoAddress);

	// 查询镜像列表
	public abstract List<Repository> getOnePageByUserId(int userId, int page,
			int limit, String search);

	//
	public abstract List<Repository> getRepoList(int userId, int page,
			int limit, String groupUuid, String search);

}
