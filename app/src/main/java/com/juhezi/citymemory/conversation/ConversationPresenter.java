package com.juhezi.citymemory.conversation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.data.module.Coversation;
import com.juhezi.citymemory.data.module.User;
import com.juhezi.citymemory.data.user.UserSource;
import com.juhezi.citymemory.util.OperateCallback;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public class ConversationPresenter implements ConversationContract.Presenter {

    private static final String TAG = "ConversationPresenter";

    private UserSource mUserSource;
    private DataSource mDataSource;

    public ConversationContract.View mView;

    public ConversationPresenter(ConversationContract.View view, UserSource userSource) {
        mView = view;
        mUserSource = userSource;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public AVUser getCurrentUser() {
        return mUserSource.getCurrentUser();
    }

    @Override
    public void getCovInfo(String receiver, OperateCallback<String> callback) {

    }

    /**
     * 初始化聊天连接
     *
     * @param sender
     * @param receiver
     */
    @Override
    public void initCov(final User sender, final User receiver) {

        if (sender == null || receiver == null) {
            //showToast()
            return;
        }
    }
}
