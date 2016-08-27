package com.juhezi.citymemory.browse.view;

import android.util.Log;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class ViewPresenter implements ViewContract.Presenter {

    private ViewContract.View mView;

    private static final String TAG = "ViewPresenter";

    public ViewPresenter(ViewContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        Log.i(TAG, "start: Hello");
    }
}
