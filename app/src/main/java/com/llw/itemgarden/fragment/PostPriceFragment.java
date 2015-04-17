package com.llw.itemgarden.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.FragmentContainerActivity;

/**
 * @author Created by liulewen on 2015/4/9.
 */
public class PostPriceFragment extends PostFragment implements View.OnClickListener{
    private final static int REQUEST_CODE = 200;
    private TextView locationTv;
    private TextView salePriceTv;
    private TextView previousTv;
    private TextView nextTv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_post_third_step, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        locationTv = (TextView) view.findViewById(R.id.post_location_tv);
        salePriceTv = (TextView) view.findViewById(R.id.post_sale_price_tv);
        previousTv = (TextView) view.findViewById(R.id.previous_button);
        nextTv = (TextView) view.findViewById(R.id.next_button);
        nextTv.setText("发布");
        previousTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
        ImageView regionImageView = (ImageView) view.findViewById(R.id.region_img);
        regionImageView.setOnClickListener(this);
        view.findViewById(R.id.post_close_img).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.region_img:
                FragmentContainerActivity.startActivityForResult(PostPriceFragment.this, RegionProvinceFragment.class,
                        false, REQUEST_CODE, null);
                break;
            case R.id.previous_button:
                if(getActivity() != null)
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.next_button:
                if(TextUtils.isEmpty(salePriceTv.getText()))
                    toast("无价之宝！别闹了，给宝贝定个价呗", true);
                break;
            case R.id.post_close_img:
                if(getActivity() != null)
                    showExitDialog(getActivity());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode){
            case REQUEST_CODE:
                String region = data.getStringExtra(RegionProvinceFragment.REGION);
                locationTv.setText(region);
                break;
        }
    }
}
