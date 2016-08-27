package com.juhezi.citymemory.browse.upload;

import android.util.Log;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class UploadPresenter implements UploadContract.Presenter {

    private static final String TAG = "UploadPresenter";

    private UploadContract.View mView;

    public UploadPresenter(UploadContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        Log.i(TAG, "start: Hello");
    }
}
