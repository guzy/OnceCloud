package com.beyondsphere.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "oc_unitprice")
public class OCUnitprice {
	
	private String priceType;
	
	private double priceValue;
	
	
	
	public OCUnitprice(String priceType, double priceValue) {
		super();
		this.priceType = priceType;
		this.priceValue = priceValue;
	}
	
	public OCUnitprice() {
		
	}
	
	@Id
	@Column(name = "price_type")
	public String getPriceType() {
		return priceType;
	}
	
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
	@Column(name = "price_value")
	public double getPriceValue() {
		return priceValue;
	}
	
	public void setPriceValue(double priceValue) {
		this.priceValue = priceValue;
	}
	
	@Override
	public String toString() {
		return "OCUnitprince [priceType=" + priceType + ", priceValue="
				+ priceValue + "]";
	}
	
}
