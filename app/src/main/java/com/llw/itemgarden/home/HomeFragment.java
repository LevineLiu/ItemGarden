package com.llw.itemgarden.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.FragmentContainerActivity;
import com.llw.itemgarden.view.PullToRefreshListView;

/**
 * @author Created by liulewen on 2015/3/17.
 */
public class HomeFragment extends BaseFragment{
    private LocationClient mLocationClient;
    private BDLocationListener mLocationListener;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        initView(view);
        return  view;
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
                if(getActivity() != null)
                    FragmentContainerActivity.startActivity(getActivity(), HomeMenuSearchFragment.class, null, false);
            }
        });
    }

    /**
     * 获取物品
     */
    private void getGoods(){

    }

    private class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String city = bdLocation.getCity();
        }
    }
}
