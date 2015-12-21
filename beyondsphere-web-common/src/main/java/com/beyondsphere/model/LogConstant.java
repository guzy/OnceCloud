/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.beyondsphere.model;

import com.beyondsphere.helper.MessageSourceHelper;


public class LogConstant {
	
	public static enum logObject {		
		Instance("log.object.Instance"), //云主机
		Volume("log.object.Volume"), //硬盘
		Snapshot("log.object.Snapshot"), //备份
		Images("log.object.Images"),  //映像 
		Snapshotseries("log.object.Snapshotseries"), //备份连
		User("log.object.User"), //用户
		Pool("log.object.Pool"), //资源池
		Ha("log.object.OCHa"),//高可用
		Storage("log.object.Storage"), //存储
		Rack("log.object.Rack"), //机架
		Host("log.object.Host"), //服务器
		Network("log.object.Network"), //网络
		Address("log.object.Address"), //地址
		AddressPool("log.object.AddressPool"), //地址池
		DataCenter("log.object.DataCenter"),  //数据中心
		MasterNode("log.object.MasterNode"), //主节点
		Role("log.object.Role"), //角色
        ISO("log.object.ISO"),  //ISO
        Router("log.object.Router"), //路由器
		AlarmLog("log.object.Alarm"),//警告
		AlarmRuleLog("log.object.Rule"),//警告规则
		Log("log.object.log");//日志
		
		private String key;
		private logObject(String key){
			this.key = key;
		}
		
		public String getKey(){
			return this.key;
		}
		MessageSourceHelper messageSourceHelper = new MessageSourceHelper();
		
		@Override
		public String toString() {
			return messageSourceHelper.getMessage(this.key);			
		}
	};
	

	public static enum logAction {
		Create("log.logAction.Create"),  //创建
		Destroy("log.logAction.Destroy"),  //销毁
		Delete("log.logAction.Delete"), //删除  
		Start("log.logAction.Start"), //启动 
		ShutDown("log.logAction.ShutDown"),  //关闭 
		Restarted("log.logAction.Restart"),  //恢复
		Binding("log.logAction.Binding"), //绑定
		Unbundling("log.logAction.Unbundling"), //解绑 
		Load("log.logAction.Load"), //加载 
		Unload("log.logAction.Unload"), //卸载 
		Adjust("log.logAction.Adjust"), //调整
		Change("log.logAction.Change"), //转换
		Join("log.logAction.Join"), //加入 
		Leave("log.logAction.Leave"), //离开 
		Connect("log.logAction.Connect"), //连接 
		Update("log.logAction.Update"), //更新
		Rollback("log.logAction.Rollback"), //回滚 
		Enable("log.logAction.Enable"), //启用
		Disable("log.logAction.Disable"), //禁用 
		Restart("log.logAction.Restart"),  //重启
		Add("log.logAction.Add"), //添加
		Remove("log.logAction.Remove"), //移除 
		Upload("log.logAction.Upload"), //上传
		Login("log.logAction.Login"), //登陆
		Exit("log.logAction.Exit"),//退出
		Export("log.logAction.export"),//导出
		Pause("log.logAction.Psuse");  //暂停
		private String key;
		private logAction(String key){
			this.key = key;
		}
		
		public String getKey(){
			return this.key;
		}
		MessageSourceHelper messageSourceHelper = new MessageSourceHelper();
		@Override
		public String toString() {			
			return messageSourceHelper.getMessage(this.key);
			
		}
	};

	public static enum logStatus {
		
		Failed("log.logStatus.Failed"), //失败
		Succeed("log.logStatus.Succeed"), //成功 
		Execution("log.logStatus.Execution"), //执行中 
		Waiting("log.logStatus.Waiting"), //等待中 
		All("log.logStatus.All"); //全部	
		
		private String key;
		private logStatus(String key){
			this.key = key;
		}
		
		public String getKey(){
			return this.key;
		}
		MessageSourceHelper messageSourceHelper = new MessageSourceHelper();
		@Override
		public String toString() {			
			return messageSourceHelper.getMessage(this.key);			
		}
	};
}