package com.oncecloud.manager.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oncecloud.core.RepositoryCore;
import com.oncecloud.manager.RepositoryManager;
import com.oncecloud.service.RepositoryService;

/**
 * @author luogan
 * 2015年6月19日
 * 下午3:23:05
 */
@Service("RepositoryManager")
public class RepositoryManagerImpl implements RepositoryManager {

	@Resource
	private RepositoryCore repositoryCore;
	@Resource
	private RepositoryService repositoryService;
	
	
}
