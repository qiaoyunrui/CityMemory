package com.juhezi.citymemory.setting.avatar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juhezi.citymemory.R;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class AvatarFragment extends Fragment implements AvatarContract.View {

    private static final String TAG = "AvatarFragment";

    private AvatarContract.Presenter mPresenter;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.avatar_frag, container, false);
        return rootView;
    }

    @Override
    public void setPresenter(AvatarContract.Presenter presenter) {
        mPresenter = presenter;
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
}
