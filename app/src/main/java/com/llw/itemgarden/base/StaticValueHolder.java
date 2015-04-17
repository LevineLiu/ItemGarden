package com.llw.itemgarden.base;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/4/11.
 */
public class StaticValueHolder {
    private static Map<String, Object> mMap;

    static {
        mMap = new HashMap<>();
    }

    public static void putInt(String key, int value) {
        mMap.put(key, value);
    }

    public static int getInt(String key, int defaultValue) {
        Object object = mMap.get(key);

        if (object == null)
            return defaultValue;
        else {
            try {
                return (int) object;
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    public static void putLong(String key, long value){
        mMap.put(key, value);
    }

    public static long getLong(String key, long defaultValue){
        Object object = mMap.get(key);
        if(object != null){
            try {
              return (long)object;
            }catch (ClassCastException e){
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    public static void putObject(String key, Object object) {
        mMap.put(key, object);
    }

    public static <T> T getObject(String key) {
        Object object = mMap.get(key);
        try {
            if (object != null)
                return (T) object;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void remove(String key) {
        mMap.remove(key);
    }
}
