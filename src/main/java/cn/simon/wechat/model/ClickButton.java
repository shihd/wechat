package cn.simon.wechat.model;

public class ClickButton extends Button {

	private String type;

	private String key;

	public ClickButton() {
		this.type = "click";
	}

	public String getType() {
		return type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
