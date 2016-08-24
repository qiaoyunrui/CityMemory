package com.juhezi.citymemory.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.juhezi.citymemory.R;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private SearchPresenter mPresenter;
    private SearchFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_act);
        initFragment();

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

        mPresenter = new SearchPresenter(mFragment);

    }
}
