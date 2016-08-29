package com.juhezi.citymemory.data.user;

import android.content.Context;

import com.avos.avoscloud.AVUser;

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
}
