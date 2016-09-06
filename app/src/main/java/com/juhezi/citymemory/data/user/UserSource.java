package com.juhezi.citymemory.data.user;

import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.data.module.User;
import com.juhezi.citymemory.util.Action;
import com.juhezi.citymemory.util.OperateCallback;

import java.util.List;

import rx.Observable;

/**
 * Created by qiaoyunrui on 16-8-28.
 */
public interface UserSource {

    AVUser getCurrentUser();

    /**
     * 添加创建的回忆
     *
     * @param success
     * @param fail
     */
    void addOwnMemory(Action success, Action fail);

    /**
     * 添加参与的回忆
     */
    void addPipMemory(Action success, Action fail);

    /**
     * 修改用户头像
     *
     * @param avatar
     * @param success
     * @param fail
     */
    void changeAvatar(String avatar, Action success, Action fail);

    void queryUsers(String name, OperateCallback<Observable<List<User>>> callback);
}
