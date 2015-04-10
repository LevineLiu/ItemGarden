package com.llw.itemgarden.base;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class BaseFragment extends Fragment{
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mProgressDialog.destroyProgressDialog();
	}
    
	/**********************************************Toast start*********************************************/
	public void toast(String text, boolean isShortDuration){
		if(getActivity() == null)
			return;
		Toast.makeText(getActivity(), text, isShortDuration ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
	}
	
	public void toast(int resourceId, boolean isShortDuration){
		if(getActivity() == null)
			return;
		Toast.makeText(getActivity(), resourceId, isShortDuration ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
	}
	/**********************************************Toast end********************************************/
	
	/**********************************************ProgressDialog start*****************************************************/
	private ProgressDialogManager mProgressDialog = new ProgressDialogManager(getClass().getSimpleName()) {
		
		@Override
		public FragmentActivity getHostActivity() {
			return getActivity();
		}
	};
	
	public void showProgressDialog(boolean isCancelable){
		mProgressDialog.showProgressDialog(isCancelable);
	}
	
	public void showProgressDialog(Context context, boolean isCancelable){
		mProgressDialog.showProgressDialog(context, isCancelable);
	}
	
	public void dismissprogressDialog(){
		mProgressDialog.dismissProgressDialog();
	}
	
	public void setProgressDialogMessage(String message){
		mProgressDialog.setProgressDialogMessage(message);
	}
	
	public void onProgressDialogCancel(DialogInterface dialog){
		mProgressDialog.onProgressDialogCancel(dialog);
	}
	
	public void onProgressDialogDismiss(DialogInterface dialog){
		mProgressDialog.onProgressDialogDismiss(dialog);
	}
	
	public int getCurrentDialogId(){
		return mProgressDialog.getCurrentDialogId();
	}
	
	/**********************************************ProgressDialog end******************************/

	/**********************************************Default Dialog start*****************************************************/


	/**********************************************Default Dialog start*****************************************************/
}
