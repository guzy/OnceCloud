package com.oncecloud.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.beyondsphere.vsphere.VMWareUtil;
import com.oncecloud.dao.PerformanceVSphereDAO;
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
import com.oncecloud.service.PerformanceVSphereService;
@Component("PerformanceVSphereService")
public class PerformanceVSphereServiceImpl implements PerformanceVSphereService {
	@Resource
	private PerformanceVSphereDAO sphereDAO;
	@Override
	public List<OCHost> getAllHost(String string) {
		
		return sphereDAO.getAllHost(string);
	}
	@Override
	public void addPerformanceData(Memory6h memory6h) {
		sphereDAO.addPerformanceData(memory6h);
		
	}
	@Override
	public void addPerformanceData(Cpu6h cpu6h) {
		sphereDAO.addPerformanceData(cpu6h);
		
	}
	@Override
	public void addPerformanceData(Cpu1d cpu1d) {
		sphereDAO.addPerformanceData(cpu1d);
		
	}
	@Override
	public void addPerformanceData(Memory1m memory1d) {
		sphereDAO.addPerformanceData(memory1d);
		
	}
	@Override
	public void addPerformanceData(Cpu2w cpu2w) {
		sphereDAO.addPerformanceData( cpu2w);
		
	}
	@Override
	public void addPerformanceData(Memory2w memory2w) {
		sphereDAO.addPerformanceData(memory2w);
		
	}
	@Override
	public void addPerformanceData(Cpu1m cpu1m) {
		sphereDAO.addPerformanceData(cpu1m);
		
	}
	@Override
	public void addPerformanceData(Memory1d memory1m) {
		sphereDAO.addPerformanceData(memory1m);
		
	}
	@Override
	public List<Double> getAllCPU30MinsByTimes(long times, String string) {
		return sphereDAO.getAllCPU30MinsByTimes(times, string);
	}
	@Override
	public long getMaxCPUTime4OneDay(String id) {
		return sphereDAO.getMaxCPUTime4OneDay(id);
	}
	@Override
	public long getMaxCPUTime4TwoWeeks(String id) {
		return sphereDAO.getMaxCPUTime4TwoWeeks(id);
	}
	@Override
	public long getMaxCPUTime4OneMonth(String id) {
		return sphereDAO.getMaxCPUTime4OneMonth(id);
	}
	@Override
	public List<Memory30min> getAllMem30MinsByTimes(long times, String string) {
		return sphereDAO.getAllMem30MinsByTimes(times, string);
	}
	@Override
	public long getMaxMem4OneDay(String id) {
		return sphereDAO.getMaxMem4OneDay(id);
	}
	@Override
	public long getMaxMem4TwoWeeks(String id) {
		return sphereDAO.getMaxMem4TwoWeeks(id);
	}
	@Override
	public long getMaxMem4OneMonth(String id) {
		return sphereDAO.getMaxMem4OneMonth(id);
	}
	@Override
	public void addPerformanceData(Cpu30min cpu30min) {
		sphereDAO.addPerformanceData( cpu30min);
		
	}
	@Override
	public void addPerformanceData(Memory30min memory30min) {
		sphereDAO.addPerformanceData( memory30min);
	}
	@Override
	public long getMaxCPUTime4SixHours(String id) {
		return sphereDAO.getMaxCPUTime4SixHours(id);
	}
	@Override
	public long getMaxMem4SixHours(String id) {
		return sphereDAO.getMaxMem4SixHours(id);
	}

public void addPerformanceData(String type){
		
		List<OCHost> ocHosts = sphereDAO.getAllHost("vSphere");
		List<OCVM> ocvms = new ArrayList<OCVM>();
		for(OCHost host:ocHosts){
			ocvms = sphereDAO.getVMsByHost(host.getHostUuid());
			
			for(OCVM ocvm:ocvms){
				String vmname = ocvm.getVmName();
				String propertyStr="";
				if("0".equals(type)){
					try {
						//创建连接
						VMWareUtil.connect("https://"+host.getHostIP().trim()+"/sdk","root",host.getHostPwd());
						
						propertyStr = VMWareUtil.callWaitForUpdatesEx("60", "1",vmname) + ":";
					} catch (Exception e) {
						e.printStackTrace();
					}
					String[] strings = propertyStr.split(",");
					for(int i=0;i<strings.length;i++){
						String[] cup2memory = strings[i].split(":");
						
						if(cup2memory[1].equals("POWERED_ON"))
						{
							double CpuUse = Double.parseDouble(cup2memory[4] == null?"0":cup2memory[4]);
							double CpuMax = Double.parseDouble(cup2memory[2]);
							double MemUse = Double.parseDouble(cup2memory[5] == null?"0":cup2memory[5])*1024;
							double MemMax = Double.parseDouble(cup2memory[3])*1024;
						
							BigDecimal bg = new BigDecimal(CpuUse/CpuMax);
					        double CPU = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
					        
							Cpu30min cpu30min = new Cpu30min();
							cpu30min.setCpuId(1);
							cpu30min.setTime(System.currentTimeMillis());
							cpu30min.setUsage(CPU);
							cpu30min.setUuid(ocvm.getVmUuid());
							sphereDAO.addPerformanceData(cpu30min);
							
							Memory30min memory30min = new Memory30min();
							memory30min.setFreeSize(MemMax-MemUse);
							memory30min.setTime(System.currentTimeMillis());
							memory30min.setTotalSize(MemMax);
							memory30min.setUuid(ocvm.getVmUuid());
							sphereDAO.addPerformanceData(memory30min);
						}
						
					}
				}
				if ("1".equals(type)) {
					// 计算15分钟内的cpu平均值
					long cpumaxtime = sphereDAO
							.getMaxCPUTime4SixHours(ocvm.getVmUuid());
					List<Double> cpus = sphereDAO
							.getAllCPU30MinsByTimes(cpumaxtime,
									ocvm.getVmUuid());
					double CPU_total = 0;
					for (double cpu : cpus) {
						CPU_total += cpu;
					}
					BigDecimal bg = new BigDecimal(cpus.size() > 0 ? CPU_total
							/ cpus.size() : 0);
					double CPU = bg.setScale(4, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					// 计算15分钟内的内存平均值
					long memmaxtime = sphereDAO.getMaxMem4SixHours(ocvm.getVmUuid());
					List<Memory30min> memory6hs = sphereDAO
							.getAllMem30MinsByTimes(memmaxtime,
									ocvm.getVmUuid());
					double memory_total = 0;
					double memory_free = 0;
					for (Memory30min memory : memory6hs) {
						memory_total += memory.getTotalSize();
						memory_free += memory.getFreeSize();
					}
					BigDecimal bg1 = new BigDecimal(
							memory6hs.size() > 0 ? memory_total / memory6hs.size()
									: 0);
					double totalsize = bg1.setScale(4, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					BigDecimal bg2 = new BigDecimal(
							memory6hs.size() > 0 ? memory_free / memory6hs.size()
									: 0);
					double freesize = bg2.setScale(4, BigDecimal.ROUND_HALF_UP)
							.doubleValue();

					Cpu6h cpu6h = new Cpu6h();
					cpu6h.setCpuId(1);
					cpu6h.setTime(System.currentTimeMillis());
					cpu6h.setUsage(CPU);
					cpu6h.setUuid(ocvm.getVmUuid());
					if (cpus.size() > 0) {
						sphereDAO.addPerformanceData(cpu6h);
					}

					Memory6h memory6h = new Memory6h();
					memory6h.setFreeSize(freesize);
					memory6h.setTime(System.currentTimeMillis());
					memory6h.setTotalSize(totalsize);
					memory6h.setUuid(ocvm.getVmUuid());
					if (memory6hs.size() > 0) {
						sphereDAO.addPerformanceData(memory6h);
					}

				}
				if ("2".equals(type)) {
					// 计算15分钟内的cpu平均值
					long cpumaxtime = sphereDAO
							.getMaxCPUTime4OneDay(ocvm.getVmUuid());
					List<Double> cpus = sphereDAO
							.getAllCPU30MinsByTimes(cpumaxtime,
									ocvm.getVmUuid());
					double CPU_total = 0;
					for (double cpu : cpus) {
						CPU_total += cpu;
					}
					BigDecimal bg = new BigDecimal(cpus.size() > 0 ? CPU_total
							/ cpus.size() : 0);
					double CPU = bg.setScale(4, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					// 计算15分钟内的内存平均值
					long memmaxtime = sphereDAO.getMaxMem4OneDay(ocvm.getVmUuid());
					List<Memory30min> memory6hs = sphereDAO
							.getAllMem30MinsByTimes(memmaxtime,
									ocvm.getVmUuid());
					double memory_total = 0;
					double memory_free = 0;
					for (Memory30min memory : memory6hs) {
						memory_total += memory.getTotalSize();
						memory_free += memory.getFreeSize();
					}
					BigDecimal bg1 = new BigDecimal(
							memory6hs.size() > 0 ? memory_total / memory6hs.size()
									: 0);
					double totalsize = bg1.setScale(4, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					BigDecimal bg2 = new BigDecimal(
							memory6hs.size() > 0 ? memory_free / memory6hs.size()
									: 0);
					double freesize = bg2.setScale(4, BigDecimal.ROUND_HALF_UP)
							.doubleValue();

					Cpu1d cpu1d = new Cpu1d();
					cpu1d.setCpuId(1);
					cpu1d.setTime(System.currentTimeMillis());
					cpu1d.setUsage(CPU);
					cpu1d.setUuid(ocvm.getVmUuid());
					if (cpus.size() > 0) {
						sphereDAO.addPerformanceData(cpu1d);
					}

					Memory1d memory6h = new Memory1d();
					memory6h.setFreeSize(freesize);
					memory6h.setTime(System.currentTimeMillis());
					memory6h.setTotalSize(totalsize);
					memory6h.setUuid(ocvm.getVmUuid());
					if (memory6hs.size() > 0) {
						sphereDAO.addPerformanceData(memory6h);
					}

				}
				if ("3".equals(type)) {
					// 计算15分钟内的cpu平均值
					long cpumaxtime = sphereDAO
							.getMaxCPUTime4TwoWeeks(ocvm.getVmUuid());
					List<Double> cpus = sphereDAO
							.getAllCPU30MinsByTimes(cpumaxtime,
									ocvm.getVmUuid());
					double CPU_total = 0;
					for (double cpu : cpus) {
						CPU_total += cpu;
					}
					BigDecimal bg = new BigDecimal(cpus.size() > 0 ? CPU_total
							/ cpus.size() : 0);
					double CPU = bg.setScale(4, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					// 计算15分钟内的内存平均值
					long memmaxtime = sphereDAO
							.getMaxMem4TwoWeeks(ocvm.getVmUuid());
					List<Memory30min> memory6hs = sphereDAO
							.getAllMem30MinsByTimes(memmaxtime,
									ocvm.getVmUuid());
					double memory_total = 0;
					double memory_free = 0;
					for (Memory30min memory : memory6hs) {
						memory_total += memory.getTotalSize();
						memory_free += memory.getFreeSize();
					}
					BigDecimal bg1 = new BigDecimal(
							memory6hs.size() > 0 ? memory_total / memory6hs.size()
									: 0);
					double totalsize = bg1.setScale(4, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					BigDecimal bg2 = new BigDecimal(
							memory6hs.size() > 0 ? memory_free / memory6hs.size()
									: 0);
					double freesize = bg2.setScale(4, BigDecimal.ROUND_HALF_UP)
							.doubleValue();

					Cpu2w cpu2w = new Cpu2w();
					cpu2w.setCpuId(1);
					cpu2w.setTime(System.currentTimeMillis());
					cpu2w.setUsage(CPU);
					cpu2w.setUuid(ocvm.getVmUuid());
					if (cpus.size() > 0) {
						sphereDAO.addPerformanceData(cpu2w);
					}

					Memory2w memory6h = new Memory2w();
					memory6h.setFreeSize(freesize);
					memory6h.setTime(System.currentTimeMillis());
					memory6h.setTotalSize(totalsize);
					memory6h.setUuid(ocvm.getVmUuid());
					if (memory6hs.size() > 0) {
						sphereDAO.addPerformanceData(memory6h);
					}
				}
				if ("4".equals(type)) {
					// 计算15分钟内的cpu平均值
					long cpumaxtime = sphereDAO
							.getMaxCPUTime4OneMonth(ocvm.getVmUuid());
					List<Double> cpus = sphereDAO
							.getAllCPU30MinsByTimes(cpumaxtime,
									ocvm.getVmUuid());
					double CPU_total = 0;
					for (double cpu : cpus) {
						CPU_total += cpu;
					}
					BigDecimal bg = new BigDecimal(cpus.size() > 0 ? CPU_total
							/ cpus.size() : 0);
					double CPU = bg.setScale(4, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					// 计算15分钟内的内存平均值
					long memmaxtime = sphereDAO
							.getMaxMem4OneMonth(ocvm.getVmUuid());
					List<Memory30min> memory6hs = sphereDAO
							.getAllMem30MinsByTimes(memmaxtime,
									ocvm.getVmUuid());
					double memory_total = 0;
					double memory_free = 0;
					for (Memory30min memory : memory6hs) {
						memory_total += memory.getTotalSize();
						memory_free += memory.getFreeSize();
					}
					BigDecimal bg1 = new BigDecimal(
							memory6hs.size() > 0 ? memory_total / memory6hs.size()
									: 0);
					double totalsize = bg1.setScale(4, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					BigDecimal bg2 = new BigDecimal(
							memory6hs.size() > 0 ? memory_free / memory6hs.size()
									: 0);
					double freesize = bg2.setScale(4, BigDecimal.ROUND_HALF_UP)
							.doubleValue();

					Cpu1m cpu1m = new Cpu1m();
					cpu1m.setCpuId(1);
					cpu1m.setTime(System.currentTimeMillis());
					cpu1m.setUsage(CPU);
					cpu1m.setUuid(ocvm.getVmUuid());
					if (cpus.size() > 0) {
						sphereDAO.addPerformanceData(cpu1m);
					}

					Memory1m memory6h = new Memory1m();
					memory6h.setFreeSize(freesize);
					memory6h.setTime(System.currentTimeMillis());
					memory6h.setTotalSize(totalsize);
					memory6h.setUuid(ocvm.getVmUuid());
					if (memory6hs.size() > 0) {
						sphereDAO.addPerformanceData(memory6h);
					}

				}
			}
			
		}
		
	}
}
