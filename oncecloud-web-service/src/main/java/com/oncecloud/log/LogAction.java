package com.oncecloud.log;


import com.oncecloud.constants.LogConstant;

public class LogAction {
	
	//创建
	public final static Integer CREATE = LogConstant.logAction.Create.ordinal();
	
	//销毁
	public final static Integer DESTORY = LogConstant.logAction.Destroy.ordinal();
	
	//删除
	public final static Integer DELETE = LogConstant.logAction.Delete.ordinal();
	
	//启动
	public final static Integer START = LogConstant.logAction.Start.ordinal();
	
	//关闭
	public final static Integer SHUTDOWN = LogConstant.logAction.ShutDown.ordinal();
	
	//暂停
	public final static Integer PAUSE = LogConstant.logAction.Pause.ordinal();
	
	//恢复
	public final static Integer RESTARTED = LogConstant.logAction.Restarted.ordinal();
	
	//绑定
	public final static Integer BINDING = LogConstant.logAction.Binding.ordinal();
	
	//解绑
	public final static Integer UNBINDING = LogConstant.logAction.Unbundling.ordinal();
	
	//加载
	public final static Integer LOAD = LogConstant.logAction.Load.ordinal();
	
	//卸载
	public final static Integer UNLOAD = LogConstant.logAction.Unload.ordinal();
	
	//调整
	public final static Integer ADJUST = LogConstant.logAction.Adjust.ordinal();
	
	//转换
	public final static Integer CHANGE = LogConstant.logAction.Change.ordinal();
	
	//加入
	public final static Integer JOIN = LogConstant.logAction.Join.ordinal();
	
	//离开
	public final static Integer LEAVE = LogConstant.logAction.Leave.ordinal();
	
	//连接
	public final static Integer CONNECT = LogConstant.logAction.Connect.ordinal();
	
	//更新
	public final static Integer UPDATE = LogConstant.logAction.Update.ordinal();
	
	//回滚
	public final static Integer ROLLBACK = LogConstant.logAction.Rollback.ordinal();
	
	//启用
	public final static Integer ENABLE = LogConstant.logAction.Enable.ordinal();
	
	//禁用
	public final static Integer DISABLE = LogConstant.logAction.Disable.ordinal();
	
	//重启
	public final static Integer RESTART = LogConstant.logAction.Restart.ordinal();
	
	//增加
	public final static Integer ADD = LogConstant.logAction.Add.ordinal();
	
	//移除
	public final static Integer REMOVE = LogConstant.logAction.Remove.ordinal();
	
	//上传
	public final static Integer UPLOAD = LogConstant.logAction.Upload.ordinal();
	
	//登陆
	public final static Integer LOGIN = LogConstant.logAction.Login.ordinal();
	
	//退出
	public final static Integer EXIT = LogConstant.logAction.Exit.ordinal();
	
	
}
