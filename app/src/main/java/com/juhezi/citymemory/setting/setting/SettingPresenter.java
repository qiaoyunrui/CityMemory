package com.juhezi.citymemory.setting.setting;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.OperateCallback;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SettingPresenter implements SettingContract.Presenter {

    private static final String TAG = "SettingPresenter";

    private SettingContract.View mView;
    private DataSource mDataSource;

    public SettingPresenter(SettingContract.View view, DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
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

    @Override
    public void getUserMemCount(String username, OperateCallback<Integer> callback) {
        mDataSource.getMemoryCount(username, callback);
    }
}
