
package com.beyondsphere.core.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.beyondsphere.constants.LoggerConfig;
import com.beyondsphere.core.HostCore;
import com.beyondsphere.core.TemplateCore;
import com.beyondsphere.tools.SSH;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DockerClientBuilder;
import com.oncedocker.core.DockerConstants;
import com.oncedocker.core.DockerConfig;

/**
 * @author luogan
 * 2015年5月19日
 * 下午1:53:34
 */
@Component("TemplateCore")
public class TemplateCoreImpl implements TemplateCore {
	
	@Resource
	private HostCore hostCore;
	@Resource
	private LoggerConfig logger;
	
	private static DockerClient client = null;
	
	static {
		client = DockerClientBuilder.getInstance(
				DockerConfig.getValue(DockerConstants.ELEM_REPOURL)).build();
	}

	
	@Override
	public List<Image> listAllImages() {
		// 查找docker容器中的image
		logger.logInfo("begin: list all images");
		List<Image> images = client.listImagesCmd().exec();
		logger.logInfo("end: list all images");
		return images;

	}

	@Override
	public boolean deleteImage(String imageId) {
		try {
			client.removeImageCmd(imageId).exec();
			return true;
		} catch (Exception e) {
			logger.logError("Class:TemplateCoreImpl---Method:deleteImage--failed.");
			return false;
		}
	}

	@Override
	public String makeImage(String masterIp, String password, String fileName, String templateName) {
		SSH ssh = hostCore.getSshConnection(DockerConfig.getValue(DockerConstants.REGISTRY_URL), DockerConfig.getValue(DockerConstants.REGISTRY_PWD), DockerConstants.DOCKER_TYPE);
		if (ssh.connect()) {
			StringBuffer commandStr = new StringBuffer();
			commandStr.append("cd /opt;");
			if(fileName.contains(".tar.gz")){
				commandStr.append("tar zxvf "+fileName+";");
			}else if (fileName.contains(".zip")) {
				commandStr.append("unzip "+fileName+";");
			}
			commandStr.append("docker build -t "+templateName+" "+DockerConstants.DOCKERFILE_PATH+fileName.substring(0, fileName.length()-7)+"/;");
			commandStr.append("rm -rf "+DockerConstants.DOCKERFILE_PATH+fileName.substring(0, fileName.length()-7)+"*;");
			try {
				logger.logInfo(commandStr.toString());
				return ssh.executeWithResult(commandStr.toString());
			} catch (Exception e) {
				logger.logError("Class:TemplateCoreImpl---Method:makeImage---failed");
				e.printStackTrace();
			}finally{
				ssh.close();
			}
		}
		return "make image failed";
	}

	@Override
	public String tagImage(String masterIp, String password, String imageId, String registryIp, String imageName) {
		SSH ssh = hostCore.getSshConnection(DockerConfig.getValue(DockerConstants.REGISTRY_URL), DockerConfig.getValue(DockerConstants.REGISTRY_PWD), DockerConstants.DOCKER_TYPE);
		if (ssh.connect()) {
			StringBuffer commandStr = new StringBuffer();
			commandStr.append("docker tag "+imageId+" localhost:"+DockerConfig.getValue(DockerConstants.REGISTRY_PORT)+"/"+imageName);
			try {
				logger.logInfo(commandStr.toString());
				return ssh.executeWithResult(commandStr.toString());
			} catch (Exception e) {
				logger.logError("Class:TemplateCoreImpl---Method:tagImage---failed");
				e.printStackTrace();
			}finally{
				ssh.close();
			}
		}
		return null;
	}

	@Override
	public boolean pushImage(String masterIp, String password, String imageTag) {
		SSH ssh = hostCore.getSshConnection(DockerConfig.getValue(DockerConstants.REGISTRY_URL), DockerConfig.getValue(DockerConstants.REGISTRY_PWD), DockerConstants.DOCKER_TYPE);
		if (ssh.connect()) {
			StringBuffer commandStr = new StringBuffer();
			commandStr.append("docker push "+imageTag);
			try {
				System.out.println("--------------------");
				System.out.println(commandStr.toString());
				System.out.println("--------------------");
				logger.logInfo(commandStr.toString());
				ssh.executeWithResult(commandStr.toString());
				return true;
			} catch (Exception e) {
				logger.logError("Class:TemplateCoreImpl---Method:pushImage---failed");
				e.printStackTrace();
			}finally{
				ssh.close();
			}
		}
		return false;
	}

	@Override
	public boolean pullImage(String imageTag) {
		client.pullImageCmd(imageTag);
		return true;
	}
	
}
