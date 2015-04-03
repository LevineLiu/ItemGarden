package com.llw.itemgarden.graphics;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;

/**
 * @author AlfredZhong
 * @version 2012-07-27
 * @version 2012-07-28, Added functions to reduce OOM error.
 * @version 2013-12-25, Added decodeBitmap() to uniform the parameters.
 * @version 2014-07-30, optimize the method about calculating inSampleSize.
 */
public class BitmapHelper {

    private static final String TAG = BitmapHelper.class.getSimpleName();

    private BitmapHelper() {
    }

    /**
     * Decode uniform bitmap source without throwing OOM error.
     * 
     * @param toBeDecode can be image byte[], image path, image InputStream, image resId or image FileDescriptor.
     * @param res
     * @param opts
     * @return
     */
    public static Bitmap decodeBitmap(Object toBeDecode, Resources res, Options opts) {
        try {
            if (toBeDecode instanceof byte[]) {
                byte[] data = (byte[]) toBeDecode;
                return BitmapFactory.decodeByteArray(data, 0, data.length, opts);
            } else if (toBeDecode instanceof String) {
                return BitmapFactory.decodeFile((String) toBeDecode, opts);
            } else if (toBeDecode instanceof InputStream) {
                // Do NOT close InputStream here, caller may only use this method to decode bounds.
                return BitmapFactory.decodeStream((InputStream) toBeDecode, null, opts);
            } else if (toBeDecode instanceof Integer) {
                // decodeResource will not cause exception, just return null if failed.
                // But some custom ROMs, such as HTC, may throw Resources$NotFoundException.
                return BitmapFactory.decodeResource(res, (Integer) toBeDecode, opts);
            } else if (toBeDecode instanceof FileDescriptor) {
                return BitmapFactory.decodeFileDescriptor((FileDescriptor) toBeDecode, null, opts);
            } else {
            	Log.e(TAG, TAG + ".decodeBitmap() nothing matches.");
            }
        } catch (Exception e) {
            Log.w(TAG, TAG + ".decodeBitmap() failed with toBeDecode : " + toBeDecode, e);
        } catch (OutOfMemoryError oom) {
            // catch OOM to avoid application crash.
            Log.e(TAG, TAG + ".decodeBitmap() failed with OutOfMemoryError.", oom);
        }
        return null;
    }

    public static Options decodeBounds(Object toBeDecode, Resources res) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        decodeBitmap(toBeDecode, res, options);
        return options;
    }

    public static int calculateInSampleSize(Options options, int reqMinWidth) {
        int inSampleSize = 4;
        final int rawWidth = options.outWidth;
        final int rawHeight = options.outHeight;
        if (reqMinWidth <= 0 || rawHeight <= 0 || rawWidth <= 0) {
            return inSampleSize;
        }
        final int reqMinHeight = reqMinWidth * rawHeight / rawWidth;
        if (rawHeight > reqMinHeight || rawWidth > reqMinWidth) {
            if (rawWidth > rawHeight) {
                inSampleSize = Math.round((float) rawHeight / (float) reqMinHeight);
            } else {
                inSampleSize = Math.round((float) rawWidth / (float) reqMinWidth);
            }
        }
        int resize = rawWidth * rawHeight * 4 / 1024 / inSampleSize / inSampleSize;
        // resized bitmap larger than 1M.
        if(resize >= 1024) {
            inSampleSize = inSampleSize * 2;
        }
        Log.d(TAG, TAG + " calculateInSampleSize returns " + inSampleSize);
        return inSampleSize;
    }

    public static Bitmap decodeScaledDownBitmap(Object toBeDecode, Resources res, final int reqMinWidth,
            final Options opts, boolean closeInputStream) {
        if (reqMinWidth <= 0) {
            return decodeBitmap(toBeDecode, res, opts);
        }
        Options options = decodeBounds(toBeDecode, res);
        final int inSampleSize = calculateInSampleSize(options, reqMinWidth);
        // Decode bitmap pixel data with inSampleSize.
        if (opts != null) {
            options = opts;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = decodeBitmap(toBeDecode, res, options);
        Log.w(TAG, "decodeScaledDownBitmap() bitmap: " + bitmap);
        // close InputStream after decode bitmap.
        if (closeInputStream&& toBeDecode instanceof InputStream) {
            try {
                ((InputStream) toBeDecode).close();
            } catch (IOException e) {
                Log.w(TAG, "decodeScaledDownBitmap() close InputStream failed : " + e);
            }
        }
        return bitmap;
    }

}
