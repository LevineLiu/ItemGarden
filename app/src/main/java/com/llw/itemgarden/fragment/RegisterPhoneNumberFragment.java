package com.llw.itemgarden.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.FragmentContainerActivity;

/**
 * @author Created by liulewen on 2015/3/19.
 */
public class RegisterPhoneNumberFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_phone_number, container, false);
        initView(view);
        return  view;
    }

    private void initView(View view){
        RelativeLayout regionLayout = (RelativeLayout) view.findViewById(R.id.register_region_layout);
        regionLayout.setOnClickListener(this);
        Button nextButton = (Button) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_region_layout:
                break;
            case R.id.next_button:
                FragmentContainerActivity.startActivity(getActivity(), RegisterIdentifyCodeFragment.class, null, false);
                break;
        }
    }
}
