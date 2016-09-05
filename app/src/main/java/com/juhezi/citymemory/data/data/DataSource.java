package com.juhezi.citymemory.data.data;

import com.amap.api.maps.model.LatLng;
import com.avos.avoscloud.ProgressCallback;
import com.juhezi.citymemory.data.module.Coversation;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.util.Action;
import com.juhezi.citymemory.util.OperateCallback;

import java.util.List;

import rx.Observable;

/**
 * Created by qiaoyunrui on 16-8-28.
 */
public interface DataSource {

    /**
     * 上传照片
     */
    void uploadFile(String memoryPath, OperateCallback<String> callback, Action action);

    void uploadFile(String memoryPath, OperateCallback<String> callback, ProgressCallback progressCallback);

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

    /**
     * 获取一个MemoryStream中所有的Memory
     */
    void getAllMemories(String streamId, OperateCallback<Observable<List<Memory>>> callback);

    void getAllStreams(OperateCallback<Observable<List<MemoryStream>>> callback);

    /**
     * 查看用户所分享的记忆的个数
     *
     * @param username
     * @param callback
     */
    void getMemoryCount(String username, OperateCallback<Integer> callback);

    /**
     * 添加会话记录到列表
     *
     * @param coversation
     * @param callback
     */
    void addCoverRecord(Coversation coversation, OperateCallback<Exception> callback);

    /**
     * 获取会话记录，按照时间排序，只获取前50条
     *
     * @param callback
     */
    void getCoverRecords(String username,
                         OperateCallback<Observable<List<Coversation>>> callback);

}
