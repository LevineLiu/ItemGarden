package com.llw.itemgarden.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;

/**
 * @author Created by liulewen on 2015/4/9.
 */
public class PostPriceFragment extends BaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_post_third_step, container, false);
        return view;
    }
}
