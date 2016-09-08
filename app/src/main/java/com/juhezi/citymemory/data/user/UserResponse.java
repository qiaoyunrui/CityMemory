package com.juhezi.citymemory.data.user;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.poisearch.PoiSearch;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.juhezi.citymemory.data.module.User;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.Action;
import com.juhezi.citymemory.util.OperateCallback;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

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

    /**
     * 根据昵称查询用户
     * 根据昵称查询用户
     *
     * @param name
     * @param callback
     */
    @Override
    public void queryUsers(String name, final OperateCallback<Observable<List<User>>> callback) {
        AVQuery<AVUser> query = new AVQuery<>("_User");
        query.whereContainedIn(Config.USER_PICK_NAME, Arrays.asList(name));
//        query.whereEqualTo(Config.USER_PICK_NAME, name);
        query.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (e != null || list == null || list.size() == 0) {
                    callback.onOperate(null);
                    return;
                }
                Observable<List<User>> observable = Observable.from(list)
                        .map(new Func1<AVUser, User>() {
                            @Override
                            public User call(AVUser user) {
                                return User.parseUser(user);
                            }
                        }).toList();
                callback.onOperate(observable);
            }
        });
    }

    @Override
    public void queryUserByUsername(String username, final OperateCallback<Observable<List<User>>> callback) {
        AVQuery<AVUser> query = new AVQuery<>("_User");
        query.whereEqualTo(Config.USER_NAME, username);
        query.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (e != null || list == null || list.size() == 0) {
                    callback.onOperate(null);
                    return;
                }
                Observable<List<User>> observable = Observable.from(list)
                        .map(new Func1<AVUser, User>() {
                            @Override
                            public User call(AVUser user) {
                                return User.parseUser(user);
                            }
                        }).toList();
                callback.onOperate(observable);
            }
        });
    }

}
