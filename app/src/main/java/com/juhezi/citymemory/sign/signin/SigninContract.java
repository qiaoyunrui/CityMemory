package com.juhezi.citymemory.sign.signin;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.map.MapContract;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public interface SigninContract {

    interface Presenter extends BasePresenter {

        void signin(String username, String passwd, LogInCallback<AVUser> callback);

    }

    interface View extends BaseView<SigninContract.Presenter> {

        void showProgressBar();

        void hideProgressBar();

        void showFabSignin();

        void hideFabSignin();

        void showSnackBar(String message);

    }

}
