package com.llw.itemgarden.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.llw.itemgarden.ItemGardenApplication;
import com.llw.itemgarden.R;
import com.llw.itemgarden.base.Constants;
import com.llw.itemgarden.base.FragmentContainerActivity;
import com.llw.itemgarden.base.StaticValueHolder;
import com.llw.itemgarden.model.Item;
import com.llw.itemgarden.model.ServiceResult;
import com.llw.itemgarden.volley.GsonRequest;
import com.llw.itemgarden.volley.VolleyErrorHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/4/9.
 */
public class PostPriceFragment extends PostFragment implements View.OnClickListener{
    private final static String TAG = PostPriceFragment.class.getSimpleName();
    private final static int REQUEST_CODE = 200;
    private TextView locationTv;
    private EditText salePriceEt,originalPriceEt,transportPriceEt;
    private TextView previousTv;
    private TextView nextTv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_post_third_step, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        ItemGardenApplication.getInstance().cancelRequests(TAG);
    }

    private void initView(View view){
        TextView title = (TextView) view.findViewById(R.id.post_header_title);
        title.setText("开个价吧！骚年");
        locationTv = (TextView) view.findViewById(R.id.post_location_tv);
        salePriceEt = (EditText) view.findViewById(R.id.post_sale_price_et);
        originalPriceEt = (EditText) view.findViewById(R.id.post_original_price_et);
        transportPriceEt = (EditText) view.findViewById(R.id.post_transport_price_et);
        previousTv = (TextView) view.findViewById(R.id.previous_button);
        nextTv = (TextView) view.findViewById(R.id.next_button);

        locationTv.setText("北京市");
        nextTv.setText("发布");
        previousTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
        ImageView regionImageView = (ImageView) view.findViewById(R.id.region_img);
        regionImageView.setOnClickListener(this);
        view.findViewById(R.id.post_close_img).setOnClickListener(this);

    }

    private void publishPost(Item item){
        Map<String, Object> map = new HashMap<>();
        String itemString = new Gson().toJson(item);
        map.put("item", itemString);
        GsonRequest publishRequest = new GsonRequest(Constants.PUBLISH_ITEM, map,
                new Response.Listener<ServiceResult>() {
                    @Override
                    public void onResponse(ServiceResult serviceResult) {
                        if(serviceResult.isSuccess()){
                            toast("发布成功", true);
                        }else{
                            if (serviceResult.getObject() != null)
                                toast(serviceResult.getObject(), true);
                            else
                                toast("发布失败", true);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(getActivity() != null)
                    toast(VolleyErrorHelper.getMessage(volleyError, getActivity()), true);
            }
        });
        ItemGardenApplication.getInstance().addRequestToQueue(publishRequest, TAG);
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
                    ((FragmentContainerActivity)getActivity()).switchFragment(PostPriceFragment.class,
                            PostDescriptionFragment.class, null);
                break;
            case R.id.next_button:
                if(TextUtils.isEmpty(salePriceEt.getText())){
                    toast("无价之宝！别闹了，给宝贝定个价呗", true);
                    return;
                }
                Item item = StaticValueHolder.getObject(ItemGardenApplication.POST_ITEM);
                item.setNewPrice(Double.parseDouble(salePriceEt.getText().toString()));
                if(!TextUtils.isEmpty(originalPriceEt.getText()))
                    item.setOldPrive(Double.parseDouble(originalPriceEt.getText().toString()));
                if(!TextUtils.isEmpty(transportPriceEt.getText()))
                    item.setCarryPrice(Double.parseDouble(transportPriceEt.getText().toString()));
                publishPost(item);
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
