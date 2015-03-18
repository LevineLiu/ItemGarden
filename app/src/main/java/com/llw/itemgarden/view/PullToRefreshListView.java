package com.llw.itemgarden.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.llw.itemgarden.R;

/**
 * @author Created by liulewen on 2015/3/14.
 */
public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener {
    private final static int SCROLL_DURATION = 400;
    private final static int SCROLL_BACK_TYPE_HEADER = 0;
    private final static int SCROLL_BACK_TYPE_FOOTER = 1;
    private final static float OFFSET_FACTOR = 2.0f;

    private PullToRefreshHeaderView mHeaderView;
    private RelativeLayout mHeaderViewContent;
    private TextView mTimeTextView;
    private Scroller mScroller;
    private int mContentHeight, mScrollBackType;
    private boolean isRefreshing = false;
    private float mDownY = -1;//the Y coordinate of finger

    public PullToRefreshListView(Context context) {
        super(context);
        initView(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        setOnScrollListener(this);
        mScroller = new Scroller(context, new AccelerateDecelerateInterpolator());
        mHeaderView = new PullToRefreshHeaderView(context);
        mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.headerView_content);
        mTimeTextView = (TextView) mHeaderView.findViewById(R.id.headerView_time);
        addHeaderView(mHeaderView);

        //get the height of header view
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mContentHeight = mHeaderViewContent.getHeight();
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    mHeaderView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    mHeaderView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void stopRefresh(){
        if(isRefreshing)
            isRefreshing = false;
        resetHeaderViewHeight();
    }

    /**
     * reset the height of header view
     */
    private boolean mStopRefresh = false;// 用于判断是否取消刷新。值为false表示正在刷新，继续下拉则取消刷新
    private void resetHeaderViewHeight(){
        if(mStopRefresh){
            mStopRefresh = false;
            stopRefresh();
        }
        int visibleHeight = mHeaderView.getHeaderHeight();
        //do not do move action
        if(visibleHeight == 0)
            return;

        if(isRefreshing && visibleHeight < mContentHeight){
            stopRefresh();
            return;
        }
        int startY = 0;
        if(isRefreshing && visibleHeight > mContentHeight){
            startY = mContentHeight;
            mStopRefresh = true;
        }
        mScroller.startScroll(0, visibleHeight, 0, startY - visibleHeight, SCROLL_DURATION);
        mScrollBackType = SCROLL_BACK_TYPE_HEADER;
        //refresh the view
        invalidate();
    }

    private void updateHeaderHeight(float offsetY){
        mHeaderView.setHeaderHeight(mHeaderView.getHeaderHeight() + (int)offsetY);
        if(!isRefreshing){
            if(mHeaderView.getHeaderHeight() > mContentHeight)
                mHeaderView.changState(PullToRefreshHeaderView.STATE_READY_TO_REFRESH);
            else
                mHeaderView.changState(PullToRefreshHeaderView.STATE_NORMAL);
        }
        //scroll to the top and update the value of getHeaderHeight()
        setSelection(0);
    }
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            if(mScrollBackType == SCROLL_BACK_TYPE_HEADER){
                mHeaderView.setHeaderHeight(mScroller.getCurrY());
            }
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getRawY();//the Y coordinate relative to screen
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetY = ev.getRawY() - mDownY;
                mDownY = ev.getRawY();
                if(getFirstVisiblePosition() == 0 ){
                    if(mHeaderView.getHeaderHeight() > 0 || offsetY > 0)
                        updateHeaderHeight(offsetY / OFFSET_FACTOR);

                }
                break;
            default:
                mDownY = -1;
                if(getFirstVisiblePosition() == 0){
                    if(!isRefreshing && mHeaderView.getHeaderHeight() > mContentHeight){
                        mHeaderView.changState(PullToRefreshHeaderView.STATE_REFRESHING);
                        isRefreshing = true;
                    }
                    resetHeaderViewHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
