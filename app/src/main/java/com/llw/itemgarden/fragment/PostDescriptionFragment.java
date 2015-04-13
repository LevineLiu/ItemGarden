package com.llw.itemgarden.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.FragmentContainerActivity;

/**
 * @author Created by liulewen on 2015/4/9.
 */
public class PostDescriptionFragment extends BaseFragment implements View.OnClickListener{
    private Dialog dialog;
    private TextView isNewTv;
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
        isNewTv = (TextView) view.findViewById(R.id.post_second_step_new_tv);
        isNewTv.setOnClickListener(this);
        isNewTv.setText(dialogItems[0]);
        view.findViewById(R.id.next_button).setOnClickListener(this);
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
        }
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
}
