package com.juhezi.citymemory.browse;

import com.amap.api.maps.model.LatLng;
import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.util.Action;
import com.juhezi.citymemory.util.OperateCallback;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public interface BrowseContract {

    interface Presenter extends BasePresenter {

        void getStreamInfo(LatLng latlng, OperateCallback<MemoryStream> callback);

        AVUser getCurrentUser();

        void getAddressByLatLng(LatLng latlng
                , OperateCallback<String> callback);

        MemoryStream createNewMemory(LatLng latLng);

    }

    interface View extends BaseView<BrowseContract.Presenter> {

        void showEmptyView();

        void hideEmptyView();

        void showProgressbar();

        void hideProgressbar();

        void showSnackBar(String message, String actionName, Action action);

        void turn2SignActivity();

        void showAddress(String address);

        void showCreater(String creater);

        void unenableTIL();

        void unenableSendButton();

        void enableSendButton();

    }

}
