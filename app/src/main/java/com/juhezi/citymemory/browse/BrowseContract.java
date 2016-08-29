package com.juhezi.citymemory.browse;

import com.amap.api.maps.model.LatLng;
import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.util.OperateCallback;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public interface BrowseContract {

    interface Presenter extends BasePresenter {

        void getStreamInfo(LatLng latlng, OperateCallback<MemoryStream> callback);

    }

    interface View extends BaseView<BrowseContract.Presenter> {

        void showEmptyView();

        void hideEmptyView();

        void showProgressbar();

        void hideProgressbar();

    }

}
