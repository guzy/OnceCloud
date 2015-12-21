package com.beyondsphere.manager.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.beyondsphere.core.RepositoryCore;
import com.beyondsphere.manager.RepositoryManager;
import com.beyondsphere.service.RepositoryService;

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
