package com.llw.itemgarden.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;

/**
 * @author Created by liulewen on 2015/4/28.
 */
public class MessageFragment extends BaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, container, false);
        initView(view);
        return  view;
    }

    private void initView(View view){
        ((TextView)view.findViewById(R.id.title_bar_title)).setText("消息");
    }
}
