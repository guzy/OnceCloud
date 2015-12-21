/*package com.beyondsphere.helper;

import org.Xen.API.Connection;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beyondsphere.dao.HostDAO;
import com.beyondsphere.dao.PoolDAO;
import com.beyondsphere.dao.UserDAO;
import com.beyondsphere.entity.OCHost;
import com.beyondsphere.entity.OCPool;
import com.beyondsphere.entity.User;

*//**
 * @author hehai
 * @version 2014/08/22
 *//*
@Component
public class Constant {
	private HostDAO hostDAO;
	private PoolDAO poolDAO;
	private UserDAO userDAO;

	private HostDAO getHostDAO() {
		return hostDAO;
	}

	@Autowired
	private void setHostDAO(HostDAO hostDAO) {
		this.hostDAO = hostDAO;
	}

	private PoolDAO getPoolDAO() {
		return poolDAO;
	}

	@Autowired
	private void setPoolDAO(PoolDAO poolDAO) {
		this.poolDAO = poolDAO;
	}

	private UserDAO getUserDAO() {
		return userDAO;
	}

	@Autowired
	private void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public static enum Status {
		不可用, 可用
	};

	public static enum Platform {
		Windows, Linux, 负载均衡, 虚拟路由
	};

	public static enum EIPType {
		电信, 联通
	};

	public static String noVNCServer = OnceConfig.getValue("no_vnc");
	public static String noVNCServerPublic = OnceConfig
			.getValue("no_vnc_public");
	private static Logger logger = Logger.getLogger(Constant.class);
	public static Double CPU_PRICE = 0.04;
	public static Double MEMORY_PRICE = 0.06;
	public static Double VOLUME_PRICE = 0.02;
	public static Double SNAPSHOT_PRICE = 0.01;
	public static Double EIP_PRICE = 0.03;
	public static Double IMAGE_PRICE = 0.04;

	static {
		PropertyConfigurator.configure(Constant.class
				.getResourceAsStream("/com/oncecloud/config/log4j.properties"));
	}

	@SuppressWarnings("deprecation")
	public Connection getConnection(int userId) {
		User user = this.getUserDAO().getUser(userId);
		OCPool pool = this.getPoolDAO().getPool(user.getUserAllocate());
		OCHost master = this.getHostDAO().getHost(pool.getPoolMaster());
		String url = "http://" + master.getHostIP() + ":9363";
		try {
			Connection c = new Connection(url, "root", master.getHostPwd());
			logger.info("Create new connection to " + url);
			return c;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public Connection getConnectionNoTransactional(int userId) {
		User user = this.getUserDAO().getUserNoTransactional(userId);
		OCPool pool = this.getPoolDAO().getPoolNoTransactional(
				user.getUserAllocate());
		OCHost master = this.getHostDAO().getHostNoTransactional(
				pool.getPoolMaster());
		String url = "http://" + master.getHostIP() + ":9363";
		try {
			Connection c = new Connection(url, "root", master.getHostPwd());
			logger.info("Create new connection to " + url);
			return c;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public Connection getConnectionFromPool(String poolUuid) {
		OCPool pool = this.getPoolDAO().getPool(poolUuid);
		OCHost master = this.getHostDAO().getHost(pool.getPoolMaster());
		String url = "http://" + master.getHostIP() + ":9363";
		try {
			Connection c = new Connection(url, "root", master.getHostPwd());
			logger.info("Create new connection to " + url);
			return c;
		} catch (Exception e) {
			return null;
		}
	}
}
*/