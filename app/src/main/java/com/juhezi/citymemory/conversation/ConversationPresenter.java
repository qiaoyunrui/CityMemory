package com.juhezi.citymemory.conversation;

import android.util.Log;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public class ConversationPresenter implements ConversationContract.Presenter {

    private static final String TAG = "ConversationPresenter";

    public ConversationContract.View mView;

    public ConversationPresenter(ConversationContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.i(TAG, "start: Hello");
    }
}
