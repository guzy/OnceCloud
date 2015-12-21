package com.oncecloud.controller.action;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.oncecloud.manager.PerformanceVSphereManger;

@Component
public class MyTask {
	@Resource
	PerformanceVSphereManger manger;
	@Scheduled(cron="0/30 * * * * ? ") //间隔30秒执行
	public void taskCycle430Seconds(){
	    manger.addPerformanceData("0");
	}
    @Scheduled(cron="0 0/5 * * * ? ") //间隔5分钟执行
    public void taskCycle4SixHours(){
        manger.addPerformanceData("1");
    }
    @Scheduled(cron="0 2/15 * * * ? ") //间隔15分钟执行
    public void taskCycle4OneDays(){
        manger.addPerformanceData("2");
    }
    @Scheduled(cron="0 3 0/4 * * ? ") //间隔4小时钟执行
    public void taskCycle4TwoWeeks(){
        manger.addPerformanceData("3");
    }
    @Scheduled(cron="0 4 12 * * ? ") //每天中午12点执行
    public void taskCycle4OneMonth(){
        manger.addPerformanceData("4");
    }
    
}