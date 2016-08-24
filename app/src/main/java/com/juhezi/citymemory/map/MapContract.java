package com.juhezi.citymemory.map;

import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public interface MapContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {
        void locate();
    }

}
