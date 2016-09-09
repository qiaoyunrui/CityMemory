package com.juhezi.citymemory.conversation;

import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.data.module.User;
import com.juhezi.citymemory.util.OperateCallback;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public interface ConversationContract {

    interface Presenter extends BasePresenter {

        AVUser getCurrentUser();

        void getCovInfo(String receiver, OperateCallback<String> callback);

        void initCov(User sender, User receiver);

    }

    interface View extends BaseView<Presenter> {

        void showProgressBar();

        void hideProgressBar();

        /**
         * 显示删除提示按钮
         */
        void showDailog();

    }

}
