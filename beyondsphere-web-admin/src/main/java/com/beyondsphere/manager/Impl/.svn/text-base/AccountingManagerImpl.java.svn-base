package com.beyondsphere.manager.Impl;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import com.beyondsphere.manager.AccountingManager;
import com.beyondsphere.service.AccountingService;
@Service("AccountingManager")
public class AccountingManagerImpl implements AccountingManager {
	@Resource
	private AccountingService accountingService;
	@Override
	public JSONArray getOnePageListOfAccounting(int page, int limit,String search, int type) {
		JSONArray ja = accountingService.getOnePageListOfAccounting(page, limit,search, type);
		return ja;
	}
}
