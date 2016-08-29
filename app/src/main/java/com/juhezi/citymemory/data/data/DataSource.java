package com.juhezi.citymemory.data.data;

import com.amap.api.maps.model.LatLng;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.data.module.MemoryStream;
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

    void getMemStream(LatLng latLng, OperateCallback<MemoryStream> callback);

    /**
     * 添加记忆流到总的仓库(以便在地图上显示)
     */
    void addStreamToWarehouse(MemoryStream memoryStream, Action success, Action fail);

    /**
     * 添加记忆到记忆流
     *
     * @param memory
     */
    void addMemoryStream(Memory memory, Action success, Action fail);

    /**
     * 添加到用户记忆仓库
     *
     * @param memory
     * @param success
     * @param fail
     */
    void addUserMemory(Memory memory, Action success, Action fail);

}
