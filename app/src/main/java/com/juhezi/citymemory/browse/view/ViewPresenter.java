package com.juhezi.citymemory.browse.view;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.Action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class ViewPresenter implements ViewContract.Presenter {

    private ViewContract.View mView;

    private static final String TAG = "ViewPresenter";

    private File dir = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), Config.APP_NAME);

    private File outputFile = null;

    public ViewPresenter(ViewContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.hideProgressbar();
    }

    @Override
    public void saveImage(Bitmap bitmap, Action success, Action fail) {
        Log.i(TAG, "saveImage: start");
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                fail.onAction();
                return;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());

        outputFile = new File(dir.getPath() +
                File.separator + "IMG_" + timeStamp + ".jpg");

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            success.onAction();
            Log.i(TAG, "saveImage: ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail.onAction();
        } catch (IOException e) {
            e.printStackTrace();
            fail.onAction();
        }
    }
}
