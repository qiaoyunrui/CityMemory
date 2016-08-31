package com.juhezi.citymemory.setting.setting;

import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.util.OperateCallback;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public interface SettingContract {

    interface Presenter extends BasePresenter {
        void getCurrentUser();

        void signout();

        void editPickname(String pickname);

        void getUserMemCount(String username, OperateCallback<Integer> callback);
    }

    interface View extends BaseView<Presenter> {

        void showUserData(AVUser user);

        void turn2MapActivity();

        void showToast(String message);

        void showDialog();

        void setShowPickname(String pickname);

    }

}
