package com.llw.itemgarden.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.llw.itemgarden.ItemGardenApplication;
import com.llw.itemgarden.R;
import com.llw.itemgarden.adpater.HomeMenuSearchAdapter;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.Constants;
import com.llw.itemgarden.model.ContentClass;
import com.llw.itemgarden.model.ServiceResult;
import com.llw.itemgarden.volley.GsonRequest;
import com.llw.itemgarden.volley.VolleyErrorHelper;

import java.util.List;

/**
 * @author Created by liulewen on 2015/5/12.
 */
public class HomeMenuSearchFragment extends BaseFragment{
    private final static String TAG = HomeMenuSearchFragment.class.getSimpleName();
    private HomeMenuSearchAdapter mAdapter;
    private List<ContentClass> parentCategoryList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_menu_search_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        ItemGardenApplication.getInstance().cancelRequests(TAG);
    }

    private void initView(View view){
        view.findViewById(R.id.search_right_tv).setVisibility(View.GONE);
        view.findViewById(R.id.search_title_bar_back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().finish();
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.menu_search_list_view);
        mAdapter = new HomeMenuSearchAdapter(getActivity());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });

        if(parentCategoryList == null)
            getGoodsCategory();
        else
            mAdapter.setData(parentCategoryList);
    }

    private void getGoodsCategory() {
        GsonRequest goodsCategoryRequest = new GsonRequest(Constants.GET_GOODS_CATEGORY,
                new Response.Listener<ServiceResult>() {
                    @Override
                    public void onResponse(ServiceResult serviceResult) {
                        if (serviceResult.isSuccess() && serviceResult.getObject() != null) {
                            parentCategoryList = new Gson().fromJson(serviceResult.getObject(),
                                    new TypeToken<List<ContentClass>>() {
                                    }.getType());
                            mAdapter.setData(parentCategoryList);
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

}
