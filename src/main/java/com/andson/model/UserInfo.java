package com.andson.model;

public class UserInfo {
	
	private String userId;
	private String userName;
	private String password;
	private String mobileId;
	private String deviceToken;
	private String bindMedium;
	private String tokenId;
	private String mobileLocale;
	private String logined;
	private int enabled;
	private int isChildUser;
	
	public int getIsChildUser() {
		return isChildUser;
	}
	public void setIsChildUser(int isChildUser) {
		this.isChildUser = isChildUser;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getTokenId() {
		return tokenId;
	}
	public String getMobileLocale() {
		return mobileLocale;
	}
	public void setMobileLocale(String mobileLocale) {
		this.mobileLocale = mobileLocale;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getBindMedium() {
		return bindMedium;
	}
	public void setBindMedium(String bindMedium) {
		this.bindMedium = bindMedium;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileId() {
		return mobileId;
	}
	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getLogined() {
		return logined;
	}
	public void setLogined(String logined) {
		this.logined = logined;
	}
}
