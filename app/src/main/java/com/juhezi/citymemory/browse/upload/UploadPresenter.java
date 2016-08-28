package com.juhezi.citymemory.browse.upload;

import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.avos.avoscloud.AVException;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.data.map.MapSource;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.Action;
import com.juhezi.citymemory.util.LocationUtil;
import com.juhezi.citymemory.util.OperateCallback;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class UploadPresenter implements UploadContract.Presenter {

    private static final String TAG = "UploadPresenter";

    private UploadContract.View mView;
    private MapSource mMapSource;
    private DataSource mDataSource;

    private String memoryStreamId;  //记忆流的唯一标识符
    private String isMemSteamNull;  //当前记忆流是否为空

    public UploadPresenter(UploadContract.View view
            , MapSource mapSource, DataSource dataSource) {
        mView = view;
        mMapSource = mapSource;
        mDataSource = dataSource;
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
            ExifInterface exifInterface = new ExifInterface(imageUri.getPath());
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

    @Override
    public void upload(String path) {
        mDataSource.uploadFile(path, new OperateCallback<String>() {
            @Override
            public void onOperate(String s) {

            }
        }, new Action() {
            @Override
            public void onAction() {
                
            }
        });
    }

}
