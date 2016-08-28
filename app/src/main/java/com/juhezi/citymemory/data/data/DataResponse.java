package com.juhezi.citymemory.data.data;

import android.content.Context;
import android.provider.Settings;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
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
    public void uploadMemory(String memoryPath, final OperateCallback<AVException> callback) {
        try {
            AVFile memory = AVFile.withAbsoluteLocalPath(System.currentTimeMillis() + "_IMG.jpg",
                    memoryPath);
            memory.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    callback.onOperate(e);
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            callback.onOperate(null);   //找不到文件
        }


    }
}
