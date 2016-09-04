package com.juhezi.citymemory.message;

import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;

/**
 * Created by qiaoyunrui on 16-9-4.
 */
public interface MessageContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {

    }

}
