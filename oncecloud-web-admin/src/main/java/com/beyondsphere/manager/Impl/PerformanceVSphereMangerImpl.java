package com.beyondsphere.manager.Impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.beyondsphere.manager.PerformanceVSphereManger;
import com.beyondsphere.vsphere.VMWareUtil;
import com.oncecloud.entity.OCHost;
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

@Component("PerformanceVSphereManger")
public class PerformanceVSphereMangerImpl implements PerformanceVSphereManger {
	@Resource
	private PerformanceVSphereService performanceVSphereService;

	public void addPerformanceData(String type) {

		List<OCHost> ocHosts = performanceVSphereService.getAllHost("vSphere");
		for (OCHost host : ocHosts) {
			if ("0".equals(type)) {
				try {
					// 创建连接
					boolean b = VMWareUtil.connect("https://" + host.getHostIP().trim()
							+ "/sdk", "root", host.getHostPwd());
					if(!b){
						
						break;
					}
					List<Map<String, Object>> maps = VMWareUtil.printHostProductDetails();
					for (Map<String, Object> map : maps) {
						System.out.println(map.get("summary.quickStats.overallCpuUsage")+"----------------------");
						double CpuUse = Double.parseDouble(String.valueOf(map.get("summary.quickStats.overallCpuUsage")== null ? "0" : map.get("summary.quickStats.overallCpuUsage")));
						double CpuMax = Double.parseDouble(String.valueOf(map.get("summary.hardware.cpuMhz")))
								* Integer.parseInt(String.valueOf(map.get("summary.hardware.numCpuCores")));
						double MemUse = Double.parseDouble(String.valueOf(map.get("summary.quickStats.overallMemoryUsage") == null ? "0" : map.get("summary.quickStats.overallMemoryUsage"))) * 1024 ;
						double MemMax = Double.parseDouble(String.valueOf(map.get("summary.hardware.memorySize")==null?"0":map.get("summary.hardware.memorySize")))/1024;
						BigDecimal bg = new BigDecimal(CpuUse / CpuMax);
						double CPU = bg.setScale(4,
								BigDecimal.ROUND_HALF_UP).doubleValue();

						Cpu30min cpu30min = new Cpu30min();
						cpu30min.setCpuId(1);
						cpu30min.setTime(System.currentTimeMillis());
						cpu30min.setUsage(CPU);
						cpu30min.setUuid(host.getHostUuid());
						performanceVSphereService.addPerformanceData(cpu30min);

						Memory30min memory30min = new Memory30min();
						memory30min.setFreeSize(MemMax - MemUse);
						memory30min.setTime(System.currentTimeMillis());
						memory30min.setTotalSize(MemMax);
						memory30min.setUuid(host.getHostUuid());
						performanceVSphereService
								.addPerformanceData(memory30min);
//						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if ("1".equals(type)) {
				// 计算15分钟内的cpu平均值
				long cpumaxtime = performanceVSphereService
						.getMaxCPUTime4SixHours(host.getHostUuid());
				List<Double> cpus = performanceVSphereService
						.getAllCPU30MinsByTimes(cpumaxtime,
								host.getHostUuid());
				double CPU_total = 0;
				for (double cpu : cpus) {
					CPU_total += cpu;
				}
				BigDecimal bg = new BigDecimal(cpus.size() > 0 ? CPU_total
						/ cpus.size() : 0);
				double CPU = bg.setScale(4, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				// 计算15分钟内的内存平均值
				long memmaxtime = performanceVSphereService.getMaxMem4SixHours(host.getHostUuid());
				List<Memory30min> memory6hs = performanceVSphereService
						.getAllMem30MinsByTimes(memmaxtime,
								host.getHostUuid());
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
				cpu6h.setUuid(host.getHostUuid());
				if (cpus.size() > 0) {
					performanceVSphereService.addPerformanceData(cpu6h);
				}

				Memory6h memory6h = new Memory6h();
				memory6h.setFreeSize(freesize);
				memory6h.setTime(System.currentTimeMillis());
				memory6h.setTotalSize(totalsize);
				memory6h.setUuid(host.getHostUuid());
				if (memory6hs.size() > 0) {
					performanceVSphereService.addPerformanceData(memory6h);
				}

			}
			if ("2".equals(type)) {
				// 计算15分钟内的cpu平均值
				long cpumaxtime = performanceVSphereService
						.getMaxCPUTime4OneDay(host.getHostUuid());
				List<Double> cpus = performanceVSphereService
						.getAllCPU30MinsByTimes(cpumaxtime,
								host.getHostUuid());
				double CPU_total = 0;
				for (double cpu : cpus) {
					CPU_total += cpu;
				}
				BigDecimal bg = new BigDecimal(cpus.size() > 0 ? CPU_total
						/ cpus.size() : 0);
				double CPU = bg.setScale(4, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				// 计算15分钟内的内存平均值
				long memmaxtime = performanceVSphereService.getMaxMem4OneDay(host.getHostUuid());
				List<Memory30min> memory6hs = performanceVSphereService
						.getAllMem30MinsByTimes(memmaxtime,
								host.getHostUuid());
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
				cpu1d.setUuid(host.getHostUuid());
				if (cpus.size() > 0) {
					performanceVSphereService.addPerformanceData(cpu1d);
				}

				Memory1d memory6h = new Memory1d();
				memory6h.setFreeSize(freesize);
				memory6h.setTime(System.currentTimeMillis());
				memory6h.setTotalSize(totalsize);
				memory6h.setUuid(host.getHostUuid());
				if (memory6hs.size() > 0) {
					performanceVSphereService.addPerformanceData(memory6h);
				}

			}
			if ("3".equals(type)) {
				// 计算15分钟内的cpu平均值
				long cpumaxtime = performanceVSphereService
						.getMaxCPUTime4TwoWeeks(host.getHostUuid());
				List<Double> cpus = performanceVSphereService
						.getAllCPU30MinsByTimes(cpumaxtime,
								host.getHostUuid());
				double CPU_total = 0;
				for (double cpu : cpus) {
					CPU_total += cpu;
				}
				BigDecimal bg = new BigDecimal(cpus.size() > 0 ? CPU_total
						/ cpus.size() : 0);
				double CPU = bg.setScale(4, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				// 计算15分钟内的内存平均值
				long memmaxtime = performanceVSphereService
						.getMaxMem4TwoWeeks(host.getHostUuid());
				List<Memory30min> memory6hs = performanceVSphereService
						.getAllMem30MinsByTimes(memmaxtime,
								host.getHostUuid());
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
				cpu2w.setUuid(host.getHostUuid());
				if (cpus.size() > 0) {
					performanceVSphereService.addPerformanceData(cpu2w);
				}

				Memory2w memory6h = new Memory2w();
				memory6h.setFreeSize(freesize);
				memory6h.setTime(System.currentTimeMillis());
				memory6h.setTotalSize(totalsize);
				memory6h.setUuid(host.getHostUuid());
				if (memory6hs.size() > 0) {
					performanceVSphereService.addPerformanceData(memory6h);
				}
			}
			if ("4".equals(type)) {
				// 计算15分钟内的cpu平均值
				long cpumaxtime = performanceVSphereService
						.getMaxCPUTime4OneMonth(host.getHostUuid());
				List<Double> cpus = performanceVSphereService
						.getAllCPU30MinsByTimes(cpumaxtime,
								host.getHostUuid());
				double CPU_total = 0;
				for (double cpu : cpus) {
					CPU_total += cpu;
				}
				BigDecimal bg = new BigDecimal(cpus.size() > 0 ? CPU_total
						/ cpus.size() : 0);
				double CPU = bg.setScale(4, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				// 计算15分钟内的内存平均值
				long memmaxtime = performanceVSphereService
						.getMaxMem4OneMonth(host.getHostUuid());
				List<Memory30min> memory6hs = performanceVSphereService
						.getAllMem30MinsByTimes(memmaxtime,
								host.getHostUuid());
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
				cpu1m.setUuid(host.getHostUuid());
				if (cpus.size() > 0) {
					performanceVSphereService.addPerformanceData(cpu1m);
				}

				Memory1m memory6h = new Memory1m();
				memory6h.setFreeSize(freesize);
				memory6h.setTime(System.currentTimeMillis());
				memory6h.setTotalSize(totalsize);
				memory6h.setUuid(host.getHostUuid());
				if (memory6hs.size() > 0) {
					performanceVSphereService.addPerformanceData(memory6h);
				}

			}
		}
	}
}
