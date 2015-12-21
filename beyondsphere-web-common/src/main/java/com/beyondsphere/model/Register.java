package com.beyondsphere.model;

import java.util.ArrayList;

public class Register {

	private Object object;
	private String metodName;
	private ArrayList<Class<?>> paramsList;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getMetodName() {
		return metodName;
	}

	public void setMetodName(String metodName) {
		this.metodName = metodName;
	}

	public ArrayList<Class<?>> getParamsList() {
		return paramsList;
	}

	public void setParamsList(ArrayList<Class<?>> paramsList) {
		this.paramsList = paramsList;
	}

}
