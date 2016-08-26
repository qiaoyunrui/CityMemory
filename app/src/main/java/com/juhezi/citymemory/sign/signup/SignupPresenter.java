package com.juhezi.citymemory.sign.signup;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.juhezi.citymemory.data.User;
import com.juhezi.citymemory.other.Config;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SignupPresenter implements SignupContract.Presenter {

    private static final String TAG = "SignupPresenter";

    private SignupContract.View mView;

    public SignupPresenter(SignupContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.hideProgressBar();
        mView.unenableSignup();
    }

    @Override
    public void signup(String username, String passwd, SignUpCallback callback) {
        mView.showProgressBar();
        AVUser user = new AVUser();
        user.setUsername(username);
        user.setPassword(passwd);
        user.put(Config.USER_PICK_NAME, username);
        user.put(Config.USER_TYPE, User.USER_TYPE_PERSON);
        user.put(Config.USER_AVATAR, "");
        user.signUpInBackground(callback);
    }
}
