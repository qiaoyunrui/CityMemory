package com.juhezi.citymemory.conversation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.module.Coversation;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public class ConversationActivity extends AppCompatActivity {

    private static final String TAG = "ConversationActivity";
    private ActionBar mActionBar;
    private Toolbar mToolbar;

    private ConversationFragment mFragment;
    private ConversationPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coversation_act);

        initActionBar();

        initFragment();

    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.tb_coversation);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
    }

    private void initFragment() {
        mFragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.rl_coversation_frag);
        if (null == mFragment) {
            mFragment = new ConversationFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.rl_coversation_frag, mFragment)
                    .commit();
        }
        mPresenter = new ConversationPresenter(mFragment);
    }

    public void setActionBarTitle(String title) {
        mActionBar.setTitle(title);
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
