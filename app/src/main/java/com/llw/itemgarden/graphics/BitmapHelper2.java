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
     * @param reqWidth require width
     * @param reqHeight require height
     * @param allowLossCompression if allow to loss compression or not, it will affect the value of options.inSampleSize
     * */
    public static Bitmap zoomBitmap(Object object, BitmapFactory.Options options, Resources resources,
                                    int reqWidth, int reqHeight, boolean allowLossCompression){
        if(reqWidth <= 0 || reqHeight <= 0)
            decodeBitmap(object, options, resources);
        BitmapFactory.Options opt = decodeBounds(object, resources);
        int simpleSize = calculateInSampleSize(opt, reqWidth, reqHeight, allowLossCompression);
        if(options != null)
            opt = options;
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = simpleSize;
        Bitmap bitmap = decodeBitmap(object, opt, resources);
        return bitmap;
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

    private static BitmapFactory.Options decodeBounds(Object object, Resources resources){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeBitmap(object, options, resources);
        return options;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight, boolean allowLossCompression){
        int height = options.outHeight;
        int width = options.outWidth;
        int simpleSize = 4;

        if(reqWidth < 0 || reqHeight < 0 )
            return  simpleSize;
        //load a large image
        if(width > reqWidth || height > reqHeight){
            if(width > height)
                simpleSize = Math.round((float)height / (float)reqHeight);
            else
                simpleSize = Math.round((float)width / (float)reqWidth);
        }
        //if allow loss compression, set the nearest value for simpleSize
        if(allowLossCompression && simpleSize > 2){
            int size = simpleSize;
            int power = 1;
            while((size = size/2) != 1){
                power++;
            }
            if(Math.abs(Math.pow(2, power) - simpleSize) > Math.abs(Math.pow(2, power+1) - simpleSize))
                simpleSize = (int)Math.pow(2, power+1);
            else
                simpleSize = (int)Math.pow(2, power);
        }
        int resize = width * height * 4 / 1024 / simpleSize/ simpleSize;
        // resized bitmap larger than 1M.
        if(resize >= 900) {
            simpleSize = simpleSize * 4;
        }
        return  simpleSize;
    }
}
