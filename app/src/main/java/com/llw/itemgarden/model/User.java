package com.llw.itemgarden.model;

import java.util.Date;


/**
 * <pre>
 * 用户实体
 * </pre>
 * 
 * @author XiaoJian
 */
public class User extends BaseEntity {

	private static final long serialVersionUID = 985235545427631654L;

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 昵称
	 */
	private String loginName;

	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 出生年月
	 */
	private String birthday;
	
	/**
	 * 头像
	 */
	private String headImage;
	
	/**
	 * 电子邮箱
	 */
	private String userEmail;
	
	/**
	 * 联系电话
	 */
	private String telephone;
	
	/**
	 * 用户状态
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 常住地址
	 */
	private String liveAddress;
	
	/**
	 * 收货地址
	 */
	private String deliveryAddress;

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

}