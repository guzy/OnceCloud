package com.beyondsphere.service;

public interface VSphereService {

	public void shutdownVM(String uuid, Integer userId);

	public void startVM(String uuid, Integer userId);

	public void vmDestory(int userId, String uuid);

}
