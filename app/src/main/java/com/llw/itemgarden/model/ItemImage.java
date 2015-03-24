package com.llw.itemgarden.model;


/**
 * <pre>
 * 物品图片实体
 * </pre>
 * 
 * @author XiaoJian
 */
public class ItemImage  extends BaseEntity{
	
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
	 * 图片路径
	 */
	private String headImage;

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

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	
	
}
