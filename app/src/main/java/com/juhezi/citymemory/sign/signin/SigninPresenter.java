package com.juhezi.citymemory.sign.signin;

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

    }
}
