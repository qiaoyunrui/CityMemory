package com.juhezi.citymemory.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.data.DataResponse;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.data.user.UserResponse;
import com.juhezi.citymemory.data.user.UserSource;
import com.juhezi.citymemory.setting.avatar.AvatarFragment;
import com.juhezi.citymemory.setting.avatar.AvatarPresenter;
import com.juhezi.citymemory.setting.setting.SettingFragment;
import com.juhezi.citymemory.setting.setting.SettingPresenter;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "SettingActivity";

    private Toolbar mTbSetting;
    private ActionBar mActionBar;

    private SettingFragment mSettingFragment;
    private SettingPresenter mSettingPresenter;

    private AvatarFragment mAvatarFragment;
    private AvatarPresenter mAvatarPresenter;

    private DataSource mDataSource;
    private UserSource mUserSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_act);

        initEvent();

        initActionBar();

        initFragment();

    }

    private void initEvent() {
        mTbSetting = (Toolbar) findViewById(R.id.tb_setting);
    }

    private void initActionBar() {
        setSupportActionBar(mTbSetting);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("设置");
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initFragment() {
        mDataSource = DataResponse.getInstance(this);
        mUserSource = UserResponse.getInstance(this);
        mSettingFragment = (SettingFragment) getSupportFragmentManager()
                .findFragmentById(R.id.rl_setting_frag);
        if (mSettingFragment == null) {
            mSettingFragment = new SettingFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rl_setting_frag, mSettingFragment)
                    .commit();
        }
        mSettingPresenter = new SettingPresenter(mSettingFragment, mDataSource);
        mAvatarFragment = new AvatarFragment();
        mAvatarPresenter = new AvatarPresenter(mAvatarFragment, mUserSource);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    mSettingPresenter.start();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openAvatarFrag() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.rl_setting_frag, mAvatarFragment)
                    .addToBackStack("avatar")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

}


