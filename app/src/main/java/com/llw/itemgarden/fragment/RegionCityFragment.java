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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by liulewen on 2015/4/16.
 */
public class RegionCityFragment extends BaseFragment {

    private ArrayList<String> cityList;
    private ArrayList<String> districtList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.region_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        cityList = getArguments().getStringArrayList(RegionProvinceFragment.CITY_LIST);
        districtList = getArguments().getStringArrayList(RegionProvinceFragment.DISTRICT_LIST);
        final String province = getArguments().getString(RegionProvinceFragment.PROVINCE);
        ListView listView = (ListView) view.findViewById(R.id.region_listview);
        final ArrayAdapter adapter= new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, cityList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = (String) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(RegionProvinceFragment.PROVINCE, province);
                bundle.putString(RegionProvinceFragment.CITY, city);
                bundle.putStringArrayList(RegionProvinceFragment.DISTRICT_LIST, districtList);
                if (getActivity() != null)
                    ((FragmentContainerActivity) getActivity()).addFragment(RegionDistrictFragment.class, true, bundle);
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
