package com.oncecloud.model;

import com.oncecloud.helper.MessageSourceHelper;

public class AlarmRuleConstant{
	
	public enum RuleName{
		CPURate("alarm.rule.cpurate"),  //CPU利用率
		MemoryRate("alarm.rule.memoryrate"), //内存利用率
		DiskRate("alarm.rule.diskrate"), //磁盘使用率
		VifIn("alarm.rule.vifin"), //网卡进流量
		VifOut("alarm.rule.vifout"); //网卡出流量

		private String key;
		private RuleName(String key){
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
	public enum RuleTerm{
		SmallThan("alarm.ruleTerm.SmallThan"), //<
		BigThan("alarm.ruleTerm.BigThan");  //>
		
		private String key;
		private RuleTerm(String key){
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
	}
}