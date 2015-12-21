package com.oncecloud.model;

public class CreateSnapshotModel {
  private String snapshotId;
  private String snapshotName;
  private String resourceUuid;
  private String resourceType;
  
public String getSnapshotId() {
	return snapshotId;
}
public void setSnapshotId(String snapshotId) {
	this.snapshotId = snapshotId;
}
public String getSnapshotName() {
	return snapshotName;
}
public void setSnapshotName(String snapshotName) {
	this.snapshotName = snapshotName;
}
public String getResourceUuid() {
	return resourceUuid;
}
public void setResourceUuid(String resourceUuid) {
	this.resourceUuid = resourceUuid;
}
public String getResourceType() {
	return resourceType;
}
public void setResourceType(String resourceType) {
	this.resourceType = resourceType;
}
  
  
}
