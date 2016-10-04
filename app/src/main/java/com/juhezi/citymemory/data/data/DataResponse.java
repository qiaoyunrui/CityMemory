package com.juhezi.citymemory.data.data;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.Messages;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.juhezi.citymemory.data.module.Coversation;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.Action;
import com.juhezi.citymemory.util.OperateCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

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
    public void getMemStream(LatLng latLng, final OperateCallback<MemoryStream> callback) {
        AVQuery<AVObject> query = new AVQuery<>(Config.STREAM_WARE_HOUSE);
        final AVGeoPoint point = new AVGeoPoint(latLng.latitude, latLng.longitude);
        query.limit(1);
        query.whereWithinKilometers(Config.MEMORY_STREAM_WHERE_CREATED, point, Config.QUERY_LIMIT_RADIUS);  //指定查询范围为50m
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list == null || list.size() == 0) {
                    callback.onOperate(null);
                } else {
                    AVObject avObject = list.get(0);
                    AVGeoPoint avGeoPoint = avObject
                            .getAVGeoPoint(Config.MEMORY_STREAM_WHERE_CREATED);
                    double d = avGeoPoint.distanceInKilometersTo(point);
                    Log.i(TAG, "done: " + d);
                    if (d < 0.05) {
                        MemoryStream memoryStream = MemoryStream.parseAVObject(avObject);
                        callback.onOperate(memoryStream);
                    } else {
                        callback.onOperate(null);
                    }
                }
            }
        });
    }

    /**
     * 带有进度的上传文件
     *
     * @param memoryPath
     * @param callback
     * @param progressCallback
     */
    @Override
    public void uploadFile(final String memoryPath, final OperateCallback<String> callback
            , final ProgressCallback progressCallback) {
        try {
            File file = new File(memoryPath);
            final String name = System.currentTimeMillis() + "_IMG";
            final AVFile memory = AVFile.withAbsoluteLocalPath(name,
                    memoryPath);
            memory.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        callback.onOperate(memory.getUrl());
                    } else {
                        callback.onOperate(null);
                    }
                }
            }, progressCallback);
        } catch (FileNotFoundException e) {
            Log.i(TAG, "uploadFile: error: " + e.getMessage());
            e.printStackTrace();
            callback.onOperate(null);
        }
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    /**
     * 不带进度条的上传文件
     *
     * @param memoryPath
     * @param operateCallback
     * @param action
     */
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
                    Log.i(TAG, "done: " + e.getCode() + " " + e.getMessage());
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

    @Override
    public void getAllMemories(String streamId, final OperateCallback<Observable<List<Memory>>> callback) {
        AVQuery<AVObject> query = new AVQuery<>(streamId);
        AVQuery<AVUser> queryUser = new AVQuery<>("_User");
        queryUser.whereEqualTo("username", "");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list != null) {
                    Observable<List<Memory>> observable = Observable.from(list)
                            .map(new Func1<AVObject, Memory>() {
                                @Override
                                public Memory call(AVObject avObject) {
                                    return Memory.parseMemory(avObject);
                                }
                            }).toList();
                    callback.onOperate(observable);
                } else {
                    callback.onOperate(null);
                }
            }
        });
    }

    @Override
    public void getAllStreams(final OperateCallback<Observable<List<MemoryStream>>> callback) {
        AVQuery<AVObject> query = new AVQuery<>(Config.STREAM_WARE_HOUSE);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list != null) {
                    Observable<List<MemoryStream>> observable = Observable.from(list)
                            .map(new Func1<AVObject, MemoryStream>() {
                                @Override
                                public MemoryStream call(AVObject avObject) {
                                    return MemoryStream.parseAVObject(avObject);
                                }
                            }).toList();
                    callback.onOperate(observable);
                } else {
                    callback.onOperate(null);
                }
            }
        });
    }

    @Override
    public void getMemoryCount(String username, final OperateCallback<Integer> callback) {
        AVQuery<AVObject> query = new AVQuery<>(username);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                int count = 0;
                if (list != null) {
                    count = list.size();
                }
                callback.onOperate(count);
            }
        });
    }

    @Override
    public void addCoverRecord(final Coversation coversation, final
    OperateCallback<Exception> callback) {
        /**/
        AVQuery<AVObject> query = new AVQuery<>(coversation.getOwnId() + Config.COVERSATION);
        final AVObject[] avObject = new AVObject[1];
        query.whereEqualTo(Config.COVER_CHATER_ID, coversation.getChaterId());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list == null || list.size() == 0) { //之前没有聊过天,添加数据
                    avObject[0] = coversation.toAvObject();
                    avObject[0].saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            callback.onOperate(e);
                        }
                    });
                } else {    //之前有聊过天，更新数据
                    avObject[0] = list.get(0);  //获取查询到的数据
                    avObject[0].put(Config.COVER_AVATAR, coversation.getAvatar());
                    avObject[0].put(Config.COVER_CONTENT, coversation.getContent());
                    avObject[0].put(Config.COVER_PICKNAME, coversation.getPickname());
                    avObject[0].saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            callback.onOperate(e);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getCoverRecords(String username, final
    OperateCallback<Observable<List<Coversation>>> callback) {
        String className = username + Config.COVERSATION;
        AVQuery<AVObject> query = new AVQuery<>(className);
        query.setLimit(50);     //限制为50条
        query.orderByAscending("updatedAt");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list != null && list.size() != 0) {
                    Observable<List<Coversation>> observable = Observable
                            .from(list)
                            .map(new Func1<AVObject, Coversation>() {
                                @Override
                                public Coversation call(AVObject avObject) {
                                    return Coversation.parseCoversation(avObject);
                                }
                            })
                            .toList();
                    callback.onOperate(observable);
                } else {    //数据查询失败
                    callback.onOperate(null);
                }
            }
        });
    }

    /**
     * 根据sender 和 receiver 获取其聊天ID
     *
     * @param sender
     * @param receiver
     * @param callback
     */
    @Override
    public void getCov(String sender, String receiver, final OperateCallback<Coversation> callback) {
        String className = sender + Config.COVERSATION;
        AVQuery<AVObject> query = new AVQuery<>(className);
        query.setLimit(50);     //限制为50条
        query.orderByAscending("updatedAt");
        query.whereEqualTo(Config.COVER_ID, receiver);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list != null && list.size() != 0) {
                    callback.onOperate(Coversation.parseCoversation(list.get(0)));
                } else {    //数据查询失败
                    callback.onOperate(null);
                }
            }
        });
    }

}
