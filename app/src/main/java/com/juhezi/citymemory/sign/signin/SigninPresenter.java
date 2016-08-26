package com.juhezi.citymemory.sign.signin;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SigninPresenter implements SigninContract.Presenter {

    private static final String TAG = "SigninPresenter";

    private SigninContract.View mView;

    public SigninPresenter(SigninContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.hideProgressBar();
        mView.hideFabSignin();
    }

    @Override
    public void signin(final String username, final String passwd, LogInCallback<AVUser> callback) {
        AVUser.logInInBackground(username, passwd, callback);
    }
}
