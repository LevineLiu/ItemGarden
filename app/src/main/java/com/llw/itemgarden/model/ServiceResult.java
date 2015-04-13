package com.llw.itemgarden.model;

import java.io.Serializable;

/**
 * 
 * 返回结果实体类
 * 
 * @author XiaoJian
 *
 */
public class ServiceResult implements Serializable {

	private static final long serialVersionUID = 7500224365132160994L;
	
	/**
	 * 参数为空
	 */
	public static final int NULLERROR = 300;
	
	/**
	 * 服务器出错
	 */
	public static final int SERVERERROR = 500;
	
	/**
	 * 解析出错
	 */
	public static final int PARSEERROR = 501;
	
	/**
	 * Token失效
	 */
	public static final int TOKENEXPIRES = 520;
	

	/**
	 * 返回信息
	 */
	private String object = "";

	/**
	 * 是否成功
	 */
	private boolean success = false;
	
	/**
	 * 错误码
	 */
	private int errorCode = 0;
	
	/**
	 * 返回成功信息
	 * @param object
	 * @return
	 */
	public static ServiceResult successResult(String object){
		ServiceResult result = new ServiceResult();
		result.setSuccess(true);
		result.setObject(object);;
		return result;
	}
	
	/**
	 * 只标志成功,什么都不返回
	 * @return
	 */
	public static ServiceResult successResult(){		
		return successResult("");
	}

	/**
	 * 返回错误信息
	 */
	public static ServiceResult failResult(String object) {
		ServiceResult result = new ServiceResult();
		result.setSuccess(false);
		result.setObject(object);;
		return result;
	}
	
	/**
	 * 只返回错误码
	 * @return
	 */
	public static ServiceResult failResult(int errorCode){
		ServiceResult result = new ServiceResult();
		result.setErrorCode(errorCode);
		result.setSuccess(false);
		return result;
	}
	
	
	/**
	 * 只标志错误,什么都不返回
	 * @return
	 */
	public static ServiceResult failResult(){
		return failResult("");
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	


}
