package com.juhezi.citymemory.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.Location;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.search.SearchActivity;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public class MapFragment extends Fragment implements MapContract.View {

    private static final String TAG = "MapFragment";
    private MapContract.Presenter mPresenter;
    private View rootView;
    private MapView mMV_map;
    private AMap mAMap;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    private FloatingActionButton mFabLocate;
    private RelativeLayout mRlSearch;

    private String currentAddress;
    private double currentLatitude;
    private double currentLongitude;
    private float mapScale = 17f;
    private Marker mMarker;
    private Marker mRemoteMarker;
    private Intent searchIntent;
    private String currentCityCode;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.map_frag, container, false);
        mMV_map = (MapView) rootView.findViewById(R.id.mv_map);
        mFabLocate = (FloatingActionButton) rootView.findViewById(R.id.fab_locate);
        mRlSearch = (RelativeLayout) rootView.findViewById(R.id.rl_search);
        mMV_map.onCreate(savedInstanceState);
        searchIntent = new Intent(getContext(), SearchActivity.class);
        initMap();
        initEvent();
        return rootView;
    }

    private void initMap() {
        mAMap = mMV_map.getMap();
        mAMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        mAMap.getUiSettings().setScaleControlsEnabled(true);
        mLocationClient = new AMapLocationClient(getContext());
        mLocationOption = new AMapLocationClientOption();
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation.getErrorCode() == 0) {
                    currentAddress = aMapLocation.getAddress();
                    currentLatitude = aMapLocation.getLatitude();
                    currentLongitude = aMapLocation.getLongitude();
                    currentCityCode = aMapLocation.getCityCode();
                    locate();
                } else {
                    Log.d(TAG, "onLocationChanged: " +
                            aMapLocation.getErrorCode() +
                            " ," + aMapLocation.getErrorInfo());
                }
            }
        });
        mLocationOption.setOnceLocation(true);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    private void initEvent() {
        mFabLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.startLocation();
            }
        });
        mRlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn2SearchAct();
            }
        });
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
        mLocationClient.stopLocation();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMV_map.onSaveInstanceState(outState);
    }

    @Override
    public void locate() {
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        mAMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(mapScale));
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(currentAddress)
                .snippet("当前所在的位置")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.locate_sign)
                );
        if (mMarker != null) {
            mMarker.destroy();
        }
        mMarker = mAMap.addMarker(markerOptions);
    }

    @Override
    public void locateRemote(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        mAMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(mapScale));
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng);
        if (mRemoteMarker != null) {
            mRemoteMarker.destroy();
        }
        mRemoteMarker = mAMap.addMarker(markerOptions);
    }

    private void turn2SearchAct() {
        searchIntent.putExtra(Config.CITY_CODE, currentCityCode);
        startActivityForResult(searchIntent, Config.SEARCH_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.SEARCH_CODE) {
            Location location = (Location) data.getSerializableExtra(Config.LOCATION_KEY);
            if (location != null) {
                locateRemote(location.getLatitude(), location.getLongitude());
            }
        }
    }


}
