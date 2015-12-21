package com.oncecloud.log;

import com.oncecloud.constants.LogConstant;

public class LogStatus {
	
	//操作失败
	public final static Integer FAILED = LogConstant.logStatus.Failed.ordinal();
	
	//操作成功
	public final static Integer SUCCESS = LogConstant.logStatus.Succeed.ordinal();
	
	//执行中
	public final static Integer EXECUTION = LogConstant.logStatus.Execution.ordinal();
	
	//等待中
	public final static Integer WAITING = LogConstant.logStatus.Waiting.ordinal();
	
	//全部
	public final static Integer ALL = LogConstant.logStatus.All.ordinal();
}
