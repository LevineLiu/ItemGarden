package com.llw.itemgarden.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

import com.llw.itemgarden.R;


public class MainActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * add a Fragment
     */
    public void addFragment(Class <? extends Fragment> fragmentClass, boolean isAddToBackStack){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        try{
            fragment = fragmentClass.newInstance();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        String fragmentName = fragmentClass.getSimpleName();
        transaction.add(getFragmentContainerId(), fragment, fragmentName);
        if(isAddToBackStack)
            transaction.addToBackStack(fragmentName);
        transaction.commitAllowingStateLoss();
    }

    /**
     * replace a Fragment
     */
    public void replaceFragment(Class<? extends  Fragment> fragmentClass, boolean isAddToBackStack){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        try{
            fragment = fragmentClass.newInstance();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        String fragmentName = fragmentClass.getSimpleName();
        transaction.replace(getFragmentContainerId(), fragment, fragmentName);
        if(isAddToBackStack)
            transaction.addToBackStack(fragmentName);
        transaction.commitAllowingStateLoss();
    }

    /**
     * show a Fragment
     */
    public void showFragment(Class<? extends Fragment> fragmentClass){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getSimpleName());
        if(fragment == null){
            try {
                fragment = fragmentClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * hide a Fragment
     */
    public void hideFragment(Class<? extends Fragment> fragmentClass){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getSimpleName());
        if(fragment == null){
            try {
                fragment = fragmentClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        transaction.hide(fragment);
        transaction.commitAllowingStateLoss();
    }

    private int getFragmentContainerId(){
        return 0;
    }
}
