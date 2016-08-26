package com.juhezi.citymemory.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.juhezi.citymemory.R;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "SettingActivity";

    private Toolbar mTbSetting;
    private ActionBar mActionBar;

    private SettingFragment mFragment;
    private SettingPresenter mPresenter;

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
        mFragment = (SettingFragment) getSupportFragmentManager()
                .findFragmentById(R.id.rl_setting_frag);
        if (mFragment == null) {
            mFragment = new SettingFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rl_setting_frag, mFragment)
                    .commit();
        }
        mPresenter = new SettingPresenter(mFragment);
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
}
