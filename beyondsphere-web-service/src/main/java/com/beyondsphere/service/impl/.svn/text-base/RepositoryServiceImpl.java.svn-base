package com.beyondsphere.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.RepositoryDAO;
import com.beyondsphere.entity.Repository;
import com.beyondsphere.message.MessagePush;
import com.beyondsphere.service.ConnectionService;
import com.beyondsphere.service.RepositoryService;

/**
 * @author luogan
 * 2015年5月19日
 * 下午2:09:34
 */
@Component("RepositoryService")
public class RepositoryServiceImpl implements RepositoryService {
	
	private final static Logger logger = Logger.getLogger(RepositoryServiceImpl.class);
	@Resource
	private ConnectionService conService;
	@Resource
	private MessagePush messagePush;
	//
	@Resource
	private RepositoryDAO repositoryDAO;
	
	@Override
	public JSONArray ContainerRepoList(int userId, int page, int limit,
			String search, String type) {
		
		JSONArray ja = new JSONArray();
		int totalNum = repositoryDAO.countByUserId(userId, search);
		List<Repository> repoList = repositoryDAO.getOnePageByUserId(userId, page,limit,search);
		ja.put(totalNum);
		if (repoList != null) {
			for (Repository repository : repoList) {
				
				JSONObject jo = new JSONObject();
				jo.put("repoid", repository.getRepoId());
				jo.put("reponame", repository.getRepoName());
				jo.put("repoversion", repository.getRepoVersion());
				jo.put("repoaddress", repository.getRepoAddress());
				
				ja.put(jo);
			}
		}
		logger.info("repoList查询成功！");
		return ja;
		
	}

}
