package com.juhezi.citymemory.browse.upload;

import android.graphics.Bitmap;
import android.net.Uri;

import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;

import java.io.File;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public interface UploadContract {

    interface Presenter extends BasePresenter {

        File getCameraOutputFile();

    }

    interface View extends BaseView<Presenter> {

        void showProgressbar();

        void hideProgressbar();

        void showImage(Uri uri);

        void showDialog();

        void turn2CameraActivity();

        void turn2GalleryActivity();
    }


}
