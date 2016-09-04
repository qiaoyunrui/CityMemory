package com.juhezi.citymemory.browse.upload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.avos.avoscloud.ProgressCallback;
import com.bumptech.glide.Glide;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.module.Memory;
import com.juhezi.citymemory.data.module.MemoryStream;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.util.Action;
import com.juhezi.citymemory.util.OperateCallback;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class UploadFragment extends Fragment implements UploadContract.View {

    private static final String TAG = "UploadFragment";

    private UploadContract.Presenter mPresenter;

    private View rootView;
    private ImageView mImgShow;
    private Button mBtnUpload;
    private Button mBtnUsePicLoc;
    private ProgressBar mPbUpload;
    private Button mBtnCamera;
    private Button mBtnGallery;
    private AlertDialog mDialog;
    private Uri mCameraUri;
    private Uri currentUri;

    private TextView mTvCurrAddress;
    private TextView mTvPicAddress;

    private Intent mCameraIntent;
    private Intent mGalleryIntent;
    private boolean imgLatlngEnable = false;    //图片经纬度是否可以使用
    private MemoryStream mMemoryStream;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.upload_frag, container, false);
        mImgShow = (ImageView) rootView.findViewById(R.id.up_upload_show);
        mBtnUpload = (Button) rootView.findViewById(R.id.btn_upload_upload);
        mBtnUsePicLoc = (Button) rootView.findViewById(R.id.btn_upload_use_pic_loc);
        mPbUpload = (ProgressBar) rootView.findViewById(R.id.pb_upload);
        mTvCurrAddress = (TextView) rootView.findViewById(R.id.tv_upload_curr_loc);
        mTvPicAddress = (TextView) rootView.findViewById(R.id.tv_upload_pic_loc);
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
        mMemoryStream = (MemoryStream) getArguments()
                .getSerializable(Config.MEMORY_STREAM_TAG);
        Log.i(TAG, "onStart: " + (mMemoryStream == null));
        mPresenter.getCurrentAddress(new OperateCallback<String>() {
            @Override
            public void onOperate(String s) {
                setCurrAddress(s);
            }
        });
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
        mBtnUsePicLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUri != null) {
                    //根据照片中所提取出的经纬度获取MemoryStream
                }
            }
        });
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUri != null) {
                    mPresenter.uploadN(currentUri.getPath(), mMemoryStream, new ProgressCallback() {
                        @Override
                        public void done(Integer integer) {
                            mPbUpload.setProgress(integer);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.start();
        }
        setCurrAddress("定位中");
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
        mPbUpload.setProgress(0);
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
        mGalleryIntent = new Intent(Intent.ACTION_PICK);
        mGalleryIntent.setType("image/*");
        startActivityForResult(mGalleryIntent, Config.GALLERY_CODE);
    }

    @Override
    public void setCurrAddress(String address) {
        mTvCurrAddress.setText("当前位置: " + address);
    }

    @Override
    public void setPicAddress(String address) {
        mTvPicAddress.setText("图片位置: " + address);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void banAllActions() {
        mBtnUpload.setVisibility(View.INVISIBLE);
        mBtnUsePicLoc.setVisibility(View.INVISIBLE);
        mImgShow.setClickable(false);
    }

    @Override
    public void allowAllActions() {
        mBtnUpload.setVisibility(View.VISIBLE);
        mBtnUsePicLoc.setVisibility(View.VISIBLE);
        mImgShow.setClickable(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Config.CAMERA_CODE:
                currentUri = mCameraUri;
                showImage(mCameraUri);
                break;
            case Config.GALLERY_CODE:
                if (data != null) {
                    currentUri = data.getData();
                    showImage(data.getData());
                    if (currentUri != null) {
                        LatLng latLng = mPresenter.getPicLocation(currentUri);
                        imgLatlngEnable = true;
                        if (latLng != null) {
                            mPresenter.getAddress(latLng
                                    , new OperateCallback<String>() {
                                        @Override
                                        public void onOperate(String s) {
                                            setPicAddress(s);
                                            Log.i(TAG, "onOperate: ");
                                        }
                                    });
                        } else {
                            imgLatlngEnable = false;
                        }
                    }
                }
                break;
        }

    }

}

