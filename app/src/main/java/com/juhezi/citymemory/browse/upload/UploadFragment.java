package com.juhezi.citymemory.browse.upload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.other.Config;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class UploadFragment extends Fragment implements UploadContract.View {

    private static final String TAG = "UploadFragment";

    private UploadContract.Presenter mPresenter;

    private View rootView;
    private ImageView mImgShow;
    private TextView mTvUpload;
    private TextView mTvUsePicLoc;
    private ProgressBar mPbUpload;
    private Button mBtnCamera;
    private Button mBtnGallery;
    private AlertDialog mDialog;
    private Uri mCameraUri;

    private Intent mCameraIntent;
    private Intent mGalleryIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.upload_frag, container, false);
        mImgShow = (ImageView) rootView.findViewById(R.id.up_upload_show);
        mTvUpload = (TextView) rootView.findViewById(R.id.tv_upload_upload);
        mTvUsePicLoc = (TextView) rootView.findViewById(R.id.tv_upload_use_pic_loc);
        mPbUpload = (ProgressBar) rootView.findViewById(R.id.pb_upload);
        initEvent();
        initCamera();
        initGallery();
        mGalleryIntent = new Intent();
        return rootView;
    }

    private void initCamera() {
        mCameraIntent = new Intent();
        mCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    private void initGallery() {
        mGalleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    private void initDialog() {
        View dialogView = LayoutInflater
                .from(getContext())
                .inflate(R.layout.dialog_upload, null);
        mBtnGallery = (Button) dialogView.findViewById(R.id.btn_dialog_upload_gallery);
        mBtnCamera = (Button) dialogView.findViewById(R.id.btn_dialog_upload_camera);
        mDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setTitle("获取图片的方式")
                .create();
        mBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.hide();
                turn2CameraActivity();
            }
        });
        mBtnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.hide();
                turn2GalleryActivity();
            }
        });
    }

    private void initEvent() {
        mImgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setPresenter(UploadContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressbar() {
        mPbUpload.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        mPbUpload.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showImage(Uri uri) {
        if (uri != null) {
            Glide.with(getContext())
                    .load(uri)
                    .error(R.drawable.city)
                    .crossFade()
                    .into(mImgShow);
        }
    }

    @Override
    public void showDialog() {
        mDialog.show();
    }

    @Override
    public void turn2CameraActivity() {
        mCameraUri = Uri.fromFile(mPresenter.getCameraOutputFile());
        mCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraUri);
        startActivityForResult(mCameraIntent, Config.CAMERA_CODE);
    }

    @Override
    public void turn2GalleryActivity() {
        startActivityForResult(mGalleryIntent, Config.GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Config.CAMERA_CODE:
                showImage(mCameraUri);
                break;
            case Config.GALLERY_CODE:
                break;
        }
    }

}

