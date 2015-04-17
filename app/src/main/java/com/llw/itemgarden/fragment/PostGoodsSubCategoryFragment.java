package com.llw.itemgarden.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;

import java.util.ArrayList;

/**
 * @author Created by liulewen on 2015/4/17.
 */
public class PostGoodsSubCategoryFragment extends BaseFragment{
    private ArrayList<String> subCategoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.region_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        subCategoryList = getArguments().getStringArrayList(PostGoodsCategoryFragment.SUB_CATEGORY_LIST);
        final String category = getArguments().getString(PostGoodsCategoryFragment.CATEGORY);
        TextView backTv = (TextView)view.findViewById(R.id.region_back_tv);
        backTv.setText(category);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.region_listview);
        final ArrayAdapter adapter= new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, subCategoryList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String subCategory = (String)adapter.getItem(position);
                String result = subCategory;
                if(getActivity() != null){
                    Intent data = new Intent();
                    data.putExtra(PostDescriptionFragment.GOODS_CATEGORY, result);
                    getActivity().setResult(Activity.RESULT_OK, data);
                    getActivity().finish();
                }
            }
        });


    }
}
