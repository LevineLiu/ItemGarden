package com.llw.itemgarden.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.llw.itemgarden.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by liulewen on 2015/4/2.
 */
public class photoGridViewAdapter extends BaseAdapter{
    private List<String> mData;
    private LayoutInflater mInflater;
    public photoGridViewAdapter(Context context){
        mInflater = LayoutInflater.from(context);

    }

    public void addData(String imageUrl){
        if(mData == null){
            mData = new ArrayList<>();
            mData.add(imageUrl);
        }else
            mData.add(imageUrl);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.photo_gridview_item, null);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.photo_gridview_item_img);
        if(position == getCount() - 1){
            imageView.setBackgroundResource(R.drawable.post_add_image_icon);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        return convertView;
    }
}
