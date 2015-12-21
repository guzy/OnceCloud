package com.oncecloud.dao;

import com.oncecloud.entity.OverView;


public interface OverViewDAO {

	public OverView getOverViewTotal();

	public boolean updateOverViewfieldNoTransaction(String filedName,
			boolean isadd) ;
}
