package com.beyondsphere.model;

public class CreateRuleModel {
	private String ruleName;
	private int rulePriority;
	private String ruleProtocol;
	private int ruleSport;
	private int ruleEport;
	private String ruleIp;
	private String ruleId;
	private String firewallId;
	
	public String getRuleName() {
		return ruleName;
	}
	
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	public int getRulePriority() {
		return rulePriority;
	}
	
	public void setRulePriority(int rulePriority) {
		this.rulePriority = rulePriority;
	}
	
	public String getRuleProtocol() {
		return ruleProtocol;
	}
	
	public void setRuleProtocol(String ruleProtocol) {
		this.ruleProtocol = ruleProtocol;
	}
	
	public int getRuleSport() {
		return ruleSport;
	}
	
	public void setRuleSport(int ruleSport) {
		this.ruleSport = ruleSport;
	}
	
	public int getRuleEport() {
		return ruleEport;
	}
	
	public void setRuleEport(int ruleEport) {
		this.ruleEport = ruleEport;
	}
	
	public String getRuleIp() {
		return ruleIp;
	}
	
	public void setRuleIp(String ruleIp) {
		this.ruleIp = ruleIp;
	}
	
	public String getRuleId() {
		return ruleId;
	}
	
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getFirewallId() {
		return firewallId;
	}
	
	public void setFirewallId(String firewallId) {
		this.firewallId = firewallId;
	}
}
