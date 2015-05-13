package com.llw.itemgarden;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;
import com.llw.itemgarden.base.ObjectIoOperation;
import com.llw.itemgarden.base.StaticValueHolder;
import com.llw.itemgarden.model.User;
import com.llw.itemgarden.utils.UniversalImageLoaderUtil;

import java.io.File;

/**
 * @author Created by liulewen on 2015/3/14.
 */
public class ItemGardenApplication extends Application {
    private static final String TAG = ItemGardenApplication.class.getSimpleName();
    public static final String USER_INFO = "user_info";
    public static final String GOODS_ID = "goods_id";
    public static final String POST_ITEM = "post_item";
    private static ItemGardenApplication mApplication;
    private RequestQueue mRequestQueue;
    private static String userInfoObjectFilePath;
    @Override
    public void onCreate() {
        super.onCreate();
        //百度SDK初始化
        //SDKInitializer.initialize(getApplicationContext());
        mApplication = this;
        userInfoObjectFilePath = getFilesDir().getPath() + File.separator + "user_info";
       // CrashHandler.getInstance().initCrashHandler(getApplicationContext());
        UniversalImageLoaderUtil.initConfig(getApplicationContext());
        deserializeUserInfo();
    }

    public static void serializeUserInfo(User user){
        StaticValueHolder.putObject(USER_INFO, user);
        ObjectIoOperation.writeObject(user, userInfoObjectFilePath);
    }

    public static void deserializeUserInfo(){
        User user = (User)ObjectIoOperation.readObject(userInfoObjectFilePath);
        StaticValueHolder.putObject(USER_INFO, user);
    }

    public static void deleteSerializeUserInfo(){
        new File(userInfoObjectFilePath).delete();
        StaticValueHolder.putObject(USER_INFO, null);
    }
    public static synchronized  ItemGardenApplication getInstance(){
        return mApplication;
    }

    /**
     * get the instance of RequestQueue
     */
    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            synchronized (mApplication){
                mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            }
        }
        return mRequestQueue;
    }

    /**
     * add a request to the queue of requests with tag
     */
    public <T> void addRequestToQueue(Request<T> request , String tag){
        if(TextUtils.isEmpty(tag))
            tag = TAG;
        request.setTag(tag);
        getRequestQueue().add(request);
    }

    /**
     * add a request to the queue of requests without tag;
     */
    public <T> void addRequestToQueue(Request<T> request){
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    /**
     * cancel requests by tag
     */
    public void cancelRequests(String tag){
        if(getRequestQueue() != null)
            getRequestQueue().cancelAll(tag);
    }
}