package com.llw.itemgarden.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;



/**
 * @author Created by liulewen on 2015/3/20.
 */
public class FragmentContainerActivity extends FragmentActivity{
    private final static String EXTRA_TO_FRAGMENT_CLASS_NAME = "extra_to_fragment_class_name";
    private final static String EXTRA_ADD_TO_BACK_STACK = "is_add_to_back_stack)";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout mFrameLayout = new FrameLayout(this);
        setContentView(mFrameLayout);
        Fragment fragment = null;
        try{
            fragment = ((Class<? extends Fragment>)getIntent().getSerializableExtra(EXTRA_TO_FRAGMENT_CLASS_NAME)).newInstance();

        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
            fragment.setArguments(bundle);
        replaceFragment(fragment, getIntent().getBooleanExtra(EXTRA_ADD_TO_BACK_STACK, true));
    }


    public static void startActivity(FragmentActivity fragmentActivity, Class<?extends Fragment> toFragment, Bundle arguments){
        startActivity(fragmentActivity, toFragment, true, false, 0, arguments);
    }

    public static void startActivity(FragmentActivity fragmentActivity, Class<?extends Fragment> toFragment, Bundle arguments, boolean isAddToBackStack){
        startActivity(fragmentActivity, toFragment, isAddToBackStack, false, 0, arguments);
    }

    public static void startActivityForResult(FragmentActivity fragmentActivity, Class<?extends Fragment> toFragment, int requestCode, Bundle arguments){
        startActivity(fragmentActivity, toFragment, true, true, requestCode, arguments);

    }

    private static void startActivity(FragmentActivity fragmentActivity, Class<?extends Fragment> toFragment, boolean isAddToBackStack, boolean isStartForResult,
                              int requestCode, Bundle arguments){
        Intent intent = new Intent(fragmentActivity, FragmentContainerActivity.class);
        intent.putExtra(EXTRA_TO_FRAGMENT_CLASS_NAME,toFragment);
        intent.putExtra(EXTRA_ADD_TO_BACK_STACK, isAddToBackStack);
        if(arguments != null)
            intent.putExtras(arguments);
        if(!isStartForResult)
            fragmentActivity.startActivity(intent);
        else
            fragmentActivity.startActivityForResult(intent, requestCode);


    }

    /**
     * add a Fragment
     */
    private void addFragment(Class <? extends Fragment> fragmentClass, boolean isAddToBackStack){
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
        //execute pending transactions immediately,or when executing other operations, it will new a instance again;
        fragmentManager.executePendingTransactions();
    }

    /**
     * replace a Fragment
     */
    private void replaceFragment(Fragment fragment, boolean isAddToBackStack){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(getFragmentContainerId(), fragment, null);
        if(isAddToBackStack)
            transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
    private int getFragmentContainerId(){
        return android.R.id.content;
    }
}
