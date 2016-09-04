package com.juhezi.citymemory.message;

import android.util.Log;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public class MessagePresenter implements MessageContract.Presenter {

    private static final String TAG = "MessagePresenter";
    private MessageContract.View mView;

    public MessagePresenter(MessageContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void start() {
        Log.i(TAG, "start: Hello");
    }
}
