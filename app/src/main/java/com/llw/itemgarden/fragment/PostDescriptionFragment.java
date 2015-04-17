package com.llw.itemgarden.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.FragmentContainerActivity;

/**
 * @author Created by liulewen on 2015/4/9.
 */
public class PostDescriptionFragment extends PostFragment implements View.OnClickListener{
    private final static int REQUEST_CODE = 200;
    public final static String GOODS_CATEGORY = "goods_category";
    private Dialog dialog;
    private TextView isNewTv;
    private TextView categoryTv;
    private EditText goodsTitle;
    private EditText goodsDescription;
    final String dialogItems[] = {"非全新", "全新"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_post_second_step, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
        dialog = null;
    }

    private void initView(View view){
        TextView title = (TextView) view.findViewById(R.id.post_header_title);
        title.setText("讲讲宝贝的故事啦");
        categoryTv = (TextView) view.findViewById(R.id.post_second_step_category_tv);
        categoryTv.setOnClickListener(this);
        isNewTv = (TextView) view.findViewById(R.id.post_second_step_new_tv);
        isNewTv.setOnClickListener(this);
        isNewTv.setText(dialogItems[0]);
        goodsTitle = (EditText) view.findViewById(R.id.post_goods_title_et);
        goodsDescription = (EditText) view.findViewById(R.id.post_goods_description_et);
        view.findViewById(R.id.next_button).setOnClickListener(this);
        view.findViewById(R.id.post_close_img).setOnClickListener(this);

    }

    private void showDialog(){
        if(dialog == null){
            if(getActivity() == null)
                return;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("使用情况");
            builder.setItems(dialogItems, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isNewTv.setText(dialogItems[which]);
                }
            });
            dialog = builder.create();
            dialog.show();
        }else
            dialog.show();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.post_second_step_new_tv:
                showDialog();
                break;
            case R.id.next_button:
                if(getActivity() != null){
                    ((FragmentContainerActivity)getActivity()).addFragment(PostPriceFragment.class, true);
                }
                break;
            case R.id.post_close_img:
                if(getActivity() != null)
                    showExitDialog(getActivity());
                break;
            case R.id.post_second_step_category_tv:
                FragmentContainerActivity.startActivityForResult(PostDescriptionFragment.this,
                        PostGoodsCategoryFragment.class, false, REQUEST_CODE, null);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode){
            case REQUEST_CODE:
                String goodsCategory = data.getStringExtra(GOODS_CATEGORY);
                categoryTv.setText("分类:"+goodsCategory);
                break;
        }
    }
}
