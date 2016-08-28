package com.juhezi.citymemory.data.data;

import com.avos.avoscloud.AVException;
import com.juhezi.citymemory.util.Func;
import com.juhezi.citymemory.util.OperateCallback;

/**
 * Created by qiaoyunrui on 16-8-28.
 */
public interface DataSource {

    /**
     * 上传记忆(照片)
     */
    void uploadMemory(String memoryPath, OperateCallback<AVException> callback);

}
