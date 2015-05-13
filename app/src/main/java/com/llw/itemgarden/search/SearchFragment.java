package com.llw.itemgarden.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;

/**
 * @author Created by liulewen on 2015/4/29.
 */
public class SearchFragment extends BaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        return view;
    }
}
