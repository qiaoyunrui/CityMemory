package com.juhezi.citymemory.search;

import android.util.Log;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public class SearchPresenter implements SearchContract.Presenter {

    private static final String TAG = "SearchPresenter";

    private SearchContract.View mView;

    public SearchPresenter(SearchContract.View view) {
        this.mView = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        Log.i(TAG, "start: Hello");
    }
}
