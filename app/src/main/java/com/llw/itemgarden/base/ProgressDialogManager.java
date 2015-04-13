package com.llw.itemgarden.base;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author Created by liulewen on 2015/3/24.
 * A helper class to manage ProgressDialog 
 */
public abstract class ProgressDialogManager {
	private static final String TAG = ProgressDialogManager.class.getSimpleName();
	private String mLogTag;
	private ProgressDialog mProgressDialog;
	private String mProgressMessage;
	private static final int NONE_DIALOG_ID = -1;
	private static final int DEFAULT_DIALOG_ID = 0;
	private int mCurrentDialogId = DEFAULT_DIALOG_ID;
	private AtomicInteger dialogIdCount = new AtomicInteger(DEFAULT_DIALOG_ID);
	private OnCancelListener mOnDialogCancelListener;
	private OnDismissListener mOnDialogDismissListener;
    
	public ProgressDialogManager(){
	    this(null);
	}
	
	public ProgressDialogManager(String logTag){
		if(TextUtils.isEmpty(logTag))
			logTag = getClass().getSimpleName();
		else
		    mLogTag = logTag;
	}
	
	private ProgressDialog createProgressDialog(Context context, String title, String message, boolean isCancelable, int style){
		ProgressDialog dialog = new ProgressDialog(context);
		if(!TextUtils.isEmpty(title))
		    dialog.setTitle(title);
		if(!TextUtils.isEmpty(message))
		    dialog.setMessage(message);
		dialog.setCancelable(isCancelable);
		dialog.setProgressStyle(style);
		return dialog;
	}
	
	private String getDefaultmessage(){
		String message = "";
		Locale locale = Locale.getDefault();
		String language = locale.getLanguage();
		String country = locale.getCountry();
		if("zh".equals(language)){
			if (country.equals("TW") || country.equals("HK")) {
                // traditional Chinese
                // ���d��...
                //message = "\u52A0\u8F09\u4E2D...";
				message = "記載中...";
            } else {
                // simplified Chinese
                // ������...
                //message = "\u52A0\u8F7D\u4E2D...";
            	message = "加载中...";
            }
		}else
			message = "Loading...";
		return message;
	}
	
	/**
	 * set the message of ProgressDialog 
	 */
	public void setProgressDialogMessage(String message){
		mProgressMessage = message;
	}
	
	/**
	 * show a ProgressDialog 
	 */
	public final int showProgressDialog(boolean isCancelable){
		Context context = getHostActivity();
		if(context != null)
			return showProgressDialog(context, isCancelable);
		return NONE_DIALOG_ID;
			
	}
	/**
	 * show a ProgressDialog 
	 */
	public final int showProgressDialog(Context context, boolean isCancelable){
		if(mOnDialogCancelListener == null){
			mOnDialogCancelListener = new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					mCurrentDialogId = NONE_DIALOG_ID;
					onProgressDialogCancel(dialog);
				}
			};
		}
		if(mOnDialogDismissListener == null){
			mOnDialogDismissListener = new OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					onProgressDialogDismiss(dialog);
				}
			};
		}
		if(mProgressDialog != null && mProgressDialog.isShowing())
		    return mCurrentDialogId;
		
		FragmentActivity activity = getHostActivity();
		if(activity != null && !activity.isFinishing()){
		    if(mProgressDialog == null){
			    if(mProgressMessage == null)
				    mProgressMessage = getDefaultmessage();
			    mProgressDialog = createProgressDialog(context, null, mProgressMessage, isCancelable, ProgressDialog.STYLE_SPINNER);
		        mProgressDialog.setOnCancelListener(mOnDialogCancelListener);
		        mProgressDialog.setOnDismissListener(mOnDialogDismissListener);
		    }
		    mProgressDialog.setCancelable(isCancelable);
		    mProgressDialog.show();
		    mCurrentDialogId = dialogIdCount.getAndIncrement();
		    Log.d(TAG, mLogTag+" show a ProgressDialog "+ mCurrentDialogId);
		}else{
			mCurrentDialogId = NONE_DIALOG_ID;
			Log.e(TAG, mLogTag+" activity is null or isFinished "+ mCurrentDialogId);
		}
		return mCurrentDialogId;
	}
	
	/**
	 * dismiss a ProgressDialog 
	 */
	public final void dismissProgressDialog(){
		Context context = getHostActivity();
		if(mProgressDialog == null || context == null || !mProgressDialog.isShowing())
			return;
		try{
			//if the activity finished,may be cause a Exception.But we still dismiss the dialog
			//to avoid memory leaked;
		    if(mProgressDialog.isShowing())
		        mProgressDialog.dismiss();
		}catch(Exception e){
			Log.w(TAG, mLogTag + " dismiss ProgressDialog failed "+ mCurrentDialogId, e);
		}
	}
	
	public void onProgressDialogCancel(DialogInterface dialog){
		
	}
	
	public void onProgressDialogDismiss(DialogInterface dialog){
		
	}
	/**
	 * The dialog id is used as a flag to check whether the dialog is canceled.So if we want to
	 * know if the task is canceled,we should use dialog id to judge.
	 * for example,"Show dialog for case A, cancel A before A finish; show dialog for case B,
     * B finish, and then A finish". Here comes the problem: when A finishs, how can A know its task is canceled?
     * We can use dialog id solve such problem"
	 */
	public final int getCurrentDialogId(){
		return mCurrentDialogId;
	}
	
	public void destroyProgressDialog(){
		try{
		    if(mProgressDialog != null){
			    dismissProgressDialog();
			    mProgressDialog = null;
		    }
		}catch (Exception e){
			Log.w(TAG, mLogTag + " destroy ProgressDialog failed " + mCurrentDialogId,e);
		}
	}
	public abstract FragmentActivity getHostActivity();
}
