package com.oncecloud.dao;

import java.util.Date;
import java.util.List;

import com.oncecloud.entity.Volume;
import com.oncecloud.model.VolumeStatus;


/**
 * CostType DAO
 * @author zll
 *
 * @Date 2015年5日
 */
public interface VolumeDAO extends QueryListDAO<Volume>{

    /**
	 * 根据主键 获取Volume
	 * @param id
	 * @return 
	 * @throws OnceDAOException
	 */
    public abstract Volume getOneByID(String id);
    
    
    /**
	 * 获取列表的总记录数
	 * @param search
	 * @return
	 */
	public int countAllList(String search);
	
	/**
	 * 获取所有Volume
   	 * @return List<Volume>
   	 * @throws OnceDAOException
	 */
	public abstract List<Volume> getAllList();
	
	/**
	 * 获取所有Volume通过userid
   	 * @return List<Volume>
   	 * @throws OnceDAOException
	 */
	public abstract List<Volume> getListbyUserid(int userid);
	
	/**
	 * 根据主机的uuid获取该主机挂载的所有硬盘的uuid列表
	 * 
	 * @param vmUuid
	 * @return
	 */
	public abstract List<String> getVolumeUuidListOfVM(String vmUuid);
	
	/**
	 * 根据主机的uuid获取该主机挂载的所有硬盘列表
	 * 
	 * @param vmUuid
	 * @return
	 */
	public abstract List<Volume> getVolumeListOfVM(String vmUuid);

	/**
	 * 清空硬盘挂载信息
	 * 
	 * @param volumeUuid
	 */
	public abstract boolean emptyDependency(String volumeUuid);

	/**
	 * 更新硬盘备份时间
	 * 
	 * @param volumeUuid
	 * @param backdate
	 * @return
	 */
	public abstract boolean updateVolumeBackdate(String volumeUuid,
			Date backdate);

	/**
	 * 判断硬盘是否存活
	 * 
	 * @param volumeUuid
	 * @return
	 */
	public abstract boolean isActive(String volumeUuid);

	/**
	 * 根据硬盘uuid获取一块硬盘
	 * 
	 * @param volumeUuid
	 * @return
	 */
	public abstract Volume getVolumeByUuid(String volumeUuid);

	/**
	 * 获取某一用户的硬盘总数
	 * 
	 * @param userId
	 * @param search
	 * @return
	 */
	public abstract int countByUserId(int userId, String search);

	/**
	 * 获取某一用户某页的硬盘列表
	 * 
	 * @param userId
	 * @param page
	 * @param limimt
	 * @param search
	 * @return
	 */
	public abstract List<Volume> getOnePageByUserId(int userId,
			int page, int limit, String search);
	
	/**
	 * 获取某一用户某页的硬盘列表
	 * 
	 * @param userId
	 * @param page
	 * @param limimt
	 * @param search
	 * @return
	 */
	public abstract List<Volume> getAbledVolumes(int userId);

	/**
	 * 预创建硬盘
	 * 
	 * @param volumeUuid
	 * @param volumeName
	 * @param volumeUID
	 * @param volumeSize
	 * @param createDate
	 * @param status
	 * @return
	 */
	public abstract boolean preCreateVolume(String volumeUuid,
			String volumeName, Integer volumeUID, Integer volumeSize,
			Date createDate, VolumeStatus status);

	/**
	 * 更新硬盘状态
	 * 
	 * @param voumeUuid
	 * @param status
	 * @return
	 */
	public abstract boolean updateVolumeStatus(String volumeUuid,
			VolumeStatus status);

	/**
	 * 删除硬盘
	 * 
	 * @param userId
	 * @param volumeUuid
	 * @return
	 */
	public abstract boolean deleteVolume(int userId, String volumeUuid);

	/**
	 * 虚拟机挂载硬盘的数据更新操作
	 * 
	 * @param volumeUuid
	 * @param vmUuid
	 */
	public abstract boolean addDependency(String volumeUuid, String vmUuid);
	
	/**
	 *更新硬盘的信息
	 *
	 *@author lining
	 *@return 
	 */
	public void updateName(String volumeUuid, String newName, String description);
}
