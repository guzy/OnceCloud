/**
 * 
 */
package com.oncecloud.core.hostinfo;

import com.oncecloud.core.HostInfo;

/**
 * @author luogan
 * 2015年5月19日
 * 下午5:52:57
 */
public class DockerHostInfo extends HostInfo {

	@Override
	protected String command() {
		return "/usr/bin/docker info | grep -E \"CPUs|ID|Total Memory\"";
		//return "cat /etc/docker/key.json | grep \"kid\"";
//		return "/usr/bin/docker -H tcp://0.0.0.0:2375 info | grep \"ID\"";
	}

	@Override
	protected String valid() {
		return "ps -aux | grep \"docker\"";
	}

}
