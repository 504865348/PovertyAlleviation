package com.example.jayny.povertyalleviation.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.jayny.povertyalleviation.R;

public class MainManagerActivity extends AppCompatActivity {
    private FragmentManager mFm;
    private FragmentTransaction mTransaction;

    private VillageListFragment mVillageListFragment;
    private UnitListFragment mUnitListFragment;
    private SuggestReturnListFragment mSuggestReturnListFragment;
    private ActionBar mActionBar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_xcjg:
                    mTransaction = mFm.beginTransaction();
                    mVillageListFragment = new VillageListFragment();
                    mTransaction.replace(R.id.content, mVillageListFragment);
                    mTransaction.commit();
                    mActionBar = getSupportActionBar();
                    assert mActionBar != null;
                    mActionBar.setTitle("精准扶贫-乡村机构");
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
        setContentView(R.layout.activity_main_manager);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // 设置默认的Fragment
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        mFm = getFragmentManager();
        mTransaction = mFm.beginTransaction();
        mVillageListFragment = new VillageListFragment();
        mTransaction.replace(R.id.content, mVillageListFragment);
        mTransaction.commit();
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle("精准扶贫-乡村机构");
        }
    }
}
