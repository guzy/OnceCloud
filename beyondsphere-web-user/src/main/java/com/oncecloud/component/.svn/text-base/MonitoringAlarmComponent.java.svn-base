package com.beyondsphere.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beyondsphere.service.AlarmLogService;

@Component
public class MonitoringAlarmComponent {

	private AlarmLogService alarmLogService;

	@Autowired
	public MonitoringAlarmComponent(AlarmLogService alarmLogService1) {
		this.alarmLogService = alarmLogService1;
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						alarmLogService.monitorAlarmRules();
						Thread.sleep(120000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

}
