package com.beyondsphere.dao;

import com.beyondsphere.entity.OverView;


public interface OverViewDAO {

	public OverView getOverViewTotal();

	public boolean updateOverViewfieldNoTransaction(String filedName,
			boolean isadd) ;
}
