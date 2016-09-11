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

    private Coversation cov;   //对话的唯一标识

    private String covId;

    private AVIMClient mClient = null;

    public ConversationPresenter(ConversationContract.View view, UserSource userSource,
                                 DataSource dataSource) {
        mView = view;
        mUserSource = userSource;
        mDataSource = dataSource;
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
    public void getCovInfo(String sender, String receiver, OperateCallback<Coversation> callback) {
        mDataSource.getCov(sender, receiver, callback);
    }

    @Override
    public void addCoverRecord(Coversation coversation, OperateCallback<Exception> callback) {
        mDataSource.addCoverRecord(coversation, callback);
    }

    /**
     * 建立对话连接
     *
     * @param sender
     * @param receiver
     * @param callback
     */
    @Override
    public void buildConnection(User sender, User receiver, final OperateCallback<AVIMClient> callback) {
        mClient = AVIMClient.getInstance(sender.getUsername());
        mClient.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    callback.onOperate(mClient);
                } else {
                    callback.onOperate(null);

                }
            }
        });
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
        } else {
            //首先获取聊天记录
            getCovInfo(sender.getUsername(), receiver.getUsername(),
                    new OperateCallback<Coversation>() {
                        @Override
                        public void onOperate(Coversation coversation) {
                            cov = coversation;
                        }
                    });
            if (cov == null) {   //没有聊天记录，先与对方建立链接，要先创建聊天记录（在两个表中）
                buildConnection(sender, receiver, new OperateCallback<AVIMClient>() {
                    @Override
                    public void onOperate(AVIMClient client) {
                        if (client != null) {    //连接成功
                            mClient = client;
                            covId = mClient.getClientId();
                            //创建两个Conversation
                            Coversation cov1 = createCoversation(sender, receiver, covId);
                            addCoverRecord(cov1, new OperateCallback<Exception>() {
                                @Override
                                public void onOperate(Exception e) {
                                    if (e != null) {    //创建聊天记录成功

                                    } else {

                                    }
                                }
                            });

                        } else { //连接失败
                            mClient = null;
                            covId = null;
                            //show unConnection Toast
                        }
                    }
                });
            }
        }
    }

    @Override
    public Coversation createCoversation(User sender, User receiver, String covId) {
        Coversation coversation = new Coversation();
        coversation.setId(covId);
        coversation.setOwnId(sender.getUsername());
        coversation.setChaterId(receiver.getUsername());
        coversation.setPickname(receiver.getPickName());
        coversation.setAvatar(receiver.getAvatar());
        coversation.setContent("");
        return coversation;
    }
}
