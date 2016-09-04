package com.juhezi.citymemory.data.user;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.Action;

/**
 * Created by qiaoyunrui on 16-8-28.
 */
public class UserResponse implements UserSource {

    private static final String TAG = "UserResponse";

    private static UserSource sUserSource;

    private Context mContext;

    public static UserSource getInstance(Context context) {
        if (sUserSource == null) {
            sUserSource = new UserResponse(context);
        }
        return sUserSource;
    }

    private UserResponse(Context context) {
        mContext = context;
    }

    @Override
    public AVUser getCurrentUser() {
        return AVUser.getCurrentUser();
    }

    @Override
    public void addOwnMemory(final Action success, final Action fail) {
        AVUser user = getCurrentUser();
        int count = user.getInt(Config.USER_OWN);
        user.put(Config.USER_OWN, count + 1);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    success.onAction();
                } else {
                    fail.onAction();
                }
            }
        });
    }

    @Override
    public void addPipMemory(final Action success, final Action fail) {
        AVUser user = getCurrentUser();
        int count = user.getInt(Config.USER_PIP);
        user.put(Config.USER_PIP, count + 1);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    success.onAction();
                } else {
                    fail.onAction();
                }
            }
        });
    }

    @Override
    public void changeAvatar(String avatar, final Action success, final Action fail) {
        AVUser user = getCurrentUser();
        if (user == null) {
            fail.onAction();
            return;
        }
        user.put(Config.USER_AVATAR, avatar);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    success.onAction();
                } else {
                    fail.onAction();
                }
            }
        });
    }


}
