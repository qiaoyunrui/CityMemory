package com.juhezi.citymemory.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.juhezi.citymemory.R;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public class MapFragment extends Fragment implements MapContract.View {

    private static final String TAG = "MapFragment";
    private MapContract.Presenter mPresenter;
    private View rootView;
    private MapView mMV_map;
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mLocationClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.map_frag, container, false);
        mMV_map = (MapView) rootView.findViewById(R.id.mv_map);
        mMV_map.onCreate(savedInstanceState);
        initMap();
        return rootView;
    }

    private void initMap() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMV_map.onResume();
        mPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMV_map.onPause();
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        mMV_map.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMV_map.onSaveInstanceState(outState);
    }
}
