package com.juhezi.citymemory;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.juhezi.citymemory.other.Config;


/**
 * Created by qiaoyunrui on 16-8-24.
 */
public class CityApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, Config.APP_ID, Config.APP_KEY);
    }
}
