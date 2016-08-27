package com.juhezi.citymemory.browse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.juhezi.citymemory.R;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class BrowseActivity extends AppCompatActivity {

    private static final String TAG = "BrowseActivity";

    private Toolbar mTbBrowse;
    private ActionBar mActionBar;

    private BrowsePresenter mBrowsePresenter;
    private BrowseFragment mBrowseFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_act);

        initActionBar();

        initFragment();

    }

    private void initActionBar() {
        mTbBrowse = (Toolbar) findViewById(R.id.tb_browse);
        setSupportActionBar(mTbBrowse);
        mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("记忆流");
    }

    private void initFragment() {

        mBrowseFragment = (BrowseFragment) getSupportFragmentManager()
                .findFragmentById(R.id.rl_browse_frag);
        if (mBrowseFragment == null) {
            mBrowseFragment = new BrowseFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.rl_browse_frag, mBrowseFragment)
                    .commit();
        }
        mBrowsePresenter = new BrowsePresenter(mBrowseFragment);
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
