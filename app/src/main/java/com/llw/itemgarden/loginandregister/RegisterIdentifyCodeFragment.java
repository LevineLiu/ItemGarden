package com.llw.itemgarden.loginandregister;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.FragmentContainerActivity;

/**
 * @author Created by liulewen on 2015/3/20.
 */
public class RegisterIdentifyCodeFragment extends BaseFragment implements View.OnClickListener{
    private EditText codeEditText;
    private TextView getCodeTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_identify_code, container, false);
        initView(view);
        return  view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null)
            countDownTimer.cancel();
        countDownTimer = null;
    }

    private void initView(View view){
        TextView title = (TextView) view.findViewById(R.id.login_register_title);
        title.setText("填写校验码");
        title.setOnClickListener(this);

        codeEditText = (EditText) view.findViewById(R.id.identify_code_et);
        getCodeTextView = (TextView) view.findViewById(R.id.get_code_tv);
        getCodeTextView.setOnClickListener(this);
        TextView nextButton = (TextView) view.findViewById(R.id.identify_code_next_button);
        nextButton.setOnClickListener(this);
        countDownTimer.start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_register_title:
                if(getActivity() != null)
                    getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.identify_code_next_button:
                if(TextUtils.isEmpty(codeEditText.getText()))
                    toast("请输入验证码", true);
                else{
                    String phoneNumber = getArguments().getString(RegisterPhoneNumberFragment.PHONE_NUMBER);
                    RegisterPasswordFragment fragment = new RegisterPasswordFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(RegisterPhoneNumberFragment.PHONE_NUMBER, phoneNumber);
                    fragment.setArguments(bundle);
                    ((FragmentContainerActivity)getActivity()).addFragment(fragment, true);
                }

                break;
        }
    }

    private CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            getCodeTextView.setEnabled(false);
            getCodeTextView.setClickable(false);
            getCodeTextView.setBackgroundResource(R.drawable.mini_smsbtn_disable);
            getCodeTextView.setText(millisUntilFinished/1000 + "秒后重发");
        }

        @Override
        public void onFinish() {
            getCodeTextView.setEnabled(true);
            getCodeTextView.setClickable(true);
            getCodeTextView.setBackgroundResource(R.drawable.mini_bg);
            getCodeTextView.setText("重发校验码");
        }
    };
}
