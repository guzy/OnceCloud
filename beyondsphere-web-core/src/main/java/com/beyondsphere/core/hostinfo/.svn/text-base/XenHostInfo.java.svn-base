/**
 * 
 */
package com.beyondsphere.core.hostinfo;

import com.beyondsphere.core.HostInfo;

/**
 * @author luogan
 * 2015年5月19日
 * 下午5:31:21
 */
public class XenHostInfo extends HostInfo {

	@Override
	protected String command() {
		return "cat /var/lib/xend/state/host.xml | grep \"uuid=\" | awk '{split($2, a, \"\\\"\"); print a[2]}'";
	}

	@Override
	protected String valid() {
		return "";
	}

}
