package com.llw.itemgarden.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.llw.itemgarden.R;
import com.llw.itemgarden.adpater.PhotoGridViewAdapter;
import com.llw.itemgarden.base.FragmentContainerActivity;
import com.llw.itemgarden.utils.PhotoUtil;
import com.llw.itemgarden.view.PhotoGridView;

/**
 * @author Created by liulewen on 2015/4/3.
 */
public class PostPhotoFragment extends Fragment implements View.OnClickListener{
    public static final int CAPTURE_REQUEST_CODE = 100;
    public static final int GALLERY_REQUEST_CODE = 200;
    public static final String ADD_PHOTO = "add_photo";
    private PhotoGridViewAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_post_first_step, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        mAdapter = new PhotoGridViewAdapter(getActivity(), PostPhotoFragment.this);
        mAdapter.addData(ADD_PHOTO);
        PhotoGridView gridView = (PhotoGridView) view.findViewById(R.id.photo_grid_view);
        gridView.setAdapter(mAdapter);

        ImageView closeImageView = (ImageView) view.findViewById(R.id.post_close_img);
        closeImageView.setOnClickListener(this);
        TextView nextButton = (TextView) view.findViewById(R.id.first_step_next_button);
        nextButton.setOnClickListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != FragmentContainerActivity.RESULT_OK)
            return;
        switch (requestCode){
            case CAPTURE_REQUEST_CODE:
                mAdapter.removeLastData();
                mAdapter.addData(mAdapter.getCaptureFilePath());
                mAdapter.addData(ADD_PHOTO);
                break;
            case GALLERY_REQUEST_CODE:
                mAdapter.removeLastData();
                mAdapter.addData(PhotoUtil.getGalleryImagePathWithIntent(getActivity(), data));
                mAdapter.addData(ADD_PHOTO);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.first_step_next_button:
                if(getActivity() != null)
                    ((FragmentContainerActivity)getActivity()).addFragment(PostDescriptionFragment.class, true);
                break;
            case R.id.post_close_img:
                if(getActivity() != null)
                    getActivity().finish();
                break;
        }
    }
}
