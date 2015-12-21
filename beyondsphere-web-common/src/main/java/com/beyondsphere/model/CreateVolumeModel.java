package com.beyondsphere.model;

public class CreateVolumeModel {
	private String volumeUuid;
	private String volumeName;
	private int volumeSize;
	
	public String getVolumeUuid() {
		return volumeUuid;
	}
	
	public void setVolumeUuid(String volumeUuid) {
		this.volumeUuid = volumeUuid;
	}
	
	public String getVolumeName() {
		return volumeName;
	}
	
	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}
	
	public int getVolumeSize() {
		return volumeSize;
	}
	
	public void setVolumeSize(int volumeSize) {
		this.volumeSize = volumeSize;
	}
}
