package com.llw.itemgarden;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.llw.itemgarden.utils.CrashHandler;


public class ItemGardenApplication extends Application {
    private static final String TAG = ItemGardenApplication.class.getSimpleName();
    private static ItemGardenApplication mApplication;
    private RequestQueue mRequestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        CrashHandler.getInstance().initCrashHandler(getApplicationContext());
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