package com.llw.itemgarden.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.llw.itemgarden.R;

/**
 * @author Created by liulewen on 2015/3/16.
 */
public class PullToRefreshHeaderView extends LinearLayout{
    private static final long ANIMATION_DURATION = 200;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_READY_TO_REFRESH = 1;
    public static final int STATE_REFRESHING = 2;

    private LinearLayout mHeaderLayout;
    private TextView mTimeTextView, mTipTextView;
    private ProgressBar mProgressBar;
    private ImageView mArrowImageView;

    private Animation mArrowTurnUpAnimation;
    private Animation mArrowTurnDownAnimation;

    public PullToRefreshHeaderView(Context context){
        super(context);
        initView(context);
    }

    public PullToRefreshHeaderView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    private void initView(Context context){
        mHeaderLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.refresh_listview_headerview, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        addView(mHeaderLayout, params);
        setGravity(Gravity.BOTTOM);

        mTimeTextView = (TextView) mHeaderLayout.findViewById(R.id.headerView_time);
        mTipTextView = (TextView) mHeaderLayout.findViewById(R.id.headerView_tip);
        mProgressBar = (ProgressBar) mHeaderLayout.findViewById(R.id.headerView_progress);
        mArrowImageView = (ImageView) mHeaderLayout.findViewById(R.id.headerView_arrow);

        //第四个参数和第六个参数分别是旋转点x轴的偏移量和y轴的偏移量，0.5f为百分比
        mArrowTurnUpAnimation = new RotateAnimation(0.0f, 180.f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mArrowTurnUpAnimation.setDuration(ANIMATION_DURATION);
        mArrowTurnUpAnimation.setFillAfter(true);

        mArrowTurnDownAnimation = new RotateAnimation(180f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mArrowTurnUpAnimation.setDuration(ANIMATION_DURATION);
        mArrowTurnUpAnimation.setFillAfter(true);
    }

    private int mState = STATE_NORMAL;
    public void changState(int state){
        if(mState == state)
            return;
        if (state == STATE_REFRESHING) {
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
        switch (state){
            case STATE_NORMAL:
                if(mState == STATE_READY_TO_REFRESH)
                    mArrowImageView.startAnimation(mArrowTurnDownAnimation);
                else if(mState == STATE_REFRESHING)
                    clearAnimation();
                mTipTextView.setText(R.string.pull_to_refresh);
                break;
            case STATE_READY_TO_REFRESH:
                mArrowImageView.startAnimation(mArrowTurnUpAnimation);
                mTipTextView.setText(R.string.release_to_refresh);
                break;
            case STATE_REFRESHING:
                mTipTextView.setText(R.string.refreshing);
                break;
        }
        mState = state;
    }

    /**
     * set the height of header view
     */
    public void setHeaderHeight(int height){
        LayoutParams params = (LayoutParams)mHeaderLayout.getLayoutParams();
        params.height = height;
        mHeaderLayout.setLayoutParams(params);
    }

    /**
     * get the height of header view
     */
    public int getHeaderHeight(){
        return mHeaderLayout.getHeight();
    }
}
