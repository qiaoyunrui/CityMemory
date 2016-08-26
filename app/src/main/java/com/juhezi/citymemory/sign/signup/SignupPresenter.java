package com.juhezi.citymemory.sign.signup;

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

    }
}
