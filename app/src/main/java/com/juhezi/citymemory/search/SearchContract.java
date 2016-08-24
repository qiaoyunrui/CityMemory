package com.juhezi.citymemory.search;

import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.map.MapContract;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public interface SearchContract {

    interface View extends BaseView<SearchContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }

}
