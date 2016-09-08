package com.juhezi.citymemory.message.search;

import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.data.module.User;

import java.util.List;

/**
 * Created by qiaoyunrui on 16-9-5.
 */
public interface SearchUserContract {

    interface Presenter extends BasePresenter {

        void search(String name);

    }

    interface View extends BaseView<Presenter> {

        void showProgressbar();

        void hideProgressbar();

        void showData(List<User> list);

        void turn2CovActivity(User user);

    }

}
