/**
 * @author zll
 * @time 上午15:22:48
 * @date 2015年5月19日
 */
package com.oncecloud.manager.Impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oncecloud.manager.VolumeManager;
import com.oncecloud.entity.Volume;
import com.oncecloud.service.VolumeService;

@Service("VolumeManager")
public class VolumeManagerImpl implements VolumeManager {

	@Resource
	private VolumeService volumeService;

	
	@Override
	public List<Volume> getAllList() {
		return volumeService.getAllList();
	}

	@Override
	public Volume getVolume(String uid) {
		Volume vo =volumeService.getOneById(uid);
		return vo;
	}

	@Override
	public List<Volume> getListbyUserid(int userid) {
		return volumeService.getListbyUserid(userid);
	}

}
