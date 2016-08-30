package com.juhezi.citymemory.browse.view;

import android.graphics.Bitmap;

import com.juhezi.citymemory.BasePresenter;
import com.juhezi.citymemory.BaseView;
import com.juhezi.citymemory.util.Action;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public interface ViewContract {

    interface Presenter extends BasePresenter {

        void saveImage(Bitmap bitmap, Action success, Action fail);

    }

    interface View extends BaseView<Presenter> {

        void showProgressbar();

        void hideProgressbar();

        void showImage(String url);

        void showToast(String message);

    }

}
