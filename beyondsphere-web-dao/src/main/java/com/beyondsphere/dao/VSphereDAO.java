package com.beyondsphere.dao;

import com.beyondsphere.model.VMPower;

public interface VSphereDAO {

	public boolean updateStatusOfPower(String uuid, VMPower vmPower);

	public boolean deleteVMByUuid(int userId, String uuid);

}
