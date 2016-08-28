package com.juhezi.citymemory.browse.upload;

import android.os.Environment;
import android.util.Log;

import com.juhezi.citymemory.other.Config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class UploadPresenter implements UploadContract.Presenter {

    private static final String TAG = "UploadPresenter";

    private UploadContract.View mView;

    public UploadPresenter(UploadContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.hideProgressbar();
    }

    @Override
    public File getCameraOutputFile() {
        File storageDir = null;
        File outputFile = null;
        try {
            storageDir = new File(Environment
                    .getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES), Config.APP_NAME);
            if (!storageDir.exists()) {
                if (!storageDir.mkdirs()) {
                    return null;
                }
            }
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());

            outputFile = new File(storageDir.getPath() +
                    File.separator + "IMG_" + timeStamp + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile;
    }
}
