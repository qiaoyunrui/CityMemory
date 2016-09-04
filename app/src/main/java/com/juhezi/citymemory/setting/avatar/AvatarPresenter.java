package com.juhezi.citymemory.setting.avatar;

import android.util.Log;

import com.juhezi.citymemory.data.user.UserSource;
import com.juhezi.citymemory.util.Action;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class AvatarPresenter implements AvatarContract.Presenter {

    private static final String TAG = "AvatarPresenter";

    private AvatarContract.View mView;
    private UserSource mUserSource;

    public AvatarPresenter(AvatarContract.View view, UserSource userSource) {
        mView = view;
        mUserSource = userSource;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void changeAvater(String avatar, Action success, Action fail) {
        mUserSource.changeAvatar(avatar, success, fail);
    }
}
