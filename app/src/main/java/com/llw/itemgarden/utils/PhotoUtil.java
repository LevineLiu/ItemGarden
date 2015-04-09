package com.llw.itemgarden.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.Locale;

/**
 * a helper class to take photo or get photo from gallery
 *
 * @author Created by liulewen on 2015/4/3.
 */
public class PhotoUtil {
    private final static String TAG = PhotoUtil.class.getSimpleName();
    /**
     * 弹出拍照和选择图片的对话框
     * @param ActivityOrFragment 上下文
     * @param captureTempFilePath 拍照缓存到SDK的路径。如果为空则不缓存，推荐设置该参数以防止发生OOM
     * @param captureRequestCode 拍照的请求码
     * @param galleryRequestCode 选取照片的请求码
     * */
    public static void showFetchImageDialog(Object ActivityOrFragment, final String captureTempFilePath,
                                            final int captureRequestCode, final int galleryRequestCode) {
        if (ActivityOrFragment == null)
            return;
        Context context = null;
        Fragment fragment = null;
        if (ActivityOrFragment instanceof FragmentActivity)
            context = (FragmentActivity) ActivityOrFragment;
        else if (ActivityOrFragment instanceof Activity)
            context = (Activity) ActivityOrFragment;
        else if (ActivityOrFragment instanceof Fragment) {
            fragment = (Fragment) ActivityOrFragment;
            context = fragment.getActivity();
        }
        final Fragment startFragment = fragment;
        final Context startContext = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String items[] = {"拍照", "图库"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent;
                        if(captureTempFilePath == null)
                            intent = getCaptureIntent();
                        else
                            intent = getCaptureIntentWithTempFilePath(captureTempFilePath);
                        if(startFragment == null)
                            startActivityForResult(startContext, intent, captureRequestCode);
                        else
                            startActivityForResult(startFragment, intent, captureRequestCode);
                        break;
                    case 1:
                        if(startFragment == null)
                            startActivityForResult(startContext, getGalleryIntent(), galleryRequestCode);
                        else
                            startActivityForResult(startFragment, getGalleryIntent(), galleryRequestCode);
                        break;
                }

            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
    /**
     * 获取拍照的不带缓存目录的Intent
     * */
    private static Intent getCaptureIntent(){
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    /**
     * 获取拍照的带缓存目录的Intent
     * 注意: 缓存目录是External的路径, 不能是应用内部路径, 如getCacheDir(), 否则拍照APP无法保存图片
     * */
    private static Intent getCaptureIntentWithTempFilePath(String tempFilePath){
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempFilePath)));
        return intent;
    }

    /**
     * 获取从图库选择图片的Intent
     * */
    private static Intent getGalleryIntent(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        return  intent;
    }

    private static void startActivityForResult(Context context, Intent intent, int requestCode){
        try{
            ((Activity)context).startActivityForResult(intent, requestCode);
        }catch (ActivityNotFoundException e){
                ToastApplicationNotFound(context);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void startActivityForResult(Fragment fragment, Intent intent, int requestCode){
        try{
            fragment.startActivityForResult(intent, requestCode);
        }catch (ActivityNotFoundException e){
            ToastApplicationNotFound(fragment.getActivity());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getGalleryImagePathWithIntent(Context context,Intent data){
        if(data == null)
            return null;
        Cursor cursor = null;
        try{
            Uri uri = data.getData();
            ContentResolver contentResolver = context.getContentResolver();
            cursor = contentResolver.query(uri, null, null, null, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            return path;
        }catch (Exception e){
            Log.e(TAG, "getGalleryImagePathWithIntent", e);
        }finally {
            if(cursor != null)
                cursor.close();
        }
        return null;
    }
    /**
     * 无法拍照或选择图库时弹出的提示
     **/
    private static void ToastApplicationNotFound(Context context){
        if(context == null)
            return;
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String message;
        if("zh".equals(language)){
            if("HK".equals(country) || "TW".equals(country))
                message = "無法找到相關應用程序";
            else
                message = "无法找到相关的应用程序";
        }else
            message = "Can't find relative application";
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
