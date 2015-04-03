package com.llw.itemgarden.utils;

import java.io.File;
import java.io.InputStream;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * A helper class to launch a new activity with Android System intent.
 *
 * @author AlfredZhong
 * @version 2012-05-15
 * @see http://developer.android.com/reference/android/content/Intent.html
 */
public class CommonIntents {

    private static final String TAG = CommonIntents.class.getSimpleName();

    private CommonIntents() {
    }

    public static void toastActivityNotFound(Context context) {
        String toast = null;
        /*
         * The language codes are two-letter lowercase ISO language codes (such as "en") as defined by
         * <a href="http://en.wikipedia.org/wiki/ISO_639-1">ISO 639-1</a>.
         * The country codes are two-letter uppercase ISO country codes (such as "US") as defined by
         * <a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-3">ISO 3166-1</a>.
         * The variant codes are unspecified.
         */
        Locale loc = Locale.getDefault();
        String language = loc.getLanguage();
        String country = loc.getCountry();
        System.out.println("Default locale is " + language + "_" + country);
        if (language.equals("zh")) {
            if (country.equals("TW") || country.equals("HK")) {
                // traditional Chinese
                toast = "無法找到相關應用程序";
            } else {
                // simplified Chinese
                toast = "无法找到相关应用程序";
            }
        } else {
            // non zh, use en.
            toast = "Can't find relative application";
        }
        android.widget.Toast.makeText(context, toast, android.widget.Toast.LENGTH_SHORT).show();
    }

    public static boolean startActivityWithToast(Context context, Intent intent) {
        try {
            context.startActivity(intent);
            return true;
        } catch (android.content.ActivityNotFoundException e) {
            toastActivityNotFound(context);
            return false;
        }
    }

    // 如果要Fragment能收到onActivityResult(), 一定要用Fragment.startActivityForResult()
    public static boolean startActivityForResultWithToast(Context context, Intent intent, int requestCode) {
        try {
            context.startActivity(intent);
            return true;
        } catch (android.content.ActivityNotFoundException e) {
            toastActivityNotFound(context);
            return false;
        }
    }

    public static boolean startActivityWithToast(Fragment fragment, Intent intent) {
        try {
            fragment.startActivity(intent);
            return true;
        } catch (android.content.ActivityNotFoundException e) {
            if (fragment.getActivity() != null)
                toastActivityNotFound(fragment.getActivity());
            return false;
        }
    }

    // 如果要Fragment能收到onActivityResult(), 一定要用Fragment.startActivityForResult()
    public static boolean startActivityForResultWithToast(Fragment fragment, Intent intent, int requestCode) {
        try {
            fragment.startActivityForResult(intent, requestCode);
            return true;
        } catch (android.content.ActivityNotFoundException e) {
            if (fragment.getActivity() != null)
                toastActivityNotFound(fragment.getActivity());
            return false;
        }
    }
    
    private static String formatPhoneNumber(String phoneNumber) {
        if(phoneNumber == null)
            return null;
        phoneNumber = phoneNumber.replace(" ", "");
        Log.d(TAG, "Phone number is " + phoneNumber);
        if(!phoneNumber.startsWith("tel:"))
            phoneNumber = "tel:" + phoneNumber;
        return phoneNumber;
    }
    
    /**
     * Show the dialer with the number being dialed.
     * <p>Need permission: android.permission.CALL_PHONE
     * @param context
     * @param phoneNumber
     */
    public static void showDialer(Context context, String phoneNumber) {
        Uri uri = Uri.parse(formatPhoneNumber(phoneNumber));
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        /*
         * Pay attention to the context you use:
         * If you are trying to start a UI component(usually Activity) 
         * from something that is not itself a UI component(usually Service or application context).
         * in this case, you should call intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         * Otherwise you will get the following exception:
         * "android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity  
         * context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?"
         */
        startActivityWithToast(context, intent);
    }
    
    /**
     * Perform a call to someone specified by the data.
     * <p>Need permission: android.permission.CALL_PHONE
     * @param context
     * @param phoneNumber
     */
    public static void phoneCall(Context context, String phoneNumber) {
        Uri uri = Uri.parse(formatPhoneNumber(phoneNumber));
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        startActivityWithToast(context, intent);
    }
    
    /**
     * Open the giving link.
     * @param context
     * @param link
     */
    public static void openLink(Context context, String link) {
        Log.d(TAG, "Open link " + link);
        if (!link.toLowerCase().startsWith("http://") && !link.toLowerCase().startsWith("https://")) {
            link = "http://" + link;
        }
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivityWithToast(context, intent);
    }

