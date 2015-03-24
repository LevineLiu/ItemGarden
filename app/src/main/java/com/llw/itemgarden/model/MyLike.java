package com.llw.itemgarden.model;


/**
 * <pre>
 * 我的喜欢实体
 * </pre>
 * 
 * @author XiaoJian
 */
public class MyLike  extends BaseEntity{
	
	private static final long serialVersionUID = 985236545427631654L;

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 用户ID
	 */
	private Long useId;

	/**
	 * 物品ID
	 */
	private Long contentId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUseId() {
		return useId;
	}

	public void setUseId(Long useId) {
		this.useId = useId;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	
	

}
