package com.llw.itemgarden.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llw.itemgarden.R;

/**
 * @author Created by liulewen on 2015/3/19.
 */
public class RegisterPhoneNumberFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_phone_number, container, false);
        initView(view);
        return  view;
    }

    private void initView(View view){

    }
}
