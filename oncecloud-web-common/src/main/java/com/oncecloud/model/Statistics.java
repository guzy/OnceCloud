package com.oncecloud.model;

import java.util.Date;

/**
 * @author hty
 * 
 */
public interface Statistics {

	public abstract String getUuid();

	public abstract String getName();

	public abstract Date getDate();

	public abstract int getStatus();

	public abstract void setName(String name);

	public abstract void setDate(Date date);

	public abstract void setUuid(String uuid);

	public abstract void setStatus(int status);

}
