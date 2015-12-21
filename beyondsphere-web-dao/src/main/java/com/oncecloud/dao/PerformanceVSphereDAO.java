package com.oncecloud.dao;

import java.util.List;

import com.oncecloud.entity.OCHost;
import com.oncecloud.entity.OCVM;
import com.oncecloud.model.performance.Cpu1d;
import com.oncecloud.model.performance.Cpu1m;
import com.oncecloud.model.performance.Cpu2w;
import com.oncecloud.model.performance.Cpu30min;
import com.oncecloud.model.performance.Cpu6h;
import com.oncecloud.model.performance.Memory1d;
import com.oncecloud.model.performance.Memory1m;
import com.oncecloud.model.performance.Memory2w;
import com.oncecloud.model.performance.Memory30min;
import com.oncecloud.model.performance.Memory6h;

public interface PerformanceVSphereDAO {

	/**
	 * 根据主机类型查询主机
	 * @param string
	 * @return
	 */
	public abstract List<OCHost> getAllHost(String string);
	/**
	 * 每5分钟添加监测信息
	 * @param cpu6h
	 */
	public abstract void addPerformanceData(Cpu6h cpu6h);
	public abstract void addPerformanceData(Memory6h memory6h);
	/**
	 * 每15分钟添加监测信息
	 * @param cpu6h
	 */
	public abstract void addPerformanceData(Cpu1d cpu1d);
	public abstract void addPerformanceData(Memory1m memory1d);
	/**
	 * 每4小时添加监测信息
	 * @param cpu6h
	 */
	public abstract void addPerformanceData(Cpu2w cpu2w);
	public abstract void addPerformanceData(Memory2w memory2w);
	/**
	 * 每1天添加监测信息
	 * @param cpu6h
	 */
	public abstract void addPerformanceData(Cpu1m cpu1m);
	public abstract void addPerformanceData(Memory1d memory1m);
	/**
	 * 查询当前最大的时间戳下的所有cpu信息
	 * @param string 
	 * @return
	 */
	public abstract List<Double> getAllCPU30MinsByTimes(long times, String string);
	/**
	 * 查询当前最大的时间戳
	 * @param id 
	 * @return
	 */
	public abstract long getMaxCPUTime4OneDay(String id);
	/**
	 * 查询当前最大的时间戳
	 * @return
	 */
	public abstract long getMaxCPUTime4TwoWeeks(String id);
	/**
	 * 查询当前最大的时间戳
	 * @return
	 */
	public abstract long getMaxCPUTime4OneMonth(String id);
	
	/**
	 * 查询当前最大的时间戳下的所有内存信息
	 * @param string 
	 * @return
	 */
	public abstract List<Memory30min> getAllMem30MinsByTimes(long times, String string);
	/**
	 * 查询当前最大的时间戳
	 * @return
	 */
	public abstract long getMaxMem4OneDay(String id);
	/**
	 * 查询当前最大的时间戳
	 * @return
	 */
	public abstract long getMaxMem4TwoWeeks(String id);
	/**
	 * 查询当前最大的时间戳
	 * @return
	 */
	public abstract long getMaxMem4OneMonth(String id);
	/**
	 * 每30秒监测
	 * @param cpu30min
	 */
	public abstract void addPerformanceData(Cpu30min cpu30min);
	public abstract void addPerformanceData(Memory30min memory30min);
	/**
	 * 查询当前最大的时间戳
	 * @param id 
	 * @return
	 */
	public abstract long getMaxCPUTime4SixHours(String id);
	/**
	 * 查询当前最大的时间戳
	 * @return
	 */
	public abstract long getMaxMem4SixHours(String id);
	
	/**
	 * 根据主机查询虚拟机
	 * @param hostUuid
	 * @return
	 */
	public abstract List<OCVM> getVMsByHost(String hostUuid);
}
