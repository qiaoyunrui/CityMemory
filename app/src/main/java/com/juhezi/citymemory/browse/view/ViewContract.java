package com.juhezi.citymemory.browse.view;

import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public interface ViewContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {

    }

}
