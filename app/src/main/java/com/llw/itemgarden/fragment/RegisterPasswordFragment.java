package com.llw.itemgarden.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.llw.itemgarden.ItemGardenApplication;
import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.Constants;
import com.llw.itemgarden.model.ServiceResult;
import com.llw.itemgarden.model.User;
import com.llw.itemgarden.volley.GsonRequest;
import com.llw.itemgarden.volley.VolleyErrorHelper;

/**
 * @author Created by liulewen on 2015/3/23.
 */
public class RegisterPasswordFragment extends BaseFragment implements View.OnClickListener{
    private final static String TAG = RegisterPasswordFragment.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_password, container, false);
        initView(view);
        return  view;
    }

    private void initView(View view){
        TextView title = (TextView)view.findViewById(R.id.login_register_title);
        title.setText("设置密码");
        title.setOnClickListener(this);
        final EditText passwordEt = (EditText) view.findViewById(R.id.password_et);
        final EditText confirmPasswordEt = (EditText) view.findViewById(R.id.confirm_password_et);
        TextView registerTv = (TextView) view.findViewById(R.id.register_tv);
        final String phoneNumber = getArguments().getString(RegisterPhoneNumberFragment.PHONE_NUMBER);
        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(passwordEt.getText())){
                    toast("请输入密码", true);
                    return;
                }
                else if(TextUtils.isEmpty(confirmPasswordEt.getText())){
                    toast("请确认密码", true);
                    return;
                }else {
                    if(!passwordEt.getText().toString().equals(confirmPasswordEt.getText().toString())){
                        toast("密码不一致", true);
                        return;
                    }
                }
                User user = new User();
                user.setTelephone(phoneNumber);
                user.setPassword(confirmPasswordEt.getText().toString());
                String requestBody = new Gson().toJson(user);
                GsonRequest<ServiceResult>registerRequest = new GsonRequest<ServiceResult>(Request.Method.POST, Constants.REGISTER_URL, ServiceResult.class,
                        requestBody, new Response.Listener<ServiceResult>() {
                    @Override
                    public void onResponse(ServiceResult result) {
                        String message = result.getMessage();
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyErrorHelper.getMessage(volleyError,getActivity());

                    }
                });
                ItemGardenApplication.getInstance().addRequestToQueue(registerRequest, TAG);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_register_title:
                if(getActivity() != null)
                    getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ItemGardenApplication.getInstance().cancelRequests(TAG);
    }
}
