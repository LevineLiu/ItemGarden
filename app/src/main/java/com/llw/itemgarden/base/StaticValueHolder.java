package com.llw.itemgarden.base;

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

    public static void putObject(String key, Object object){
        mMap.put(key, object);
    }

    public static <T> T getObject(String key){
        Object object = mMap.get(key);
        try{
            if(object != null)
                return (T)object;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return null;
    }
}