    /**
     * 弹出地图应用action chooser, 选择地图应用后指定所给的经纬度
     * 
     * @param context
     * @param lat
     * @param lng
     * @return
     */
    public static boolean showLocationOnMap(Context context, double lat, double lng) {
        // http://developer.android.com/guide/components/intents-common.html#Maps
        Uri geoLocation = Uri.parse(String.format("geo:%s,%s", lat, lng));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
            return true;
        } else {
            toastActivityNotFound(context);
            return false;
        }
    }

    /**
     * 获取调用摄像头拍照的Intent
     * 可以在onActivityResult(int requestCode, int resultCode, Intent data)中获取图片:
     * if (data == null || data.getExtras() == null) return;
     * Bitmap bitmap = data.getExtras().getParcelable("data");
     * 
     * @param context
     * @param requestCode
     * @return
     */
    public static Intent getCaptureIntent() {
        // MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    /**
     * 获取调用摄像头拍照的Intent, 并将图片保存到指定的临时文件
     * 可以在onActivityResult()中访问tempFilePath获取图片
     * 注意: 临时文件要存放在External的路径, 不能是应用内部路径, 如getCacheDir(), 否则拍照APP无法保存图片
     * 
     * @param tempFilePath e.g. getExternalCacheDir() + File.separator + "capture_photo_temp.png"
     * @return
     */
    public static Intent getCaptureIntentWithTempFileOutput(String tempFilePath) {
        Log.d(TAG, "getCaptureIntentWithTempFileOutput : " + tempFilePath);
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempFilePath)));
        // intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return intent;
    }

    /**
     * 获取调用图库获取图片的Intent
     * 可以在onActivityResult(int requestCode, int resultCode, Intent data)中获取图片:
     * if (data == null) return;
     * Uri uri = data.getData();
     * 也可以参考:
     * getGalleryImageInputStreamFromActivityResultIntent()
     * getGalleryImagePathFromActivityResultIntent()
     * 
     * @param tempFilePath
     * @return
     */
    public static Intent getGalleryIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        // intent.putExtra("crop", "circle"); // 设置此参数就会调用裁剪, 否则就会跳过裁剪的过程
        // intent.putExtra("aspectX", 100); // 这个是裁剪时候的裁剪框的X方向的比例, 只能为整型, 否则无效
        // intent.putExtra("aspectY", 100); // 这个是裁剪时候的裁剪框的Y方向的比例, 只能为整型, 否则无效
        // intent.putExtra("outputX", 200); // 返回数据的时候的X像素大小
        // intent.putExtra("outputY", 200); // 返回数据的时候的Y像素大小
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

    public static InputStream getGalleryImageInputStreamFromActivityResultIntent(Context context, Intent data) {
        if (data == null) {
            Log.e(TAG, "getGalleryImageInputStreamFromActivityResultIntent() failed : result intent is null.");
            return null;
        }
        try {
            Uri uri = data.getData();
            return context.getContentResolver().openInputStream(uri);
        } catch (Exception e) {
            Log.e(TAG, "getGalleryImageInputStreamFromActivityResultIntent() failed : " + e, e);
        } 
        return null;
    }
    
    public static String getGalleryImagePathFromActivityResultIntent(Context context, Intent data) {
        if (data == null) {
            Log.e(TAG, "getGalleryImagePathFromActivityResultIntent() failed : result intent is null.");
            return null;
        }
        Uri uri = data.getData();
        return getGalleryImagePathFromActivityResultIntentUri(context, uri);
    }
    
    public static String getGalleryImagePathFromActivityResultIntentUri(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            return filePath;
        } catch (Exception e) {
            Log.e(TAG, "getGalleryImagePathFromActivityResultIntent() failed : " + e, e);
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e2) {
            }
        }
        return null;
    }

    /**
     * 弹出选择图片的对话框
     * 注意: 如果使用临时文件返回图片, 记得要在使用完图片后要删除该临时文件
     * 
     * @param activityOrFragment
     * @param captureTempFilePath, 如果此参数为空, 则返回拍照图片bitmap; 建议自己设置该参数, 然后自己decode临时文件以防OOM.
     * @param captureRequestCode
     * @param galleryRequestCode
     */
    public static void showFetchImageDialog(Object activityOrFragment, final String captureTempFilePath,
            final int captureRequestCode, final int galleryRequestCode) {
        Context context = null;
        Fragment fragment = null;
        if (activityOrFragment instanceof Activity) {
            context = (Activity) activityOrFragment;
        } else if (activityOrFragment instanceof FragmentActivity) {
            context = (FragmentActivity) activityOrFragment;
        } else if (activityOrFragment instanceof Fragment) {
            fragment = (Fragment) activityOrFragment;
            context = fragment.getActivity();
        }
        final Context ctx = context;
        final Fragment frag = fragment;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String[] items = new String[] { "拍照", "图库" };
        builder.setTitle("选择图片");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                case 0:
                    Intent intent = null;
                    if (captureTempFilePath == null) {
                        intent = getCaptureIntent();
                    } else {
                        intent = getCaptureIntentWithTempFileOutput(captureTempFilePath);
                    }
                    if (frag != null)
                        startActivityForResultWithToast(frag, intent, captureRequestCode);
                    else
                        startActivityForResultWithToast(ctx, intent, captureRequestCode);
                    break;
                case 1:
                    if (frag != null)
                        startActivityForResultWithToast(frag, getGalleryIntent(), galleryRequestCode);
                    else
                        startActivityForResultWithToast(ctx, getGalleryIntent(), galleryRequestCode);
                    break;
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create();
        builder.show();
    }

}
