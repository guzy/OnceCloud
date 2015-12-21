package com.beyondsphere.manager.Impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.beyondsphere.manager.AccountManager;
import com.beyondsphere.service.AccountService;

@Service("accountManager")
public class AccountManagerImpl implements AccountManager {
	
	@Resource
	private AccountService accountService;
	
	public Map<String, Object> getInfrastructureInfos() {
		return accountService.getInfrastructureInfos();
	}

}
