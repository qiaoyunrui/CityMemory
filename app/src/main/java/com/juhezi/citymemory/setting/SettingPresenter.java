package com.juhezi.citymemory.setting;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.juhezi.citymemory.other.Config;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SettingPresenter implements SettingContract.Presenter {

    private static final String TAG = "SettingPresenter";

    private SettingContract.View mView;

    public SettingPresenter(SettingContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        getCurrentUser();
    }

    @Override
    public void getCurrentUser() {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) {
            mView.showUserData(user);
        }
    }

    @Override
    public void signout() {
        AVUser.logOut();
        mView.turn2MapActivity();
    }

    @Override
    public void editPickname(final String pickname) {
        AVUser.getCurrentUser().put(Config.USER_PICK_NAME, pickname);
        AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                mView.setShowPickname(pickname);
                mView.showToast("修改成功");
            }
        });

    }
}
