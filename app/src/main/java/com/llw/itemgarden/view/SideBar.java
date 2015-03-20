package com.llw.itemgarden.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class SideBar extends View{
    
	public static String[] firstLetter = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
		"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
		"W", "X", "Y", "Z", "#" };
	private Paint mPaint = new Paint();
	private static final int NONE_SELECTED_POSITION = -1;
	private int selectedPosition = NONE_SELECTED_POSITION;
	private TextView displayTextView;
	private OnLetterTouchListener touchListener;
	
	public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
    
	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public SideBar(Context context) {
		super(context);
	}
    
	public void setDisplayTextView(TextView displayTextView) {
		this.displayTextView = displayTextView;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int viewWidth = getWidth();
		int viewHeight = getHeight();
		int singleLetterHeight = viewHeight/firstLetter.length;
		for(int i=0; i<firstLetter.length; i++){
			if(i == selectedPosition)
		    	mPaint.setColor(Color.parseColor("#3399ff"));
			else
			    mPaint.setColor(Color.rgb(33, 65, 98));
		    mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		    mPaint.setAntiAlias(true);
		    mPaint.setTextSize(20);
		    float xPos = viewWidth / 2 - mPaint.measureText(firstLetter[i]) / 2;
			float yPos = singleLetterHeight * i + singleLetterHeight;
			canvas.drawText(firstLetter[i], xPos, yPos, mPaint);
		    mPaint.reset();
		}
		
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float y = event.getY();
		int currentPosition = (int) (y/getHeight() * firstLetter.length);
		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundColor(Color.parseColor("#00000000"));
			selectedPosition = NONE_SELECTED_POSITION;
			if(displayTextView != null && displayTextView.getVisibility() == View.VISIBLE)
				displayTextView.setVisibility(View.GONE);
			invalidate();
			break;

		default:
			setBackgroundColor(Color.parseColor("#99C60000"));
			if(currentPosition >= 0 && currentPosition < firstLetter.length){
				if(displayTextView != null){
					displayTextView.setVisibility(View.VISIBLE);
					displayTextView.setText(firstLetter[currentPosition]);
				}
				if(touchListener != null){
					touchListener.onLetterTouch(firstLetter[currentPosition]);
				}
				selectedPosition = currentPosition;
				invalidate();
			}
			break;
		}
		return true;
	}
	
	public void setOnLetterTouchLisener(OnLetterTouchListener listener){
		touchListener = listener;
	}
	
	public interface OnLetterTouchListener{
		public void onLetterTouch(String letter);
	}
	
	
} 
