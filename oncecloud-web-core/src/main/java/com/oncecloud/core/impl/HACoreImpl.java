package com.oncecloud.core.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.oncecloud.core.PoolHACore;
import com.oncecloud.core.ha.HAPoolWorker;

@Component("HACore")
public class HACoreImpl implements PoolHACore {

private static Map<String,HAPoolWorker> haMaps=new HashMap<String,HAPoolWorker>();
	
	public boolean stop(String poolUuid) {
			
	    try {
			HAPoolWorker worker;
			if(haMaps.size()>0 && haMaps.containsKey(poolUuid)){
				worker= haMaps.get(poolUuid);
				worker.stop();
				haMaps.remove(poolUuid);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean start(String poolUuid,String haPath,String masterIP) {
	
		try {
			stop(poolUuid);
			HAPoolWorker worker = new HAPoolWorker(poolUuid,haPath,masterIP);
			haMaps.put(poolUuid, worker);
			Thread thread = new Thread(worker);
			thread.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public boolean isStarted(String poolUuid) {
		try {
			HAPoolWorker worker = haMaps.get(poolUuid);
			return worker.isStarted();
		} catch (Exception e) {
			return false;
		}
	}

}
