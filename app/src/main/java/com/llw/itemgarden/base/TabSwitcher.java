package com.llw.itemgarden.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by liulewen on 2015/4/28.
 */
public class TabSwitcher {
    private final static String TAG = TabSwitcher.class.getSimpleName();
    public final static int OPERATION_REPLACE = 0;
    public final static int OPERATION_SHOW_HIDE = 1;
    private List<Fragment> fragments;
    private List<Integer> operationList;
    private int fragmentCount;
    private int mContainerId;
    private int mCurrentPosition;

    public TabSwitcher(int containerId){
        fragments = new ArrayList<>();
        operationList = new ArrayList<>();
        mContainerId = containerId;
    }

    public void addFragment(Fragment fragment, int operation){
        fragments.add(fragment);
        operationList.add(operation);
        fragmentCount++;
    }

    public void switchFragment(FragmentManager fragmentManager, int position){
        if(position >= 0 && position < fragmentCount){
            mCurrentPosition = position;
            if(operationList .get(position) == OPERATION_REPLACE)
                replaceFragment(fragmentManager, position, getFragmentTag(position));
            else if(operationList.get(position) == OPERATION_SHOW_HIDE)
                showHideFragment(fragmentManager, position);
        }else{
            Log.e(TAG, "switch fragment failed with position" + position);
            throw new IndexOutOfBoundsException("switch fragment failed with position" + position);
        }
    }

    private String getFragmentTag(int position){
        return mContainerId + " : " + position;
    }

    public int getCurrentPosition(){
        return  mCurrentPosition;
    }
    private void replaceFragment(FragmentManager fragmentManager, int position, String tag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragments.get(position);
        transaction.replace(mContainerId, fragment, tag);
        transaction.commit();
    }

    private void showHideFragment(FragmentManager fragmentManager, int position){
        for(int i=0; i < fragmentCount; i++){
            if(fragments.get(i) != null){
                if(i != position)
                    hideFragment(fragmentManager, i);
                else
                    showFragment(fragmentManager, i);
            }

        }
    }

    private void showFragment(FragmentManager fragmentManager, int position){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragments.get(position);
        transaction.show(fragment);
        transaction.commit();
    }

    private void hideFragment(FragmentManager fragmentManager, int position){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragments.get(position);
        transaction.hide(fragment);
        transaction.commit();
    }


}
