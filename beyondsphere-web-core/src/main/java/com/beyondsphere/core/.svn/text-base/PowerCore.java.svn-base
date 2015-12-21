package com.beyondsphere.core;

public interface PowerCore {
	
	/**
	 * 获取服务器电源状态
	 * @author lining
	 * @param powerIp, powerPort, userName, password
	 * @return
	 */
	public int getStatusOfPower(String powerIP,int powerPort,String userName,String passWord); 
	
	/**
	 * 开启电源
	 * @author lining
	 * @param userId, powerIp, powerPort, userName, password
	 * @return
	 */
	public boolean startup(int userId, String powerIP, int powerPort,
			String userName, String passWord);
	
	/**
	 * 关闭电源
	 * @author lining
	 * @param userId, powerIp, powerPort, userName, passWord
	 * @return
	 */
	public boolean shutdown(int userId, String powerIP,int powerPort,String userName,String passWord);
	
}
