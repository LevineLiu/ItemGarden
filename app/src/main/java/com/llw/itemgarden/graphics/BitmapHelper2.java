package com.llw.itemgarden.graphics;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;

/**
 * @author Created by liulewen on 2015/4/3.
 */
public class BitmapHelper2 {
     private final static String TAG = BitmapHelper2.class.getSimpleName();
    /**
     * zoom bitmap in order to avoid OOM
     * @param object the object to be decoded
     * @param options
     * @param resources this is used when the object to be decoded is from the resources
     * @param reqSize require size
     * */
    public static Bitmap zoomBitmap(Object object, BitmapFactory.Options options, Resources resources, int reqSize){
        if(reqSize <= 0)
            decodeBitmap(object, options, resources);

        return null;
    }

    public static Bitmap decodeBitmap(Object object, BitmapFactory.Options options, Resources resources){
        try {
            if (object instanceof byte[]) {
                byte[] data = (byte[]) object;
                return BitmapFactory.decodeByteArray(data, 0, data.length, options);
            } else if (object instanceof String) {
                return BitmapFactory.decodeFile((String) object, options);
            } else if (object instanceof InputStream) {
                return BitmapFactory.decodeStream((InputStream) object, null, options);
            } else if (object instanceof Integer) {
                return BitmapFactory.decodeResource(resources, (Integer) object, options);
            }
        }catch (Exception e){
            Log.e(TAG, "decodeBitmap() failed", e);
        }catch (OutOfMemoryError e){
            Log.e(TAG, "decodeBitmap() OOM", e);
        }
        return  null;
    }
}
