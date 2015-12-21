package com.oncecloud.model;

/**
 * @author hty
 * 
 */
public class CaptchaNumber {
	private Integer first;
	private Integer second;
	private Integer total;

	public CaptchaNumber(Integer first, Integer second, Integer total) {
		this.first = first;
		this.second = second;
		this.total = total;
	}

	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
