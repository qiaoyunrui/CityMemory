package com.juhezi.citymemory.browse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.browse.upload.UploadFragment;
import com.juhezi.citymemory.browse.upload.UploadPresenter;
import com.juhezi.citymemory.browse.view.ViewFragment;
import com.juhezi.citymemory.browse.view.ViewPresenter;
import com.juhezi.citymemory.data.data.DataResponse;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.data.map.MapResponse;
import com.juhezi.citymemory.data.map.MapSource;
import com.juhezi.citymemory.data.user.UserResponse;
import com.juhezi.citymemory.data.user.UserSource;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class BrowseActivity extends AppCompatActivity {

    private static final String TAG = "BrowseActivity";

    private Toolbar mTbBrowse;
    private ActionBar mActionBar;

    private BrowsePresenter mBrowsePresenter;
    private BrowseFragment mBrowseFragment;

    private UploadPresenter mUploadPresenter;
    private UploadFragment mUploadFragment;

    private ViewPresenter mViewPresenter;
    private ViewFragment mViewFragment;

    private MapSource mMapSource;
    private DataSource mDataSource;
    private UserSource mUserSource;

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

        mMapSource = MapResponse.getInstance(this);
        mDataSource = DataResponse.getInstance(this);
        mUserSource = UserResponse.getInstance(this);

        mBrowseFragment = (BrowseFragment) getSupportFragmentManager()
                .findFragmentById(R.id.rl_browse_frag);
        if (mBrowseFragment == null) {
            mBrowseFragment = new BrowseFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.rl_browse_frag, mBrowseFragment)
                    .commit();
        }
        mBrowsePresenter = new BrowsePresenter(mBrowseFragment
                , mDataSource, mUserSource, mMapSource);

        mUploadFragment = new UploadFragment();
        mUploadPresenter = new UploadPresenter(mUploadFragment
                , mMapSource, mDataSource, mUserSource);
        mViewFragment = new ViewFragment();
        mViewPresenter = new ViewPresenter(mViewFragment);
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

    public void openDiscussFragment(Bundle MemoryStream) {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            mUploadFragment.setArguments(MemoryStream);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.rl_browse_frag, mUploadFragment)
                    .addToBackStack("discuss")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    public void openViewFragment(Bundle imgUrl) {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            mViewFragment.setArguments(imgUrl);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.rl_browse_frag, mViewFragment)
                    .addToBackStack("view")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }

    }


}
