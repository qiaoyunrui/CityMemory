package com.juhezi.citymemory.data.data;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.SaveCallback;
import com.juhezi.citymemory.util.Action;
import com.juhezi.citymemory.util.OperateCallback;

import java.io.FileNotFoundException;

/**
 * Created by qiaoyunrui on 16-8-28.
 */
public class DataResponse implements DataSource {

    private static final String TAG = "DataResponse";

    private Context mContext;

    private static DataResponse sDataResponse;

    private DataResponse(Context context) {
        mContext = context;
    }

    public static DataResponse getInstance(Context context) {
        if (sDataResponse == null) {
            sDataResponse = new DataResponse(context);
        }
        return sDataResponse;
    }

    @Override
    public void uploadFile(final String memoryPath, final OperateCallback<String> operateCallback, final Action action) {
        try {
            final String name = System.currentTimeMillis() + "_IMG";
            final AVFile memory = AVFile.withAbsoluteLocalPath(name,
                    memoryPath);
            memory.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        operateCallback.onOperate(memory.getUrl());
                    } else {
                        action.onAction();
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            action.onAction();
        }


    }
}
