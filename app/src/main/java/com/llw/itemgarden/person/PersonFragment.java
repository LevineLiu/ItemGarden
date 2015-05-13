package com.llw.itemgarden.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.llw.itemgarden.ItemGardenApplication;
import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.FragmentContainerActivity;
import com.llw.itemgarden.base.MainActivity;
import com.llw.itemgarden.base.StaticValueHolder;
import com.llw.itemgarden.loginandregister.LoginFragment;
import com.llw.itemgarden.model.User;

/**
 * @author Created by liulewen on 2015/4/25.
 */
public class PersonFragment extends BaseFragment implements View.OnClickListener {
    private TextView loginTv;
    private TextView userName;
    private TextView descriptionTv;
    private TextView loginTipTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        userName = (TextView) view.findViewById(R.id.person_user_name);
        descriptionTv = (TextView) view.findViewById(R.id.person_description);
        loginTv = (TextView) view.findViewById(R.id.person_login_tv);
        loginTipTv = (TextView) view.findViewById(R.id.login_tip_tv);
        User user = StaticValueHolder.getObject(ItemGardenApplication.USER_INFO);
        if (user == null) {
            userName.setVisibility(View.GONE);
            descriptionTv.setVisibility(View.GONE);
            loginTipTv.setVisibility(View.VISIBLE);
            loginTv.setVisibility(View.VISIBLE);
        }
        loginTv.setOnClickListener(this);
        view.findViewById(R.id.person_setting_layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_setting_layout:
                FragmentContainerActivity.startActivityForResult(PersonFragment.this, SettingFragment.class,
                        false, MainActivity.EXIT_LOGIN_REQUEST_CODE, null);
                break;
            case R.id.person_login_tv:
                FragmentContainerActivity.startActivityForResult(PersonFragment.this, LoginFragment.class,
                        false, MainActivity.LOGIN_REQUEST_CODE, null);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode){
            case MainActivity.EXIT_LOGIN_REQUEST_CODE:
                userName.setVisibility(View.GONE);
                descriptionTv.setVisibility(View.GONE);
                loginTipTv.setVisibility(View.VISIBLE);
                loginTv.setVisibility(View.VISIBLE);
                break;
            case MainActivity.LOGIN_REQUEST_CODE:
                userName.setVisibility(View.VISIBLE);
                descriptionTv.setVisibility(View.VISIBLE);
                loginTipTv.setVisibility(View.GONE);
                loginTv.setVisibility(View.GONE);
                break;
        }
    }
}
