package com.llw.itemgarden.fragment;

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
import com.llw.itemgarden.base.FragmentContainerActivity;
import com.llw.itemgarden.utils.UniversalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/4/14.
 */
public class PostGoodsCategoryFragment extends BaseFragment implements View.OnClickListener{
    public final static String CATEGORY = "category";
    public final static String SUB_CATEGORY_LIST = "sub_category_list";
    private ArrayList<String> categoryList = new ArrayList<>();
    private Map<String, ArrayList<String>> subCategoryMap = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.region_fragment, container, false);
        initView(view);
        return view;
    }
    private void initView(View view){
        ListView listView = (ListView)view.findViewById(R.id.region_listview);
        if(categoryList.size() == 0 && subCategoryMap.size() == 0)
            UniversalUtil.getGoodsCategory(getActivity(), categoryList, subCategoryMap);
        final ArrayAdapter adapter= new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, categoryList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = (String) adapter.getItem(position);
                ArrayList<String> subCategoryList = subCategoryMap.get(category);
                Bundle bundle = new Bundle();
                bundle.putString(CATEGORY, category);
                bundle.putStringArrayList(SUB_CATEGORY_LIST, subCategoryList);
                if (getActivity() != null)
                    ((FragmentContainerActivity) getActivity()).addFragment(PostGoodsSubCategoryFragment.class, true, bundle);
            }
        });
        TextView backTv = (TextView)view.findViewById(R.id.region_back_tv);
        backTv.setText("类别");
        backTv.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryList = null;
        subCategoryMap = null;
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
