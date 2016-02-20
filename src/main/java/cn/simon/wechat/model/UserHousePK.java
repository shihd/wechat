package cn.simon.wechat.model;

import java.io.Serializable;

import javax.persistence.Column;

public class UserHousePK implements Serializable {

	@Column(name = "user_id")
	private String userId;

	@Column(name = "house_id")
	private String houseId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

}
