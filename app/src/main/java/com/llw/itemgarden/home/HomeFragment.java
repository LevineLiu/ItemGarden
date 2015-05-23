package com.llw.itemgarden.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.llw.itemgarden.ItemGardenApplication;
import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.Constants;
import com.llw.itemgarden.base.FragmentContainerActivity;
import com.llw.itemgarden.model.Item;
import com.llw.itemgarden.model.ServiceResult;
import com.llw.itemgarden.view.PullToRefreshListView;
import com.llw.itemgarden.volley.GsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/3/17.
 */
public class HomeFragment extends BaseFragment{
    private final static String TAG = HomeFragment.class.getSimpleName();
    private LocationClient mLocationClient;
    private BDLocationListener mLocationListener;
    private List<Item> items;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        initView(view);
        return  view;
    }

    @Override
    public void onStop() {
        super.onStop();
        ItemGardenApplication.getInstance().cancelRequests(TAG);
    }

    private void initView(View view){
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");//返回坐标类型，返回国测局经纬度坐标系 coor=gcj02，返回百度墨卡托坐标系 coor=bd09，返回百度经纬度坐标系 coor=bd09ll
        option.setIsNeedAddress(true);
        option.setProdName(getActivity().getApplicationContext().getPackageName());//产品线名称
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

        String test[] = {"111","222","333","444","555","666","777","888","999","10","11","12","13","14","15"};
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, test);
        PullToRefreshListView listView = (PullToRefreshListView)view.findViewById(R.id.refresh_list_view);
        listView.setAdapter(adapter);
        listView.setOnLoadMoreListener(new PullToRefreshListView.onLoadMoreListener() {
            @Override
            public void onLoad() {

            }
        });
        listView.setOnRefreshListener(new PullToRefreshListView.onRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLocationClient.requestLocation();
            }
        });

        view.findViewById(R.id.menu_search_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    FragmentContainerActivity.startActivity(getActivity(), HomeMenuSearchFragment.class, null, false);
            }
        });
        if(items == null)
            getGoods(1);
    }

    /**
     * 获取物品
     */
    private void getGoods(int page){
        Map<String, Object> map = new HashMap<>();
        map.put("page", page + "");
        GsonRequest getGoodsRequest = new GsonRequest(Constants.FIND_ITEM_BY_NAME, map,
                new Response.Listener<ServiceResult>() {
            @Override
            public void onResponse(ServiceResult serviceResult) {
                if(serviceResult.isSuccess() && serviceResult.getObject() != null) {
                    try {
                        String result = new JSONObject(serviceResult.getObject()).getString("rows");
                        items = new Gson().fromJson(result,
                                new TypeToken<List<Item>>() {
                                }.getType());
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                toast("获取数据失败", true);
            }
        });
        ItemGardenApplication.getInstance().addRequestToQueue(getGoodsRequest, TAG);
    }

    private class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String city = bdLocation.getCity();
        }
    }
}
