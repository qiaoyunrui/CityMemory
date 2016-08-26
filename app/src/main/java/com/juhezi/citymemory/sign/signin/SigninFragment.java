package com.juhezi.citymemory.sign.signin;

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
import android.widget.ProgressBar;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.map.MapContract;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SigninFragment extends Fragment implements SigninContract.View {

    private static final String TAG = "SigninFragment";

    private SigninContract.Presenter mPresenter;
    private View rootView;
    private TextInputLayout mTILUsername;
    private TextInputLayout mTILPasswd;
    private FloatingActionButton mFabSignin;
    private ProgressBar mPbSignin;

    private boolean usernameOK = false;
    private boolean passwdOK = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signin_frag, container, false);
        mTILUsername = (TextInputLayout) rootView.findViewById(R.id.til_signin_username);
        mTILPasswd = (TextInputLayout) rootView.findViewById(R.id.til_signin_passwd);
        mFabSignin = (FloatingActionButton) rootView.findViewById(R.id.fab_signin);
        mPbSignin = (ProgressBar) rootView.findViewById(R.id.pb_signin);
        configEdit();
        initEvent();
        return rootView;
    }

    private void configEdit() {
        mTILUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mTILUsername.setError("用户名不能为空");
                    usernameOK = false;
                    hideFabSignin();
                } else {
                    mTILUsername.setError(null);
                    usernameOK = true;
                    showFabSignin();
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
                if (s.length() == 0) {
                    mTILPasswd.setError("密码不能为空");
                    passwdOK = false;
                    hideFabSignin();
                } else {
                    mTILPasswd.setError(null);
                    passwdOK = true;
                    showFabSignin();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initEvent() {
        mFabSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                mPresenter.signin(mTILUsername.getEditText().getText().toString().trim(),
                        mTILPasswd.getEditText().getText().toString().trim(),
                        new LogInCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                hideProgressBar();
                                if (e == null) {
                                    showSnackBar("登陆成功");
                                    getActivity().finish();
                                } else {
                                    switch (e.getCode()) {
                                        case 210:
                                            showSnackBar("用户名和密码不匹配");
                                            break;
                                        case 0:
                                            showSnackBar("网络链接错误");
                                            break;
                                        case 1:
                                            showSnackBar("登录失败次数超过限制，请稍候再试");
                                            break;
                                        default:
                                            showSnackBar("未知错误");
                                    }
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void setPresenter(SigninContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar() {
        mPbSignin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mPbSignin.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showFabSignin() {
        if (usernameOK && passwdOK) {
            mFabSignin.show();
        }

    }

    @Override
    public void hideFabSignin() {
        mFabSignin.hide();
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

}
