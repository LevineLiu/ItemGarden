package com.llw.itemgarden.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.StaticValueHolder;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/4/16.
 */
public class RegionDistrictFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.region_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        final String province = getArguments().getString(RegionProvinceFragment.PROVINCE);
        final String city = getArguments().getString(RegionProvinceFragment.CITY);
        Map<String, ArrayList<String>> map = StaticValueHolder.getObject(RegionProvinceFragment.DISTRICT_LIST);
        ArrayList<String> districtList;
        if(map != null){
            districtList = map.get(city);
        }else
            return;
        ListView listView = (ListView) view.findViewById(R.id.region_listview);
        final ArrayAdapter adapter= new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, districtList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String district = (String) adapter.getItem(position);
                String region = province + city + district;
                Intent intent = new Intent();
                intent.putExtra(RegionProvinceFragment.REGION, region);
                if (getActivity() != null) {
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }

            }
        });
        view.findViewById(R.id.region_back_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
