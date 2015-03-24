package com.llw.itemgarden.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.FragmentContainerActivity;

/**
 * @author Created by liulewen on 2015/3/19.
 */
public class RegisterPhoneNumberFragment extends BaseFragment implements View.OnClickListener{
    private EditText phoneNumberEt;
    public final static String PHONE_NUMBER = "phone_number";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_phone_number, container, false);
        initView(view);
        return  view;
    }

    private void initView(View view){
        ImageView backImageView = (ImageView) view.findViewById(R.id.title_bar_back_img);
        backImageView.setOnClickListener(this);
        TextView title = (TextView) view.findViewById(R.id.login_register_title);
        title.setText("注册");

        RelativeLayout regionLayout = (RelativeLayout) view.findViewById(R.id.register_region_layout);
        regionLayout.setOnClickListener(this);
        TextView nextButton = (TextView) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);
        phoneNumberEt = (EditText) view.findViewById(R.id.phone_number_et);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_region_layout:
                break;
            case R.id.next_button:
                if(TextUtils.isEmpty(phoneNumberEt.getText()))
                    toast("请输入电话号码", true);
                else{
                    RegisterIdentifyCodeFragment fragment = new RegisterIdentifyCodeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(PHONE_NUMBER, phoneNumberEt.getText().toString());
                    fragment.setArguments(bundle);
                    ((FragmentContainerActivity)getActivity()).addFragment(fragment, true);
                }
                break;
            case R.id.title_bar_back_img:
                if(getActivity() != null)
                    getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
