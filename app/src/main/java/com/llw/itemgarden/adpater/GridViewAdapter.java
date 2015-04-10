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
import com.llw.itemgarden.utils.PhotoUtil;
import com.llw.itemgarden.view.PhotoGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by liulewen on 2015/4/2.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;
    private LayoutInflater mInflater;
    private Fragment mFragment;

    public GridViewAdapter(Context context, Fragment fragment) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mFragment = fragment;
    }

    public void addData(String filePath) {
        if (mData == null) {
            mData = new ArrayList<>();
            mData.add(filePath);
        } else
            mData.add(filePath);
        notifyDataSetChanged();
    }

    public void removeLastData() {
        int size = getCount();
        if (size > 0)
            mData.remove(size - 1);
    }

    public void removeData(int position) {
        mData.remove(position);
        notifyDataSetChanged();
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
        if (PhotoGridView.isOnMeasure == true) {
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
            setImage(imageView, mData.get(position));
            deleteImageView.setVisibility(View.VISIBLE);
            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeData(position);
                }
            });
        }

        return convertView;
    }

    private void setImage(ImageView imageView, String imagePath) {
        if (imageView == null || imagePath == null)
            return;
        Bitmap bitmap = BitmapHelper2.zoomBitmap(imagePath, null, null, 480, 480, true);
        imageView.setImageBitmap(bitmap);
    }

    public String getCaptureFilePath() {
        return mContext.getExternalCacheDir() + File.separator + "capture_photo_temp.png";
    }
}
