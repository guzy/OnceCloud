/**
 * @author hty
 * @time 上午11:00:22
 * @date 2014年12月8日
 */
package com.beyondsphere.service.impl;

import org.springframework.stereotype.Component;

import org.Xen.API.Connection;
import org.Xen.API.VM;
import com.beyondsphere.service.VlanService;

@Component("VlanService")
public class VlanServiceImpl implements VlanService {

	public boolean vmBindVlan(String uuid, String vlanTag, Connection c) {
		try {
			VM vm = VM.getByUuid(c, uuid);
			vm.setTag(c, vm.getVIFs(c).iterator().next(),
					vlanTag);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
