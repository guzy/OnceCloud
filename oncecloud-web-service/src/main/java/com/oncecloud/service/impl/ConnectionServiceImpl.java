/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.oncecloud.service.impl;

import java.net.URL;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.Session;
import org.apache.log4j.Logger;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.stereotype.Component;

import com.oncecloud.dao.HostDAO;
import com.oncecloud.dao.PoolDAO;
import com.oncecloud.dao.UserDAO;
import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCPool;
import com.oncecloud.entity.User;
import com.oncecloud.service.ConnectionService;

@Component("ConnectionService")
public class ConnectionServiceImpl implements ConnectionService {

	private static Logger logger = Logger.getLogger(Constant.class);

	@Resource
	private UserDAO userDAO;
	@Resource
	private PoolDAO poolDAO;
	@Resource
	private HostDAO hostDAO;

	public Connection getConnectionFromUser(int userId) {
		User user = userDAO.getUser(userId);
		return this.getConnectionFromPool(user.getUserAllocate());
	}

	public Connection getConnectionFromPool(String poolUuid) {
		OCPool pool = poolDAO.getPool(poolUuid);
		OCHost master = hostDAO.getHost(pool.getPoolMaster());
		String url = "http://" + master.getHostIP() + ":9363/";
		Connection c = null;
		try {
			c = new Connection(new URL(url));
			Session.loginWithPassword(c, "root", master.getHostPwd(), null);
			logger.info("Create new connection to " + url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

}
