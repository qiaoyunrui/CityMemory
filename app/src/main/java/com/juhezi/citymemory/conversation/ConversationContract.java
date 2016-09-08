package com.juhezi.citymemory.conversation;

import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public interface ConversationContract {

    interface Presenter extends BasePresenter {

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
