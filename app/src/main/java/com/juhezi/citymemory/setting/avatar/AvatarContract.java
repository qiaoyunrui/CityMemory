package com.juhezi.citymemory.setting.avatar;

import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.search.SearchContract;
import com.juhezi.citymemory.util.Action;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public interface AvatarContract {

    interface Presenter extends BasePresenter {

        void changeAvater(String avatar, Action success, Action fail);

    }

    interface View extends BaseView<Presenter> {

        void showToast(String message);

    }

}
