package com.llw.itemgarden.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.llw.itemgarden.ItemGardenApplication;
import com.llw.itemgarden.R;
import com.llw.itemgarden.adpater.GoodsCategoryAdapter;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.Constants;
import com.llw.itemgarden.base.FragmentContainerActivity;
import com.llw.itemgarden.model.ContentClass;
import com.llw.itemgarden.model.ServiceResult;
import com.llw.itemgarden.utils.UniversalUtil;
import com.llw.itemgarden.volley.GsonRequest;
import com.llw.itemgarden.volley.VolleyErrorHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/4/14.
 */
public class PostGoodsCategoryFragment extends BaseFragment implements View.OnClickListener {
    private final static String TAG = PostGoodsCategoryFragment.class.getSimpleName();
    public final static String PID = "pid";
    public final static String CATEGORY_NAME = "category_name";
    private GoodsCategoryAdapter mAdapter;
    private List<ContentClass> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.region_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.region_listview);
        mAdapter = new GoodsCategoryAdapter(getActivity());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContentClass content = (ContentClass) mAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putLong(PID, content.getId());
                bundle.putString(CATEGORY_NAME, content.getClassName());
                if (getActivity() != null)
                    ((FragmentContainerActivity) getActivity()).addFragment(PostGoodsSubCategoryFragment.class, true, bundle);
            }
        });
        TextView backTv = (TextView) view.findViewById(R.id.region_back_tv);
        backTv.setText("类别");
        backTv.setOnClickListener(this);
        if (list == null)
            getGoodsCategory();
        else
            mAdapter.setData(list);
    }

    private void getGoodsCategory() {
        GsonRequest goodsCategoryRequest = new GsonRequest(Constants.GET_GOODS_CATEGORY,
                new Response.Listener<ServiceResult>() {
                    @Override
                    public void onResponse(ServiceResult serviceResult) {
                        if (serviceResult.isSuccess() && serviceResult.getObject() != null) {
                            list = new Gson().fromJson(serviceResult.getObject(),
                                    new TypeToken<List<ContentClass>>() {
                                    }.getType());
                            mAdapter.setData(list);
                        }else if (!TextUtils.isEmpty(serviceResult.getObject()))
                            toast(serviceResult.getObject(), true);
                        else
                            toast("获取数据失败", true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (getActivity() != null)
                    toast(VolleyErrorHelper.getMessage(volleyError, getActivity()), true);
            }
        });
        ItemGardenApplication.getInstance().addRequestToQueue(goodsCategoryRequest, TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
        ItemGardenApplication.getInstance().cancelRequests(TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.region_back_tv:
                if (getActivity() != null)
                    getActivity().finish();
                break;
        }
    }
}
