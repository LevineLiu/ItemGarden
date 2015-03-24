package com.llw.itemgarden.model;


/**
 * <pre>
 * 分类实体
 * </pre>
 * 
 * @author XiaoJian
 */
public class ContentClass  extends BaseEntity{
	
	private static final long serialVersionUID = 985236545427631654L;

	/**
	 * 自增ID
	 */
	private Long id;
	
	/**
	 * 分类名
	 */
	private String className;
	
	/**
	 * 所属分类
	 */
	private Long classId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}
	
	

}
