package com.juhezi.citymemory.sign.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.sign.SignActivity;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SignupFragment extends Fragment implements SignupContract.View {

    private static final String TAG = "SignupFragment";

    private SignupContract.Presenter mPresenter;

    private TextInputLayout mTILUsername;
    private TextInputLayout mTILPasswd;
    private TextInputLayout mTILPasswdAgain;

    private FloatingActionButton mFabSignup;
    private ProgressBar mPbSignup;

    private View rootView;

    private boolean usernameOK = false;
    private boolean passwdOK = false;
    private boolean passwdAgainOK = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signup_frag, container, false);
        mTILUsername = (TextInputLayout) rootView.findViewById(R.id.til_signup_username);
        mTILPasswd = (TextInputLayout) rootView.findViewById(R.id.til_signup_passwd);
        mTILPasswdAgain = (TextInputLayout) rootView.findViewById(R.id.til_signup_passwd_again);
        mFabSignup = (FloatingActionButton) rootView.findViewById(R.id.fab_signup);
        mPbSignup = (ProgressBar) rootView.findViewById(R.id.pb_signup);
        configEdit();
        initEvent();
        return rootView;
    }

    private void initEvent() {
        mFabSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.signup(mTILUsername.getEditText().getText().toString(),
                        mTILPasswd.getEditText().getText().toString(),
                        new SignUpCallback() {
                            @Override
                            public void done(AVException e) {
                                hideProgressBar();
                                if (e == null) {
                                    showToast("注册成功,欢迎加入城市记忆~");
                                    ((SignActivity) getActivity()).turnSignIn();
                                } else {
                                    switch (e.getCode()) {
                                        case 202:
                                            showToast("该用户名已经存在");
                                            break;
                                        default:
                                            showToast("未知错误");
                                    }
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    public void setPresenter(SignupContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar() {
        mPbSignup.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mPbSignup.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToast(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void enableSignup() {
        if (usernameOK && passwdOK && passwdAgainOK) {
            mFabSignup.show();
        }

    }

    @Override
    public void unenableSignup() {
        mFabSignup.hide();
    }

    private void configEdit() {
        mTILUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    mTILUsername.setError("用户名长度太短");
                    usernameOK = false;
                    unenableSignup();
                } else {
                    mTILUsername.setError(null);
                    usernameOK = true;
                    enableSignup();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTILPasswd.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    passwdOK = false;
                    mTILPasswd.setError("密码长度太短");
                    unenableSignup();
                } else {
                    mTILPasswd.setError(null);
                    passwdOK = true;
                    enableSignup();
                }
                if (!s.toString().equals(
                        mTILPasswdAgain.getEditText().getText().toString())) {
                    mTILPasswdAgain.setError("密码不一致");
                    unenableSignup();
                    passwdAgainOK = false;
                } else {
                    mTILPasswdAgain.setError(null);
                    passwdAgainOK = true;
                    enableSignup();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mTILPasswdAgain.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(mTILPasswd.getEditText().getText().toString())) {
                    mTILPasswdAgain.setError("密码不一致");
                    passwdAgainOK = false;
                    unenableSignup();
                } else {
                    mTILPasswdAgain.setError(null);
                    passwdAgainOK = true;
                    enableSignup();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
