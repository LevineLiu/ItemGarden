package com.llw.itemgarden.base;

/**
 * @author Created by liulewen on 2015/3/23.
 */
public class Constants {

    public final static String BASE_URL = "http://112.74.125.71:8080/itemgarden/";

    /**
     * 注册
     */
    public final static String REGISTER_URL = BASE_URL + "user/register";

    /**
     * 登陆
     */
    public final static String LOGIN_URL = BASE_URL + "user/login";

    /**
     * 发布物品
     */
    public final static String PUBLISH_ITEM = BASE_URL + "item/publish";

    /**
     * 更新物品图片
     */
    public final static String UPDATE_ITEM_IMAGE = BASE_URL + "itemImage/update";

    /**
     * 删除物品图片
     */
    public final static String DELETE_ITEM_IMAGE = BASE_URL + "itemImage/delete";

    /**
     * 删除物品
     */
    public final static String DELETE_ITEM = BASE_URL + "item/delete";
}
