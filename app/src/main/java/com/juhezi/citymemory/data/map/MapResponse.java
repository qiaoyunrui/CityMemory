package com.juhezi.citymemory.data.map;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.juhezi.citymemory.util.OperateCallback;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by qiaoyunrui on 16-8-28.
 */
public class MapResponse implements MapSource {

    private static final String TAG = "MapResponse";

    private static MapResponse sMapResponse;

    private GeocodeSearch mGeocodeSearch;

    private AMapLocationClient mClient;
    private AMapLocationClientOption mOption;

    private Context mContext;

    private MapResponse(Context context) {
        mContext = context;
    }

    public static MapResponse getInstance(Context context) {
        if (sMapResponse == null) {
            sMapResponse = new MapResponse(context);
        }
        return sMapResponse;
    }

    @Override
    public void getAddressByLatLng(LatLng latlng, final OperateCallback<String> callback) {
        if (mGeocodeSearch == null) {
            mGeocodeSearch = new GeocodeSearch(mContext);
        }
        mGeocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                Observable.just(regeocodeResult)
                        .map(new Func1<RegeocodeResult, RegeocodeAddress>() {
                            @Override
                            public RegeocodeAddress call(RegeocodeResult regeocodeResult) {
                                return regeocodeResult.getRegeocodeAddress();
                            }
                        })
                        .subscribe(new Observer<RegeocodeAddress>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(RegeocodeAddress regeocodeAddress) {
                                callback.onOperate(regeocodeAddress.getFormatAddress());
                            }
                        })
                        .unsubscribe();
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(
                latlng.latitude, latlng.longitude),
                100, GeocodeSearch.AMAP);
        mGeocodeSearch.getFromLocationAsyn(query);
    }

    @Override
    public void getCurrLocation(final OperateCallback<LatLng> callback) {
        if (mClient == null) {
            mClient = new AMapLocationClient(mContext);
        }
        mOption = new AMapLocationClientOption();
        mClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation.getErrorCode() == 0) {
                    double lon = aMapLocation.getLongitude();
                    double lat = aMapLocation.getLatitude();
                    callback.onOperate(new LatLng(lat, lon));
                } else {
                    Log.i(TAG, "定位失败: " + aMapLocation.getErrorCode() +
                            " ," + aMapLocation.getErrorInfo());
                }
            }
        });
        mOption.setOnceLocation(true);
        mOption.setLocationMode(AMapLocationClientOption
                .AMapLocationMode.Hight_Accuracy);
        mClient.setLocationOption(mOption);
        mClient.startLocation();
    }


}
