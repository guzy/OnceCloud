package com.beyondsphere.helper;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.Xen.API.Connection;
import org.Xen.API.VM;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.beyondsphere.core.constant.Constant;
import com.beyondsphere.dao.ImageDAO;
import com.beyondsphere.dao.VDIDAO;
import com.beyondsphere.entity.Image;

/**
 * @author hty
 */
@Component
public class PreallocateHelper implements Runnable {
	private final Logger logger = Logger.getLogger(PreallocateHelper.class);
	private AtomicBoolean ready = new AtomicBoolean(true);
	@Resource
	private ImageDAO imageDAO;
	@Resource
	private VDIDAO vdiDAO;
	@Resource
	private Constant constant;

	private boolean getReady() {
		return ready.get();
	}

	public void run() {
		while (this.getReady()) {
			preAllocateVDIs();
			waitForNextPeriod();
		}
	}

	private void preAllocateVDIs() {
		List<Image> imageList = imageDAO.getSystemImage();
		if (imageList != null) {
			for (Image image : imageList) {
				String imageUuid = image.getImageUuid();
				int preAllocate = image.getPreAllocate();
				String poolUuid = image.getPoolUuid();
				preAllocateVDIForSpecifiedTemplate(imageUuid, preAllocate,
						poolUuid);
			}
		}
	}

	/**
	 * 根据预留资源和实际资源，初期只执行顺序执行，拷贝一台模板的时间大约为3分钟
	 * 
	 * @param imageUuid
	 *            , preAllocate, poolUuid
	 */
	private void preAllocateVDIForSpecifiedTemplate(String imageUuid,
			int preAllocate, String poolUuid) {
		logger.info("Start preallocating VDIs for Template " + imageUuid + " ");
		int remain = getDBTemplateReservedInfo(imageUuid);
		if (checkValid(preAllocate) && checkDBAccessByResult(remain)) {
			int needsVMsNumber = needsVMsNumber(preAllocate, remain);
			while (needsVMsNumber > 0) {
				String vdiUuid = UUID.randomUUID().toString();
				if (sucessful(preCreateVMFromTemplate(imageUuid, vdiUuid,
						poolUuid))) {
					// 如果此时失败，会出现VDI创建成功，但不可见状态，还需要回滚保障一致性
					// 还需要删除VDI函数，以后支持
					logger.info("Create VDI " + vdiUuid + " Sucessful");
					updateDBTemplateReservedInfo(imageUuid, vdiUuid);
				} else {
					errorReport("Failed to Create VDI " + vdiUuid);
				}
				needsVMsNumber--;
			}
		} else {
			errorReport("Ignore, the reserved value should greater than 1");
		}
	}

	/**
	 * 如果此时失败，会出现VDI创建成功，但不可见状态，还需要回滚保障一致性
	 * 
	 * @param imageUuid
	 *            , vdiUuid
	 */
	private void updateDBTemplateReservedInfo(String imageUuid, String vdiUuid) {
		vdiDAO.saveVDI(imageUuid, vdiUuid);
	}

	private boolean preCreateVMFromTemplate(String tmpUuid, String vmUuid,
			String poolUuid) {
		try {
			Connection conn = constant.getConnectionFromPool(poolUuid);
			VM.cloneSystemVDI(conn, tmpUuid, vmUuid);
		} catch (Exception e) {
			e.printStackTrace();
			errorReport(e.getMessage());
			return false;
		}
		return true;
	}

	private int needsVMsNumber(int experted, int remain) {
		int diff = experted - remain;
		return (diff > 0) ? diff : 0;
	}

	/**
	 * 根据UUID查询预留的虚拟机数量
	 * 
	 * @param imageUuid
	 */
	private int getDBTemplateReservedInfo(String imageUuid) {
		return vdiDAO.countFreeVDI(imageUuid);
	}

	private void waitForNextPeriod() {
		try {
			Thread.sleep(60000 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void errorReport(String msg) {
		logger.error(msg);
	}

	/**
	 * 小于1的赋值为不合法约束
	 * 
	 * @param reserved
	 */
	private boolean checkValid(int reserved) {
		return (reserved < 1) ? false : true;
	}

	private boolean sucessful(boolean result) {
		return (result == true);
	}

	/**
	 * 当数据库不可连接时，约束其返回值是-1
	 * 
	 * @param num
	 */
	private boolean checkDBAccessByResult(int num) {
		return (num == -1) ? false : true;
	}
	
	public void preallocate() {
		Thread demo = new Thread(this);
		demo.start();
	}
	
}
