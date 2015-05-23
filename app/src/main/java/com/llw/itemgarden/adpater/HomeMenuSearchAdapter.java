package com.llw.itemgarden.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.model.ContentClass;

import java.util.List;

/**
 * @author Created by liulewen on 2015/5/23.
 */
public class HomeMenuSearchAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<ContentClass> categoryList;
    public HomeMenuSearchAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<ContentClass> list){
        categoryList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        ContentClass category = categoryList.get(position);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.home_menu_search_item, null);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.goods_category_img);
            viewHolder.contentTv = (TextView) convertView.findViewById(R.id.home_menu_search_item_content);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();
        switch (position){
            case 0:
                viewHolder.icon.setBackgroundResource(R.drawable.women_dress);
                break;
            case 1:
                viewHolder.icon.setBackgroundResource(R.drawable.men_dress);
                break;
            case 2:
                viewHolder.icon.setBackgroundResource(R.drawable.shoes);
                break;
            case 3:
                viewHolder.icon.setBackgroundResource(R.drawable.phone);
                break;
            case 4:
                viewHolder.icon.setBackgroundResource(R.drawable.camera);
                break;
            case 5:
                viewHolder.icon.setBackgroundResource(R.drawable.computer);
                break;
            case 6:
                viewHolder.icon.setBackgroundResource(R.drawable.digit);
                break;
            case 7:
                viewHolder.icon.setBackgroundResource(R.drawable.luxury);
                break;
            case 8:
                viewHolder.icon.setBackgroundResource(R.drawable.men_dress);
                break;
            case 9:
                viewHolder.icon.setBackgroundResource(R.drawable.cosmetology);
                break;
            case 10:
                viewHolder.icon.setBackgroundResource(R.drawable.commodity);
                break;
            case 11:
                viewHolder.icon.setBackgroundResource(R.drawable.food);
                break;
            case 12:
                viewHolder.icon.setBackgroundResource(R.drawable.electrical_equipment);
                break;
            case 13:
                viewHolder.icon.setBackgroundResource(R.drawable.baby_appliance);
                break;
            case 14:
                viewHolder.icon.setBackgroundResource(R.drawable.live_service);
                break;
            case 15:
                viewHolder.icon.setBackgroundResource(R.drawable.pet);
                break;
            case 16:
                viewHolder.icon.setBackgroundResource(R.drawable.book);
                break;
            case 17:
                viewHolder.icon.setBackgroundResource(R.drawable.bike);
                break;
            case 18:
                viewHolder.icon.setBackgroundResource(R.drawable.jewelry);
                break;
            case 19:
                viewHolder.icon.setBackgroundResource(R.drawable.antique);
                break;
            case 20:
                viewHolder.icon.setBackgroundResource(R.drawable.other);
                break;

        }
        viewHolder.contentTv.setText(category.getClassName());
        return convertView;
    }

    private static class ViewHolder{
        ImageView icon;
        TextView contentTv;
    }
}
