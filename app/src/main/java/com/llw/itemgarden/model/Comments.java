package com.llw.itemgarden.model;

import java.util.Date;


/**
 * <pre>
 * 评论实体
 * </pre>
 * 
 * @author XiaoJian
 */
public class Comments  extends BaseEntity{

	private static final long serialVersionUID = 985236545427631654L;

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 物品ID
	 */
	private Long contentId;

	/**
	 * 评论时间
	 */
	private Date commentDate;

	/**
	 * 评论内容
	 */
	private String content;

	/**
	 * 评论父ID
	 */
	private Long parentId;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
}
