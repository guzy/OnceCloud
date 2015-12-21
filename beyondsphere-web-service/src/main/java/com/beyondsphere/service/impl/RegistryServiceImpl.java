package com.beyondsphere.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.RegistryDAO;
import com.beyondsphere.dao.RepositoryDAO;
import com.beyondsphere.entity.Registry;
import com.beyondsphere.entity.Repository;
import com.beyondsphere.message.MessagePush;
import com.beyondsphere.service.RegistryService;
import com.beyondsphere.util.Utilities;

/**
 * @author luogan
 * 2015年7月15日
 * 下午12:10:08
 */
@Component("RegistryService")
public class RegistryServiceImpl implements RegistryService {

	private final static Logger logger = Logger.getLogger(RegistryServiceImpl.class);
	// private static DockerClient client = null;

	@Resource
	private MessagePush messagePush;
	@Resource
	private RegistryDAO registryDAO;
	@Resource
	private RepositoryDAO repositoryDAO;

	// static {
	// try {
	// client
	// =DockerClientBuilder.getInstance(DokcerConfig.getValue(DockerConstants.ELEM_REPOURL)).build();
	// } catch (Exception e) {
	// client = DockerClientBuilder.getInstance("http://133.133.134.169:2375")
	// .build();
	// }
	// }

	@Override
	public JSONArray regRepoList(int userId, int page, int limit,
			String search, String type) {

		JSONArray ja = new JSONArray();
		int totalNum = repositoryDAO.countByUserId(userId, search);
		List<Repository> repoList = repositoryDAO.getRepoList(userId, page,limit,null,search);
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
			logger.info("repoList查询成功！");
		}else{
			logger.info("repoList查询失败！");
		}
		
		return ja;
	}
	
	//创建仓库
		@Override
		public boolean createRegistry(String regUuid, String name,String ip, String port,String status,Date createTime) {

			 // 如果创建docker创建成功保存到本地oc_registry数据库里
			 boolean result = false;
			 result = registryDAO.createRegistry(regUuid,name,ip,port,status,createTime);
			 if(result==true){
				 logger.info("创建仓库成功！");
			 }else{
				 logger.info("创建仓库失败！");
			 }
			 
			 return result;
		}
		
	// 查找oc_registry表里面的列表数据
	@Override
	public JSONArray registryList(int userId, int page, int limit,
			String search, String type) {
				JSONArray ja = new JSONArray();

				int totalNum = registryDAO.countByUserId(userId, search);

				List<Registry> regList = registryDAO.getOnePageByUserId(userId,page,limit,null,search);
				ja.put(totalNum);
				if (regList != null) {
					for (Registry reg : regList) {
						JSONObject jo = new JSONObject();
						jo.put("regid", reg.getRegId());
						jo.put("regname", Utilities.encodeText(reg.getRegName()));
						jo.put("regip",reg.getRegIP());
						jo.put("regport", reg.getRegPort());
						jo.put("regstatus", reg.getRegStatus());
						String regCreateTime = Utilities.formatTime(reg.getRegCreateTime());
						jo.put("regtime",regCreateTime);
						
						ja.put(jo);
					}
					logger.info("查询仓库列表成功！");
				}else{
					logger.info("查询仓库列表失败！");
				}
			
				return ja;
	}

	@Override
	public boolean deleteRegistry(int userId, String containerId) {
		
		 // 如果docker删除成功对应的去删除本地oc_registry数据
		 boolean result = false;
		 result = registryDAO.deleteRegistry(userId, containerId);
		 if(result==true){
			 logger.info("删除仓库成功！");
		 }else{
			 logger.info("删除仓库失败！");
		 }
		 
		 return result;
	}

}
