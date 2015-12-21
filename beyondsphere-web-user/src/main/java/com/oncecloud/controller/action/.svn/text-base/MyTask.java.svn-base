package com.beyondsphere.controller.action;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.beyondsphere.service.PerformanceVSphereService;

@Component
public class MyTask {
	@Resource
	PerformanceVSphereService vSphereService;
	@Scheduled(cron="0/30 * * * * ? ") //间隔30秒执行
	public void taskCycle430Seconds(){
	    vSphereService.addPerformanceData("0");
	}
    @Scheduled(cron="0 0/5 * * * ? ") //间隔5分钟执行
    public void taskCycle4SixHours(){
        vSphereService.addPerformanceData("1");
    }
    @Scheduled(cron="0 2/15 * * * ? ") //间隔15分钟执行
    public void taskCycle4OneDays(){
        vSphereService.addPerformanceData("2");
    }
    @Scheduled(cron="0 3 0/4 * * ? ") //间隔4小时钟执行
    public void taskCycle4TwoWeeks(){
        vSphereService.addPerformanceData("3");
    }
    @Scheduled(cron="0 4 12 * * ? ") //每天中午12点执行
    public void taskCycle4OneMonth(){
        vSphereService.addPerformanceData("4");
    }
    
}