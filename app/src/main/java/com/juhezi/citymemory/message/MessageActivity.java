package com.juhezi.citymemory.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.data.DataResponse;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.data.user.UserResponse;
import com.juhezi.citymemory.data.user.UserSource;
import com.juhezi.citymemory.message.search.SearchUserContract;
import com.juhezi.citymemory.message.search.SearchUserFragment;
import com.juhezi.citymemory.message.search.SearchUserPresenter;
import com.juhezi.citymemory.search.SearchContract;
import com.juhezi.citymemory.search.SearchFragment;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "MessageActivity";

    private Toolbar mTbMeesage;
    private ActionBar mActionBar;
    private EditText mEtSearchUser;

    private MessageFragment mFragment;
    private MessagePresenter mPresenter;

    private SearchUserFragment mSUFragment;
    private SearchUserPresenter mSUPresenter;

    private DataSource mDataSource;
    private UserSource mUserSource;

    private int backStackEntryCount;   //被添加的Fragment的个数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_act);

        initView();

        initEvent();

        initActionBar();

        initFragment();
    }

    private void initView() {
        mEtSearchUser = (EditText) findViewById(R.id.et_search_name);
    }

    private void initEvent() {
        mEtSearchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //search and show
                if (mSUPresenter != null) {
                    ((SearchUserContract.Presenter) mSUPresenter).search(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

        mSUFragment = new SearchUserFragment();
        mSUPresenter = new SearchUserPresenter(mSUFragment, mUserSource);
        //监听SUFragment的打开状况，如果打开，显示搜索框，否则关闭搜索框
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
                        switch (backStackEntryCount) {
                            case 1:
                                showEditText();
                                break;
                            case 0:
                                hideEditText();
                                break;
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

    public void showEditText() {
        mEtSearchUser.setVisibility(View.VISIBLE);
    }

    public void hideEditText() {
        mEtSearchUser.setVisibility(View.INVISIBLE);
    }

    /**
     * 打开搜索界面
     */
    public void addSearchUserFragment() {
        if (backStackEntryCount != 0) {
            return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rl_message_frag, mSUFragment)
                .addToBackStack("Message")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

}
