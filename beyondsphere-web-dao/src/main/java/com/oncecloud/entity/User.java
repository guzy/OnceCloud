/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.oncecloud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.oncecloud.model.UserLevel;
import com.oncecloud.model.UserStatus;


@Entity
@Table(name = "oc_user")
public class User {
	
	private Integer userId;
	private String userName;
	private String userPass;
	private String userMail;
	private String userPhone;
	private String userCompany;
	private UserLevel userLevel;
	private UserStatus userStatus;
	private Date userDate;
	private String userAllocate;
	private Double userBalance;
	private Integer userVoucher;
	private Integer userRoleId;
	//
	private String userLocation;
	private String userActiveLocation;
	private String lng;
	private String lat;
	public User() {
	}

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_pass")
	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	@Column(name = "user_mail")
	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	@Column(name = "user_phone")
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@Column(name = "user_company")
	public String getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "user_level")
	public UserLevel getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "user_status")
	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	@Column(name = "user_date")
	public Date getUserDate() {
		return userDate;
	}

	public void setUserDate(Date userDate) {
		this.userDate = userDate;
	}

	@Column(name = "user_allocate")
	public String getUserAllocate() {
		return userAllocate;
	}

	public void setUserAllocate(String userAllocate) {
		this.userAllocate = userAllocate;
	}

	@Column(name = "user_balance")
	public Double getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(Double userBalance) {
		this.userBalance = userBalance;
	}

	@Column(name = "user_voucher")
	public Integer getUserVoucher() {
		return userVoucher;
	}

	public void setUserVoucher(Integer userVoucher) {
		this.userVoucher = userVoucher;
	}

	@Column(name = "user_roleid")
	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	@Column(name = "user_location")
	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	@Column(name = "user_activelocation")
	public String getUserActiveLocation() {
		return userActiveLocation;
	}

	public void setUserActiveLocation(String userActiveLocation) {
		this.userActiveLocation = userActiveLocation;
	}
	
	@Column(name = "user_lng")
	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	@Column(name = "user_lat")
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}


	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", userPass=" + userPass + ", userMail=" + userMail
				+ ", userPhone=" + userPhone + ", userCompany=" + userCompany
				+ ", userLevel=" + userLevel + ", userStatus=" + userStatus
				+ ", userDate=" + userDate + ", userAllocate=" + userAllocate
				+ ", userBalance=" + userBalance + ", userVoucher="
				+ userVoucher + ", userRoleId=" + userRoleId
				+ ", userLocation=" + userLocation + ", userActiveLocation="
				+ userActiveLocation + "]";
	}

}
