package com.llw.itemgarden.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llw.itemgarden.ItemGardenApplication;
import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.FragmentContainerActivity;
import com.llw.itemgarden.base.MainActivity;
import com.llw.itemgarden.base.ObjectIoOperation;
import com.llw.itemgarden.base.StaticValueHolder;
import com.llw.itemgarden.model.User;
import com.llw.itemgarden.post.PostPhotoFragment;

/**
 * @author Created by liulewen on 2015/4/25.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener{
    private TextView exitLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        User user = StaticValueHolder.getObject(ItemGardenApplication.USER_INFO);
        if(user == null)
            view.findViewById(R.id.exit_login_layout).setVisibility(View.GONE);
        view.findViewById(R.id.setting_title_bar_back_img).setOnClickListener(this);
        exitLogin = (TextView) view.findViewById(R.id.setting_exit_login_tv);
        exitLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_title_bar_back_img:
                if(getActivity() != null)
                    getActivity().finish();
                break;
            case R.id.setting_exit_login_tv:
                ItemGardenApplication.deleteSerializeUserInfo();
                if(getActivity() != null){
                    getActivity().setResult();
                    getActivity().finish();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
            return;
        if(requestCode == MainActivity.LOGIN_REQUEST_CODE){
            exitLogin.setVisibility(View.VISIBLE);
        }
    }
}
