package com.llw.itemgarden.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.llw.itemgarden.R;
import com.llw.itemgarden.fragment.HomeFragment;
import com.llw.itemgarden.fragment.LoginFragment;


public class MainActivity extends FragmentActivity{
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initFragment(){
        //addFragment(LoginFragment.class, false);
        addFragment(HomeFragment.class, false);
    }
    private void initView(){
        initFragment();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.tab_radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tab_home_button:
                        showFragment(HomeFragment.class);
                        //hideFragment(LoginFragment.class);
                        break;
                    case R.id.tab_find_button:
                        break;
                    case R.id.tab_post_button:
                        break;
                    case R.id.tab_message_button:
//                        showFragment(LoginFragment.class);
//                        hideFragment(HomeFragment.class);
                        FragmentContainerActivity.startActivity(MainActivity.this, LoginFragment.class, null, false);
                        break;
                    case R.id.tab_person_button:
//                        showFragment(LoginFragment.class);
//                        hideFragment(HomeFragment.class);
                        break;
                }
            }
        });
        ((RadioButton)findViewById(R.id.tab_home_button)).setChecked(true);
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
        //execute pending transactions immediately,or when executing other operations, it will new a instance again;
        fragmentManager.executePendingTransactions();
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
        // 如果有参数传递，
		if (args != null && !args.isEmpty()) {
			toFragment.getArguments().putAll(args);
		}
//        // 设置Fragment切换效果
//        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
//                android.R.anim.fade_in, android.R.anim.fade_out);
        /**
         * 如果要切换到的Fragment没有被Fragment事务添加，则隐藏被切换的Fragment，添加要切换的Fragment
         * 否则，则隐藏被切换的Fragment，显示要切换的Fragment
         */
        if (!toFragment.isAdded()) {
            ft.hide(fromFragment);
            ft.add(R.id.fragment_container, toFragment, toTag);
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
        return R.id.fragment_container;
    }
}
