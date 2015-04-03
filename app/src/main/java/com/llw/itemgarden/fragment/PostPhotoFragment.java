package com.llw.itemgarden.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.llw.itemgarden.R;
import com.llw.itemgarden.utils.PhotoUtil;

/**
 * @author Created by liulewen on 2015/4/3.
 */
public class PostPhotoFragment extends Fragment{
    public static final int CAPTURE_REQUEST_CODE = 100;
    public static final int GALLERY_REQUEST_CODE = 200;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_post_first_step, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        Button nextButton = (Button) view.findViewById(R.id.first_step_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoUtil.showFetchImageDialog(PostPhotoFragment.this, null, CAPTURE_REQUEST_CODE, GALLERY_REQUEST_CODE);
            }
        });

    }
}
