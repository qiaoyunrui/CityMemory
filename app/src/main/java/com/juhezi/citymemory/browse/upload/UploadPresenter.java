package com.juhezi.citymemory.browse.upload;

import android.app.Activity;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.ProgressCallback;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.data.map.MapSource;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.data.user.UserSource;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.Action;
import com.juhezi.citymemory.util.LocationUtil;
import com.juhezi.citymemory.util.OperateCallback;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class UploadPresenter implements UploadContract.Presenter {

    private static final String TAG = "UploadPresenter";

    private UploadContract.View mView;

    private MapSource mMapSource;
    private DataSource mDataSource;
    private UserSource mUserSource;

    private String memoryStreamId;  //记忆流的唯一标识符
    private String isMemSteamNull;  //当前记忆流是否为空

    private Action failAction = new Action() {

        @Override
        public void onAction() {
            mView.allowAllActions();
            mView.hideProgressbar();
            mView.showToast("上传失败");
        }
    };

    private Action successAction = new Action() {
        @Override
        public void onAction() {
            mView.allowAllActions();
            mView.hideProgressbar();
            mView.showToast("上传成功");
        }
    };

    public UploadPresenter(UploadContract.View view
            , MapSource mapSource, DataSource dataSource, UserSource userSource) {
        mView = view;
        mMapSource = mapSource;
        mDataSource = dataSource;
        mUserSource = userSource;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.hideProgressbar();
    }

    @Override
    public File getCameraOutputFile() {
        File storageDir = null;
        File outputFile = null;
        try {
            storageDir = new File(Environment
                    .getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES), Config.APP_NAME);
            if (!storageDir.exists()) {
                if (!storageDir.mkdirs()) {
                    return null;
                }
            }
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());

            outputFile = new File(storageDir.getPath() +
                    File.separator + "IMG_" + timeStamp + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    @Override
    public LatLng getPicLocation(Uri imageUri) {
        try {
            ExifInterface exifInterface = new ExifInterface(uri2Path(imageUri));
            String lat = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String lon = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            if (lat != null && lon != null) {
                double latitude = LocationUtil.parseLatOrLon(lat);
                double longitude = LocationUtil.parseLatOrLon(lon);
                return new LatLng(latitude, longitude);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void getCurrentAddress(final OperateCallback<String> callback) {
        mMapSource.getCurrLocation(new OperateCallback<LatLng>() {
            @Override
            public void onOperate(LatLng latLng) {
                mMapSource.getAddressByLatLng(latLng, callback);
            }
        });
    }

    @Override
    public void getCurrentMemoryLocation() {

    }

    @Override
    public void checkRightUploadPic() {

    }

    @Override
    public void getAddress(LatLng latLng, OperateCallback<String> callback) {
        mMapSource.getAddressByLatLng(latLng, callback);
    }

    /**
     * 这里需要优化,现在的代码结构太丑了!!!
     *
     * @param path
     * @param memoryStream
     */
    @Override
    public void upload(final String path, final MemoryStream memoryStream) {
        mView.showProgressbar();
        mDataSource.uploadFile(path, s -> {
            if (memoryStream.isNew()) {
                mDataSource.addStreamToWarehouse(memoryStream, () -> {
                    final Memory memory = createNewMemory(s, memoryStream.getId());
                    mDataSource.addMemoryStream(memory, () -> mDataSource.addUserMemory(memory, new Action() {
                        @Override
                        public void onAction() {
                            if (memoryStream.getOwner().equals(memory.getCreater())) {
                                mUserSource.addOwnMemory(successAction, failAction);
                            } else {
                                mUserSource.addPipMemory(successAction, failAction);
                            }
                        }
                    }, failAction), failAction);
                }, failAction);
            } else {
                final Memory memory = createNewMemory(s, memoryStream.getId());
                mDataSource.addMemoryStream(memory, () -> mDataSource.addUserMemory(memory, new Action() {
                    @Override
                    public void onAction() {
                        if (memoryStream.getOwner().equals(memory.getCreater())) {
                            mUserSource.addOwnMemory(successAction, failAction);
                        } else {
                            mUserSource.addPipMemory(successAction, failAction);
                        }
                    }
                }, failAction), failAction);
            }
        }, failAction);
    }

    @Override
    public void uploadN(String path, final MemoryStream memoryStream, ProgressCallback callback) {
        mView.showProgressbar();
        mView.banAllActions();
        mDataSource.uploadFile(path, s -> {
            if (s == null) {
                failAction.onAction();
                return;
            }
            if (memoryStream.isNew()) {
                mDataSource.addStreamToWarehouse(memoryStream, () -> {
                    final Memory memory = createNewMemory(s, memoryStream.getId());
                    mDataSource.addMemoryStream(memory, () -> mDataSource.addUserMemory(memory, () -> {
                        if (memoryStream.getOwner().equals(memory.getCreater())) {
                            mUserSource.addOwnMemory(successAction, failAction);
                        } else {
                            mUserSource.addPipMemory(successAction, failAction);
                        }
                    }, failAction), failAction);
                }, failAction);
            } else {
                final Memory memory = createNewMemory(s, memoryStream.getId());
                mDataSource.addMemoryStream(memory, () -> mDataSource.addUserMemory(memory, () -> {
                    if (memoryStream.getOwner().equals(memory.getCreater())) {
                        mUserSource.addOwnMemory(successAction, failAction);
                    } else {
                        mUserSource.addPipMemory(successAction, failAction);
                    }
                }, failAction), failAction);
            }
        }, callback);
    }

    @Override
    public Memory createNewMemory(String path, String memoryStreamId) {
        Memory memory = new Memory();
        memory.setId(UUID.randomUUID().toString());
        memory.setPicture(path);
        memory.setType(Memory.MEMORY_TYPE_PIC);
        AVUser user = mUserSource.getCurrentUser();
        memory.setCreater(user.getUsername());
        memory.setAvatar(user.getString(Config.USER_AVATAR));
        memory.setPickname(user.getString(Config.USER_PICK_NAME));
        memory.setStreamId(memoryStreamId);
        memory.setDiscuss("");
        return memory;
    }

    @Override
    public String uri2Path(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = ((Activity) mDataSource.getContext()).managedQuery(uri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
