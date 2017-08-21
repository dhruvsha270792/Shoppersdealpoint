package com.nexusdevs.shoppersdeal.server.dto;

public class UserSession {

	private String id;
	private String userId;
	private String token;
	private boolean expired;
	private long validTill;
	private long createTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public long getValidTill() {
		return validTill;
	}

	public void setValidTill(long validTill) {
		this.validTill = validTill;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
