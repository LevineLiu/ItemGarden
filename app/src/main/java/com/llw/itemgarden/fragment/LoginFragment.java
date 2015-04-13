package com.llw.itemgarden.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.llw.itemgarden.ItemGardenApplication;
import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.Constants;
import com.llw.itemgarden.base.FragmentContainerActivity;
import com.llw.itemgarden.model.ServiceResult;
import com.llw.itemgarden.model.User;
import com.llw.itemgarden.volley.GsonRequest;
import com.llw.itemgarden.volley.VolleyErrorHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/3/19.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private final static String TAG = LoginFragment.class.getSimpleName();
    private TextView registerTv;
    private TextView forgetPasswordTv;
    private TextView loginTv;
    private EditText accountEt, passwordEt;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.login_anim_layout);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.login_animation);
        animation.setFillAfter(true);
        linearLayout.setAnimation(animation);
        TextView title = (TextView) view.findViewById(R.id.login_register_title);
        title.setText("账号登陆");
        title.setOnClickListener(this);

        accountEt = (EditText) view.findViewById(R.id.login_account_et);
        passwordEt = (EditText) view.findViewById(R.id.login_password_et);
        registerTv = (TextView) view.findViewById(R.id.register_tv);
        forgetPasswordTv = (TextView) view.findViewById(R.id.forget_password_tv);
        loginTv = (TextView) view.findViewById(R.id.login_tv);
        registerTv.setOnClickListener(this);
        forgetPasswordTv.setOnClickListener(this);
        loginTv.setOnClickListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        ItemGardenApplication.getInstance().cancelRequests(TAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_tv:
                if (getActivity() != null)
                    ((FragmentContainerActivity) getActivity()).addFragment(RegisterPhoneNumberFragment.class, true);
                break;
            case R.id.forget_password_tv:
                break;
            case R.id.login_register_title:
                if (getActivity() != null)
                    getActivity().finish();
                break;
            case R.id.login_tv:
                login();
                break;
        }

    }

    private void login() {
        if (TextUtils.isEmpty(accountEt.getText())) {
            toast("请输入账号", true);
            return;
        } else if (TextUtils.isEmpty(passwordEt.getText())) {
            toast("请输入密码", true);
            return;
        }
        showProgressDialog(false);
        User user = new User();
        user.setTelephone(accountEt.getText().toString());
        user.setPassword(passwordEt.getText().toString());
        String requestBody = new Gson().toJson(user);
        Map<String, String> map = new HashMap<>();
        map.put("user", requestBody);
        GsonRequest<ServiceResult> loginRequest = new GsonRequest<>(Request.Method.POST, Constants.LOGIN_URL, ServiceResult.class,
                map, new Response.Listener<ServiceResult>() {
            @Override
            public void onResponse(ServiceResult result) {
                dismissprogressDialog();
                if (result.isSuccess()) {
                    User user = new Gson().fromJson(result.getObject(), User.class);
                    ItemGardenApplication.serializeUserInfo(user);
                    if(getActivity() != null){
                        getActivity().setResult(FragmentContainerActivity.RESULT_OK);
                        getActivity().finish();
                    }
                }else{
                    toast(result.getObject(), true);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissprogressDialog();
                toast(VolleyErrorHelper.getMessage(volleyError, getActivity()), true);

            }
        });
        ItemGardenApplication.getInstance().addRequestToQueue(loginRequest, TAG);
    }
}
