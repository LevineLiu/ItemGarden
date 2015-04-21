package com.llw.itemgarden.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.llw.itemgarden.ItemGardenApplication;
import com.llw.itemgarden.R;
import com.llw.itemgarden.adpater.GridViewAdapter;
import com.llw.itemgarden.base.Constants;
import com.llw.itemgarden.base.FragmentContainerActivity;
import com.llw.itemgarden.base.StaticValueHolder;
import com.llw.itemgarden.model.Item;
import com.llw.itemgarden.model.ItemImage;
import com.llw.itemgarden.model.ServiceResult;
import com.llw.itemgarden.model.User;
import com.llw.itemgarden.utils.PhotoUtil;
import com.llw.itemgarden.view.PhotoGridView;
import com.llw.itemgarden.volley.GsonRequest;
import com.llw.itemgarden.volley.VolleyErrorHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by liulewen on 2015/4/3.
 */
public class PostPhotoFragment extends PostFragment implements View.OnClickListener {
    public static final String TAG = PostPhotoFragment.class.getSimpleName();
    public static final int CAPTURE_REQUEST_CODE = 100;
    public static final int GALLERY_REQUEST_CODE = 200;
    public static final String ADD_PHOTO = "add_photo";
    private static final int TIME_OUT = 10000;
    private GridViewAdapter mAdapter;
    private PhotoGridView gridView;
    private long itemId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_post_first_step, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        ItemGardenApplication.getInstance().cancelRequests(TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleBitmap();
    }

    private void recycleBitmap(){
        if(mAdapter == null)
            return;
        Map<Integer, Bitmap> bitmapMap = mAdapter.getBitmapCache();
        if(bitmapMap == null)
            return;
        int size = bitmapMap.size();
        for(int i=0; i<size; i++) {
            Bitmap bitmap = bitmapMap.get(i);
            if (bitmap != null) {
                bitmapMap.put(i, null);
                bitmap.recycle();
                System.gc();
            }
        }
    }
    private void initView(View view) {
        mAdapter = new GridViewAdapter(getActivity(), PostPhotoFragment.this) {
            @Override
            public void upLoad(int position) {
                upLoadPhoto(position);
            }

            @Override
            public void deleteImage(int position) {

            }
        };
        ItemImage itemImage = new ItemImage();
        itemImage.setHeadImage(ADD_PHOTO);
        mAdapter.addData(itemImage);
        gridView = (PhotoGridView) view.findViewById(R.id.photo_grid_view);
        gridView.setAdapter(mAdapter);
        ImageView closeImageView = (ImageView) view.findViewById(R.id.post_close_img);
        closeImageView.setOnClickListener(this);
        TextView nextButton = (TextView) view.findViewById(R.id.first_step_next_button);
        nextButton.setOnClickListener(this);
        getImageItemId();
    }

    private void upLoadPhoto(final int position) {
        if (itemId == -1)
            getImageItemId();
        String image = PhotoUtil.bitmapToBase64(mAdapter.getBitmapCache().get(position));
        Map<String, Object> map = new HashMap<>();
        map.put("itemID", itemId);
        map.put("image", image);
        GsonRequest upLoadPhotoRequest = new GsonRequest(Request.Method.POST,
                Constants.UPDATE_ITEM_IMAGE, map,
                new Response.Listener<ServiceResult>() {
                    @Override
                    public void onResponse(ServiceResult serviceResult) {
                        if (serviceResult.isSuccess()) {
                            toast("上传成功",true);
                            mAdapter.getIsPhotoUpload().set(position, true);
                        } else {
                            mAdapter.getIsPhotoUpload().set(position, false);
                            toast(serviceResult.getObject(), true);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                toast("上传失败", true);
                mAdapter.getIsPhotoUpload().set(position, false);
            }
        });
        //Because upload photo need a long time, we should set the time of TimeOut
        upLoadPhotoRequest.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ItemGardenApplication.getInstance().addRequestToQueue(upLoadPhotoRequest, TAG);
    }

    private void deleteImage(int position){
        ItemImage itemImage = (ItemImage)mAdapter.getItem(position);
        Map<String, Object> map = new HashMap<>();
        map.put("id", itemImage.getId());
        GsonRequest deleteImageRequest = new GsonRequest(Constants.DELETE_ITEM_IMAGE, map,
                new Response.Listener<ServiceResult>() {
                    @Override
                    public void onResponse(ServiceResult serviceResult) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(getActivity() != null)
                    toast(VolleyErrorHelper.getMessage(volleyError, getActivity()), true);
            }
        });
    }

    private void getImageItemId() {
        User user = StaticValueHolder.getObject(ItemGardenApplication.USER_INFO);
        if (user == null)
            return;
        Item item = new Item();
        item.setCreateBy(user.getId());
        String requestBody = new Gson().toJson(item);
        Map<String, Object> map = new HashMap<>();
        map.put("item", requestBody);
        GsonRequest request = new GsonRequest(Request.Method.POST,
                Constants.PUBLISH_ITEM, map,
                new Response.Listener<ServiceResult>() {
                    @Override
                    public void onResponse(ServiceResult serviceResult) {
                        if (serviceResult.isSuccess()) {
                            Item item = new Gson().fromJson(serviceResult.getObject(), Item.class);
                            itemId = item.getId();
                            StaticValueHolder.putLong(ItemGardenApplication.GOODS_ID, itemId);
                        } else
                            toast(serviceResult.getObject(), true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                toast(VolleyErrorHelper.getMessage(volleyError, getActivity()), true);
            }
        });
        ItemGardenApplication.getInstance().addRequestToQueue(request, TAG);
    }

    /**
     * judge photo upload finished or not
     */
    private boolean isPhotoUploadFinished() {
        List<Boolean> list = mAdapter.getIsPhotoUpload();
        boolean isUpLoadFinished = true;
        for (int i=0; i< mAdapter.getCount()-1; i++) {
            Boolean item = list.get(i);
            if (!item)
                isUpLoadFinished = false;
        }
        return isUpLoadFinished;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != FragmentContainerActivity.RESULT_OK)
            return;
        switch (requestCode) {
            case CAPTURE_REQUEST_CODE:
                mAdapter.removeLastData();
                ItemImage itemImage = new ItemImage();
                itemImage.setHeadImage(mAdapter.getCaptureFilePath());
                mAdapter.addData(itemImage);
                ItemImage addButton = new ItemImage();
                addButton.setHeadImage(ADD_PHOTO);
                mAdapter.addData(addButton);
                mAdapter.notifyDataSetChanged();
                break;
            case GALLERY_REQUEST_CODE:
                mAdapter.removeLastData();
                ItemImage itemImage1 = new ItemImage();
                itemImage1.setHeadImage(PhotoUtil.getGalleryImagePathWithIntent(getActivity(), data));
                mAdapter.addData(itemImage1);
                ItemImage addButton1 = new ItemImage();
                addButton1.setHeadImage(ADD_PHOTO);
                mAdapter.addData(addButton1);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_step_next_button:
                if (mAdapter.getCount() == 1) {
                    toast("总得为宝贝拍张照片吧", true);
                    return;
                }
                if (!isPhotoUploadFinished()) {
                    toast("图片上传中,请稍等", true);
                    return;
                }
                if (getActivity() != null)
                    ((FragmentContainerActivity) getActivity()).addFragment(PostDescriptionFragment.class, true);
                break;
            case R.id.post_close_img:
                showExitDialog(getActivity());
                break;
        }
    }
}
