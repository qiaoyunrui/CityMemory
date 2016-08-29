package com.juhezi.citymemory.browse;

import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.util.OperateCallback;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class BrowsePresenter implements BrowseContract.Presenter {

    private static final String TAG = "BrowsePresenter";

    private BrowseContract.View mView;
    private DataSource mDataSource;

    public BrowsePresenter(BrowseContract.View view, DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
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
}
