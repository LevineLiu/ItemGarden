package com.llw.itemgarden.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.llw.itemgarden.ItemGardenApplication;
import com.llw.itemgarden.R;
import com.llw.itemgarden.adpater.GoodsCategoryAdapter;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.Constants;
import com.llw.itemgarden.model.ContentClass;
import com.llw.itemgarden.model.ServiceResult;
import com.llw.itemgarden.volley.GsonRequest;
import com.llw.itemgarden.volley.VolleyErrorHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/4/17.
 */
public class PostGoodsSubCategoryFragment extends BaseFragment {
    private final static String TAG = PostGoodsCategoryFragment.class.getSimpleName();
    private GoodsCategoryAdapter mAdapter;
    private List<ContentClass> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.region_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        ItemGardenApplication.getInstance().cancelRequests(TAG);
    }

    private void initView(View view) {
        final long pid = getArguments().getLong(PostGoodsCategoryFragment.PID);
        String categoryName = getArguments().getString(PostGoodsCategoryFragment.CATEGORY_NAME);
        TextView backTv = (TextView) view.findViewById(R.id.region_back_tv);
        backTv.setText(categoryName);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.region_listview);
        mAdapter = new GoodsCategoryAdapter(getActivity());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContentClass contentClass = (ContentClass) mAdapter.getItem(position);
                if (getActivity() != null) {
                    Intent data = new Intent();
                    data.putExtra(PostDescriptionFragment.GOODS_CATEGORY, contentClass);
                    getActivity().setResult(Activity.RESULT_OK, data);
                    getActivity().finish();
                }
            }
        });
        if(list == null)
            getGoodsSubCategory(pid);
        else
            mAdapter.setData(list);
    }

    private void getGoodsSubCategory(long pid) {
        Map<String, Object> map = new HashMap<>();
        map.put("pid", pid);
        GsonRequest subCategoryRequest = new GsonRequest(Constants.GET_GOODS_SUB_CATEGORY, map,
                new Response.Listener<ServiceResult>() {
                    @Override
                    public void onResponse(ServiceResult serviceResult) {
                        if (serviceResult.isSuccess() && serviceResult.getObject() != null) {
                            list = new Gson().fromJson(serviceResult.getObject(),
                                    new TypeToken<List<ContentClass>>(){}.getType());
                            mAdapter.setData(list);
                        }else{
                            if(!TextUtils.isEmpty(serviceResult.getObject()))
                                toast(serviceResult.getObject(),true);
                            else
                                toast("获取数据失败", true);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (getActivity() != null)
                    toast(VolleyErrorHelper.getMessage(volleyError, getActivity()), true);
            }
        });
        ItemGardenApplication.getInstance().addRequestToQueue(subCategoryRequest, TAG);
    }
}
