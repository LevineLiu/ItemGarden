package com.llw.itemgarden.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.llw.itemgarden.R;


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
        String tag = null;
        try{
            Class<?extends Fragment> fragmentClass = (Class)getIntent().getSerializableExtra(EXTRA_TO_FRAGMENT_CLASS_NAME);
            tag = fragmentClass.getSimpleName();
            fragment = fragmentClass.newInstance();

        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        Bundle bundle = getIntent().getExtras();
        if(fragment != null && bundle != null)
            fragment.setArguments(bundle);
        replaceFragment(fragment, getIntent().getBooleanExtra(EXTRA_ADD_TO_BACK_STACK, true), tag);
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

    public static void startActivityForResult(Fragment startFragment, Class<?extends Fragment> toFragment,
                                              boolean isAddToBackStack, int requestCode, Bundle arguments){
        if(startFragment.getActivity() == null)
            return;
        Intent intent = new Intent(startFragment.getActivity(), FragmentContainerActivity.class);
        intent.putExtra(EXTRA_TO_FRAGMENT_CLASS_NAME,toFragment);
        intent.putExtra(EXTRA_ADD_TO_BACK_STACK, isAddToBackStack);
        if(arguments != null)
            intent.putExtras(arguments);
        startFragment.startActivityForResult(intent, requestCode);
    }
    public static void startActivityForResult(FragmentActivity fragmentActivity, Class<?extends Fragment>
            toFragment, boolean isAddToBackStack, int requestCode, Bundle arguments){
        startActivity(fragmentActivity, toFragment, isAddToBackStack, true, requestCode, arguments);
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

    public void addFragment(Class <? extends Fragment> fragmentClass, boolean isAddToBackStack, Bundle bundle){
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
        fragment.setArguments(bundle);
        String fragmentName = fragmentClass.getSimpleName();
        transaction.add(getFragmentContainerId(), fragment, fragmentName);
        if(isAddToBackStack)
            transaction.addToBackStack(fragmentName);
        transaction.commitAllowingStateLoss();
    }

    public void addFragment(Fragment fragment, boolean isAddToBackStack){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(getFragmentContainerId(), fragment, null);
        if(isAddToBackStack)
            transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    /**
     * replace a Fragment
     */
    public void replaceFragment(Fragment fragment, boolean isAddToBackStack){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(getFragmentContainerId(), fragment, null);
        if(isAddToBackStack)
            transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    /**
     * replace a Fragment
     */
    public void replaceFragment(Fragment fragment, boolean isAddToBackStack, String tag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(getFragmentContainerId(), fragment, tag);
        if(isAddToBackStack)
            transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }



    /**
     * switch the fragment
     *
     */
    public void switchFragment(Class<? extends Fragment> fromFragmentClass,
                               Class<? extends Fragment> toFragmentClass, Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        // Fragment事务
        FragmentTransaction ft = fm.beginTransaction();
        // 被切换的Fragment标签
        String fromTag = fromFragmentClass.getSimpleName();
        // 切换到的Fragment标签
        String toTag = toFragmentClass.getSimpleName();
        // 查找切换的Fragment
        Fragment fromFragment = fm.findFragmentByTag(fromTag);
        Fragment toFragment = fm.findFragmentByTag(toTag);
        // 如果要切换到的Fragment不存在，则创建
        if (toFragment == null) {
            try {
                toFragment = toFragmentClass.newInstance();
                toFragment.setArguments(args);
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

//        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
//                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        /**
         * 如果要切换到的Fragment没有被Fragment事务添加，则隐藏被切换的Fragment，添加要切换的Fragment
         * 否则，则隐藏被切换的Fragment，显示要切换的Fragment
         */
        if (!toFragment.isAdded()) {
            ft.hide(fromFragment);
            ft.add(getFragmentContainerId(), toFragment, toTag);
            // 添加到返回堆栈
            ft.addToBackStack(toTag);
        } else {
            ft.hide(fromFragment);
            ft.show(toFragment);
        }
        // 不保留状态提交事务
        ft.commitAllowingStateLoss();
    }

    /**
     * switch the fragment
     *
     */
    public void switchFragment(Fragment fromFragment,
                               Class<? extends Fragment> toFragmentClass, Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        // Fragment事务
        FragmentTransaction ft = fm.beginTransaction();
        // 切换到的Fragment标签
        String toTag = toFragmentClass.getSimpleName();
        Fragment toFragment = fm.findFragmentByTag(toTag);
        // 如果要切换到的Fragment不存在，则创建
        if (toFragment == null) {
            try {
                toFragment = toFragmentClass.newInstance();
                toFragment.setArguments(args);
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        // 设置Fragment切换效果
//        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
//                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        /**
         * 如果要切换到的Fragment没有被Fragment事务添加，则隐藏被切换的Fragment，添加要切换的Fragment
         * 否则，则隐藏被切换的Fragment，显示要切换的Fragment
         */
        if (!toFragment.isAdded()) {
            ft.hide(fromFragment);
            ft.add(getFragmentContainerId(), toFragment, toTag);
            // 添加到返回堆栈
            ft.addToBackStack(toTag);
        } else {
            ft.hide(fromFragment);
            ft.show(toFragment);
        }
        // 不保留状态提交事务
        ft.commitAllowingStateLoss();
    }

    private int getFragmentContainerId(){
        return android.R.id.content;
    }
}
