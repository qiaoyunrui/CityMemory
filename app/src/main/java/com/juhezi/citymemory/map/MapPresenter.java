package com.juhezi.citymemory.map;

import android.util.Log;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public class MapPresenter implements MapContract.Presenter {

    private static final String TAG = "MapPresenter";

    private MapContract.View mView;

    public MapPresenter(MapContract.View mFragment) {
        mView = mFragment;
        mFragment.setPresenter(this);
    }

    @Override
    public void start() {
    }
}
