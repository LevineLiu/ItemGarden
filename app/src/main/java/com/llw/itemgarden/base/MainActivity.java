package com.llw.itemgarden.base;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.llw.itemgarden.ItemGardenApplication;
import com.llw.itemgarden.R;
import com.llw.itemgarden.message.MessageFragment;
import com.llw.itemgarden.person.PersonFragment;
import com.llw.itemgarden.home.HomeFragment;
import com.llw.itemgarden.loginandregister.LoginFragment;
import com.llw.itemgarden.post.PostPhotoFragment;
import com.llw.itemgarden.model.User;
import com.llw.itemgarden.search.SearchFragment;

/**
 * @author Created by liulewen on 2015/3/24.
 */
public class MainActivity extends FragmentActivity{
    private final static String TAG = MainActivity.class.getSimpleName();
    public final static int LOGIN_REQUEST_CODE = 200;
    private long mLastTime;
    private static final long BACK_INTERVAL_TIME = 3000;
    private TabSwitcher mTabSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabSwitcher = null;
    }

    private void initTabSwitcher(){
        mTabSwitcher = new TabSwitcher(getFragmentContainerId());
        mTabSwitcher.addFragment(new HomeFragment(), TabSwitcher.OPERATION_REPLACE);
        mTabSwitcher.addFragment(new SearchFragment(), TabSwitcher.OPERATION_REPLACE);
        mTabSwitcher.addFragment(new MessageFragment(), TabSwitcher.OPERATION_REPLACE);
        mTabSwitcher.addFragment(new PersonFragment(), TabSwitcher.OPERATION_REPLACE);
    }
    private void initView(){
        initTabSwitcher();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.tab_radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_home_button:
                        mTabSwitcher.switchFragment(getSupportFragmentManager(), 0);
                        break;
                    case R.id.tab_find_button:
                        mTabSwitcher.switchFragment(getSupportFragmentManager(), 1);
                        break;
                    case R.id.tab_message_button:
                        User user = StaticValueHolder.getObject(ItemGardenApplication.USER_INFO);
                        if (user == null) {
                            ((RadioButton) findViewById(R.id.tab_message_button)).setChecked(false);
                            FragmentContainerActivity.startActivity(MainActivity.this, LoginFragment.class, null, false);
                        }else{
                            mTabSwitcher.switchFragment(getSupportFragmentManager(), 2);
                        }
                        break;
                    case R.id.tab_person_button:
                        mTabSwitcher.switchFragment(getSupportFragmentManager(), 3);
                        break;
                }
            }
        });
        ((RadioButton)findViewById(R.id.tab_home_button)).setChecked(true);
        findViewById(R.id.tab_post_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = StaticValueHolder.getObject(ItemGardenApplication.USER_INFO);
                if (user != null)
                    FragmentContainerActivity.startActivity(MainActivity.this, PostPhotoFragment.class, null);
                else
                    FragmentContainerActivity.startActivityForResult(MainActivity.this, LoginFragment.class, false, LOGIN_REQUEST_CODE, null);
            }
        });
    }

    private int getFragmentContainerId(){
        return R.id.fragment_container;
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - mLastTime > BACK_INTERVAL_TIME){
            mLastTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出易物园", Toast.LENGTH_SHORT).show();
        }else
            super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //You should call this method, if you want to dispatch result to fragments
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK)
            return;
        if(requestCode == LOGIN_REQUEST_CODE){
            FragmentContainerActivity.startActivity(MainActivity.this, PostPhotoFragment.class, null, false);
        }
    }
}
