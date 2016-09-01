package com.juhezi.citymemory.map;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.browse.BrowseActivity;
import com.juhezi.citymemory.data.module.Location;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.search.SearchActivity;
import com.juhezi.citymemory.setting.SettingActivity;
import com.juhezi.citymemory.sign.SignActivity;
import com.juhezi.citymemory.util.OperateCallback;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

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
    private RelativeLayout mRlView;
    private TextView mTvBoard;

    private String currentAddress;
    private double currentLatitude;
    private double currentLongitude;
    private float mapScale = 17f;
    private Marker mMarker;
    private Marker mRemoteMarker;
    private Intent searchIntent;
    private String currentCityCode;
    private Projection mProjection;
    private int mapHeight;
    private int mapWidth;
    private Intent signIntent;
    private Intent settingIntent;
    private Intent browseIntent;

    private View mVMarker;
    private ImageView mImgMarker;

    private ObjectAnimator fabAnim;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.map_frag, container, false);
        mMV_map = (MapView) rootView.findViewById(R.id.mv_map);
        mFabLocate = (FloatingActionButton) rootView.findViewById(R.id.fab_locate);
        mRlSearch = (RelativeLayout) rootView.findViewById(R.id.rl_search);
        mRlView = (RelativeLayout) rootView.findViewById(R.id.rl_view);
        mTvBoard = (TextView) rootView.findViewById(R.id.tv_board);
        mMV_map.onCreate(savedInstanceState);
        searchIntent = new Intent(getContext(), SearchActivity.class);
        initMap();
        initEvent();
        requestPrimission();
        configFabAnim();
        return rootView;
    }

    private void initMap() {
        mAMap = mMV_map.getMap();
        mAMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        mAMap.getUiSettings().setScaleControlsEnabled(true);
        mProjection = mAMap.getProjection();
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
                    Log.d(TAG, "定位失败: " +
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

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getAllMemoryStream(new OperateCallback<Observable<List<MemoryStream>>>() {
            @Override
            public void onOperate(Observable<List<MemoryStream>> list) {
                if (list != null) {
                    list.subscribe(new Observer<List<MemoryStream>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<MemoryStream> memoryStreams) {
                            showAllMemoryStream(memoryStreams);
                        }
                    });
                } else {
                    showAllMemoryStream(null);
                }

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initEvent() {
        mFabLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.startLocation();
                if (!fabAnim.isRunning()) {
                    fabAnim.start();
                }
            }
        });
        mRlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn2SearchAct();
            }
        });
        mAMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                mRlSearch.setVisibility(View.GONE);
                mRlView.setVisibility(View.GONE);
                setBoardContent("移动中");
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                mRlSearch.setVisibility(View.VISIBLE);
                mRlView.setVisibility(View.VISIBLE);
                mPresenter.getAddress(getPointAddress(mapWidth / 2, mapHeight / 2),
                        new OperateCallback<Observable<RegeocodeResult>>() {
                            @Override
                            public void onOperate(Observable<RegeocodeResult> regeocodeResultObservable) {
                                regeocodeResultObservable
                                        .map(new Func1<RegeocodeResult, RegeocodeAddress>() {
                                            @Override
                                            public RegeocodeAddress call(RegeocodeResult regeocodeResult) {
                                                return regeocodeResult.getRegeocodeAddress();
                                            }
                                        }).subscribe(new Observer<RegeocodeAddress>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(RegeocodeAddress regeocodeAddress) {
                                        setBoardContent(regeocodeAddress.getFormatAddress());
                                    }
                                });
                            }
                        });
            }
        });
        mMV_map.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mapHeight = mMV_map.getHeight();
                mapWidth = mMV_map.getWidth();
            }
        });
        mRlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn2BrowseActivity(getPointAddress(
                        mapWidth / 2, mapHeight / 2));
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
        mVMarker = LayoutInflater.from(getContext())
                .inflate(R.layout.marker_view, null);
        mImgMarker = (ImageView) mVMarker.findViewById(R.id.img_marker);
        mImgMarker.setImageResource(R.drawable.gpsx);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(currentAddress)
                .snippet("当前所在的位置")
                .icon(BitmapDescriptorFactory.fromView(mVMarker)
                ).anchor(0.5f, 0.5f);
        if (mMarker != null) {
            mMarker.destroy();
        }
        mMarker = mAMap.addMarker(markerOptions);
    }

    /**
     * 加载远程地址
     *
     * @param latitude
     * @param longitude
     */
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

    @Override
    public void setBoardContent(String address) {
        mTvBoard.setText(address);
    }

    private void turn2SearchAct() {
        searchIntent.putExtra(Config.CITY_CODE, currentCityCode);
        startActivityForResult(searchIntent, Config.SEARCH_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Config.SIGN_CODE:
                initUser(mPresenter.getCurrUserData());
                break;
            case Config.SEARCH_CODE:
                if (data != null) {
                    Location location = (Location) data.getSerializableExtra(Config.LOCATION_KEY);
                    if (location != null) {
                        locateRemote(location.getLatitude(), location.getLongitude());
                    }
                }
                break;
            case Config.SETTING_CODE:
                if (resultCode == Config.SETTING_CODE) {
                    ((MapActivity) getActivity()).cleanUserInfo();
                }
                break;
            case Config.BROWSE_CODE:
                break;
        }


    }


    @Override
    public void initUser(AVUser user) {
        if (user != null) {
            ((MapActivity) getActivity()).showUserInfo(user);
        }
    }

    /**
     * 获取屏幕坐标对应的地图经纬度
     *
     * @param x
     * @param y
     * @return
     */
    public LatLng getPointAddress(int x, int y) {
        LatLng latLng = mProjection.fromScreenLocation(new Point(x, y));
        return latLng;
    }

    @Override
    public void showAllMemoryStream(List<MemoryStream> list) {
        if (list == null) {
            return;
        }
        for (MemoryStream memoryStream : list) {
            mVMarker = LayoutInflater.from(getContext())
                    .inflate(R.layout.marker_view, null);
            mImgMarker = (ImageView) mVMarker.findViewById(R.id.img_marker);
            mImgMarker.setImageResource(R.drawable.memory_stream);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(memoryStream.getLat(), memoryStream.getLon()))
                    .icon(BitmapDescriptorFactory
                            .fromView(mVMarker)
                    ).anchor(0.5f, 0.5f);
            mAMap.addMarker(markerOptions);
        }
    }

    public void turn2SignActivity() {
        if (signIntent == null) {
            signIntent = new Intent(getContext(), SignActivity.class);
        }
        startActivityForResult(signIntent, Config.SIGN_CODE);
    }

    public void turn2SettingActivity() {
        if (settingIntent == null) {
            settingIntent = new Intent(getContext(), SettingActivity.class);
        }
        startActivityForResult(settingIntent, Config.SETTING_CODE);
    }

    public void turn2BrowseActivity(LatLng latLng) {
        if (browseIntent == null) {
            browseIntent = new Intent(getContext(), BrowseActivity.class);
        }
        browseIntent.putExtra(Config.MEMORY_STREAM_LATLNG, latLng);
        startActivityForResult(browseIntent, Config.BROWSE_CODE);
    }

    private void requestPrimission() {
        if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions.requestPermissions(getContext(), "Camera"
                    , 0x123,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void configFabAnim() {
        fabAnim = ObjectAnimator.ofFloat(mFabLocate, "rotation", 0f, 180f);
        fabAnim.setInterpolator(new AccelerateDecelerateInterpolator());
    }

}
