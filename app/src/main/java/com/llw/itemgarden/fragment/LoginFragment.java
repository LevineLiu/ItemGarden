package com.llw.itemgarden.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.MainActivity;

/**
 * @author Created by liulewen on 2015/3/19.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{
    private TextView registerTv;
    private TextView forgetPasswordTv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        TextView title  = (TextView) view.findViewById(R.id.login_register_title);
        title.setText("账号登陆");
        initView(view);
        return view;
    }

    private void initView(View view){
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.login_anim_layout);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.login_animation);
        animation.setFillAfter(true);
        linearLayout.setAnimation(animation);
        TextView title  = (TextView) view.findViewById(R.id.login_register_title);
        title.setText("账号登陆");

        registerTv = (TextView) view.findViewById(R.id.register_tv);
        forgetPasswordTv = (TextView) view.findViewById(R.id.forget_password_tv);
        registerTv.setOnClickListener(this);
        forgetPasswordTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_tv:
                if(getActivity() != null)
                    ((MainActivity)getActivity()).switchFragment(LoginFragment.class, RegisterPhoneNumberFragment.class, null);
                break;
            case R.id.forget_password_tv:
                break;
        }

    }
}
