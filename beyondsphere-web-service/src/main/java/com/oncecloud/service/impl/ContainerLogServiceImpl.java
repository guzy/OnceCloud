package com.oncecloud.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.oncecloud.core.ContainerCore;
import com.oncecloud.service.ContainerLogService;
import com.oncedocker.core.DockerConstants;
import com.oncedocker.core.DockerConfig;

@Service
public class ContainerLogServiceImpl implements ContainerLogService {
	
	@Resource
	private ContainerCore containerCore;
	
	@SuppressWarnings("unused")
	private static DockerClient client = null;
	
	static{
		client = DockerClientBuilder.getInstance(DockerConfig.getValue(DockerConstants.ELEM_REPOURL)).build();
	}
	
	@Override
	public JSONObject getContainerLogs(String containerId, int logType) {
		InputStream is = null;
		JSONObject jo = new JSONObject();
//		if(DockerLogType.INFO.ordinal() == logType){
//			is = client.logContainerCmd(containerId).withStdOut().exec();
//		}else if(DockerLogType.ERROR.ordinal() == logType){
//			is = client.logContainerCmd(containerId).withStdErr().exec();
//		}
		String logs = "";
		try {
			logs =  inputStream2String(new InputStreamReader(is));
			jo.put("log", logs);
		} catch (IOException e) {
			//
		}
		return jo;
	}
	
	
	private String inputStream2String(InputStreamReader reader) throws IOException{ 
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(reader);
		String line = null;
		while((line = br.readLine()) != null) {
			try {
				if(line.substring(8).startsWith("\t")){
					sb.append(line.substring(8)).append("<br><span style= \" margin-left:60px \"></span>");
				}else{
					sb.append(line.substring(8)).append("<br>");
				}
				
			} catch (Exception e) {
				// this is ok
			}
		}
		return sb.toString();
	}

}
