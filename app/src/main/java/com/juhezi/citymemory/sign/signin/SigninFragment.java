package com.juhezi.citymemory.sign.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juhezi.citymemory.R;
import com.juhezi.citymemory.map.MapContract;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SigninFragment extends Fragment implements SigninContract.View {

    private static final String TAG = "SigninFragment";

    private SigninContract.Presenter mPresenter;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signin_frag, container, false);
        return rootView;
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
}
