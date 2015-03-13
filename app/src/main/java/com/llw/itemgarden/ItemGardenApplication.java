package com.llw.itemgarden;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.llw.itemgarden.utils.CrashHandler;


public class ItemGardenApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().initCrashHandler(getApplicationContext());
    }
}