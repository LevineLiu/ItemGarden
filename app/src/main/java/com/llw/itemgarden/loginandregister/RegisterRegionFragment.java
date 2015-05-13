package com.llw.itemgarden.loginandregister;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.view.SideBar;

/**
 * @author Created by liulewen on 2015/3/20.
 */
public class RegisterRegionFragment extends BaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_region_fragment, container, false);
        return  view;
    }

    private void initView(View view){
        TextView title = (TextView) view.findViewById(R.id.login_register_title);
        title.setText("选择地区");
        ListView listView = (ListView) view.findViewById(R.id.region_listview);
        TextView disPlayTextView = (TextView) view.findViewById(R.id.region_display_tv);
        SideBar sideBar = (SideBar) view.findViewById(R.id.region_sideBar);
        sideBar.setDisplayTextView(disPlayTextView);
        sideBar.setOnLetterTouchLisener(new SideBar.OnLetterTouchListener() {
            @Override
            public void onLetterTouch(String letter) {

            }
        });
    }
}
