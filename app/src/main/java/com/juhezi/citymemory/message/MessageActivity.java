package com.juhezi.citymemory.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.data.DataResponse;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.data.user.UserResponse;
import com.juhezi.citymemory.data.user.UserSource;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "MessageActivity";

    private Toolbar mTbMeesage;
    private ActionBar mActionBar;

    private MessageFragment mFragment;
    private MessagePresenter mPresenter;

    private DataSource mDataSource;
    private UserSource mUserSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_act);

        initActionBar();

        initFragment();
    }

    private void initActionBar() {
        mTbMeesage = (Toolbar) findViewById(R.id.tb_message);
        setSupportActionBar(mTbMeesage);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("私信");
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
    }

    private void initFragment() {
        mDataSource = DataResponse.getInstance(this);
        mUserSource = UserResponse.getInstance(this);
        mFragment = (MessageFragment) getSupportFragmentManager()
                .findFragmentById(R.id.rl_message_frag);
        if (mFragment == null) {
            mFragment = new MessageFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.rl_message_frag, mFragment)
                    .commit();
        }
        mPresenter = new MessagePresenter(mFragment,
                mDataSource, mUserSource);
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
