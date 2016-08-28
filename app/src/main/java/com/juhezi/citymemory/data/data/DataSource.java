package com.juhezi.citymemory.data.data;

import com.juhezi.citymemory.util.Action;
import com.juhezi.citymemory.util.OperateCallback;

/**
 * Created by qiaoyunrui on 16-8-28.
 */
public interface DataSource {

    /**
     * 上传照片
     */
    void uploadFile(String memoryPath, OperateCallback<String> callback, Action action);

}
