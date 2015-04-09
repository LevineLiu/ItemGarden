package com.llw.itemgarden.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.GridView;

/**
 * Created by Administrator on 2015/4/8.
 */
public class PhotoGridView extends GridView {
    public static boolean isOnMeasure;
    public PhotoGridView(Context context) {
        super(context);
    }

    public PhotoGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public PhotoGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("onMeasure", "onMeasure");
        isOnMeasure = true;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("onLayout", "onLayout");
        isOnMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }
}
