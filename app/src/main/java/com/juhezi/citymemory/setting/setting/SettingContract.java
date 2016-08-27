package com.juhezi.citymemory.setting.setting;

import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public interface SettingContract {

    interface Presenter extends BasePresenter {
        void getCurrentUser();

        void signout();

        void editPickname(String pickname);
    }

    interface View extends BaseView<Presenter> {

        void showUserData(AVUser user);

        void turn2MapActivity();

        void showToast(String message);

        void showDialog();

        void setShowPickname(String pickname);

    }

}
