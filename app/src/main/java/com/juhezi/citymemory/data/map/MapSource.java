package com.juhezi.citymemory.data.map;

import com.amap.api.maps.model.LatLng;
import com.juhezi.citymemory.util.OperateCallback;

/**
 * Created by qiaoyunrui on 16-8-28.
 */
public interface MapSource {

    void getAddressByLatLng(LatLng latLng, OperateCallback<String> callback);

    void getCurrLocation(OperateCallback<LatLng> callback);

}
