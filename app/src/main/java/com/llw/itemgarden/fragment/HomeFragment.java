package com.llw.itemgarden.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.llw.itemgarden.R;
import com.llw.itemgarden.view.PullToRefreshListView;

/**
 * @author Created by liulewen on 2015/3/17.
 */
public class HomeFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        initView(view);
        return  view;
    }

    private void initView(View view){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, new String[]{"11111","55555"});
        PullToRefreshListView listView = (PullToRefreshListView)view.findViewById(R.id.refresh_listview);
        listView.setAdapter(adapter);

    }
}
