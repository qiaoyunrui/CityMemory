package com.juhezi.citymemory.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.BaseKeyListener;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.other.Config;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private String cityName;
    private SearchPresenter mPresenter;
    private SearchFragment mFragment;
    private ActionBar mActionBar;

    private Toolbar mTbSearch;
    private EditText mEtSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_act);
        initActionBar();
        initView();
        initEvent();
        initFragment();
        getCityName();
    }

    private void initView() {
        mEtSearch = (EditText) findViewById(R.id.et_search);
    }

    private void initEvent() {
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPresenter.search(s.toString(), cityName);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initActionBar() {
        mTbSearch = (Toolbar) findViewById(R.id.tb_search);
        setSupportActionBar(mTbSearch);
        mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initFragment() {

        mFragment = (SearchFragment) getSupportFragmentManager()
                .findFragmentById(R.id.ll_search_frag);
        if (mFragment == null) {
            mFragment = new SearchFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.ll_search_frag, mFragment)
                    .commit();
        }

        mPresenter = new SearchPresenter(mFragment, this);
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

    void getCityName() {
        if (getIntent() != null) {
            cityName = getIntent().getStringExtra(Config.CITY_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: " + requestCode);
    }
}
