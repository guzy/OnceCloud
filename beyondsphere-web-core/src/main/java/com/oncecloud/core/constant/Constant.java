package com.oncecloud.core.constant;

import java.net.URL;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.Session;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.HostDAO;
import com.oncecloud.dao.PoolDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCPool;
import com.oncecloud.entity.User;


@Component("constant")
public class Constant {
	
	@Resource
	private HostDAO hostDAO;
	@Resource
	private PoolDAO poolDAO;
	@Resource
	private UserDAO userDAO;
	
	private static Logger logger = Logger.getLogger(Constant.class);
	
	public Connection getConnection(int userId) {
		User user = userDAO.getUser(userId);
		OCPool pool = poolDAO.getPool(user.getUserAllocate());
		OCHost master = hostDAO.getHost(pool.getPoolMaster());
		try {
			if (master != null) {
				return getConnectionFromHost(master);
			}else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public Connection getConnectionNoTransactional(int userId) {
		User user = userDAO.getUserNoTransactional(userId);
		OCPool pool = poolDAO.getPoolNoTransactional(
				user.getUserAllocate());
		OCHost master = hostDAO.getHostNoTransactional(
				pool.getPoolMaster());
		try {
			if (master != null) {
				return getConnectionFromHost(master);
			}else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public Connection getConnectionFromPool(String poolUuid) throws Exception {
		OCPool pool = poolDAO.getPool(poolUuid);
		OCHost master = hostDAO.getHost(pool.getPoolMaster());
		if (master != null) {
			return getConnectionFromHost(master);
		}else {
			return null;
		}
	}
	
	public Connection getConnectionFromHost(OCHost host){
		URL url = null;
		Connection conn = null;
		try {
			url = new URL("HTTP",host.getHostIP(),com.oncecloud.constants.SystemConstant.DEFAULT_PORT,"/");
			conn = new Connection(url);
			Session.loginWithPassword(conn, com.oncecloud.constants.SystemConstant.DEFAULT_USER, host.getHostPwd(), null);
			logger.info("Create new connection to " + url);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return conn;
	}
}
