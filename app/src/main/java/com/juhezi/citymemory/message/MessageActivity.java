package com.juhezi.citymemory.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.juhezi.citymemory.R;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "MessageActivity";

    private Toolbar mTbMeesage;
    private ActionBar mActionBar;

    private MessageFragment mFragment;
    private MessagePresenter mPresenter;

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
        mFragment = (MessageFragment) getSupportFragmentManager()
                .findFragmentById(R.id.rl_message_frag);
        if (mFragment == null) {
            mFragment = new MessageFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.rl_message_frag, mFragment)
                    .commit();
        }
        mPresenter = new MessagePresenter(mFragment);
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
