package com.llw.itemgarden.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.FragmentContainerActivity;
import com.llw.itemgarden.base.StaticValueHolder;
import com.llw.itemgarden.utils.UniversalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/4/15.
 */
public class RegionProvinceFragment extends BaseFragment implements View.OnClickListener{
    public final static String PROVINCE = "province";
    public final static String CITY = "city";
    public final static String REGION = "region";
    public final static String CITY_LIST = "city_list";
    public final static String DISTRICT_LIST = "district_list";
    private ArrayList<String> provinceList = new ArrayList<>();
    private Map<String, ArrayList<String>> cityMap = new HashMap<>();
    private Map<String, ArrayList<String>> districtMap = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.region_fragment, container, false);
        initView(view);
        return view;
    }
    private void initView(View view){
        ListView listView = (ListView)view.findViewById(R.id.region_listview);
        if(provinceList.size() == 0 && cityMap.size() == 0)
            UniversalUtil.getRegion(getActivity(), provinceList, cityMap, districtMap);
        StaticValueHolder.putObject(DISTRICT_LIST, districtMap);
        final ArrayAdapter adapter= new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, provinceList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String province = (String) adapter.getItem(position);
                ArrayList<String> cityList = cityMap.get(province);
                Bundle bundle = new Bundle();
                bundle.putString(PROVINCE, province);
                bundle.putStringArrayList(CITY_LIST, cityList);
                if (getActivity() != null)
                    ((FragmentContainerActivity) getActivity()).addFragment(RegionCityFragment.class, true, bundle);
            }
        });
        view.findViewById(R.id.region_back_tv).setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        StaticValueHolder.remove(DISTRICT_LIST);
        provinceList = null;
        cityMap = null;
        districtMap = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.region_back_tv:
                if(getActivity() != null)
                    getActivity().finish();
                break;
        }
    }
}
