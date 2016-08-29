package com.juhezi.citymemory.browse;

import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.data.map.MapSource;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.data.user.UserSource;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.OperateCallback;

import java.util.UUID;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class BrowsePresenter implements BrowseContract.Presenter {

    private static final String TAG = "BrowsePresenter";

    private BrowseContract.View mView;
    private DataSource mDataSource;
    private UserSource mUserSource;
    private MapSource mMapSource;

    public BrowsePresenter(BrowseContract.View view, DataSource dataSource
            , UserSource userSource, MapSource mapSource) {
        mView = view;
        mDataSource = dataSource;
        mUserSource = userSource;
        mMapSource = mapSource;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showProgressbar();
    }

    @Override
    public void getStreamInfo(LatLng latlng, OperateCallback<MemoryStream> callback) {
        mDataSource.getMemStream(latlng, callback);
    }

    @Override
    public AVUser getCurrentUser() {
        return mUserSource.getCurrentUser();
    }

    @Override
    public void getAddressByLatLng(LatLng latlng, OperateCallback<String> callback) {
        mMapSource.getAddressByLatLng(latlng, callback);
    }

    @Override
    public MemoryStream createNewMemory(LatLng latLng) {
        MemoryStream memoryStream = new MemoryStream();
        memoryStream.setId(UUID.randomUUID().toString());   //创建唯一ID
        memoryStream.setLat(latLng.latitude);
        memoryStream.setLon(latLng.longitude);
        memoryStream.setMemoryCount(0);
        memoryStream.setDiscussCount(0);
        memoryStream.setOwner(getCurrentUser().getUsername());
        return memoryStream;
    }
}
