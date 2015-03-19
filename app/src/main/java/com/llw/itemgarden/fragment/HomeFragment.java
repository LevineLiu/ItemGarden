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
        String test[] = {"111","222","333","444","555","666","777","888","999","10","11","12","13","14","15"};
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, test);
        PullToRefreshListView listView = (PullToRefreshListView)view.findViewById(R.id.refresh_listview);
        listView.setAdapter(adapter);
        listView.setOnLoadMoreListener(new PullToRefreshListView.onLoadMoreListener() {
            @Override
            public void onLoad() {

            }
        });
        listView.setOnRefreshListener(new PullToRefreshListView.onRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

    }
}
