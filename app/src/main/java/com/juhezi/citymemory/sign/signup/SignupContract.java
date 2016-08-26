package com.juhezi.citymemory.sign.signup;

import com.avos.avoscloud.SignUpCallback;
import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public interface SignupContract {

    interface Presenter extends BasePresenter {
        void signup(String username, String passwd, SignUpCallback callback);
    }

    interface View extends BaseView<Presenter> {

        void showProgressBar();

        void hideProgressBar();

        void showToast(String message);

        void enableSignup();

        void unenableSignup();
    }

}
