package cn.simon.wechat.model;

import java.util.List;

public class NewsMessage extends BaseMessage {

	private int articleCount;

	private List<Article> articles;

	public int getArticleCount() {
		return this.articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public List<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
