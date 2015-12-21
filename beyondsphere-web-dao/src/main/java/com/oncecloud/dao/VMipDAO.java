package com.oncecloud.dao;

import com.oncecloud.entity.OCVMip;

public interface VMipDAO {
	
	/**
	 * 插入ip地址
	 * @author lining
	 * @param vMip
	 * @return
	 */
	public abstract boolean insertVMip(OCVMip vMip);
	
	/**
	 * 更新ip地址
	 * @author lining
	 * @param vMip 
	 * @return
	 */
	public abstract boolean updateVMip(OCVMip vMip);
	
	/**
	 * 根据mac地址，查看vmip的信息是否存在
	 * @author lining
	 */
	public abstract OCVMip getVMip(String vmMC);
	
	/**
	 * 根据vlan号判断IP地址是否冲突
	 * @author lining
	 * @param vlan
	 * @param vmIP
	 * @return
	 */
	public abstract int countVMipByIpAndVlan(String vmIP,int vlan);
}
