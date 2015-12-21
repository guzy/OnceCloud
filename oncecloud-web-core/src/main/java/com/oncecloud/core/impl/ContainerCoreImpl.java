package com.oncecloud.core.impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Container.Port;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DockerClientBuilder;
import com.oncecloud.constants.LoggerConfig;
import com.oncecloud.core.ContainerCore;
import com.oncecloud.core.HostCore;
import com.oncecloud.core.TemplateCore;
import com.oncecloud.tools.SSH;
import com.oncedocker.core.DockerConstants;
import com.oncedocker.core.DockerConfig;

/**
 * @author luogan 
 * 2015年5月19日 
 * 下午1:55:11
 */
@Component("ContainerCore")
public class ContainerCoreImpl implements ContainerCore {

	private static DockerClient client = null;
	
	@Resource
	private HostCore hostCore;
	@Resource
	private TemplateCore templateCore;
	@Resource
	private LoggerConfig logger;
	
	static {
		client = DockerClientBuilder.getInstance(
				DockerConfig.getValue(DockerConstants.ELEM_REPOURL)).build();
	}
	
	@Override
	public List<Container> listAllContainers() {
		return client.listContainersCmd().withShowAll(true).exec();
	}
	
	@Override
	public JSONObject createContainer(String url, String name,
			String command, int cpu, int memory, String startCommand,
			String link, String dockerParams) {
		// 创建docker容器
		CreateContainerResponse resp = client.createContainerCmd(url).withName(name).exec();
		if (resp.getId()!=null || !"".equals(resp.getId())) {
			client.startContainerCmd(resp.getId()).exec();
		}
		return containerInfo(resp.getId());
	}
	
	@Override
	public JSONObject createContainer(String hostIp, String hostPwd, String imageName, String name,
			String startParams) {
		String containerId = "";
		if (!imageIsExist(imageName)) {
			templateCore.pullImage(imageName);
		}
		SSH ssh = hostCore.getSshConnection(hostIp, hostPwd, DockerConstants.DOCKER_TYPE);
		if (ssh.connect()) {
			StringBuffer commandStr = new StringBuffer();
			commandStr.append(DockerConfig.getValue(DockerConstants.CREATE_COMMAND));
			if (!("").equals(name)) {
				commandStr.append(" --name " + name);
			}
			commandStr.append(" "+imageName);
			try {
				containerId = ssh.executeWithResult(commandStr.toString());
			} catch (Exception e) {
				logger.logError("Create container failed");
				e.printStackTrace();
			} finally{
				ssh.close();
			}
		}
		return containerInfo(containerId.substring(0, containerId.length()-1));
	}

	@Override
	public boolean startContainer(String containerId) {
		try {
			client.startContainerCmd(containerId).exec();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean stopContainer(String containerId) {
		try {
			client.stopContainerCmd(containerId).exec();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteContainer(String containerId) {
		try {
			client.removeContainerCmd(containerId).exec();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean contianerMakeImage(String containerId, String imgaeAddress,
			String imageName, String imageVersion) {
		String imageUUID = client.commitCmd(containerId)
				.withRepository(imgaeAddress + "/" + imageName)
				.withTag(imageVersion).exec();
		if (imageUUID == null || "".equals(imageUUID)) {
			return false;
		}
		client.pushImageCmd(imgaeAddress + "/" + imageName).exec();
		return true;
	}
	
	private boolean imageIsExist(String imageName){
		List<Image> imageList = client.listImagesCmd().exec();
		for(Image image:imageList){
			for(int i=0;i<image.getRepoTags().length;i++){
				if (imageName.equals(image.getRepoTags()[i])) {
					return true;
				}
			}
		}
		return false;
	}
	
	private JSONObject containerInfo(String containerId){
		JSONObject jo = new JSONObject();
		if (!"".equals(containerId)) {
			boolean start = false;
			while (!start) {
				List<Container> containers = client.listContainersCmd().withShowAll(true).exec();
				int flag = 0;
				for (Container container : containers) {
					System.out.println(containerId.substring(0, containerId.length()-1));
					System.out.println(container.getId());
					if (container.getId().equals(containerId)) {
						jo.put("containerid",container.getId());
					    jo.put("containername", container.getNames());
					    System.out.println("------"+container.getStatus());
						jo.put("containerstatus", container.getStatus());
						jo.put("containercommand", container.getCommand());
						jo.put("containerimage", container.getImage()); 
						Port[] ports = container.getPorts();
						StringBuffer portInfo = new StringBuffer();
						for (Port port:ports) {
							portInfo.append(port.getIp()+":"+port.getPublicPort()+"-->"+port.getPrivatePort()+" ");
						}
						jo.put("containerport", portInfo.toString());
						start = true;
						break;
					}
					flag++;
				}
				if (flag >= containers.size()) {
					logger.logError("Class:Container---method:createContainer----failed");
					break;
				}
			}
		}
		return jo;
	}

}
