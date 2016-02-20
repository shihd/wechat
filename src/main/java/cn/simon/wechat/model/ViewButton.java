package cn.simon.wechat.model;

public class ViewButton extends Button {

	private String type;

	private String url;

	public ViewButton() {
		this.type = "view";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

}
