package com.juhezi.citymemory.conversation;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.data.module.Coversation;
import com.juhezi.citymemory.data.module.User;
import com.juhezi.citymemory.util.OperateCallback;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public interface ConversationContract {

    interface Presenter extends BasePresenter {

        AVUser getCurrentUser();

        void getCovInfo(String sender, String receiver,
                        OperateCallback<Coversation> callback);

        /**
         * 上传聊天记录
         *
         * @param coversation
         * @param callback
         */
        void addCoverRecord(Coversation coversation, OperateCallback<Exception> callback);

        /**
         * 建立对话连接
         */
        void buildConnection(User sender, User receiver, OperateCallback<AVIMClient> callback);

        void initCov(User sender, User receiver);

        Coversation createCoversation(User sender, User receiver, String covId);
    }

    interface View extends BaseView<Presenter> {

        void showProgressBar();

        void hideProgressBar();

        /**
         * 显示删除提示按钮
         */
        void showDailog();

        void setTitle(String title);

    }

}
