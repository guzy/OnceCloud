package com.oncecloud.dao.impl;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oncecloud.dao.VMDAO;
import com.oncecloud.model.VMPlatform;
import com.oncecloud.model.VMPower;
import com.oncecloud.model.VMStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:./com/oncecloud/config/application-context.xml")
@WebAppConfiguration
public class VMDAOImplTest {

	private VMDAO vmdao;

	public VMDAO getVmdao() {
		return vmdao;
	}

	@Autowired
	public void setVmdao(VMDAO vmdao) {
		this.vmdao = vmdao;
	}

	@Test
	@Ignore
	public void testPreCreateVM() {
		String vmUuid = UUID.randomUUID().toString();
		String vmPwd = "onceas";
		Integer vmUid = 1;
		String vmName = "hty";
		VMPlatform vmPlatform = VMPlatform.WINDOWS;
		String vmMac = "fd.fd.fd.ee.e0.67";
		Integer vmMem = 1024;
		Integer vmCpu = 2;
		VMPower vmPower = VMPower.POWER_RUNNING;
		VMStatus vmStatus = VMStatus.DELETED;
		Date vmCreateDate = new Date();
		String vmTplUuid = "woshimubandeuuid";
		try {
			this.getVmdao().preCreateVM(vmUuid, vmPwd, vmUid, vmName,
					vmPlatform, vmMac, vmMem, vmCpu, vmPower, vmStatus,
					vmCreateDate, vmTplUuid);
			Assert.assertTrue(this.getVmdao().preCreateVM(vmUuid, vmPwd, vmUid,
					vmName, vmPlatform, vmMac, vmMem, vmCpu, vmPower, vmStatus,
					vmCreateDate, vmTplUuid));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Ignore
	public void testGetVMByUuid() {
		try {
			Assert.assertNotNull(this.getVmdao().getVMByUuid(
					"f59c5716-6a92-4961-b113-52195f27322e"));
			Assert.assertNull(this.getVmdao().getVMByUuid(""));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Ignore
	public void testDeleteVMByUuid() {
		try {
			Assert.assertTrue(this.getVmdao().deleteVMByUuid(1,
					"f59c5716-6a92-4961-b113-52195f27322e"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Ignore
	public void testUpdateVMPower() {
		try {
			Assert.assertTrue(this.getVmdao().updateVMPowerWhenCreateVM(1, "f59c5716-6a92-4961-b113-52195f27322e",
					"onceas", VMPower.POWER_RUNNING, "fffc5716-6a92-4961-b113-52195f27322e"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testUpdateVMPowerWhenStartVM() {
		try {
			Assert.assertTrue(this.getVmdao().updateVMPower("f59c5716-6a92-4961-b113-52195f27322e", VMPower.POWER_SHUTDOWN));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testUpdateVMHost() {
		try {
			Assert.assertTrue(this.getVmdao().updateVMHost("f59c5716-6a92-4961-b113-52195f27322e", "hhhhhhhh"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testUpdateVMConfigure() {
		try {
			Assert.assertTrue(this.getVmdao().updateVMConfigure("f59c5716-6a92-4961-b113-52195f27322e", 2048, 8));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testCountVMByUserId() {
		try {
			Assert.assertEquals(1, this.getVmdao().countByUserId(1, ""));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testGetOnePageVMByUserId() {
		try {
			Assert.assertNotNull(this.getVmdao().getOnePageByUserId(1, 1, 2, ""));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
