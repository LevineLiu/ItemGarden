package com.llw.itemgarden.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.llw.itemgarden.ItemGardenApplication;
import com.llw.itemgarden.base.BaseFragment;
import com.llw.itemgarden.base.Constants;
import com.llw.itemgarden.base.FragmentContainerActivity;
import com.llw.itemgarden.base.StaticValueHolder;
import com.llw.itemgarden.model.Item;
import com.llw.itemgarden.model.ServiceResult;
import com.llw.itemgarden.volley.GsonRequest;
import com.llw.itemgarden.volley.VolleyErrorHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/4/14.
 */
public class PostFragment extends BaseFragment {
    private static final String TAG = PostFragment.class.getSimpleName();
    private Dialog dialog;

    @Override
    public void onStop() {
        super.onStop();
        ItemGardenApplication.getInstance().cancelRequests(TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        deleteGoods();
        StaticValueHolder.remove(ItemGardenApplication.POST_ITEM);
        dialog = null;
    }

    public void showExitDialog(final Context context) {
        if(context == null)
            return;
        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("是否退出");
            builder.setMessage("步骤还没完成，是否退出");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((FragmentContainerActivity)context).finish();
                }
            });
            builder.setNegativeButton("否", null);
            dialog = builder.create();
            dialog.show();
        }else
            dialog.show();
    }

    private void deleteGoods(){
        Item item = StaticValueHolder.getObject(ItemGardenApplication.POST_ITEM);
        if(item == null){
            if(getActivity() != null)
                getActivity().finish();
            return;
        }
        long itemId = item.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("id", itemId);
        GsonRequest deleteGoodsRequest = new GsonRequest(Request.Method.POST, Constants.DELETE_ITEM, map,
                new Response.Listener<ServiceResult>(){
                    @Override
                    public void onResponse(ServiceResult serviceResult) {
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {


            }
        });
        ItemGardenApplication.getInstance().addRequestToQueue(deleteGoodsRequest, TAG);
    }
}
