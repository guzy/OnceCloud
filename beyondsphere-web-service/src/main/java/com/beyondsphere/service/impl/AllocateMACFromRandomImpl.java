/**
 * @author hty
 * @time 下午3:18:25
 * @date 2014年12月5日
 */
package com.beyondsphere.service.impl;

import java.util.Random;

import com.beyondsphere.service.AllocateMACFromRandom;

public class AllocateMACFromRandomImpl implements AllocateMACFromRandom {

	public String getMac() {
		String macAddr = "00:16:3e";
		try {
			int min = 0x00;
			int max = 0x7f;
			Random random = new Random(System.currentTimeMillis());
			int addr = random.nextInt(max - min + 1) + min;
			String addrStr = String.format("%02x", addr);
			macAddr += (":" + addrStr);
			max = 0xff;
			for (int i = 0; i < 2; i++) {
				addr = random.nextInt(max - min + 1) + min;
				addrStr = String.format("%02x", addr);
				macAddr += (":" + addrStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return macAddr;
	}

}
