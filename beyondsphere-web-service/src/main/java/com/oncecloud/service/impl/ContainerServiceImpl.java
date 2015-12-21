package com.oncecloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.oncecloud.core.ContainerCore;
import com.oncecloud.core.TemplateCore;
import com.oncecloud.dao.ContainerDAO;
import com.oncecloud.dao.RepositoryDAO;
import com.oncecloud.entity.OCVM;
import com.oncecloud.message.MessagePush;
import com.oncecloud.service.ContainerService;
import com.oncecloud.util.Utilities;

/**
 * @author luogan 
 * 2015年5月19日 
 * 下午2:07:19
 */
@Component("ContainerService")
public class ContainerServiceImpl implements ContainerService {


	@Resource
	private MessagePush messagePush;
	@Resource
	private RepositoryDAO repositoryDAO;
	@Resource
	private ContainerDAO containerDAO;
	@Resource
	private ContainerCore containerCore;
	@Resource
	private TemplateCore templateCore;
	private String str;

	// 查找docker容器中的image
	@Override
	public JSONArray ContainerTemplateList(int userId, int page, int limit,
			String search, String type) {

		List<Image> images = templateCore.listAllImages();

		JSONArray ja = new JSONArray();
		if (images != null) {
			for (Image image : images) {
				JSONObject jo = new JSONObject();
				jo.put("imageid", image.getId());
				String[] repoTags = image.getRepoTags();
				// String[] values = repoTags[0].split(":");
				int i = repoTags[0].lastIndexOf(":");
				str = repoTags[0];
				String name = str.substring(0, i);
				String version = str.substring(i + 1, repoTags[0].length());

				jo.put("imagename", name);
				jo.put("imageversion", version);
				// jo.put("imagename", values[0]);
				// jo.put("imageversion", values[1]);
				jo.put("imagesize", image.getVirtualSize());
				ja.put(jo);

			}
		}
		return ja;

	}

	@Override
	public boolean deleteContainer(int userId, String containerId) {
		// client.removeContainerCmd(containerId).exec();
		return containerDAO.deleteContainer(userId, containerId);
	}

	@Override
	public boolean startContainer(int userId, String containerId) {
		// client.startContainerCmd(containerId).exec();
		return containerDAO.startContainer(userId, containerId);
	}

	@Override
	public boolean stopContainer(int userId, String containerId) {
		// client.stopContainerCmd(containerId).exec();
		return containerDAO.stopContainer(userId, containerId);
	}

	// 容器制作镜像
	@Override
	public boolean contianerMakeImage(String containerId, String imgaeAddress,
			String imageName, String imageVersion) {

		boolean created = containerCore.contianerMakeImage(containerId,
				imgaeAddress, imageName, imageVersion);

		return created;
	}

	//创建容器
	@Override
	public boolean createContainer(String vmUuid, String tplUuid, int userId,
			int cpu, int memory, String name,String desc, String statistics,
			String startCommand, String link, String dockerParams, String port,Date conCreateTime) {

		// 如果创建docker容器成功保存到本地OCVM数据库里
		boolean result = false;
		result = containerDAO.createContainer(vmUuid, tplUuid, userId, cpu,
				memory, null, name, desc, statistics,port,conCreateTime);

		return result;
	}

	@Override
	public JSONArray queryContainerList(int userId, int page, int limit,
			String groupUuid, String search) {
		// 通过用户查询数据库的docker容器列表
		JSONArray ja = new JSONArray();

		int totalNum = containerDAO.countByUserId(userId, search);

		List<OCVM> VMList = containerDAO.getOnePageByUserId(userId, page,
				limit, groupUuid, search);
		ja.put(totalNum);
		if (VMList != null) {
			for (OCVM ocvm : VMList) {
				JSONObject jo = new JSONObject();
				jo.put("containerid", ocvm.getVmUuid());
				jo.put("containername", Utilities.encodeText(ocvm.getVmName()));
				jo.put("containerstatus",ocvm.getStatus());
				jo.put("containervlan", ocvm.getVmVlan());
				jo.put("containercpu", ocvm.getVmCpu());
				jo.put("containermem", ocvm.getVmMem());
				jo.put("containerimage", ocvm.getVmDesc());
				jo.put("containerport", ocvm.getVmImportance());
				if (ocvm.getVmCreateDate() != null) {
					jo.put("containertime",Utilities.formatTime(ocvm.getVmCreateDate()));
				}
				
				
				//jo.put("vmplatform", ocvm.getVmPlatform().toString());
				//jo.put("vmMac", ocvm.getVmMac());
				/*if (ocvm.getVmBackUpDate() == null) {
					jo.put("backupdate", "");
				} else {
					String timeUsed = Utilities.encodeText(Utilities
							.dateToUsed(ocvm.getVmBackUpDate()));
					jo.put("backupdate", timeUsed);
				}
				OCGroup group = groupService.getGroup(userId, ocvm.getVmGroup());
				if (group == null) {
					jo.put("groupName", "默认分组");
					jo.put("groupColor", "#ffffff");
				} else {
					jo.put("groupName", group.getGroupName());
					jo.put("groupColor", group.getGroupColor());
				}
				String timeUsed = Utilities.encodeText(Utilities
						.dateToUsed(ocvm.getVmCreateDate()));

				OCHost ocHost = hostDAO.getHost(ocvm.getVmHostUuid());
				String hosttype = ocHost.getHostType();
				jo.put("hosttype", hosttype);
				jo.put("createdate", timeUsed);*/
				ja.put(jo);
			}
		}

		return ja;
	}

	@Override
	public JSONArray listConstainers() {
		List<Container> containers = containerCore.listAllContainers();
		JSONArray ja = new JSONArray();
		if (containers != null) {
			for (Container container : containers) {
				JSONObject jo = new JSONObject();
				jo.put("containerid", container.getId());
				jo.put("containername", container.getNames());
				ja.put(jo);
			}
		}
		return ja;
	}

}
