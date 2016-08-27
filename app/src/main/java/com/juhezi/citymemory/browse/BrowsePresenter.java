package com.juhezi.citymemory.browse;

import android.util.Log;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class BrowsePresenter implements BrowseContract.Presenter {

    private static final String TAG = "BrowsePresenter";

    private BrowseContract.View mView;

    public BrowsePresenter(BrowseContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.i(TAG, "start: Hello");
    }
}
