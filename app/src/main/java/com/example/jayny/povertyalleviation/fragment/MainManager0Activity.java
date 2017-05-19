package com.example.jayny.povertyalleviation.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jayny.povertyalleviation.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.jayny.povertyalleviation.R.id.aUnit;
import static com.example.jayny.povertyalleviation.R.id.lllSrc;

//10000037普通用户，主界面
public class MainManager0Activity extends AppCompatActivity {
    private FragmentManager mFm;
    private FragmentTransaction mTransaction;


    private ItemListFragment mItemListFragment;
    private AssistSetFragment mAssistSetFragment;
    private RequireListFragment mRequireListFragment;
    private  PoorInfoFragment mPoorInfoFragment;
    private ActionBar mActionBar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_jtqk:
                    mFm = getFragmentManager();
                    mTransaction = mFm.beginTransaction();
                    mPoorInfoFragment = new PoorInfoFragment();
                    mTransaction.replace(R.id.content, mPoorInfoFragment);
                    mTransaction.commit();
                    mActionBar = getSupportActionBar();
                    if (mActionBar != null) {
                        mActionBar.setTitle("精准扶贫-家庭情况");
                    }
                    return true;
                case R.id.navigation_bfrw:
                    mFm = getFragmentManager();
                    mTransaction = mFm.beginTransaction();
                    mAssistSetFragment = new AssistSetFragment();
                    mTransaction.replace(R.id.content, mAssistSetFragment);
                    mTransaction.commit();
                    mActionBar = getSupportActionBar();
                    if (mActionBar != null) {
                        mActionBar.setTitle("精准扶贫-帮扶任务");
                    }
                    return true;
                case R.id.navigation_zcwj:
                    getIntent().putExtra("getDocType", "0");
                    mTransaction = mFm.beginTransaction();
                    mItemListFragment = new ItemListFragment();
                    mTransaction.replace(R.id.content, mItemListFragment);
                    mTransaction.commit();
                    mActionBar = getSupportActionBar();
                    assert mActionBar != null;
                    mActionBar.setTitle("精准扶贫-政策文件");
                    return true;
                case R.id.navigation_fpdt:
                    getIntent().putExtra("getDocType", "1");
                    mTransaction = mFm.beginTransaction();
                    mItemListFragment = new ItemListFragment();
                    mTransaction.replace(R.id.content, mItemListFragment);
                    mTransaction.commit();
                    mActionBar = getSupportActionBar();
                    assert mActionBar != null;
                    mActionBar.setTitle("精准扶贫-扶贫动态");
                    return true;
                case R.id.navigation_xqss:
                    mTransaction = mFm.beginTransaction();
                    mRequireListFragment = new RequireListFragment();
                    mTransaction.replace(R.id.content, mRequireListFragment);
                    mTransaction.commit();
                    mActionBar = getSupportActionBar();
                    assert mActionBar != null;
                    mActionBar.setTitle("精准扶贫-需求诉说");
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager0);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // 设置默认的Fragment
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        getIntent().putExtra("aUnit",aUnit);
        mFm = getFragmentManager();
        mTransaction = mFm.beginTransaction();
        mPoorInfoFragment = new PoorInfoFragment();
        mTransaction.replace(R.id.content, mPoorInfoFragment);
        mTransaction.commit();
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle("精准扶贫-家庭情况");
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

