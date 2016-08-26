package com.juhezi.citymemory.setting;

import android.util.Log;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SettingPresenter implements SettingContract.Presenter {

    private static final String TAG = "SettingPresenter";

    private SettingContract.View mView;

    public SettingPresenter(SettingContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.i(TAG, "start: Hello");
    }
}
