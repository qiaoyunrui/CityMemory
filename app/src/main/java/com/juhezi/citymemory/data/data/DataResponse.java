package com.juhezi.citymemory.data.data;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.Action;
import com.juhezi.citymemory.util.OperateCallback;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by qiaoyunrui on 16-8-28.
 */
public class DataResponse implements DataSource {

    private static final String TAG = "DataResponse";

    private Context mContext;

    private static DataResponse sDataResponse;

    private DataResponse(Context context) {
        mContext = context;
    }

    public static DataResponse getInstance(Context context) {
        if (sDataResponse == null) {
            sDataResponse = new DataResponse(context);
        }
        return sDataResponse;
    }

    @Override
    public void uploadFile(final String memoryPath, final OperateCallback<String> operateCallback, final Action action) {
        try {
            final String name = System.currentTimeMillis() + "_IMG";
            final AVFile memory = AVFile.withAbsoluteLocalPath(name,
                    memoryPath);
            memory.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        operateCallback.onOperate(memory.getUrl());
                    } else {
                        action.onAction();
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            action.onAction();
        }


    }

    @Override
    public void getMemStream(LatLng latLng, final OperateCallback<MemoryStream> callback) {
        AVQuery<AVObject> query = new AVQuery<>("allMemorySteam");
        AVGeoPoint point = new AVGeoPoint(latLng.latitude, latLng.longitude);
        query.limit(10);
        query.whereWithinMiles("whereCreated", point, Config.QUERY_LIMIT_RADIUS);  //指定查询范围为50m
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list == null || list.size() == 0) {
                    callback.onOperate(null);
                } else {
                    AVObject avObject = list.get(0);
                    MemoryStream memoryStream = MemoryStream.parseAVObject(avObject);
                    callback.onOperate(memoryStream);
                }
            }
        });
    }

    @Override
    public void addStreamToWarehouse(MemoryStream memoryStream, final Action success, final Action fail) {
        AVObject avObject = memoryStream.toAVObject();
        avObject.saveInBackground(new SaveCallback() {
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
    public void addMemoryStream(Memory memory, final Action success, final Action fail) {
        AVObject avObject = memory.toAvObject(memory.getStreamId());
        avObject.saveInBackground(new SaveCallback() {
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
    public void addUserMemory(Memory memory, final Action success, final Action fail) {
        AVObject avObject = memory.toAvObject(memory.getCreater());
        avObject.saveInBackground(new SaveCallback() {
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
