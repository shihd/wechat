package cn.simon.wechat.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_house_mapping")
public class UserHouseMapping {

	@EmbeddedId
	private UserHousePK id;

	@Column(name = "house_name")
	private String houseName;

	@Column(name = "is_default")
	private String isDefault;

	@Column(name = "status")
	private String status;

	public UserHousePK getId() {
		return id;
	}

	public void setId(UserHousePK id) {
		this.id = id;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
