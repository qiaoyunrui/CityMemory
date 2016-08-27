package com.juhezi.citymemory.setting.avatar;

import android.util.Log;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class AvatarPresenter implements AvatarContract.Presenter {

    private static final String TAG = "AvatarPresenter";

    private AvatarContract.View mView;

    public AvatarPresenter(AvatarContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.i(TAG, "start: Hello");
    }
}
