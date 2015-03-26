package com.llw.itemgarden.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @author Created by liulewen on 2015/3/26.
 */
public class UniversalImageLoaderUtil {

    public static void initConfig(Context context){
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY) //显示的图片按比例缩小
                .bitmapConfig(Bitmap.Config.RGB_565) //减少OOM的出现
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory() //不缓存具有多种尺寸的图片
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheFileCount(50)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //以MD5的方式加密文件名(为了不让用户看见)
                .tasksProcessingOrder(QueueProcessingType.LIFO) //任务处理方式:后进先出
                .defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
