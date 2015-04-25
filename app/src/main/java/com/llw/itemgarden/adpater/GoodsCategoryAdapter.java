package com.llw.itemgarden.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.model.ContentClass;

import java.util.List;

/**
 * @author Created by liulewen on 2015/4/23.
 */
public class GoodsCategoryAdapter extends BaseAdapter{
    private List<ContentClass> mData;
    private LayoutInflater mInflater;

    public GoodsCategoryAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<ContentClass> list){
        mData = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
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
        convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
        TextView textView = (TextView)convertView.findViewById(android.R.id.text1);
        textView.setText(mData.get(position).getClassName());
        textView.setBackgroundResource(R.drawable.simple_list_item_selector);
        return convertView;
    }
}
