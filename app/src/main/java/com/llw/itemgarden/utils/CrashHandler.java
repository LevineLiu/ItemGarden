package com.llw.itemgarden.utils;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Created by liulewen on 2015/3/12.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private static CrashHandler mCrashHandler;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    private CrashHandler(){
    }

    public static CrashHandler getInstance(){
        if(mCrashHandler == null)
            mCrashHandler = new CrashHandler();
        return mCrashHandler;
    }

    public void initCrashHandler(Context context){
        this.mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            Log.e("uncaugthException", "uncaugthException", ex);
            handleCrashException(ex);
        } catch (Exception e) {
            if(mDefaultHandler != null)
                mDefaultHandler.uncaughtException(thread, ex);
        }

    }

    /**
     * handle exception, make a toast to users or do other things
     */
    private void handleCrashException(Throwable throwable){
        String message = throwable.getLocalizedMessage();
        if(message != null)
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        Looper.prepare();
        Looper.loop();
    }
}
