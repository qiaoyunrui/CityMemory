package com.juhezi.citymemory.map;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.util.OperateCallback;

import rx.Observable;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public interface MapContract {

    interface Presenter extends BasePresenter {

        void getAddress(LatLng latlng,
                        OperateCallback<Observable<RegeocodeResult>> callback);

        AVUser getCurrUserData();

    }

    interface View extends BaseView<Presenter> {
        void locate();

        void locateRemote(double latitude, double longitude);

        void setBoardContent(String address);

        void initUser(AVUser user);
    }

}
