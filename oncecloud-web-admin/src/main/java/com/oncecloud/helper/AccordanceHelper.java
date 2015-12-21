package com.oncecloud.helper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.VM;
import org.springframework.stereotype.Component;

import com.oncecloud.core.constant.Constant;
import com.oncecloud.dao.VMDAO;
import com.oncecloud.entity.OCPool;
import com.oncecloud.entity.OCVM;
import com.oncecloud.model.VMStatus;
import com.oncecloud.service.PoolService;
import com.oncecloud.service.VMService;


/**
 * @author hty
 * 
 */
@Component
public class AccordanceHelper implements Runnable {

	private AtomicInteger TIMER = new AtomicInteger(1000 * 30);
	private AtomicBoolean flag = new AtomicBoolean(false);

	@Resource
	private PoolService poolService;
	@Resource
	private VMDAO vmDAO;
	@Resource
	private VMService vmService;
	@Resource
	private Constant constant;


	public boolean isFlag() {
		return flag.get();
	}

	public void setFlag(boolean bool) {
		flag.set(bool);
	}

	private Integer getTIMER() {
		return TIMER.get();
	}

	public void setTIMER(Integer tIMER) {
		TIMER.set(tIMER);
	}

	/**
	 * 1.遍历所有存活资源池 1'.获取所有虚拟机持久化数据对象 2.获取Master节点后连接xend
	 * 3.获取并遍历该池中所有虚拟机的record信息 4.从持久化数据对象列表中移除该虚拟机 5.判断该虚拟机的持久化数据对象的状态是否为0
	 * 6.若为0，则销毁该虚拟机，并且删除持久化对象 7.若不为0，校验持久化数据对象的host信息和power信息，不一致则更新
	 * 8.若该列表中并没有这个虚拟机信息，则销毁该虚拟机 9.执行结束后，删除列表内剩余的持久化数据对象
	 */
	public void run() {
		while (this.isFlag()) {
			List<OCPool> poolList = poolService.getPoolList();
			List<String> vmList = vmDAO.getAllList();
			for (OCPool ocPool : poolList) {
				if (ocPool.getPoolMaster() == null) {
					continue;
				}
				try {
					Connection conn = constant.getConnectionFromPool(ocPool.getPoolUuid());
					Map<VM, VM.Record> map = VM.getAllRecords(conn);
					for (VM thisVM : map.keySet()) {
						VM.Record vmRecord = map.get(thisVM);
						if (!vmRecord.isControlDomain
								&& !vmRecord.isATemplate) {
							String name = vmRecord.nameLabel;
							if (name.contains("i-")) {
								String vmUuid = vmRecord.uuid;
								OCVM ocvm = vmService.getVm(vmUuid);
								if (vmList.contains(vmUuid)) {
									vmList.remove(vmUuid);
									if (ocvm.getVmStatus() == VMStatus.DELETED) {
										try {
											thisVM.hardShutdown(conn);
											thisVM.destroy(conn, false);
										} catch (Exception e) {
											e.printStackTrace();
											continue;
										}
										vmDAO.deleteVM(ocvm, true);
									} else {
										int power = vmRecord.powerState
												.toString().equals(
														"Running") ? 1 : 0;
										String hostUuid = vmRecord.residentOn
												.toWireString();
										if (ocvm.getVmHostUuid() == null
												|| !ocvm.getVmHostUuid()
														.equals(hostUuid)) {
											vmService.updateHostUuidOfVM(vmUuid, hostUuid);
										}
										if (ocvm.getVmPower().ordinal() != power) {
											vmService.updateStatusOfPower(vmUuid, power);
										}
									}
								} else {
									try {
										thisVM.hardShutdown(conn);
										thisVM.destroy(conn, false);
									} catch (Exception e) {
										e.printStackTrace();
										continue;
									}
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					this.setFlag(false);
					continue;
				}
			}
			if (vmList.size() > 0) {
				for (String vmUuid : vmList) {
					vmDAO.deleteVM(vmService.getVm(vmUuid), true);
				}
			}
			try {
				Thread.sleep(this.getTIMER());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void keepAccordance() {
		Thread demo = new Thread(this);
		demo.start();
	}

}