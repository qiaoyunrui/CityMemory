package com.juhezi.citymemory.browse.upload;

import android.graphics.Bitmap;
import android.net.Uri;

import com.amap.api.maps.model.LatLng;
import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.util.OperateCallback;

import java.io.File;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public interface UploadContract {

    interface Presenter extends BasePresenter {

        File getCameraOutputFile();

        /**
         * 获取照片中的地理位置
         *
         * @param imageUri
         */
        LatLng getPicLocation(Uri imageUri);

        /**
         * 获取当前地理位置
         */
        void getCurrentAddress(OperateCallback<String> callback);

        /**
         * 获取当前记忆流位置
         */
        void getCurrentMemoryLocation();

        /**
         * 检查是否可以上传图片
         */
        void checkRightUploadPic();

        void getAddress(LatLng latLng, OperateCallback<String> callback);

        void upload(String path);   //需要修改
    }

    interface View extends BaseView<Presenter> {

        void showProgressbar();

        void hideProgressbar();

        void showImage(Uri uri);

        void showDialog();

        void turn2CameraActivity();

        void turn2GalleryActivity();

        void setCurrAddress(String address);

        void setPicAddress(String address);

    }


}
