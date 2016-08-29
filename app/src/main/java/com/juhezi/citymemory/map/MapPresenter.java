package com.juhezi.citymemory.map;

import android.content.Context;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.data.data.DataSource;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.util.OperateCallback;

import rx.Observable;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public class MapPresenter implements MapContract.Presenter {

    private static final String TAG = "MapPresenter";

    private MapContract.View mView;
    private GeocodeSearch mGeocodeSearch;
    private Context mContext;
    private DataSource mDataSource;

    public MapPresenter(MapContract.View mFragment, Context context, DataSource dataSource) {
        mView = mFragment;
        mContext = context;
        mDataSource = dataSource;
        mFragment.setPresenter(this);
    }

    @Override
    public void start() {
        mView.initUser(getCurrUserData());
    }

    @Override
    public void getAddress(LatLng latlng,
                           final OperateCallback<Observable<RegeocodeResult>> callback) {
        if (mGeocodeSearch == null) {
            mGeocodeSearch = new GeocodeSearch(mContext);
        }
        mGeocodeSearch.setOnGeocodeSearchListener(
                new GeocodeSearch.OnGeocodeSearchListener() {
                    @Override
                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                        Observable<RegeocodeResult> observable =
                                Observable.just(regeocodeResult);
                        callback.onOperate(observable);
                    }

                    @Override
                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                    }
                });
        RegeocodeQuery query = new RegeocodeQuery(
                new LatLonPoint(
                        latlng.latitude, latlng.longitude),
                100, GeocodeSearch.AMAP);
        mGeocodeSearch.getFromLocationAsyn(query);
    }

    @Override
    public AVUser getCurrUserData() {
        return AVUser.getCurrentUser();
    }

    @Override
    public void getStreamInfo(int pointX, int pointY
            , OperateCallback<MemoryStream> callback) {
        mDataSource.getMemStream(mView.getPointAddress(pointX, pointY)
                , callback);
    }

}
