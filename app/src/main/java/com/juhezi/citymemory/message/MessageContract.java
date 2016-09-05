package com.juhezi.citymemory.message;

import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.data.module.Coversation;

import java.util.List;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public interface MessageContract {

    interface Presenter extends BasePresenter {

        void loadData();

    }

    interface View extends BaseView<Presenter> {

        void setData(List<Coversation> list);

        void showProgressBar();

        void hideProgressBar();

        void showEmptyView();

        void hideEmptyView();

        void showToast(String message);
    }

}
