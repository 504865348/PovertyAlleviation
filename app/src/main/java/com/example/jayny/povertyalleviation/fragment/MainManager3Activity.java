package com.example.jayny.povertyalleviation.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jayny.povertyalleviation.R;
import com.example.jayny.povertyalleviation.UnitListActivity;
import com.example.jayny.povertyalleviation.suggestRetrunListActivity;

//10000044村，主界面
public class MainManager3Activity extends AppCompatActivity {
    private FragmentManager mFm;
    private FragmentTransaction mTransaction;

    private PoorerListFragment mPoorerListFragment;
    private PoorerNotLinkedListFragment mPoorerNotLinkedListFragment;
    private UnitListFragment mUnitListFragment;
    private SuggestReturnListFragment mSuggestReturnListFragment;
    private ActionBar mActionBar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_bfdx:
                    mFm = getFragmentManager();
                    mTransaction = mFm.beginTransaction();
                    mPoorerListFragment = new PoorerListFragment();
                    mTransaction.replace(R.id.content, mPoorerListFragment);
                    mTransaction.commit();
                    mActionBar = getSupportActionBar();
                    if (mActionBar != null) {
                        mActionBar.setTitle("精准扶贫-帮扶对象");
                    }
                    return true;
                case R.id.navigation_wggbfdx:
                    mFm = getFragmentManager();
                    mTransaction = mFm.beginTransaction();
                    mPoorerNotLinkedListFragment = new PoorerNotLinkedListFragment();
                    mTransaction.replace(R.id.content, mPoorerNotLinkedListFragment);
                    mTransaction.commit();
                    mActionBar = getSupportActionBar();
                    if (mActionBar != null) {
                        mActionBar.setTitle("精准扶贫-尚未挂钩");
                    }
                    return true;
                case R.id.navigation_bfdw:
                    mTransaction = mFm.beginTransaction();
                    mUnitListFragment = new UnitListFragment();
                    mTransaction.replace(R.id.content, mUnitListFragment);
                    mTransaction.commit();
                    mActionBar = getSupportActionBar();
                    assert mActionBar != null;
                    mActionBar.setTitle("精准扶贫-帮扶单位");
                    return true;
                case R.id.navigation_yjfk:
                    mTransaction = mFm.beginTransaction();
                    mSuggestReturnListFragment = new SuggestReturnListFragment();
                    mTransaction.replace(R.id.content, mSuggestReturnListFragment);
                    mTransaction.commit();
                    mActionBar = getSupportActionBar();
                    assert mActionBar != null;
                    mActionBar.setTitle("精准扶贫-意见反馈");
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager3);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // 设置默认的Fragment
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        mFm = getFragmentManager();
        mTransaction = mFm.beginTransaction();
        mPoorerListFragment = new PoorerListFragment();
        mTransaction.replace(R.id.content, mPoorerListFragment);
        mTransaction.commit();
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle("精准扶贫-帮扶对象");
        }
    }
    private long mPressedTime = 0;

    /**
     * 双击退出
     */
    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            System.exit(0);
        }
    }
}

