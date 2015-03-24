package com.llw.itemgarden.model;


import java.util.Date;


/**
 * <pre>
 * 物品实体
 * </pre>
 * 
 * @author XiaoJian
 */
public class Item  extends BaseEntity{
	
	private static final long serialVersionUID = 985236545427631654L;

	/**
	 * 自增ID
	 */
	private Long id;
	
	/**
	 * 名称
	 */
	private String title;
	
	/**
	 * 描述
	 */
	private String detail;
	
	/**
	 * 喜欢数
	 */
	private Integer numLike;
	
	/**
	 * 评论数
	 */
	private Integer numComment;
	
	/**
	 * 浏览数
	 */
	private Integer numBrower;
	
	/**
	 * 发布位置
	 */
	private String station;
	
	/**
	 * 发布时间
	 */
	private Date publishDate;
	
	/**
	 * 发布用户
	 */
	private Long createBy;
	
	/**
	 * 现价
	 */
	private Double newPrice;
	
	/**
	 * 原价
	 */
	private Double oldPrive;
	
	/**
	 * 运费
	 */
	private Double carryPrice;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 是否全新
	 */
	private Integer isNew;
	
	/**
	 * 买家
	 */
	private Long buyBy;
	
	/**
	 * 物品所属分类
	 */
	private Long contentClass;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getNumLike() {
		return numLike;
	}

	public void setNumLike(Integer numLike) {
		this.numLike = numLike;
	}

	public Integer getNumComment() {
		return numComment;
	}

	public void setNumComment(Integer numComment) {
		this.numComment = numComment;
	}

	public Integer getNumBrower() {
		return numBrower;
	}

	public void setNumBrower(Integer numBrower) {
		this.numBrower = numBrower;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}

	public Double getOldPrive() {
		return oldPrive;
	}

	public void setOldPrive(Double oldPrive) {
		this.oldPrive = oldPrive;
	}

	public Double getCarryPrice() {
		return carryPrice;
	}

	public void setCarryPrice(Double carryPrice) {
		this.carryPrice = carryPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public Long getBuyBy() {
		return buyBy;
	}

	public void setBuyBy(Long buyBy) {
		this.buyBy = buyBy;
	}

	public Long getContentClass() {
		return contentClass;
	}

	public void setContentClass(Long contentClass) {
		this.contentClass = contentClass;
	}
	
	

}
