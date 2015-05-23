package com.llw.itemgarden.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.model.Item;

import java.util.List;

/**
 * @author Created by liulewen on 2015/5/13.
 */
public class HomeAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Item> itemList;
    public HomeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemList != null ? itemList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Item item = itemList.get(position);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.home_post_item, null);
            viewHolder = new ViewHolder();
            viewHolder.userAvatar = (ImageView) convertView.findViewById(R.id.home_post_item_avatar);
            viewHolder.userNameTv = (TextView) convertView.findViewById(R.id.home_post_item_user_name);
            viewHolder.contentTv = (TextView) convertView.findViewById(R.id.home_post_item_content);
            viewHolder.marketPriceTv = (TextView) convertView.findViewById(R.id.home_post_item_market_price);
            viewHolder.salePriceTv = (TextView) convertView.findViewById(R.id.home_post_item_sale_price);
            viewHolder.recyclerView = (RecyclerView) convertView.findViewById(R.id.home_post_item_recyclerview);
            viewHolder.locationTv = (TextView) convertView.findViewById(R.id.home_post_item_location);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private static class ViewHolder{
        ImageView userAvatar;
        TextView userNameTv;
        TextView contentTv;
        TextView salePriceTv;
        TextView marketPriceTv;
        RecyclerView recyclerView;
        TextView locationTv;
    }
}
