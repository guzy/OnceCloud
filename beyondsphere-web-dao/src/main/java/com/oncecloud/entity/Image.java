/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.oncecloud.model.ImagePlatform;
import com.oncecloud.model.ImageStatus;

@Entity
@Table(name = "oc_image")
public class Image {
	
	private String imageUuid;
	private String imageName;
	private String imagePwd;
	private Integer imageUID;
	private Integer imageDisk;
	private ImagePlatform imagePlatform;
	private ImageStatus imageStatus;
	private String poolUuid;
	private String imageDesc;
	private Date createDate;
	private String hostUuid;
	private String referenceUuid;
	private Integer preAllocate;
	
	public Image(){}

	public Image(String imageUuid, String imageName, String imagePwd,
			Integer imageUID, Integer imageDisk, ImagePlatform imagePlatform,
			ImageStatus imageStatus, String poolUuid, String imageDesc, String hostUuid,
			Date createDate, String referenceUuid) {
		super();
		this.imageUuid = imageUuid;
		this.imageName = imageName;
		this.imagePwd = imagePwd;
		this.imageUID = imageUID;
		this.imageDisk = imageDisk;
		this.imagePlatform = imagePlatform;
		this.imageStatus = imageStatus;
		this.poolUuid = poolUuid;
		this.imageDesc = imageDesc;
		this.hostUuid = hostUuid;
		this.createDate = createDate;
		this.referenceUuid = referenceUuid;
	}

	/**
	 * 获取image的密码
	 * @return
	 */
	@Column(name = "image_pwd")
	public String getImagePwd() {
		return imagePwd;
	}

	/**
	 * 设置image的密码
	 * @param imagePwd
	 */
	public void setImagePwd(String imagePwd) {
		this.imagePwd = imagePwd;
	}

	/**
	 * 获取image创建时间
	 * @return
	 */
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置image创建时间
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取image的描述信息
	 * @return
	 */
	@Column(name = "image_desc")
	public String getImageDesc() {
		return imageDesc;
	}

	/**
	 * 设置image的描述信息
	 * @param imageDesc
	 */
	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}

	/**
	 * 获取image的uuid，主键
	 * @return
	 */
	@Id
	@Column(name = "image_uuid")
	public String getImageUuid() {
		return imageUuid;
	}

	/**
	 * 设置image的uuid，主键
	 * @param imageUuid
	 */
	public void setImageUuid(String imageUuid) {
		this.imageUuid = imageUuid;
	}

	/**
	 * 获取image的名称
	 * @return
	 */
	@Column(name = "image_name")
	public String getImageName() {
		return imageName;
	}

	/**
	 * 设置image的名称
	 * @param imageName
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * 获取image的拥有者id
	 * @return
	 */
	@Column(name = "image_uid")
	public Integer getImageUID() {
		return imageUID;
	}

	/**
	 * 设置image的拥有者id
	 * @param imageUID
	 */
	public void setImageUID(Integer imageUID) {
		this.imageUID = imageUID;
	}

	/**
	 * 获取image的硬盘大小，单位为G
	 * @return
	 */
	@Column(name = "image_disk")
	public Integer getImageDisk() {
		return imageDisk;
	}

	/**
	 * 设置image的硬盘大小，单位为G
	 * @param imageDisk
	 */
	public void setImageDisk(Integer imageDisk) {
		this.imageDisk = imageDisk;
	}

	/**
	 * 获取image的类型
	 * @return
	 */
	@Column(name = "image_platform")
	public ImagePlatform getImagePlatform() {
		return imagePlatform;
	}

	/**
	 * 设置image的类型
	 * @param imagePlatform
	 */
	public void setImagePlatform(ImagePlatform imagePlatform) {
		this.imagePlatform = imagePlatform;
	}

	/**
	 * 获取image是否被删除
	 * @return
	 */
	@Column(name = "image_status")
	public ImageStatus getImageStatus() {
		return imageStatus;
	}

	/**
	 * 设置iamge是否被删除
	 * @param imageStatus
	 */
	public void setImageStatus(ImageStatus imageStatus) {
		this.imageStatus = imageStatus;
	}

	/**
	 * 获取image所在服务器的uuid
	 * @return
	 */
	@Column(name = "host_uuid")
	public String getHostUuid() {
		return hostUuid;
	}

	/**
	 * 设置image所在服务器的uuid
	 * @param hostUuid
	 */
	public void setHostUuid(String hostUuid) {
		this.hostUuid = hostUuid;
	}

	/**
	 * 获取image所在资源池的uuid
	 * @return
	 */
	@Column(name = "pool_uuid")
	public String getPoolUuid() {
		return poolUuid;
	}

	/**
	 * 设置image所在iyuanchi的uuid
	 * @param poolUuid
	 */
	public void setPoolUuid(String poolUuid) {
		this.poolUuid = poolUuid;
	}

	/**
	 * 获取共享image所依赖的image的uuid
	 * @return
	 */
	@Column(name = "reference_uuid")
	public String getReferenceUuid() {
		return referenceUuid;
	}

	/**
	 * 设置共享image所依赖的image的uuid
	 * @param referenceUuid
	 */
	public void setReferenceUuid(String referenceUuid) {
		this.referenceUuid = referenceUuid;
	}

	/**
	 * 获取全拷贝的数量
	 * @return
	 */
	@Column(name = "pre_allocate")
	public Integer getPreAllocate() {
		return preAllocate;
	}

	/**
	 * 设置全拷贝的数量
	 * @param preAllocate
	 */
	public void setPreAllocate(Integer preAllocate) {
		this.preAllocate = preAllocate;
	}
	
	@Override
	public String toString() {
		return "Image [imageUuid=" + imageUuid + ", imageName=" + imageName
				+ ", imagePwd=" + imagePwd + ", imageUID=" + imageUID
				+ ", imageDisk=" + imageDisk + ", imagePlatform="
				+ imagePlatform + ", imageStatus=" + imageStatus
				+ ", poolUuid=" + poolUuid + ", imageDesc=" + imageDesc
				+ ", createDate=" + createDate + ", hostUuid=" + hostUuid
				+ "]";
	}
}
