/**
 * @author hty
 * @time 上午11:05:48
 * @date 2014年12月9日
 */
package com.oncecloud.manager;

import com.oncecloud.entity.Power;

public interface PowerManager {

	public abstract boolean savePower(int userId, String powerUuid,
			String hostUuid, String motherboardIP, int powerPort,
			String powerUsername, String powerPassword, Integer powerValid);

	public abstract int getStatusOfPower(String powerIP, int powerPort,
			String userName, String passWord);

	public abstract boolean startPower(int userId, String hostUuid);

	public abstract boolean stopPower(int userId, String hostUuid);

	/**
	 * 开启电源
	 * 
	 * @author lining
	 * @param userId
	 *            , powerIp, powerPort, userName, password
	 * @return
	 */
	public boolean startPower(int userId, String powerIP, int powerPort,
			String userName, String passWord);

	/**
	 * 关闭电源
	 * 
	 * @author lining
	 * @param userId
	 *            , powerIp, powerPort, userName, passWord
	 * @return
	 */
	public abstract boolean stopPower(int userId, String powerIP,
			int powerPort, String userName, String passWord);

	public abstract boolean deletePower(String hostUuid);

	public abstract Power getPower(String hostUuid);

}
