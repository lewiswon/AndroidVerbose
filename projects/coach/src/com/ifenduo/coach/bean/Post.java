package com.ifenduo.coach.bean;

import java.io.Serializable;

/**
 * 
 *                       
 * @Filename Version.java
 *
 * @Description 文章（新闻/诉讼指南）
 *
 * @Version 1.0
 *
 * @Author edyang
 *
 * @Email edyang123@gmail.com
 *       
 * @History
 *<li>Author: edyang</li>
 *<li>Date: 2015年2月4日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class Post implements Serializable {
	
	private static final long	serialVersionUID	= -5490267970836870657L;
	
	private Long				id;
	
	/** 文章类型 news | guide */
	private String				postType;
	
	/** 标题图 */
	private String				titleImg;
	
	/** 标题 */
	private String				title;
	
	/** 摘要 */
	private String				summary;
	
	/** 是否推荐置顶 */
	private boolean				recommend;
	
	/** 更新时间 */
	private String				date;
	
	/**内容（富文本哟）*/
	private String				content;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPostType() {
		return postType;
	}
	
	public void setPostType(String postType) {
		this.postType = postType;
	}
	
	public String getTitleImg() {
		return titleImg;
	}
	
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public boolean isRecommend() {
		return recommend;
	}
	
	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
}