package com.llw.itemgarden.adpater;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.fragment.PostPhotoFragment;
import com.llw.itemgarden.graphics.BitmapHelper2;
import com.llw.itemgarden.model.ItemImage;
import com.llw.itemgarden.utils.PhotoUtil;
import com.llw.itemgarden.view.PhotoGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/4/2.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ItemImage> mData;
    private LayoutInflater mInflater;
    private Fragment mFragment;
    private List<Boolean> isPhotoUpLoad = new ArrayList<>();
    private Map<Integer, Bitmap> bitmapCache = new HashMap<>();
    private Map<Integer, ImageView> images = new HashMap<>();
    public GridViewAdapter(Context context, Fragment fragment) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mFragment = fragment;
    }

    public void addData(ItemImage itemImage) {
        if (mData == null) {
            mData = new ArrayList<>();
            mData.add(itemImage);
        } else{
            //limit the number of photo to 9
            if(mData.size() == 10){
                mData.remove(mData.size() - 1);
                notifyDataSetChanged();
            }
            else
                mData.add(itemImage);
        }
        isPhotoUpLoad.add(false);

    }

    public void removeLastData() {
        int size = getCount();
        if (size > 0)
            mData.remove(size - 1);
    }

    public void removeData(int position) {
        mData.remove(position);
        isPhotoUpLoad.remove(position);
        bitmapCache.remove(position);
        notifyDataSetChanged();
    }

    public Map<Integer, ImageView> getImages(){
        return images;
    }

    public Map<Integer, Bitmap> getBitmapCache(){
        return bitmapCache;
    }

    public List<Boolean> getIsPhotoUpload(){
        return isPhotoUpLoad;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("position", position + "");
        convertView = mInflater.inflate(R.layout.photo_gridview_item, null);
        if (PhotoGridView.isOnMeasure) {
            return convertView;
        }
        final ImageView deleteImageView = (ImageView) convertView.findViewById(R.id.delete_photo_img);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.photo_gridview_item_img);
        // a way to solve the problem of calling getView multiple times when position is 0
        if (position == getCount() - 1) {
            deleteImageView.setVisibility(View.GONE);
            imageView.setBackgroundResource(R.drawable.post_add_image_icon);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoUtil.showFetchImageDialog(mFragment, getCaptureFilePath(), PostPhotoFragment.CAPTURE_REQUEST_CODE,
                            PostPhotoFragment.GALLERY_REQUEST_CODE);
                }
            });
        } else {
            if(bitmapCache.get(position) == null){
                Bitmap bitmap = setImage(imageView, mData.get(position).getHeadImage());
                bitmapCache.put(position, bitmap);
                images.put(position, imageView);
                upLoad(position);
            }else
                imageView.setImageBitmap(bitmapCache.get(position));
//            if(!isPhotoUpLoad.get(position)){
//                upLoad(position);
//            }
            deleteImageView.setVisibility(View.VISIBLE);
            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isPhotoUpLoad.get(position))
                        deleteImage(position);
                    removeData(position);
                }
            });
        }

        return convertView;
    }

    private Bitmap setImage(ImageView imageView, String imagePath) {
        if (imageView == null || imagePath == null)
            return null;
        Bitmap bitmap = BitmapHelper2.zoomBitmap(imagePath, null, null, 480, 480, true);
        imageView.setImageBitmap(bitmap);
        return bitmap;
    }

    public String getCaptureFilePath() {
        return mContext.getExternalCacheDir() + File.separator + "capture_photo_temp.png";
    }

    public void upLoad(int position){

    }
    public void deleteImage(int position){

    }
}
