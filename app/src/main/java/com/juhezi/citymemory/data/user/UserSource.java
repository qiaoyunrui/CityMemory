package com.juhezi.citymemory.data.user;

import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.util.Action;

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
}
