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
     * 返回消息
     */
    private String message = "";

    /**
     * 返回对象
     */
    private Object object = null;

    /**
     * 是否成功
     */
    private boolean success = false;

    /**
     * 错误码
     */
    private int errorCode = 0;

    /**
     * 返回成功信息和对象
     */
    public static ServiceResult successResult(Object object, String message) {
        ServiceResult result = new ServiceResult();
        result.setObject(object);
        result.setSuccess(true);
        result.setMessage(message);
        return result;
    }

    /**
     * 只返回对象
     * @param obj
     * @return
     */
    public static ServiceResult successResult(Object obj){
        return successResult(obj, "");
    }

    /**
     * 只返回成功信息
     * @param message
     * @return
     */
    public static ServiceResult successResult(String message){
        return successResult(null, message);
    }

    /**
     * 只标志成功,什么都不返回
     * @return
     */
    public static ServiceResult successResult(){
        return successResult(null,"");
    }

    /**
     * 返回错误信息及对象
     */
    public static ServiceResult failResult(Object object,String message) {
        ServiceResult result = new ServiceResult();
        result.setObject(object);
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

    /**
     * 只返回错误码
     * @param errorCode
     * @return
     */
    public static ServiceResult failResult(int errorCode){
        ServiceResult result = new ServiceResult();
        result.setErrorCode(errorCode);
        result.setSuccess(false);
        return result;
    }

    /**
     * 只返回错误对象
     * @param obj
     * @return
     */
    public static ServiceResult failResult(Object obj){
        return failResult(obj, "");
    }

    /**
     * 只返回错误信息
     * @param message
     * @return
     */
    public static ServiceResult failResult(String message){
        return failResult(null, message);
    }

    /**
     * 只标志错误,什么都不返回
     * @return
     */
    public static ServiceResult failResult(){
        return failResult(null, "");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
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
