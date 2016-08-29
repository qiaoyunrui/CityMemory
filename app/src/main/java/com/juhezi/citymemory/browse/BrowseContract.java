package com.juhezi.citymemory.browse;

import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public interface BrowseContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<BrowseContract.Presenter> {

        void showEmptyView();

        void hideEmptyView();

        void showProgressbar();

        void hideProgressbar();

    }

}
