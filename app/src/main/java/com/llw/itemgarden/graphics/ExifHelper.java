package com.llw.itemgarden.graphics;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

/**
 * A handy class to handle Exchangeable Image File.
 * 
 * @author AlfredZhong
 * @version 2014-08-18
 */
public class ExifHelper {

    private ExifHelper() {
    }

    /**
     * Returns rotated bitmap according to the EXIF orientation info.
     * 
     * @param filePath
     * @return
     */
    public static Bitmap getRotatedBitmap(String filePath, Bitmap bitmap, boolean recycleOriginalBitmap) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
        Matrix matrix = new Matrix();
        int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        switch (rotation) {
        case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
            matrix.setScale(-1, 1);
            break;
        case ExifInterface.ORIENTATION_ROTATE_180:
            matrix.setRotate(180);
            break;
        case ExifInterface.ORIENTATION_FLIP_VERTICAL:
            matrix.setRotate(180);
            matrix.postScale(-1, 1);
            break;
        case ExifInterface.ORIENTATION_TRANSPOSE:
            matrix.setRotate(90);
            matrix.postScale(-1, 1);
            break;
        case ExifInterface.ORIENTATION_ROTATE_90:
            matrix.setRotate(90);
            break;
        case ExifInterface.ORIENTATION_TRANSVERSE:
            matrix.setRotate(-90);
            matrix.postScale(-1, 1);
            break;
        case ExifInterface.ORIENTATION_ROTATE_270:
            matrix.setRotate(-90);
            break;
        case ExifInterface.ORIENTATION_NORMAL:
        case ExifInterface.ORIENTATION_UNDEFINED:
            // You can do nothing if you don't know the orientation.
        default:
            return bitmap;
        }
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Bitmap rotatedBitmap = null;
        try {
            rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if(rotatedBitmap == null) {
            return bitmap;
        }
        if (recycleOriginalBitmap) {
            // recycle the bitmap to release resources.
            bitmap.recycle();
        }
        return rotatedBitmap;
    }

}
